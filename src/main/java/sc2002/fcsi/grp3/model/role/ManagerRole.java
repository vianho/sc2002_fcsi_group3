package sc2002.fcsi.grp3.model.role;

import sc2002.fcsi.grp3.model.permission.IApplicationPermission;
import sc2002.fcsi.grp3.model.permission.ManagerApplicationPermission;

public class ManagerRole implements IRole {
    private static final String roleName = "Manager";
    private final IApplicationPermission IApplicationPermission = new ManagerApplicationPermission();

    public String getRoleName() {
        return roleName;
    }

    @Override
    public IApplicationPermission getApplicationPermission() {
        return IApplicationPermission;
    }
}
