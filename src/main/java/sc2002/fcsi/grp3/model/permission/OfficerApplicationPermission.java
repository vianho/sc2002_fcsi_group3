package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;

public class OfficerApplicationPermission extends ApplicantApplicationPermission {
    @Override
    public boolean canCreateApplication(User user, Project project) {
        if (isProjectOfficer(user, project)) {
            return false;
        }
        return super.canCreateApplication(user, project);
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
