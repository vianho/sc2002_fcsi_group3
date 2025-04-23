package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.MainMenuView;
import sc2002.fcsi.grp3.view.SharedView;

public class MainMenuController implements IBaseController {
    private final SharedView sharedView;
    private final MainMenuView view;
    private final LoginController loginController;
    private final ControllerFactory controllerFactory;

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
