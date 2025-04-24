package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.Booking;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.role.OfficerRole;
import sc2002.fcsi.grp3.service.AuthService;
import sc2002.fcsi.grp3.service.BookingService;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.AccountView;
import sc2002.fcsi.grp3.view.SharedView;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for managing user account settings such as
 * viewing profile information and changing passwords.
 */
public class AccountController implements IBaseController {
    
    private final AuthService authService;
    private final ProjectService projectService;
    private final BookingService bookingService;
    private final SharedView sharedView;
    private final AccountView accountView;

    /**
     * Constructs an AccountController with the necessary service and view dependencies.
     *
     * @param authService     the authentication service
     * @param projectService  the project service
     * @param bookingService  the booking service
     * @param sharedView      the shared view for common UI elements
     * @param accountView     the account-specific view
     */
    public AccountController(AuthService authService, ProjectService projectService, BookingService bookingService, SharedView sharedView, AccountView accountView) {
        this.authService = authService;
        this.projectService = projectService;
        this.bookingService = bookingService;
        this.sharedView = sharedView;
        this.accountView = accountView;
    }

     /**
     * Starts the controller loop, present user with a menu of account options.
     * Handles user interaction for viewing profile info or changing password.
     */
    @Override
    public void start() {
        int choice;
        String[] options = {"My Info", "Change Password", "Back to main menu"};
        User user = Session.getCurrentUser();

        do {
            choice = sharedView.showMenuAndGetChoice("Account Settings", options);
            switch (choice) {
                case 1 -> viewInfo(user);
                case 2 -> changePassword(user);
                case 3 -> {}
                default -> sharedView.showInvalidChoice();
            }
        } while (choice != options.length);
    }

    /**
     * Displays the current user's profile information, including any managed projects
     * or successful bookings, depending on the user's role.
     *
     * @param user the currently logged-in user
     */
    private void viewInfo(User user) {
        sharedView.showTitle("My Info");
        Optional<Project> managedProjectRes;
        Project managedProject = null;
        Booking successfulBooking = null;
        switch (user.getRoleName()) {
            case "Manager" -> {
                managedProjectRes = projectService.getProjectsManagedBy(user.getNric()).stream()
                        .filter(Project::isApplicationOpen)
                        .findFirst();
                managedProject = managedProjectRes.orElse(null);
            }
            case "Officer" -> {
                managedProjectRes = projectService.getProjectsManagedByOfficer(user.getNric()).stream()
                        .filter(Project::isApplicationOpen)
                        .findFirst();
                managedProject = managedProjectRes.orElse(null);
                successfulBooking = bookingService.getBookingByUser(user).isPresent()
                        ? bookingService.getBookingByUser(user).get()
                        : null;
            }
            case "Applicant" -> {
                successfulBooking = bookingService.getBookingByUser(user).isPresent()
                        ? bookingService.getBookingByUser(user).get()
                        : null;
            }
        }

        accountView.showProfile(user, managedProject, successfulBooking);
        sharedView.pressEnterToContinue();
    }

    /**
     * Allows the user to change their password. Verifies the current password,
     * then prompts for and confirms the new password before attempting an update.
     *
     * @param user the currently logged-in user
     */
    private void changePassword(User user) {
        sharedView.showTitle("Change Password");

        String currentPassword = accountView.promptPassword("Enter current password: ");

        if (currentPassword.isEmpty()) {
            accountView.showMessage("Password change canceled.");
            return;
        }

        if (!currentPassword.equals(user.getPassword())) {
            accountView.showError("Incorrect current password. Password not changed.");
            return;
        }

        while (true) {
            String newPassword = accountView.promptPassword("Enter new password (Enter to cancel): ");
            if (newPassword.isBlank()) {
                sharedView.showMessage("Password change cancelled.");
                return;
            }

            String confirm = accountView.promptPassword("Confirm new password: ");
            String result = authService.changePassword(user, newPassword, confirm);
            if (result == null) {
                accountView.showPasswordChangeSuccess();
            } else {
                accountView.showError(result);
            }
        }
    }
}
