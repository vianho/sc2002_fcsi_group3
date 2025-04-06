package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;

public class ApplicantApplicationPermission implements ApplicationPermission {
    @Override
    public boolean canCreateApplication(User user, Project project) {
        if (user.getAge() >= 35 && user.getMaritalStatus() == MaritalStatus.SINGLE) {
            boolean hasEligibleFlat = project.getFlats()
                    .stream()
                    .anyMatch(flat -> flat.getType().equals(FlatType.TWO_ROOM));
            return hasEligibleFlat && project.isVisible();
        }
        return project.isVisible() && user.getAge() >= 21 && user.getMaritalStatus() == MaritalStatus.MARRIED;
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
