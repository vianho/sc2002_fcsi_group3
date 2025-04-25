package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.dto.FlatBookingReportRow;
import sc2002.fcsi.grp3.model.*;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;
import sc2002.fcsi.grp3.service.*;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.*;

import java.util.List;

/**
 * The ManagerController class handles all operations related to the manager's role in the system.
 * It provides functionality for managing projects, applications, registrations, enquiries, and account settings.
 * This controller interacts with various services and views to facilitate the manager's tasks.
 */
public class ManagerController implements IBaseController {

    private final ManagerView view;
    private final SharedView sharedView;
    private final AccountView accountView;
    private final EnquiryView enquiryView;
    private final ReportView reportView;
    private final AuthService authService;
    private final BookingService bookingService;
    private final ProjectService projectService;
    private final EnquiryService enquiryService;
    private final ReportService reportService;

    /**
     * Constructs a ManagerController with the necessary dependencies.
     *
     * @param view           The ManagerView for displaying manager-specific UI.
     * @param sharedView     The SharedView for displaying shared UI elements.
     * @param accountView    The AccountView for managing account-related UI.
     * @param enquiryView    The EnquiryView for managing enquiries.
     * @param reportView     The ReportView for generating reports.
     * @param authService    The AuthService for authentication-related operations.
     * @param bookingService The BookingService for managing bookings.
     * @param projectService The ProjectService for managing projects.
     * @param enquiryService The EnquiryService for managing enquiries.
     * @param reportService  The ReportService for generating reports.
     */
    public ManagerController(
            ManagerView view,
            SharedView sharedView,
            AccountView accountView,
            EnquiryView enquiryView,
            ReportView reportView,
            AuthService authService,
            BookingService bookingService,
            ProjectService projectService,
            EnquiryService enquiryService,
            ReportService reportService
    ) {
        this.view = view;
        this.sharedView = sharedView;
        this.accountView = accountView;
        this.enquiryView = enquiryView;
        this.reportView = reportView;
        this.authService = authService;
        this.bookingService = bookingService;
        this.projectService = projectService;
        this.enquiryService = enquiryService;
        this.reportService = reportService;
    }

    /**
     * Starts the manager's main menu and handles navigation to different management functionalities.
     */
    @Override
    public void start() {
        int choice;
        String[] options = {
                "Manage Projects",
                "Manage Applications",
                "Manage Registrations",
                "Manage Enquiries",
                "My Account",
                "Logout"
        };

        do {
            choice = view.showMenuAndGetChoice("HDB Manager Menu", options);
            switch (choice) {
                case 1 -> manageProjects();
                case 2 -> manageApplications();
                case 3 -> manageRegistrations();
                case 4 -> manageEnquiries();
                case 5 -> accountSettings();
                case 6 -> logout();
                default -> view.showMessage("Invalid choice.");
            }
        } while (choice != options.length); // Loop until the user chooses to logout
    }

