package sc2002.fcsi.grp3.service;


import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.session.Session;

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
}
