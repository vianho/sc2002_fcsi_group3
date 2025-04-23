package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;

public class ApplicantApplicationPermission implements IApplicationPermission {
    @Override
    public boolean canApplyForFlat(User user, Project project, FlatType flatType) {
        return project.isVisible() && project.hasAvailableFlatType(flatType) && flatType.isEligible(user);
    };

    @Override
    public boolean canViewApplication(User user, Application application) {
        return application.getApplicant().getNric().equals(user.getNric());
    };

    @Override
    public boolean canWithdrawApplication(User user, Application application) {
        return application.getApplicant().getNric().equals(user.getNric());
    };
    @Override
    public boolean canViewProjectApplications(User user, Project project) {
        return false;
    };

    @Override
    public boolean canApproveApplications(User user, Project project) {
        return false;
    };
}
