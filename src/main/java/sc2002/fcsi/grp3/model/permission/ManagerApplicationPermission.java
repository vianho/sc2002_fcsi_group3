package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;

/**
 * The ManagerApplicationPermission class defines the permissions for managers regarding applications and projects.
 * It implements the IApplicationPermission interface.
 */
public class ManagerApplicationPermission implements IApplicationPermission {

    /**
     * Determines if the manager can apply for a flat in the specified project and flat type.
     * Managers are not allowed to apply for flats.
     *
     * @param user     the manager attempting to apply
     * @param project  the project for which the application is being made
     * @param flatType the type of flat being applied for
     * @return false, as managers cannot apply for flats
     */
    @Override
    public boolean canApplyForFlat(User user, Project project, FlatType flatType) {
        return false;
    }

    /**
     * Determines if the manager can view the specified application.
     * Managers are allowed to view all applications.
     *
     * @param user       the manager attempting to view the application
     * @param application the application being viewed
     * @return true, as managers can view all applications
     */
    @Override
    public boolean canViewApplication(User user, Application application) {
        return true;
    }

    /**
     * Determines if the manager can withdraw the specified application.
     * Managers are not allowed to withdraw applications.
     *
     * @param user       the manager attempting to withdraw the application
     * @param application the application being withdrawn
     * @return false, as managers cannot withdraw applications
     */
    @Override
    public boolean canWithdrawApplication(User user, Application application) {
        return false;
    }

    /**
     * Determines if the manager can view all applications for the specified project.
     * Managers can view applications for projects they manage.
     *
     * @param user    the manager attempting to view project applications
     * @param project the project whose applications are being viewed
     * @return true if the manager manages the project, false otherwise
     */
    @Override
    public boolean canViewProjectApplications(User user, Project project) {
        return project.getManagerNric().equals(user.getNric());
    }

    /**
     * Determines if the manager can approve applications for the specified project.
     * Managers can approve applications for projects they manage.
     *
     * @param user    the manager attempting to approve applications
     * @param project the project whose applications are being approved
     * @return true if the manager manages the project, false otherwise
     */
    @Override
    public boolean canApproveApplications(User user, Project project) {
        return project.getManagerNric().equals(user.getNric());
    }
}
