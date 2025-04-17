package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Registration;
import sc2002.fcsi.grp3.model.User;

import java.time.LocalDate;

public class RegistrationService {
    private final DataStore db;

    public RegistrationService(DataStore db) {
        this.db = db;
    }

    public void Join(Project project, User officer, LocalDate today){
        Registration reg = new Registration(project, officer, today);
        db.addRegistration(reg);
    }



}
