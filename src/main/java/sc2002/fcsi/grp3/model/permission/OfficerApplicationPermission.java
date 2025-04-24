package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;

public class OfficerApplicationPermission extends ApplicantApplicationPermission {
    @Override
    public boolean canApplyForFlat(User user, Project project, FlatType flatType) {
        if (isProjectOfficer(user, project)) {
            return false;
        }
        return super.canApplyForFlat(user, project, flatType);
    };

    @Override
    public boolean canViewProjectApplications(User user, Project project) {
        return isProjectOfficer(user, project);
    };

    private boolean isProjectOfficer(User user, Project project) {
        return project.getOfficerNrics()
                .stream()
                .anyMatch(nric -> nric.equalsIgnoreCase(user.getNric()));
    };
}
