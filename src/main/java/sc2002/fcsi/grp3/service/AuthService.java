package sc2002.fcsi.grp3.service;


import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.util.Validator;

import java.util.Optional;

public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User authenticate(String nric, String password) {
        Optional<User> user = userService.findByNRIC(nric);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            Session.setCurrentUser(user.get());
            return user.get();
        }
        return null;
    }

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
