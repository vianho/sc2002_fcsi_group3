package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.permission.ApplicantApplicationPermission;
import sc2002.fcsi.grp3.model.permission.OfficerApplicationPermission;
import sc2002.fcsi.grp3.service.*;
import sc2002.fcsi.grp3.init.ViewInitializer;

/**
 * Factory class for creating controllers based on the user's role or specific requirements.
 * It initializes controllers with the necessary services and views.
 */
public class ControllerFactory {

    private final DataStore store;
    private final ViewInitializer viewInit;

    /**
     * Constructs a ControllerFactory with the required dependencies.
     *
     * @param store    the data store containing application data
     * @param viewInit the initializer for setting up views
     */
    public ControllerFactory(DataStore store, ViewInitializer viewInit) {
        this.store = store;
        this.viewInit = viewInit;
    }

    /**
     * Creates and returns the MainMenuController.
     *
     * @return the MainMenuController instance
     */
    public MainMenuController createMainMenuController() {
        return new MainMenuController(
                viewInit.getSharedView(),
                viewInit.getMainMenuView(),
                createLoginController(),
                this
        );
    }

    /**
     * Creates and returns the LoginController.
     *
     * @return the LoginController instance
     */
    public LoginController createLoginController() {
        UserService userService = new UserService(store.getUsers());
        AuthService authService = new AuthService(userService);
        return new LoginController(authService, viewInit.getAuthView());
    }

    /**
     * Creates and returns a controller based on the user's role.
     * Supports roles such as "Applicant", "Officer", and "Manager".
     *
     * @param user the user for whom the controller is being created
     * @return the appropriate controller for the user's role
     * @throws IllegalStateException if the user's role is unknown
     */
    public IBaseController createControllerForUser(User user) {
        String roleName = user.getRoleName();
        UserService userService = new UserService(store.getUsers());


        return switch (roleName) {
            case "Applicant" -> {
                ApplicantApplicationPermission appPermission = new ApplicantApplicationPermission();
                yield new ApplicantController(
                    viewInit.getApplicantViews(),
                    new AuthService(userService),
                    new ProjectService(store),
                    new ApplicationService(appPermission, store),
                    new EnquiryService(store),
                    new BookingService(store)
                );
            }
            case "Officer" ->{
                OfficerApplicationPermission appPermission = new OfficerApplicationPermission();
                yield new OfficerController(
                    viewInit.getOfficerViews(),
                    new AuthService(userService),
                    new ProjectService(store),
                    new ApplicationService(appPermission, store),
                    new RegistrationService(store),
                    new EnquiryService(store),
                    new BookingService(store),
                    userService
                );
            }
            case "Manager" -> new ManagerController(
                    viewInit.getManagerView(),
                    viewInit.getSharedView(),
                    viewInit.getAccountView(),
                    viewInit.getEnquiryView(),
                    viewInit.getReportView(),
                    new AuthService(userService),
                    new BookingService(store),
                    new ProjectService(store),
                    new EnquiryService(store),
                    new ReportService(store)
            );
            default -> throw new IllegalStateException("Unknown role: " + roleName);
        };
    }
}
