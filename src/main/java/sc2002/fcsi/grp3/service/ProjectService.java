package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectService {
    private final DataStore db;

    public ProjectService(DataStore db) {
        this.db = db;
    }

    public List<Project> getVisibleProjects() {
        return db.getProjects()
                .stream()
                .filter(Project::isVisible)
                .collect(Collectors.toList());
    }

    public Optional<Project> getProjectById(int id) {
        return db.getProjects()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }
}
