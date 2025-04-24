package sc2002.fcsi.grp3.io;

import sc2002.fcsi.grp3.model.*;

import java.util.List;

/**
 * The IDataLoader interface defines methods for loading data into the application.
 * Implementations of this interface are responsible for loading users and projects.
 */
public interface IDataLoader {

    /**
     * Loads user data and returns a list of users.
     *
     * @return a list of users
     */
    List<User> loadUsers();

    /**
     * Loads project data and returns a list of projects.
     *
     * @return a list of projects
     */
    List<Project> loadProjects();
}
