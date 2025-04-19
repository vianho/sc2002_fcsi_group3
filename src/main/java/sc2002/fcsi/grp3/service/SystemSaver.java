package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.io.CSVDataLoader;
import sc2002.fcsi.grp3.io.ConfigLoader;

public class SystemSaver {
    private final DataStore dataStore;
    private final ConfigLoader config;

    public SystemSaver(DataStore dataStore, ConfigLoader config) {
        this.dataStore = dataStore;
        this.config = config;
    }

    public void saveAll() {
        CSVDataLoader.saveUsers(config.get("usersFile"), dataStore.getUsers());
        CSVDataLoader.saveProjects(config.get("projectsFile"), dataStore.getProjects());
        CSVDataLoader.saveApplications(config.get("applicationsFile"), dataStore.getApplications());
        CSVDataLoader.saveEnquiries(config.get("enquiriesFile"), dataStore.getEnquiries());
    }
}