package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationService {
    private final DataStore db;

    public ApplicationService(DataStore db) {
        this.db = db;
    }

    public List<Application> getOwnApplications(User user) {
        return db.getApplications()
                .stream()
                .filter(app -> app.getApplicant().getNric().equalsIgnoreCase(user.getNric()))
                .collect(Collectors.toList());
    }

    public boolean canApply (User user, Project project, FlatType flatType){
        return project.hasAvailableFlatType(flatType) && flatType.isEligible(user);
    }

    public boolean apply(User user, Project project, FlatType flatType) {
        if (canApply(user, project, flatType)) {
            Application app = new Application(project, user, flatType);
            db.addApplication(app);
            return true;
        }
        return false;
    }
}
