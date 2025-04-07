package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.MainMenuView;

public class MainMenuController implements IBaseController {
    private final MainMenuView view;
    private final LoginController loginController;
    private final ControllerFactory controllerFactory;

    public MainMenuController(
            MainMenuView view,
            LoginController loginController,
            ControllerFactory controllerFactory
    ) {
        this.view = view;
        this.loginController = loginController;
        this.controllerFactory = controllerFactory;
    }

    public void start() {
        int choice;
        do {
            choice = view.showMainMenuAndGetChoice();

            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> view.showExitMessage();
                default -> view.showInvalidChoice();
            }
        } while (choice != 2);
    }

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
