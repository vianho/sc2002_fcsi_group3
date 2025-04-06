package sc2002.fcsi.grp3.model.role;

import sc2002.fcsi.grp3.model.permission.ApplicationPermission;
import sc2002.fcsi.grp3.model.permission.ManagerApplicationPermission;

public class ManagerRole implements IRole {
    private static final String roleName = "Manager";
    private final ApplicationPermission applicationPermission = new ManagerApplicationPermission();

    public String getRoleName() {
        return roleName;
    }

    @Override
    public ApplicationPermission getApplicationPermission() {
        return applicationPermission;
    }
}
