package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.Enquiry;

public interface EnquiryPermission {
    boolean canCreateEnquiry(User user, Project project);
    boolean canUpdateEnquiry(User user, Enquiry enquiry);
    boolean canViewProjectEnquiries(User user, Project project);
    boolean canReplyProjectEnquiries(User user, Project project);
    boolean canViewAllEnquiries(User user);
}
