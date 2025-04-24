package sc2002.fcsi.grp3.model.permission;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;

/**
 * The OfficerApplicationPermission class defines the permissions for officers regarding applications and projects.
 * It extends the ApplicantApplicationPermission class and overrides specific behaviors for officers.
 */
public class OfficerApplicationPermission extends ApplicantApplicationPermission {

    /**
     * Determines if the officer can apply for a flat in the specified project and flat type.
     * Officers cannot apply for flats in projects they are assigned to as officers.
     *
     * @param user     the officer attempting to apply
     * @param project  the project for which the application is being made
     * @param flatType the type of flat being applied for
     * @return true if the officer can apply, false otherwise
     */
    @Override
    public boolean canApplyForFlat(User user, Project project, FlatType flatType) {
        if (isProjectOfficer(user, project)) {
            return false;
        }
        return super.canApplyForFlat(user, project, flatType);
    }

    /**
     * Determines if the officer can view all applications for the specified project.
     * Officers can view applications for projects they are assigned to as officers.
     *
     * @param user    the officer attempting to view project applications
     * @param project the project whose applications are being viewed
     * @return true if the officer is assigned to the project, false otherwise
     */
    @Override
    public boolean canViewProjectApplications(User user, Project project) {
        return isProjectOfficer(user, project);
    }

    /**
     * Checks if the user is an officer assigned to the specified project.
     *
     * @param user    the user to check
     * @param project the project to check against
     * @return true if the user is an officer for the project, false otherwise
     */
    private boolean isProjectOfficer(User user, Project project) {
        return project.getOfficerNrics()
                .stream()
                .anyMatch(nric -> nric.equalsIgnoreCase(user.getNric()));
    }
}
