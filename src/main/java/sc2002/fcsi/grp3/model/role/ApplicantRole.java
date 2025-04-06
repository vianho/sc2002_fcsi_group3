package sc2002.fcsi.grp3.model.role;

import sc2002.fcsi.grp3.model.*;
import sc2002.fcsi.grp3.model.enums.*;
import sc2002.fcsi.grp3.model.permission.ApplicantApplicationPermission;
import sc2002.fcsi.grp3.model.permission.ApplicationPermission;

public class ApplicantRole implements IRole {
    private static final String roleName = "Applicant";
    private final ApplicationPermission applicationPermission = new ApplicantApplicationPermission();

    public String getRoleName() {
        return roleName;
    }

    @Override
    public ApplicationPermission getApplicationPermission() {
        return applicationPermission;
    }
}