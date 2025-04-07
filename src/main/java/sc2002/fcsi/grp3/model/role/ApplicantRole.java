package sc2002.fcsi.grp3.model.role;

import sc2002.fcsi.grp3.model.permission.ApplicantApplicationPermission;
import sc2002.fcsi.grp3.model.permission.IApplicationPermission;

public class ApplicantRole implements IRole {
    private static final String roleName = "Applicant";
    private final IApplicationPermission IApplicationPermission = new ApplicantApplicationPermission();

    public String getRoleName() {
        return roleName;
    }

    @Override
    public IApplicationPermission getApplicationPermission() {
        return IApplicationPermission;
    }
}