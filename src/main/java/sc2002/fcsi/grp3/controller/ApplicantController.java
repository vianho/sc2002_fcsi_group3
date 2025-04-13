package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.service.ApplicationService;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.ApplicantView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ApplicantController implements IBaseController {
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
            String[] options = {
                    "View Available Projects",
                    "Apply for a Project",
                    "View My Applications",
                    "View My Enquiries",
                    "View Profile",
                    "Logout"};
            choice = view.showMenuAndGetChoice("Applicant Menu", options);
            switch (choice) {
                case 1 -> viewProjects();
                case 2 -> applyForProject();
                case 3 -> viewApplications();
//                case 4 -> viewEnquiries();
//                case 5 -> viewProfile();
                case 6 -> logout();
                default -> view.showMessage("Invalid choice.");
            }
        } while (choice != 6);
    }

    private void viewProjects() {
        User user = Session.getCurrentUser();
        view.showProjects(projectService.getVisibleProjects(user));
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

        List<Flat> availableFlats = projectService.getAvailableFlats(user, project.get());

        if (availableFlats.isEmpty()) {
            view.showMessage("No available flats.");
            return;
        }

        view.showFlatOptions(availableFlats);

        int chosenFlatType = view.getFlatChoice();
        if (chosenFlatType == 0 || chosenFlatType > availableFlats.size()) {
            return;
        }

        if (user.getRole()
                .getApplicationPermission()
                .canCreateApplication(user, project.get())) {

            boolean success = applicationService.apply(user, project.get(), availableFlats.get(chosenFlatType-1).getType());
            if (success) {
                view.showApplicationSuccess();
            } else {
                view.showApplicationFailure("Unable to apply for project.");
            }
        } else {
            view.showMessage("You do not have permission to apply for this project.");
        }
    }

    private void viewApplications() {
        User user = Session.getCurrentUser();
        view.showApplications(applicationService.getOwnApplications(user));
    }
}
