package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Registration;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;

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

    public void getStatus (User user){

        Registration found = null;

        for(Registration reg : db.getRegistrations()){
            if(reg.getApplicant() == user){
                found = reg;
            }
        }

        if(found != null)
            System.out.println(found.getStatus());
    }

    public void setStatus (User user){

        Registration found = null;

        for(Registration reg : db.getRegistrations()){
            if(reg.getApplicant() == user){
                found = reg;
            }
        }

        if(found != null)
            found.setStatus(RegistrationStatus.APPROVED);
    }

    public void getProjectName (User user){

        Registration found = null;

        for(Registration reg : db.getRegistrations()){
            if(reg.getApplicant() == user){
                found = reg;
            }
        }

        if(found != null)
            System.out.println(found.getProject().getName());
    }

    public Project getHandledProject (User user){

        Project found = null;

        for(Registration reg : db.getRegistrations()){
            if(reg.getApplicant() == user){
                if(reg.getStatus() == RegistrationStatus.APPROVED){
                    found = reg.getProject();
                }
            }
        }

        return found;
    }



}
