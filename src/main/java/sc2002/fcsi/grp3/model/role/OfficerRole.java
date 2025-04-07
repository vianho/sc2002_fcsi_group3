package sc2002.fcsi.grp3.model.role;

import sc2002.fcsi.grp3.model.permission.IApplicationPermission;
import sc2002.fcsi.grp3.model.permission.OfficerApplicationPermission;

public class OfficerRole implements IRole {
    private static final String roleName = "Officer";
    private final IApplicationPermission IApplicationPermission = new OfficerApplicationPermission();

    public String getRoleName() {
        return roleName;
    }

    @Override
    public IApplicationPermission getApplicationPermission() {
        return IApplicationPermission;
    }
}
