package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.AuthService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.AuthView;

import java.util.Optional;

/**
 * Controller class for handling user login functionality.
 * Manages the authentication process and user session initialization.
 */
public class LoginController {

    private final AuthService authService;
    private final AuthView view;

    /**
     * Constructs a LoginController with the required dependencies.
     *
     * @param authService the authentication service for validating user credentials
     * @param view        the view for displaying login-related UI elements
     */
    public LoginController(AuthService authService, AuthView view) {
        this.authService = authService;
        this.view = view;
    }

    /**
     * Handles the login process by prompting the user for credentials,
     * authenticating them, and initializing the user session if successful.
     *
     * @return true if login is successful, false otherwise
     */
    public boolean login() {
        view.showHeader();
        String nric = view.promptNRIC();
        String password = view.promptPassword();

        Optional<User> result = Optional.ofNullable(authService.authenticate(nric, password));

        if (result.isPresent()) {
            User user = result.get();
            Session.setCurrentUser(user);
            view.showLoginSuccess(user);
            return true;
        } else {
            view.showLoginFailure();
            return false;
        }

    }
}
