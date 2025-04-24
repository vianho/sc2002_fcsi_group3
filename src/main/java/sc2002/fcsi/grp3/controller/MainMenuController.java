package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.MainMenuView;
import sc2002.fcsi.grp3.view.SharedView;

/**
 * Controller class for managing the main menu of the application.
 * Handles user login and navigation to role-specific controllers.
 */
public class MainMenuController implements IBaseController {

    private final SharedView sharedView;
    private final MainMenuView view;
    private final LoginController loginController;
    private final ControllerFactory controllerFactory;

    /**
     * Constructs a MainMenuController with the required dependencies.
     *
     * @param sharedView        the shared view for displaying common UI elements
     * @param view              the view for displaying main menu UI elements
     * @param loginController   the controller for handling user login
     * @param controllerFactory the factory for creating role-specific controllers
     */
    public MainMenuController(
            SharedView sharedView,
            MainMenuView view,
            LoginController loginController,
            ControllerFactory controllerFactory) {
        this.sharedView = sharedView;
        this.view = view;
        this.loginController = loginController;
        this.controllerFactory = controllerFactory;
    }

    /**
     * Starts the main menu, allowing the user to log in or exit the application.
     */
    public void start() {
        int choice;
        String[] options = { "Login", "Exit" };
        do {
            choice = sharedView.showMenuAndGetChoice("BTO Management System", options);

            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> view.showExitMessage();
                default -> sharedView.showInvalidChoice();
            }
        } while (choice != options.length);
    }

    /**
     * Handles the login process. If login is successful, navigates to the
     * appropriate controller based on the user's role.
     */
    private void handleLogin() {
        boolean success = loginController.login();
        if (!success) return;
        User loggedIn = Session.getCurrentUser();
        IBaseController userController = controllerFactory.createControllerForUser(loggedIn);

        if (userController != null) {
            userController.start();
            if (!Session.isLoggedIn()) {
                view.showMessage("You have been logged out. Returning to Main Menu...");
            }
        } else {
            view.showUnknownRole();
        }
    }
}
