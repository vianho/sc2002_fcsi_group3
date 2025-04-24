package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;

public class ManagerApplicationPermission implements IApplicationPermission {
    @Override
    public boolean canApplyForFlat(User user, Project project, FlatType flatType) {
        return false;
    };

    @Override
    public boolean canViewApplication(User user, Application application) {
        return true;
    };

    @Override
    public boolean canWithdrawApplication(User user, Application application) {
        return false;
    };

    @Override
    public boolean canViewProjectApplications(User user, Project project) {
        return project.getManagerNric().equals(user.getNric());
    };

    @Override
    public boolean canApproveApplications(User user, Project project) {
        return project.getManagerNric().equals(user.getNric());
    };
}
