package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.AuthService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.LoginView;

import java.util.Optional;

public class LoginController {
    private final AuthService authService;
    private final LoginView view;

    public LoginController(AuthService authService, LoginView view) {
        this.authService = authService;
        this.view = view;
    }

    public boolean login() {
        view.showHeader();
        String nric = view.promptNRIC();
        String password = view.promptPassword();

        Optional<User> result = Optional.ofNullable(authService.authenticate(nric, password));

        if (result.isPresent()) {
            User user = result.get();
            Session.setCurrentUser(user);
            view.showSuccess(user);
            return true;
        } else {
            view.showFailure();
            return false;
        }

    }
}
