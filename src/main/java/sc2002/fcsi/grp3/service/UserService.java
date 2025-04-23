package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.model.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final List<User> users;

    public UserService(List<User> users) {
        this.users = users;
    }

    public Optional<User> findByNRIC(String nric) {
        if (nric == null) return Optional.empty();

        return users.stream()
                .filter(u -> u.getNric().equalsIgnoreCase(nric))
                .findFirst();
    }
}
