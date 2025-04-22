package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Registration;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;
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
                    "Approve/Reject HDB Officer Registrations",
                    "Approve/Reject Applicant BTO Applications",
                    "Approve/Reject Applicant Withdrawal Requests",
                    "Create Project",
                    "View Project Details",
                    "Edit Project",
                    "Delete Project",
                    "Toggle Project Visibility",
                    "Logout"};

            choice = view.showMenuAndGetChoice("HDB Manager Menu", options);
            switch (choice) {
                case 1 -> viewProjects();
                case 2 -> viewHDBOfficerRegistrations();
                case 3 -> approveBTOApplication();
                case 4 -> approveWithdrawalRequests();
                case 5 -> createProject();
                case 6 -> viewProjectDetails();
                case 7 -> editProject();
                case 8 -> deleteProject();
                case 9 -> toggleProjectVisibility();
                case 10 -> logout();
                default -> view.showMessage("Invalid choice.");
            }
        } 
        while (choice != 10);
    }
        //Show all projects 
        public void viewProjects() {
                    List<Project> projects = projectService.getAllProjects();
            if (projects.isEmpty()) {
                view.showMessage("No projects found under your management.");
            } 
            else {
                view.showProjects(projects);
            }
        }
    
        
        //Create a Project
        public void createProject() {
            User user = Session.getCurrentUser();
            //Prompt for the Project information 
            Project newProject = view.getProjectDetails();
        
            //Create the project
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

        // View and approve/reject HDB Officer registrations
        public void viewHDBOfficerRegistrations() {
            User user = Session.getCurrentUser();
            List<Project> projects = projectService.getProjectsManagedBy(user.getNric());

            for (Project project : projects) {
                List<Registration> registrations = projectService.getPendingOfficerRegistrations(project.getId());
                view.showPendingOfficerRegistrations(registrations);

                for (Registration registration : registrations) {
                    String decision = view.promptApprovalDecision("Approve or Reject?");
                    if (decision.equals("approve")) {
                        projectService.updateOfficerRegistrationStatus(registration, RegistrationStatus.APPROVED);
                        view.showMessage("Registration approved.");
                    } else if (decision.equals("reject")) {
                        projectService.updateOfficerRegistrationStatus(registration, RegistrationStatus.REJECTED);
                        view.showMessage("Registration rejected.");
                    }
                }
            }
        }

        // View and approve/reject Applicant BTO applications
        public void approveBTOApplication() {
            User user = Session.getCurrentUser();
            List<Project> projects = projectService.getProjectsManagedBy(user.getNric());

            for (Project project : projects) {
                List<Application> applications = projectService.getPendingBTOApplications(project.getId());
                view.showPendingBTOApplications(applications);

                for (Application application : applications) {
                    String decision = view.promptApprovalDecision("Approve or Reject?");
                    if (decision.equals("approve")) {
                        boolean success = projectService.updateBTOApplicationStatus(application, ApplicationStatus.SUCCESSFUL);
                        view.showApprovalMessage(success, "Application approved.", "Approval failed. No available units for the selected flat type.");
                    } else if (decision.equals("reject")) {
                        projectService.updateBTOApplicationStatus(application, ApplicationStatus.UNSUCCESSFUL);
                        view.showMessage("Application rejected.");
                    }
                }
            }
        }

        // Approve/reject Applicant withdrawal requests
        public void approveWithdrawalRequests() {
            User user = Session.getCurrentUser();
            List<Project> projects = projectService.getProjectsManagedBy(user.getNric());

            for (Project project : projects) {
                List<Application> applications = projectService.getPendingWithdrawalRequests(project.getId());
                view.showPendingBTOApplications(applications);

                for (Application application : applications) {
                    String decision = view.promptApprovalDecision("Approve or Reject Withdrawal?");
                    projectService.updateWithdrawalRequest(application, decision.equals("approve"));
                    view.showMessage("Withdrawal " + (decision.equals("approve") ? "approved." : "rejected."));
                }
            }
        }
}
