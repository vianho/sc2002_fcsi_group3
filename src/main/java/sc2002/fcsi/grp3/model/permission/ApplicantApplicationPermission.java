package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;

/**
 * The ApplicantApplicationPermission class defines the permissions for applicants regarding applications and projects.
 * It implements the IApplicationPermission interface.
 */
public class ApplicantApplicationPermission implements IApplicationPermission {

    /**
     * Determines if the user can apply for a flat in the specified project and flat type.
     *
     * @param user     the user attempting to apply
     * @param project  the project for which the application is being made
     * @param flatType the type of flat being applied for
     * @return true if the user can apply, false otherwise
     */
    @Override
    public boolean canApplyForFlat(User user, Project project, FlatType flatType) {
        return project.isVisible() && project.hasAvailableFlatType(flatType) && flatType.isEligible(user);
    }

    /**
     * Determines if the user can view the specified application.
     *
     * @param user       the user attempting to view the application
     * @param application the application being viewed
     * @return true if the user can view the application, false otherwise
     */
    @Override
    public boolean canViewApplication(User user, Application application) {
        return application.getApplicant().getNric().equals(user.getNric());
    }

    /**
     * Determines if the user can withdraw the specified application.
     *
     * @param user       the user attempting to withdraw the application
     * @param application the application being withdrawn
     * @return true if the user can withdraw the application, false otherwise
     */
    @Override
    public boolean canWithdrawApplication(User user, Application application) {
        return application.getApplicant().getNric().equals(user.getNric());
    }

    /**
     * Determines if the user can view all applications for the specified project.
     * Applicants are not allowed to view project applications.
     *
     * @param user    the user attempting to view project applications
     * @param project the project whose applications are being viewed
     * @return false, as applicants cannot view project applications
     */
    @Override
    public boolean canViewProjectApplications(User user, Project project) {
        return false;
    }

    /**
     * Determines if the user can approve applications for the specified project.
     * Applicants are not allowed to approve applications.
     *
     * @param user    the user attempting to approve applications
     * @param project the project whose applications are being approved
     * @return false, as applicants cannot approve applications
     */
    @Override
    public boolean canApproveApplications(User user, Project project) {
        return false;
    }
}
