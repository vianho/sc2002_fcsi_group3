package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.model.User;

import java.util.List;
import java.util.Optional;

/**
 * The UserService class provides functionality for managing users.
 * It includes methods to search for users by their NRIC.
 */
public class UserService {

    private final List<User> users;

    /**
     * Constructs a UserService with the specified list of users.
     *
     * @param users the list of users to manage
     */
    public UserService(List<User> users) {
        this.users = users;
    }

    /**
     * Finds a user by their NRIC.
     *
     * @param nric the NRIC of the user to find
     * @return an Optional containing the user if found, or empty if not found
     */
    public Optional<User> findByNRIC(String nric) {
        if (nric == null) return Optional.empty();

        return users.stream()
                .filter(u -> u.getNric().equalsIgnoreCase(nric))
                .findFirst();
    }
}
