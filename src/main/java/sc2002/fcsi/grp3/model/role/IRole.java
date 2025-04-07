package sc2002.fcsi.grp3.model.role;

import sc2002.fcsi.grp3.model.permission.*;

public interface IRole {
    String getRoleName();
    IApplicationPermission getApplicationPermission();
//    IBookingPermission getBookingPermission();
//    IEnquiryPermission getEnquiryPermission();
//    IProjectPermission getProjectPermission();
//    IRegistrationPermission getRegistrationPermission();
}
