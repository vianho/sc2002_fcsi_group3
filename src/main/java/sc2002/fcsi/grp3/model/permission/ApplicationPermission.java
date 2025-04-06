package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.Application;

public interface ApplicationPermission {
    boolean canCreateApplication(User user, Project project);
    boolean canViewApplication(User user, Application application);
    boolean canWithdrawApplication(User user, Application application);
    boolean canViewProjectApplications(User user, Project project);
    boolean canApproveApplications(User user, Project project);
}
