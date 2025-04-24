package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.service.*;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.ApplicantViews;

/**
 * The ApplicantController class handles all operations related to the applicant's role in the system.
 * It provides functionality for viewing available projects, managing applications, enquiries, and account settings.
 * This controller interacts with various services and views to facilitate the applicant's tasks.
 */
public class ApplicantController implements IBaseController {

    private final ApplicantViews views;
    private final AuthService authService;
    private final ProjectService projectService;
    private final ApplicationService applicationService;
    private final EnquiryService enquiryService;
    private final BookingService bookingService;

    /**
     * Constructs an ApplicantController with the necessary dependencies.
     *
     * @param views              The ApplicantViews for displaying applicant-specific UI.
     * @param authService        The AuthService for authentication-related operations.
     * @param projectService     The ProjectService for managing projects.
     * @param applicationService The ApplicationService for managing applications.
     * @param enquiryService     The EnquiryService for managing enquiries.
     * @param bookingService     The BookingService for managing bookings.
     */
    public ApplicantController(
            ApplicantViews views,
            AuthService authService,
            ProjectService projectService,
            ApplicationService applicationService,
            EnquiryService enquiryService,
            BookingService bookingService
    ) {
        this.views = views;
        this.authService = authService;
        this.projectService = projectService;
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
        this.bookingService = bookingService;
    }

    /**
     * Starts the applicant's main menu and handles navigation to different functionalities.
     */
    @Override
    public void start() {
        int choice;
        String[] options = {
                "View available projects",
                "My Applications",
                "My Enquiries",
                "My Account",
                "Logout"
        };
        do {
            choice = views.sharedView().showMenuAndGetChoice("Applicant Menu", options);
            switch (choice) {
                case 1 -> viewProjects();
                case 2 -> applicationActions();
                case 3 -> viewEnquiries();
                case 4 -> accountSettings();
                case 5 -> logout();
                default -> views.sharedView().showInvalidChoice();
            }
        } while (choice != options.length);
    }

    /**
     * Logs the applicant out of the system and displays a logout message.
     */
    private void logout() {
        Session.logout();
        views.sharedView().showMessage("Logging out...");
    }

    /**
     * Displays available projects to the applicant using the ProjectViewerController.
     */
    private void viewProjects() {
        ProjectViewerController projectViewerController = new ProjectViewerController(
                views.sharedView(),
                views.projectView(),
                projectService
        );
        projectViewerController.start();
    }

    /**
     * Handles application-related actions for the applicant using the ApplicationController.
     */
    private void applicationActions() {
        ApplicationController applicationController = new ApplicationController(
                views.sharedView(),
                views.applicationView(),
                views.projectView(),
                projectService,
                applicationService
        );
        applicationController.start();
    }

    /**
     * Handles account settings for the applicant using the AccountController.
     */
    private void accountSettings() {
        AccountController accountController = new AccountController(
                authService,
                projectService,
                bookingService,
                views.sharedView(),
                views.accountView()
        );
        accountController.start();
    }

    /**
     * Displays and manages enquiries for the applicant using the ApplicantEnquiryController.
     */
    private void viewEnquiries() {
        ApplicantEnquiryController enquiryController = new ApplicantEnquiryController(
                views.sharedView(),
                views.enquiryView(),
                enquiryService
        );
        enquiryController.start();
    }
}
