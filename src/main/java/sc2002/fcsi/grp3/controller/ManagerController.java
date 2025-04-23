package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Registration;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.service.ApplicationService;
import sc2002.fcsi.grp3.service.EnquiryService;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.ApplicantView;
import sc2002.fcsi.grp3.view.ManagerView;
import sc2002.fcsi.grp3.view.EnquiryViewManager;

import java.util.List;


public class ManagerController implements IBaseController {
    private final ManagerView view;
    private final ProjectService projectService;
    private final EnquiryService enquiryService;
    private final EnquiryViewManager enquiryView;

    public ManagerController(ManagerView view, ProjectService projectService, EnquiryService enquiryService, EnquiryViewManager enquiryView) {
        this.view = view;
        this.projectService = projectService;
        this.enquiryService = enquiryService;
        this.enquiryView = enquiryView;
    }


    @Override
    public void start() {
        int choice;
        do {
            String[] options = {
                    "Manage Projects",
                    "Manage Applications",
                    "Manage Registrations",
                    "Manage Enquiries",
                    "Logout"
            };

            choice = view.showMenuAndGetChoice("HDB Manager Menu", options);
            switch (choice) {
                case 1 -> manageProjects();
                case 2 -> manageApplications();
                case 3 -> manageRegistrations();
                case 4 -> manageEnquiries();
                case 5 -> logout();
                default -> view.showMessage("Invalid choice.");
            }
        } while (choice != 5); // Loop until the user chooses to logout
    }
        //Show all projects 
        public void viewProjects() {
            // Prompt the user to choose between viewing all projects or projects created by the manager
            String[] options = {
                    "View All Projects",
                    "View Projects Created by You"
            };

            int choice = view.showMenuAndGetChoice("View Projects", options);

            switch (choice) {
                case 1 -> {
                    // View all projects
                    List<Project> allProjects = projectService.getAllProjects();
                    if (allProjects.isEmpty()) {
                        view.showMessage("No projects available.");
                    } else {
                        view.showProjects(allProjects);
                    }
                }
                case 2 -> {
                    // View projects created by the manager
                    User user = Session.getCurrentUser();
                    List<Project> managedProjects = projectService.getProjectsManagedBy(user.getNric());
                    if (managedProjects.isEmpty()) {
                        view.showMessage("No projects found under your management.");
                    } else {
                        view.showProjects(managedProjects);
                    }
                }
                default -> view.showMessage("Invalid choice.");
            }
        }
    
        
        //Create a Project
        public void createProject() {
            User user = Session.getCurrentUser();
            Project newProject = view.getNewProjectDetails();

            // Check for overlapping application periods
            boolean hasOverlap = projectService.getProjectsManagedBy(user.getNric()).stream()
                    .anyMatch(project -> 
                        !newProject.getApplicationClosingDate().isBefore(project.getApplicationOpeningDate()) &&
                        !newProject.getApplicationOpeningDate().isAfter(project.getApplicationClosingDate())
                    );
            //If Overlap happens, creation is unsuccessful
            if (hasOverlap) {
                view.showMessage("Cannot create project. Overlapping application periods detected.");
                return;
            }
            //Otherwise creation is successful, ManagerNRIC set, and added to db
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

            boolean editing = true;
            while (editing) {
                String[] options = {
                        "Edit Project Name",
                        "Edit Neighbourhood",
                        "Edit Application Opening Date",
                        "Edit Application Closing Date",
                        "Edit Flats",
                        "Edit Total Officer Slots",
                        "Toggle Project Visibility",
                        "Finish Editing"
                };

                int choice = view.showMenuAndGetChoice("Edit Project", options);
                switch (choice) {
                    case 1 -> project.setName(view.promptString("Enter new project name: "));
                    case 2 -> project.setNeighbourhood(view.promptString("Enter new neighbourhood: "));
                    case 3 -> project.setApplicationOpeningDate(view.promptDate("Enter new application opening date (YYYY-MM-DD): "));
                    case 4 -> project.setApplicationClosingDate(view.promptDate("Enter new application closing date (YYYY-MM-DD): "));
                    case 5 -> project.setFlats(view.getFlatDetails());
                    case 6 -> {
                        int newSlots = view.promptInt("Enter new total officer slots (max 10): ");
                        boolean success = projectService.updateTotalOfficerSlots(project, newSlots);
                        if (!success) {
                            view.showMessage("Invalid number of officer slots. Must be between 0 and 10.");
                        } else {
                            view.showMessage("Total officer slots updated successfully.");
                        }
                    }
                    case 7 -> {
                        // Toggle project visibility
                        boolean newVisibility = !project.isVisible();
                        projectService.setProjectVisibility(project, newVisibility);
                        view.showMessage("Project visibility has been " + (newVisibility ? "enabled." : "disabled."));
                    }
                    case 8 -> editing = false;
                    default -> view.showMessage("Invalid choice.");
                }
            }

            // Check for overlapping application periods if dates were modified
            boolean hasOverlap = projectService.getProjectsManagedBy(user.getNric()).stream()
                    .filter(p -> p.getId() != project.getId()) // Exclude the current project being edited
                    .anyMatch(p -> 
                        !project.getApplicationClosingDate().isBefore(p.getApplicationOpeningDate()) &&
                        !project.getApplicationOpeningDate().isAfter(p.getApplicationClosingDate())
                    );

            if (hasOverlap) {
                view.showMessage("Cannot update project. Overlapping application periods detected.");
                return;
            }

            boolean success = projectService.updateProject(project.getId(), project);

            if (success) {
                view.showMessage("Project updated successfully.");
            } else {
                view.showMessage("Failed to update project. Invalid data detected.");
            }
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

            boolean success = projectService.deleteProject(project.getId());
            if (success) {
                view.showMessage("Project deleted successfully.");
            } else {
                view.showMessage("Failed to delete project. Please try again.");
            }
        }

