package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;

public interface IProjectPermission {
    boolean canCreateProject(User user, Project project);
}
