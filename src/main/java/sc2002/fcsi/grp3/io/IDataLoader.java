package sc2002.fcsi.grp3.io;

import sc2002.fcsi.grp3.model.*;

import java.util.List;

public interface IDataLoader {
    List<User> loadUsers();
    List<Project> loadProjects();
}