        public void logout() {
            Session.logout();
            view.showMessage("Logging out...");

        }

        // // View and approve/reject HDB Officer registrations
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
                    } 
                    else if (decision.equals("reject")) {
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

            if (projects.isEmpty()) {
                view.showMessage("No projects found under your management.");
                return;
            }

            for (Project project : projects) {
                List<Application> applications = projectService.getPendingBTOApplications(project.getId());

                if (applications.isEmpty()) {
                    view.showMessage("No pending applications for project: " + project.getName());
                    continue;
                }

                boolean managingApplications = true;
                while (managingApplications) {
                    view.showPendingBTOApplications(applications);

                    String[] options = {
                            "Approve Application",
                            "Reject Application",
                            "Finish Managing Applications"
                    };

                    int choice = view.showMenuAndGetChoice("Manage Applications for Project: " + project.getName(), options);

                    switch (choice) {
                        case 1 -> {
                            // Approve an application
                            Application application = view.selectApplication(applications);
                            if (application == null) {
                                view.showMessage("No application selected.");
                                continue;
                            }

                            boolean success = projectService.updateBTOApplicationStatus(application, ApplicationStatus.SUCCESSFUL);
                            view.showApprovalMessage(success, "Application approved.", "Approval failed. No available units for the selected flat type.");
                            if (success) {
                                applications.remove(application); // Remove the application from the list if approved
                            }
                        }
                        case 2 -> {
                            // Reject an application
                            Application application = view.selectApplication(applications);
                            if (application == null) {
                                view.showMessage("No application selected.");
                                continue;
                            }

                            projectService.updateBTOApplicationStatus(application, ApplicationStatus.UNSUCCESSFUL);
                            view.showMessage("Application rejected.");
                            applications.remove(application); // Remove the application from the list if rejected
                        }
                        case 3 -> managingApplications = false; // Exit the loop
                        default -> view.showMessage("Invalid choice.");
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

        
        private void manageRegistrations() {
            String[] options = {
                    "View All Registrations for Projects", // Includes both pending and approved registrations
                    "Approve/Reject Pending Officer Registrations",
                    "Back to Main Menu"
            };

            int choice;
            do {
                choice = view.showMenuAndGetChoice("Manage Registrations", options);
                switch (choice) {
                    case 1 -> viewAllRegistrationsForProjects(); // View all registrations
                    case 2 -> approveOrRejectRegistration(); // Approve/Reject pending registrations
                    case 3 -> view.showMessage("Returning to main menu...");
                    default -> view.showMessage("Invalid choice.");
                }
            } while (choice != 3); // Exit submenu when "Back to Main Menu" is selected
        }

        private void manageApplications() {
            String[] options = {
                    "Approve/Reject Applicant BTO Applications",
                    "Approve/Reject Applicant Withdrawal Requests",
                    "Generate Applicant Report",
                    "Back to Main Menu"
            };

            int choice;
            do {
                choice = view.showMenuAndGetChoice("Manage Applications", options);
                switch (choice) {
                    case 1 -> approveBTOApplication();
                    case 2 -> approveWithdrawalRequests();
                    //case 3 -> generateApplicantReport();
                    case 4 -> view.showMessage("Returning to main menu...");
                    default -> view.showMessage("Invalid choice.");
                }
            } while (choice != 4); // Exit submenu when "Back to Main Menu" is selected
        }

        private void manageProjects() {
            String[] options = {
                    "View Projects",
                    "Create Project",
                    "Edit Project",
                    "Delete Project",
                    "Toggle Project Visibility",
                    "View Project Details",
                    "Back to Main Menu"
            };

            int choice;
            do {
                choice = view.showMenuAndGetChoice("Manage Projects", options);
                switch (choice) {
                    case 1 -> viewProjects();
                    case 2 -> createProject();
                    case 3 -> editProject();
                    case 4 -> deleteProject();
                    case 5 -> viewProjectDetails();
                    case 6 -> view.showMessage("Returning to main menu...");
                    default -> view.showMessage("Invalid choice.");
                }
            } while (choice != 7); // Exit submenu when "Back to Main Menu" is selected
        }

        


        private void approveOrRejectRegistration() {
            User user = Session.getCurrentUser();
            List<Project> projects = projectService.getProjectsManagedBy(user.getNric());

            if (projects.isEmpty()) {
                view.showMessage("No projects found under your management.");
                return;
            }

            for (Project project : projects) {
                List<Registration> registrations = projectService.getPendingOfficerRegistrations(project.getId());

                if (registrations.isEmpty()) {
                    view.showMessage("No pending registrations for project: " + project.getName());
                    continue;
                }

                view.showPendingOfficerRegistrations(registrations);

                Registration registration = view.selectRegistration(registrations);
                if (registration == null) {
                    view.showMessage("No registration selected.");
                    continue;
                }

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

        private void viewAllRegistrationsForProjects() {
            User user = Session.getCurrentUser();
            List<Project> projects = projectService.getProjectsManagedBy(user.getNric());

            if (projects.isEmpty()) {
                view.showMessage("No projects found under your management.");
                return;
            }

            for (Project project : projects) {
                view.showMessage("Registrations for Project: " + project.getName());
                List<Registration> pendingRegistrations = projectService.getPendingOfficerRegistrations(project.getId());
                List<Registration> approvedRegistrations = projectService.getApprovedOfficerRegistrations(project.getId());

                if (pendingRegistrations.isEmpty()) {
                    view.showMessage("No pending registrations for this project.");
                } else {
                    view.showMessage("Pending Registrations:");
                    view.showPendingOfficerRegistrations(pendingRegistrations);
                }

                if (approvedRegistrations.isEmpty()) {
                    view.showMessage("No approved registrations for this project.");
                } else {
                    view.showMessage("Approved Registrations:");
                    view.showApprovedOfficerRegistrations(approvedRegistrations);
                }
            }
        }
        private void manageEnquiries(){
            EnquiryControllerManager enquiryController = new EnquiryControllerManager(enquiryView, enquiryService);
            enquiryController.start();
        }

}
