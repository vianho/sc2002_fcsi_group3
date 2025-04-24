package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.enums.FlatType;

/**
 * The IApplicationPermission interface defines the permissions for users regarding applications and projects.
 * Implementations of this interface specify the rules for different user roles.
 */
public interface IApplicationPermission {

    /**
     * Determines if the user can apply for a flat in the specified project and flat type.
     *
     * @param user     the user attempting to apply
     * @param project  the project for which the application is being made
     * @param flatType the type of flat being applied for
     * @return true if the user can apply, false otherwise
     */
    boolean canApplyForFlat(User user, Project project, FlatType flatType);

    /**
     * Determines if the user can view the specified application.
     *
     * @param user       the user attempting to view the application
     * @param application the application being viewed
     * @return true if the user can view the application, false otherwise
     */
    boolean canViewApplication(User user, Application application);

    /**
     * Determines if the user can withdraw the specified application.
     *
     * @param user       the user attempting to withdraw the application
     * @param application the application being withdrawn
     * @return true if the user can withdraw the application, false otherwise
     */
    boolean canWithdrawApplication(User user, Application application);

    /**
     * Determines if the user can view all applications for the specified project.
     *
     * @param user    the user attempting to view project applications
     * @param project the project whose applications are being viewed
     * @return true if the user can view project applications, false otherwise
     */
    boolean canViewProjectApplications(User user, Project project);

    /**
     * Determines if the user can approve applications for the specified project.
     *
     * @param user    the user attempting to approve applications
     * @param project the project whose applications are being approved
     * @return true if the user can approve applications, false otherwise
     */
    boolean canApproveApplications(User user, Project project);
}
