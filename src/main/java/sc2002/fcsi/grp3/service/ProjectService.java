package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;

import java.util.*;
import java.util.stream.Collectors;

public class ProjectService {
    private final DataStore db;

    public ProjectService(DataStore db) {
        this.db = db;
    }

    public List<Project> getVisibleProjects(User user) {
        List<Project> visibleProjects = db.getProjects()
                .stream()
                .filter(Project::isVisible)
                .toList();
        if (user.isEligibleFor2R()) {
            return visibleProjects
                    .stream()
                    .filter(p -> p.hasAvailableFlatType(FlatType.TWO_ROOM))
                    .toList();
        }
        return visibleProjects;
    }

    public List<Project> getProjectsManagedBy(String officerNric) {
        return db.getProjects().stream()
                .filter(project -> project.getOfficerNrics().contains(officerNric))
                .toList();
    }

    public List<Project> getAllProjects() {
        return db.getProjects();
    }

    public Optional<Project> getProjectById(int id) {
        return db.getProjects()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public List<Flat> getAvailableFlats(User user, Project project) {
        // get flats that have available units
        List<Flat> availableFlats = project.getFlats()
                .stream()
                .filter(flat -> flat.getUnitsAvailable() > 0)
                .toList();

        // get flats that are available to user
        if (user.isEligibleForAny()) {
            return availableFlats ;
        }

        if (user.isEligibleFor2R()) {
            return availableFlats
                    .stream()
                    .filter(flat -> flat.getType() == FlatType.TWO_ROOM)
                    .toList();
        }

        return List.of();
    }

    public void setProjectVisibility(Project project, boolean newVisibility){
        project.setVisible(newVisibility);
    }

    public void createProject(Project project, String Nric){
        //TO COMPLETE
    }

    public void deleteProject(int projectID){
        //TO COMPLETE
    }
    public void updaetProject(){

    } 




        

    }
}
