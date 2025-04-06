package sc2002.fcsi.grp3.model.role;

import sc2002.fcsi.grp3.model.permission.ApplicationPermission;
import sc2002.fcsi.grp3.model.permission.OfficerApplicationPermission;

public class OfficerRole implements IRole {
    private static final String roleName = "Officer";
    private final ApplicationPermission applicationPermission = new OfficerApplicationPermission();

    public String getRoleName() {
        return roleName;
    }

    @Override
    public ApplicationPermission getApplicationPermission() {
        return applicationPermission;
    }
}
