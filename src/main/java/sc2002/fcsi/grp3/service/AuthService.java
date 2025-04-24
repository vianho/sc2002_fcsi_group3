package sc2002.fcsi.grp3.service;


import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.util.Validator;

import java.util.Optional;

/**
 * The AuthService class provides authentication and password management functionality.
 */
public class AuthService {

    private final UserService userService;

    /**
     * Constructs an AuthService with the specified UserService.
     *
     * @param userService the UserService for managing user-related operations
     */
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Authenticates a user based on their NRIC and password.
     * If authentication is successful, the user is set as the current session user.
     *
     * @param nric     the NRIC of the user
     * @param password the password of the user
     * @return the authenticated User object, or null if authentication fails
     */
    public User authenticate(String nric, String password) {
        Optional<User> user = userService.findByNRIC(nric);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            Session.setCurrentUser(user.get());
            return user.get();
        }
        return null;
    }

    /**
     * Changes the password for the specified user.
     * Validates the old password, ensures the new password is strong, and updates the password.
     *
     * @param user        the user whose password is being changed
     * @param oldPassword the current password
     * @param newPassword the new password
     * @return null if the password change is successful, or an error message if validation fails
     */
    public String changePassword(User user, String oldPassword, String newPassword) {
        if (!user.getPassword().equals(oldPassword)) {
            return "Incorrect current password.";
        }

        if (newPassword.equals(oldPassword)) {
            return "New password cannot be the same as the current password.";
        }

        if (!Validator.isStrongPassword(newPassword)) {
            return "Password must be at least 8 characters long and include upper/lower case, a number, and a special character.";
        }

        user.setPassword(newPassword);
        return null;
    }
}
