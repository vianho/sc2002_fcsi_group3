package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.dto.ProjectFlatRow;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.ApplicationService;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.util.ProjectViewUtils;
import sc2002.fcsi.grp3.view.ApplicationView;
import sc2002.fcsi.grp3.view.SharedView;

import java.util.List;

public class ApplicationController {
    private final ProjectService projectService;
    private final ApplicationService applicationService;
    private final SharedView sharedView;
    private final ApplicationView applicationView;

    public ApplicationController(ApplicationService applicationService, SharedView sharedView, ApplicationView applicationView) {
        this.applicationService = applicationService;
        this.sharedView = sharedView;
        this.applicationView = applicationView;
    }

    public void start() {
        int choice;
        String[] options = {
                "View Available Projects",
                "Apply for a Project",
                "View Applications",
                "Withdraw Application",
                "Back"};
        User user = Session.getCurrentUser();

        do {
            choice = sharedView.showMenuAndGetChoice("Account Settings", options);
            switch (choice) {
                case 1 -> viewProjects(user);
                case 2 -> applyForProject(user);
                case 3 -> viewApplications(user);
                case 4 -> withdrawApplication(user);
                default -> sharedView.showInvalidChoice();
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
}
