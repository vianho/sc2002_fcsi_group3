package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Registration;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;

import java.time.LocalDate;
import java.util.List;

public class RegistrationService {
    private final DataStore db;

    public RegistrationService(DataStore db) {
        this.db = db;
    }

    public void Join(Project project, User officer, LocalDate today){

        Registration reg = new Registration(project, officer, today);
        db.addRegistration(reg);


    }



    public String getStatus (User user){

        Registration found = null;

        for(Registration reg : db.getRegistrations()){
            if(reg.getApplicant() == user){
                found = reg;
            }
        }

        if(found != null)
            //System.out.println(found.getStatus());
            return found.getStatus().toString();
        else
            return "NIL";

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

    public String getProjectName (User user){

        Registration found = null;

        for(Registration reg : db.getRegistrations()){
            if(reg.getApplicant() == user){
                found = reg;
            }
        }

        if(found != null)
            //System.out.println(found.getProject().getName());
            return found.getProject().getName();
        else{
            return "None found";
        }
    }

    public Project getHandledProject (String userNRIC){

        Project found = null;

        for (Project proj : db.getProjects()) {
            List<String> lNRIC = proj.getOfficerNrics();
            for (String nric : lNRIC){
                if(nric.equals(userNRIC)){
                    found = proj;
                }
            }

        }

//        for (Registration reg : db.getRegistrations()) {
//            if (reg.getApplicant() == user) {
//                if (reg.getStatus() == RegistrationStatus.APPROVED) {
//                    found = reg.getProject();
//                }
//            }
//        }

        return found;
    }
}
