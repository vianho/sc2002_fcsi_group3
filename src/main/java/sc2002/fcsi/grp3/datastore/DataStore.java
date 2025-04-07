package sc2002.fcsi.grp3.datastore;

import sc2002.fcsi.grp3.model.*;
import java.util.List;
import java.util.ArrayList;

public final class DataStore {
    private static DataStore instance;
    private List<User> users = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private List<Application> applications = new ArrayList<>();

    private DataStore() {
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public List<Application> getApplications() { return applications; }

    public void setApplications(List<Application> applications) { this.applications = applications; }
}