    /**
     * Displays a menu for viewing projects. The manager can choose to view all projects or only the projects they manage.
     */
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
                    view.showProjects("All Projects", allProjects);
                }
            }
            case 2 -> {
                // View projects created by the manager
                User user = Session.getCurrentUser();
                List<Project> managedProjects = projectService.getProjectsManagedBy(user.getNric());
                if (managedProjects.isEmpty()) {
                    view.showMessage("No projects found under your management.");
                } else {
                    view.showProjects("Your Projects", managedProjects);
                }
            }
            default -> view.showMessage("Invalid choice.");
        }
    }

    /**
     * Allows the manager to create a new project. Ensures that there are no overlapping application periods
     * with existing projects managed by the same manager.
     */
    public void createProject() {
        User user = Session.getCurrentUser(); // Get the current manager
        String managerNric = user.getNric(); // Get the manager's NRIC

        // Get new project details from the view
        Project newProject = view.getNewProjectDetails(managerNric);

        // Check for overlapping application periods
        boolean hasOverlap = projectService.getProjectsManagedBy(managerNric).stream()
                .anyMatch(project ->
                        !newProject.getApplicationClosingDate().isBefore(project.getApplicationOpeningDate()) &&
                                !newProject.getApplicationOpeningDate().isAfter(project.getApplicationClosingDate())
                );

        if (hasOverlap) {
            view.showMessage("Cannot create project. Overlapping application periods detected.");
            return;
        }

        // Create the project
        projectService.createProject(newProject, managerNric);
        view.showMessage("Project created successfully with ID: " + newProject.getId());
    }

    /**
     * Displays the details of a selected project. The manager can view all projects and select one to view its details.
     */
    public void viewProjectDetails() {

        List<Project> projects = projectService.getAllProjects();
        if (projects.isEmpty()) {
            view.showMessage("No projects found under your management.");
            return;
        }
        Project project = view.selectProject(projects);
        view.showProjectDetails(project);
    }

    /**
     * Allows the manager to edit a project they manage. Provides options to modify project details such as name,
     * neighborhood, application dates, flats, officer slots, and visibility.
     * Ensures that there are no overlapping application periods after editing.
     */
    public void editProject() {
        User user = Session.getCurrentUser();
        List<Project> projects = projectService.getProjectsManagedBy(user.getNric());

        if (projects.isEmpty()) {
            view.showMessage("No projects found under your management.");
            return;
        }

        Project project = view.selectProject(projects);
        if (project == null) {
            view.showMessage("No project selected/Unauthorised Access.");
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
                    int currentOfficers = project.getOfficerNrics().size();

                    if (newSlots < currentOfficers) {
                        view.showMessage("Invalid number of officer slots. The new value cannot be lower than the current number of registered officers (" + currentOfficers + ").");
                        break;
                    }

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

    /**
     * Allows the manager to delete a project they manage. Confirms the deletion before proceeding.
     */
    public void deleteProject() {
        User user = Session.getCurrentUser();

        List<Project> projects = projectService.getProjectsManagedBy(user.getNric());
        if (projects.isEmpty()) {
            view.showMessage("No projects found under your management.");
            return;
        }

        Project project = view.selectProject(projects);
        if (project == null) {
            view.showMessage("No project selected/Unathorised Access.");
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

    /**
     * Handles account settings for the manager. Delegates to the AccountController for managing account-related tasks.
     */
    private void accountSettings() {
        AccountController accountController = new AccountController(
                authService,
                projectService,
                bookingService,
                sharedView,
                accountView
        );
        accountController.start();
    }

    /**
     * Logs the manager out of the system and displays a logout message.
     */
    public void logout() {
        Session.logout();
        view.showMessage("Logging out...");

    }

    /**
     * Displays and manages HDB officer registrations for the manager's projects.
     * Allows the manager to approve or reject pending registrations.
     */
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

    /**
     * Displays and manages applicant BTO applications for the manager's projects.
     * Allows the manager to approve or reject applications.
     */
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

    /**
     * Displays and manages withdrawal requests for the manager's projects.
     * Allows the manager to approve or reject withdrawal requests.
     */
    public void approveWithdrawalRequests() {
        User user = Session.getCurrentUser();
        List<Project> projects = projectService.getProjectsManagedBy(user.getNric());
        if (projects.isEmpty()) {
            view.showMessage("No projects found under your management.");
            return;
        }

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

    /**
     * Generates a report for applicants and displays it using the ReportViewerController.
     */
    public void generateApplicantReport() {
        ReportViewerController reportViewerController = new ReportViewerController(
                sharedView,
                reportView,
                reportService
        );
        reportViewerController.start();
    }

    /**
     * Displays a menu for managing registrations. The manager can view all registrations, approve/reject pending
     * officer registrations, or return to the main menu.
     */
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

    /**
     * Displays a menu for managing applications. The manager can approve/reject BTO applications,
     * approve/reject withdrawal requests, generate applicant reports, or return to the main menu.
     */
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
                case 3 -> generateApplicantReport();
                case 4 -> view.showMessage("Returning to main menu...");
                default -> view.showMessage("Invalid choice.");
            }
        } while (choice != 4); // Exit submenu when "Back to Main Menu" is selected
    }

    /**
     * Displays a menu for managing projects. The manager can view projects, create a project, edit a project,
     * delete a project, view project details, or return to the main menu.
     */
    private void manageProjects() {
        String[] options = {
                "View Projects",
                "Create Project",
                "Edit Project",
                "Delete Project",
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
        } while (choice != 6); // Exit submenu when "Back to Main Menu" is selected
    }

    /**
     * Approves or rejects pending officer registrations for the manager's projects.
     */
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
                boolean success = projectService.updateOfficerRegistrationStatus(registration, RegistrationStatus.APPROVED);
                if (success) {
                    view.showMessage("Registration approved, and officer added to the project.");
                } else {
                    view.showMessage("Registration approval failed.");
                }
            } else if (decision.equals("reject")) {
                projectService.updateOfficerRegistrationStatus(registration, RegistrationStatus.REJECTED);
                view.showMessage("Registration rejected.");
            }
        }
    }

    /**
     * Displays all registrations (pending and approved) for the manager's projects.
     */
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

    /**
     * Manages enquiries for the manager's projects. Delegates to the ManagerEnquiryController for handling enquiries.
     */
    private void manageEnquiries() {
        ManagerEnquiryController enquiryController = new ManagerEnquiryController(
                sharedView,
                enquiryView,
                enquiryService
        );
        enquiryController.start();
    }
}
