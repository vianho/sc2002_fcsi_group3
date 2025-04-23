package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.dto.ProjectFlatRow;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.ApplicationService;
import sc2002.fcsi.grp3.service.EnquiryService;
import sc2002.fcsi.grp3.service.AuthService;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.service.result.ActionResult;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.util.ProjectViewUtils;
import sc2002.fcsi.grp3.util.Validator;
import sc2002.fcsi.grp3.view.ApplicantViews;

import java.util.*;

public class ApplicantController implements IBaseController {
    private final ApplicantViews views;

    private final AuthService authService;
    private final ProjectService projectService;
    private final ApplicationService applicationService;
    private final EnquiryService enquiryService;

    public ApplicantController(
            ApplicantViews views,
            AuthService authService,
            ProjectService projectService,
            ApplicationService applicationService,
            EnquiryService enquiryService
    ) {
        this.views = views;
        this.authService = authService;
        this.projectService = projectService;
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
    }

    public void start() {
        int choice;
        String[] options = {
                "View Available Projects",
                "Apply for a Project",
                "View Applications",
                "Withdraw Application",
                "View My Enquiries",
                "My Account",
                "Logout"
        };
        User user = Session.getCurrentUser();
        do {
            choice = views.sharedView().showMenuAndGetChoice("Applicant Menu", options);
            switch (choice) {
                case 1 -> viewProjects(user);
                case 2 -> applyForProject(user);
                case 3 -> viewApplications(user);
                case 4 -> withdrawApplication(user);
                case 5 -> viewEnquiries(user);
                case 6 -> accountSettings();
                case 7 -> logout();
                default -> views.sharedView().showInvalidChoice();
            }
        } while (choice != options.length);
    }

    private List<Project> getVisibleProjects(User user) {
        List<Project> projects = Session.getList("visibleProjects", Project.class);

        if (projects == null) {
            projects = projectService.getVisibleProjects(user);
            Session.put("visibleProjects", projects);
        }

        if (projects.isEmpty()) {
            views.sharedView().showMessage("No projects found.");
        }
        return projects;
    }

    private void viewProjects(User user) {
        views.sharedView().showTitle("Projects");
        List<Project> projects = getVisibleProjects(user);
        List<ProjectFlatRow> rows = ProjectViewUtils.flattenEligibleFlats(projects, user);
        views.projectView().showProjectFlats(rows);
        views.sharedView().pressEnterToContinue();
    }

    private void logout() {
        Session.logout();
        views.sharedView().showMessage("Logging out...");
    }

    private Optional<Integer> getProjectId(User user) {
        // prompt for project id
        boolean isValidProjectId;
        String projectId;
        do {
            projectId = views.applicationView().promptProjectId().trim();
            if (projectId.isEmpty()) {
                return Optional.empty();
            }
            isValidProjectId = Validator.isNumeric(projectId);
            views.sharedView().showMessage(String.valueOf(isValidProjectId));
            if (!isValidProjectId) {
                views.sharedView().showError("Invalid project ID.");
            }
        } while (!isValidProjectId);

        return Optional.of(Integer.parseInt(projectId));
    }

    private void applyForProject(User user) {
        // check if user has an active application
        Application activeApplication = getActiveApplication(user);
        if (activeApplication != null) {
            views.sharedView().showError("You can only apply for one project at a time.");
            views.sharedView().showMessage("Please withdraw your active application before applying for a new project.");
            views.sharedView().showMessage("Your active application:");
            views.applicationView().showApplications(List.of(activeApplication));
            return;
        }

        // Show eligible projects
        views.sharedView().showTitle("Eligible Projects");
        List<Project> eligibleProjects = getVisibleProjects(user);
        List<ProjectFlatRow> rows = ProjectViewUtils.flattenEligibleFlats(eligibleProjects, user);
        views.projectView().showProjectFlats(rows);

        // prompt and select project
        Optional<Integer> projectId = getProjectId(user);
        if (projectId.isEmpty()) {
            views.sharedView().showMessage("Application cancelled.");
            return;
        }

        Optional<Project> project = projectService.getProjectById(projectId.get());
        if (project.isEmpty()) {
            views.sharedView().showMessage("Project not found.");
            return;
        }

        // list flats that are available for user under the project
        List<Flat> availableFlats = projectService.getAvailableFlats(user, project.get());

        if (availableFlats.isEmpty()) {
            views.sharedView().showMessage("No available flats.");
            return;
        }

        views.applicationView().showFlatOptions(availableFlats);

        int chosenFlatType = views.applicationView().getFlatChoice();
        if (chosenFlatType == 0 || chosenFlatType > availableFlats.size()) {
            return;
        }

        ActionResult<Application> result = applicationService.apply(user, project.get(), availableFlats.get(chosenFlatType-1).getType());
        if (result.isSuccess()) {
            views.sharedView().showMessage(result.getMessage());
        } else {
            views.sharedView().showError(result.getMessage());
        }
    }

    private void viewApplications(User user) {
        views.sharedView().showTitle("Applications");
        views.applicationView().showApplications(applicationService.getApplicationsFor(user));
        views.sharedView().pressEnterToContinue();
    }

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

    private void withdrawApplication(User user) {
        // check if user has an active application
        Application activeApplication = getActiveApplication(user);
        if (activeApplication == null) {
            views.sharedView().showError("You do not have any active application.");
            return;
        }
        views.sharedView().showMessage("This is your active application: ");
        views.applicationView().showApplications(List.of(activeApplication));
        boolean confirm = views.sharedView().getConfirmation("Are you sure you want to withdraw your application? ");
        if (confirm) {
            ActionResult<Application> result = applicationService.withdraw(user, activeApplication);
            if (result.isSuccess()) {
                views.sharedView().showMessage(result.getMessage());
                Session.remove("activeApplication");
                return;
            }
            views.sharedView().showError(result.getMessage());
            return;
        }
        views.sharedView().showMessage("Withdrawal cancelled.");
    }

    private void viewEnquiries(User user) {
    }

    private void accountSettings() {
        AccountController accountController = new AccountController(
                authService,
                views.sharedView(),
                views.accountView()
        );
        accountController.start();
    }


    private void viewEnquiries(){
        EnquiryController enquiryController = new EnquiryController(views.enquiryView(), enquiryService);
        enquiryController.start();
    }
}
