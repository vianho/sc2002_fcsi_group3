package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.dto.ProjectFlatRow;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.ApplicationService;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.service.result.ActionResult;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.util.ProjectViewUtils;
import sc2002.fcsi.grp3.util.Validator;
import sc2002.fcsi.grp3.view.ApplicationView;
import sc2002.fcsi.grp3.view.ProjectView;
import sc2002.fcsi.grp3.view.SharedView;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing user applications for projects.
 * Handles operations such as applying for a project, viewing applications,
 * and withdrawing applications.
 */
public class ApplicationController implements IBaseController {

    private final SharedView sharedView;
    private final ApplicationView applicationView;
    private final ProjectView projectView;
    private final ProjectService projectService;
    private final ApplicationService applicationService;

    /**
     * Constructs an ApplicationController with the required dependencies.
     *
     * @param sharedView         the shared view for displaying common UI elements
     * @param applicationView    the view for displaying application-related UI elements
     * @param projectView        the view for displaying project-related UI elements
     * @param projectService     the service for managing project-related operations
     * @param applicationService the service for managing application-related operations
     */
    public ApplicationController(
            SharedView sharedView,
            ApplicationView applicationView,
            ProjectView projectView,
            ProjectService projectService,
            ApplicationService applicationService
    ) {
        this.sharedView = sharedView;
        this.applicationView = applicationView;
        this.projectView = projectView;
        this.projectService = projectService;
        this.applicationService = applicationService;
    }

    /**
     * Starts the application controller, displaying a menu for the user to
     * perform various application-related actions.
     */
    public void start() {
        int choice;
        String[] options = {
                "Apply for a Project",
                "View Applications",
                "Withdraw Application",
                "Back"};
        User user = Session.getCurrentUser();

        do {
            choice = sharedView.showMenuAndGetChoice("Account Settings", options);
            switch (choice) {
                case 1 -> applyForProject(user);
                case 2 -> viewApplications(user);
                case 3 -> withdrawApplication(user);
                case 4 -> {}
                default -> sharedView.showInvalidChoice();
            }
        } while (choice != options.length);
    }

    /**
     * Retrieves the list of projects visible to the user.
     * If the list is not cached in the session, it fetches the projects
     * from the project service and caches them.
     *
     * @param user the current user
     * @return a list of visible projects
     */
    private List<Project> getVisibleProjects(User user) {
        List<Project> projects = Session.getList("visibleProjects", Project.class);

        if (projects == null) {
            projects = projectService.getVisibleProjects(user);
            Session.put("visibleProjects", projects);
        }

        if (projects.isEmpty()) {
            sharedView.showMessage("No projects found.");
        }
        return projects;
    }

    /**
     * Prompts the user to enter a project ID and validates the input.
     *
     * @param user the current user
     * @return an Optional containing the project ID if valid, or empty if cancelled
     */
    private Optional<Integer> getProjectId(User user) {
        // prompt for project id
        boolean isValidProjectId;
        String projectId;
        do {
            projectId = applicationView.promptProjectId().trim();
            if (projectId.isEmpty()) {
                return Optional.empty();
            }
            isValidProjectId = Validator.isNumeric(projectId);
            sharedView.showMessage(String.valueOf(isValidProjectId));
            if (!isValidProjectId) {
                sharedView.showError("Invalid project ID.");
            }
        } while (!isValidProjectId);

        return Optional.of(Integer.parseInt(projectId));
    }

    /**
     * Allows the user to apply for a project. Displays eligible projects,
     * prompts the user to select a project and flat type, and submits the application.
     *
     * @param user the current user
     */
    private void applyForProject(User user) {
        // check if user has an active application
        Application activeApplication = getActiveApplication(user);
        if (activeApplication != null) {
            sharedView.showError("You can only apply for one project at a time.");
            sharedView.showMessage("Please withdraw your active application before applying for a new project.");
            sharedView.showMessage("Your active application:");
            applicationView.showApplications(List.of(activeApplication));
            return;
        }

        // Show eligible projects
        sharedView.showTitle("Eligible Projects");
        List<Project> eligibleProjects = getVisibleProjects(user);
        List<ProjectFlatRow> rows = ProjectViewUtils.flattenEligibleFlats(eligibleProjects, user);
        projectView.showProjectFlats(rows);

        // prompt and select project
        Optional<Integer> projectId = getProjectId(user);
        if (projectId.isEmpty()) {
            sharedView.showMessage("Application cancelled.");
            return;
        }

        Optional<Project> project = projectService.getProjectById(projectId.get());
        if (project.isEmpty()) {
            sharedView.showMessage("Project not found.");
            return;
        }

        // list flats that are available for user under the project
        List<Flat> availableFlats = projectService.getAvailableFlats(user, project.get());

        if (availableFlats.isEmpty()) {
            sharedView.showMessage("No available flats.");
            return;
        }

        applicationView.showFlatOptions(availableFlats);

        int chosenFlatType = applicationView.getFlatChoice();
        if (chosenFlatType == 0 || chosenFlatType > availableFlats.size()) {
            return;
        }

        ActionResult<Application> result = applicationService.apply(user, project.get(), availableFlats.get(chosenFlatType-1).getType());
        if (result.isSuccess()) {
            sharedView.showMessage(result.getMessage());
        } else {
            sharedView.showError(result.getMessage());
        }
    }

    /**
     * Displays the list of applications submitted by the user.
     *
     * @param user the current user
     */
    private void viewApplications(User user) {
        sharedView.showTitle("Applications");
        applicationView.showApplications(applicationService.getApplicationsFor(user));
        sharedView.pressEnterToContinue();
    }

    /**
     * Retrieves the active application for the user from the session or
     * fetches it from the application service if not cached.
     *
     * @param user the current user
     * @return the active application, or null if none exists
     */
    private Application getActiveApplication(User user) {
        Application application = Session.get("activeApplication", Application.class);
        if (application == null) {
            Optional<Application> activeApplication = applicationService.getActiveApplicationFor(user);
            if (activeApplication.isPresent()) {
                Session.put("activeApplication", activeApplication);
                return activeApplication.get();
            } else {
                return null;
            }
        }
        return application;
    }

    /**
     * Allows the user to withdraw their active application.
     * Prompts for confirmation and performs the withdrawal if confirmed.
     *
     * @param user the current user
     */
    private void withdrawApplication(User user) {
        // check if user has an active application
        Application activeApplication = getActiveApplication(user);
        if (activeApplication == null) {
            sharedView.showError("You do not have any active application.");
            return;
        }
        sharedView.showMessage("This is your active application: ");
        applicationView.showApplications(List.of(activeApplication));
        boolean confirm = sharedView.getConfirmation("Are you sure you want to withdraw your application? ");
        if (confirm) {
            ActionResult<Application> result = applicationService.withdraw(user, activeApplication);
            if (result.isSuccess()) {
                sharedView.showMessage(result.getMessage());
                Session.remove("activeApplication");
                return;
            }
            sharedView.showError(result.getMessage());
            return;
        }
        sharedView.showMessage("Withdrawal cancelled.");
    }
}
