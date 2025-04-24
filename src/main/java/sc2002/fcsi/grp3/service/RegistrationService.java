package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Registration;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * The RegistrationService class handles operations related to user registrations.
 * It provides methods to manage and process registrations for projects.
 */
public class RegistrationService {

    private final DataStore db;

    /**
     * Constructs a RegistrationService with the specified data store.
     *
     * @param db the data store containing registration data
     */
    public RegistrationService(DataStore db) {
        this.db = db;
    }

    /**
     * Allows a user to join a project by creating a registration.
     *
     * @param project the project to join
     * @param officer the officer joining the project
     * @param today   the date of registration
     */
    public void Join(Project project, User officer, LocalDate today) {

        Registration reg = new Registration(project, officer, today);
        db.addRegistration(reg);


    }

    /**
     * Retrieves the registration status of the specified user.
     *
     * @param user the user whose registration status is to be retrieved
     * @return the registration status as a string, or "NIL" if no registration is found
     */
    public String getStatus(User user) {

        Registration found = null;

        for (Registration reg : db.getRegistrations()) {
            if (reg.getApplicant() == user) {
                found = reg;
            }
        }

        if (found != null)
            //System.out.println(found.getStatus());
            return found.getStatus().toString();
        else
            return "NIL";

    }

    /**
     * Sets the registration status of the specified user to APPROVED.
     *
     * @param user the user whose registration status is to be updated
     */
    public void setStatus(User user) {

        Registration found = null;

        for (Registration reg : db.getRegistrations()) {
            if (reg.getApplicant() == user) {
                found = reg;
            }
        }

        if (found != null)
            found.setStatus(RegistrationStatus.APPROVED);
    }

    /**
     * Retrieves the name of the project associated with the specified user's registration.
     *
     * @param user the user whose project name is to be retrieved
     * @return the project name, or "None found" if no registration is found
     */
    public String getProjectName(User user) {

        Registration found = null;

        for (Registration reg : db.getRegistrations()) {
            if (reg.getApplicant() == user) {
                found = reg;
            }
        }

        if (found != null)
            //System.out.println(found.getProject().getName());
            return found.getProject().getName();
        else {
            return "None found";
        }
    }

    /**
     * Retrieves the project handled by the specified officer.
     *
     * @param userNRIC the NRIC of the officer
     * @return the project handled by the officer, or null if no project is found
     */
    public Project getHandledProject(String userNRIC) {

        Project found = null;

        for (Project proj : db.getProjects()) {
            List<String> lNRIC = proj.getOfficerNrics();
            for (String nric : lNRIC) {
                if (nric.equals(userNRIC)) {
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
