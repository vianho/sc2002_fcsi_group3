package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.ApplicationService;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.ApplicantView;

import java.util.Optional;

public class ApplicantController implements BaseController {
    private final ApplicantView view;
    private final ProjectService projectService;
    private final ApplicationService applicationService;

    public ApplicantController(
            ApplicantView view,
            ProjectService projectService,
            ApplicationService applicationService
    ) {
        this.view = view;
        this.projectService = projectService;
        this.applicationService = applicationService;
    }

    public void start() {
        int choice;
        do {
            choice = view.showApplicantMenuAndGetChoice();
            switch (choice) {
                case 1 -> viewProjects();
                case 2 -> applyForProject();
//                case 3 -> viewApplications();
//                case 4 -> viewEnquiry();
                case 5 -> logout();
                default -> view.showMessage("Invalid choice.");
            }
        } while (choice != 5);
    }

    private void viewProjects() {
        view.showProjects(projectService.getVisibleProjects());
    }

    private void logout() {
        Session.logout();
        view.showMessage("Logging out...");
    }

    private void applyForProject() {
        int projectId = view.promptProjectId();
        User user = Session.getCurrentUser();
        Optional<Project> project = projectService.getProjectById(projectId);

        if (project.isEmpty()) {
            view.showMessage("Project not found.");
            return;
        }

        if (user.getRole()
                .getApplicationPermission()
                .canCreateApplication(user, project.get())) {

            boolean success = applicationService.apply(user, project.get());
            if (success) {
                view.showMessage("Application submitted.");
            } else {
                view.showMessage("Application failed.");
            }
        } else {
            view.showMessage("You do not have permission to apply for this project.");
        }
    }
}
