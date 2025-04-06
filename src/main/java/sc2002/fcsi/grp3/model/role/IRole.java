package sc2002.fcsi.grp3.model.role;

import sc2002.fcsi.grp3.model.permission.*;

public interface IRole {
    String getRoleName();
    ApplicationPermission getApplicationPermission();
//    BookingPermission getBookingPermission();
//    EnquiryPermission getEnquiryPermission();
//    ProjectPermission getProjectPermission();
//    RegistrationPermission getRegistrationPermission();
}
