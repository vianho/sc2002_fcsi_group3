package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.ApplicationService;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.ApplicantView;
import sc2002.fcsi.grp3.view.ManagerView;

import java.util.List;


public class ManagerController implements IBaseController {
    private final ManagerView view;
    private final ProjectService projectService;

    public ManagerController(ManagerView view, ProjectService projectService) {
        this.view = view;
        this.projectService = projectService;      
    }

    @Override
    public void start() {
        int choice;
        do {
            String[] options = {
                    "View all Projects",
                    "Approve/Reject Application",
                    "Create Project",
                    "View Project Details",
                    "Edit Project",
                    "Delete Project",
                    "Toggle Project Visibility",
                    "Logout"};

            choice = view.showMenuAndGetChoice("HDB Manager Menu", options);
            switch (choice) {
                case 1 -> viewProjects();
                case 2 -> approveProject();
                case 3 -> createProject();
                case 4 -> viewProjectDetails();
                case 5 -> editProject();
                case 6 -> deleteProject();
                case 7 -> toggleProjectVisibility();
                case 8 -> logout();
                default -> view.showMessage("Invalid choice.");
            }
        } 
        while (choice != 8);
    }

        public void viewProjects() {
            User user = Session.getCurrentUser(); 
        
            List<Project> projects = projectService.getProjectsManagedBy(user.getNric());

            if (projects.isEmpty()) {
                view.showMessage("No projects found under your management.");
            } 
            else {
                view.showProjects(projects);
            }
        }

        public void approveProject(){
            
        }

    public void createProject() {
        User user = Session.getCurrentUser();

        Project newProject = view.getProjectDetails();
       
        projectService.createProject(newProject, user.getNric());
        view.showMessage("Project created successfully.");
    }

    public void viewProjectDetails() {
        User user = Session.getCurrentUser();
       
        List<Project> projects = projectService.getProjectsManagedBy(user.getNric());
        if (projects.isEmpty()) {
            view.showMessage("No projects found under your management.");
            return;
        }
        Project project = view.selectProject(projects);
        view.showProjectDetails(project);
    }
    
    public void editProject() {
    }

    public void toggleProjectVisibility() {
        User user = Session.getCurrentUser();

        List<Project> projects = projectService.getProjectsManagedBy(user.getNric());
        if (projects.isEmpty()) {
            view.showMessage("No projects found under your management.");
            return;
        }

        Project project = view.selectProject(projects);
        if (project == null) {
            view.showMessage("No project selected.");
            return;
        }

        boolean newVisibility = !project.isVisible();
        projectService.setProjectVisibility(project, newVisibility);
        view.showMessage("Project visibility has been " + (newVisibility ? "enabled." : "disabled."));
    }

    public void deleteProject() {
        User user = Session.getCurrentUser();

        List<Project> projects = projectService.getProjectsManagedBy(user.getNric());
        if (projects.isEmpty()) {
            view.showMessage("No projects found under your management.");
            return;
        }

        Project project = view.selectProject(projects);
        if (project == null) {
            view.showMessage("No project selected.");
            return;
        }

        boolean confirm = view.confirmDeletion(project);
        if (!confirm) {
            view.showMessage("Project deletion canceled.");
            return;
        }

        projectService.deleteProject(project.getId());
        view.showMessage("Project deleted successfully.");
    }

    public void logout() {
        Session.logout();
        view.showMessage("Logging out...");

    }

}
