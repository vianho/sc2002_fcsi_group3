package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;

import java.util.List;

public class ApplicationService {
    private final DataStore db;

    public ApplicationService(DataStore db) {
        this.db = db;
    }

    public List<Application> getApplications() {
        return db.getApplications();
    }

    public boolean apply(User user, Project project) {
        return true;
    }
}
