package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.enums.FlatType;

public interface IApplicationPermission {
    boolean canApplyForFlat(User user, Project project, FlatType flatType);
    boolean canViewApplication(User user, Application application);
    boolean canWithdrawApplication(User user, Application application);
    boolean canViewProjectApplications(User user, Project project);
    boolean canApproveApplications(User user, Project project);
}
