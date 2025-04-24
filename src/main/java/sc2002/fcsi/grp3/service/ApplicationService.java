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

public class ApplicationService {
    private final IApplicationPermission permission;
    private final DataStore db;

    public ApplicationService(IApplicationPermission permission, DataStore db) {
        this.permission = permission;
        this.db = db;
    }

    public List<Application> getApplicationsFor(User user) {
        return db.getApplications()
                .stream()
                .filter(app -> app.getApplicant().getNric().equalsIgnoreCase(user.getNric()))
                .collect(Collectors.toList());
    }

    public Optional<Application> getActiveApplicationFor(User user) {
        return getApplicationsFor(user).stream()
                .filter(Application::isActive)
                .findFirst();
    }

    public ActionResult<Application> apply(User user, Project project, FlatType flatType) {
        if (permission.canApplyForFlat(user, project, flatType)) {
            Application app = new Application(project, user, flatType);
            db.addApplication(app);
            return ActionResult.success("Application for " + flatType.getDisplayName() + " flat in project " +project.getName() + " submitted", app);
        }
        return ActionResult.failure("You do not have permission to apply for a " + flatType.getDisplayName() + " flat in project " +project.getName() + " submitted");
    }

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

    public ActionResult<Application> withdraw(User user, Application application) {
        if (permission.canWithdrawApplication(user, application)) {
            application.setStatus(ApplicationStatus.WITHDRAWAL_REQUESTED);
            return ActionResult.success("Withdrawal request submitted.", application);
        }
        return ActionResult.failure("You are not allowed to withdraw this application");
    }
}
