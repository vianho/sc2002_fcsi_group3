package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.AuthService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.AccountView;
import sc2002.fcsi.grp3.view.SharedView;

public class AccountController {
    private final AuthService authService;
    private final SharedView sharedView;
    private final AccountView accountView;

    public AccountController(AuthService authService, SharedView sharedView, AccountView accountView) {
        this.authService = authService;
        this.sharedView = sharedView;
        this.accountView = accountView;
    }

    public void start() {
        int choice;
        String[] options = {"My Info", "Change Password", "Back"};
        User user = Session.getCurrentUser();

        do {
            choice = sharedView.showMenuAndGetChoice("Account Settings", options);
            switch (choice) {
                case 1 -> viewInfo(user);
                case 2 -> changePassword(user);
                default -> sharedView.showInvalidChoice();
            }
        } while (choice != options.length);
    }

    private void viewInfo(User user) {
        sharedView.showTitle("My Info");
        accountView.showProfile(user);
        sharedView.pressEnterToContinue();
    }

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
