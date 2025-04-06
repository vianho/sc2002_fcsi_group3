package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;

public interface BookingPermission {
    boolean canManageBooking(User user, Project project);
    boolean canViewBooking(User user, Project project);
}
