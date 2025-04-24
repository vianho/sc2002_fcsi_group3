package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.permission.IApplicationPermission;
import sc2002.fcsi.grp3.service.result.ActionResult;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The ApplicationService class provides functionality for managing applications.
 * It includes methods for applying, withdrawing, and retrieving applications.
 */
public class ApplicationService {

    private final IApplicationPermission permission;
    private final DataStore db;

    /**
     * Constructs an ApplicationService with the specified permission and data store.
     *
     * @param permission the permission handler for application-related actions
     * @param db         the data store containing application data
     */
    public ApplicationService(IApplicationPermission permission, DataStore db) {
        this.permission = permission;
        this.db = db;
    }

    /**
     * Retrieves all applications submitted by the specified user.
     *
     * @param user the user whose applications are to be retrieved
     * @return a list of applications submitted by the user
     */
    public List<Application> getApplicationsFor(User user) {
        return db.getApplications()
                .stream()
                .filter(app -> app.getApplicant().getNric().equalsIgnoreCase(user.getNric()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the active application for the specified user, if any.
     * An application is considered active if its status is PENDING or SUCCESSFUL.
     *
     * @param user the user whose active application is to be retrieved
     * @return an Optional containing the active application, or empty if none exists
     */
    public Optional<Application> getActiveApplicationFor(User user) {
        return getApplicationsFor(user).stream()
                .filter(Application::isActive)
                .findFirst();
    }

    /**
     * Submits an application for a flat in the specified project and flat type.
     *
     * @param user     the user submitting the application
     * @param project  the project for which the application is being made
     * @param flatType the type of flat being applied for
     * @return an ActionResult containing the application if successful, or an error message if not
     */
    public ActionResult<Application> apply(User user, Project project, FlatType flatType) {
        if (permission.canApplyForFlat(user, project, flatType)) {
            Application app = new Application(project, user, flatType);
            db.addApplication(app);
            return ActionResult.success("Application for " + flatType.getDisplayName() + " flat in project " +project.getName() + " submitted", app);
        }
        return ActionResult.failure("You do not have permission to apply for a " + flatType.getDisplayName() + " flat in project " +project.getName() + " submitted");
    }

    /**
     * Finds an application by the applicant's NRIC.
     *
     * @param nric the NRIC of the applicant
     * @return the application if found, or null if not found
     */
    public Application findApplication(String nric){
        Application found = null;
        for(Application app : db.getApplications()){
            if(app.getApplicant().getNric().equals(nric)) {
                found = app;
                break;
            }
        }
        return found;
    }

    /**
     * Submits a withdrawal request for the specified application.
     *
     * @param user        the user submitting the withdrawal request
     * @param application the application to be withdrawn
     * @return an ActionResult indicating success or failure of the withdrawal request
     */
    public ActionResult<Application> withdraw(User user, Application application) {
        if (permission.canWithdrawApplication(user, application)) {
            application.setStatus(ApplicationStatus.WITHDRAWAL_REQUESTED);
            return ActionResult.success("Withdrawal request submitted.", application);
        }
        return ActionResult.failure("You are not allowed to withdraw this application");
    }
}
