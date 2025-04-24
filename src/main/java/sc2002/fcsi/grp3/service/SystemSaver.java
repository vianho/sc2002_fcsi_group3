package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.io.CSVDataLoader;
import sc2002.fcsi.grp3.io.ConfigLoader;

/**
 * The SystemSaver class is responsible for saving the system's state to CSV files.
 * It uses the DataStore for accessing data and the ConfigLoader for file paths.
 */
public class SystemSaver {

    private final DataStore dataStore;
    private final ConfigLoader config;

    /**
     * Constructs a SystemSaver with the specified data store and configuration loader.
     *
     * @param dataStore the data store containing the system's data
     * @param config    the configuration loader for retrieving file paths
     */
    public SystemSaver(DataStore dataStore, ConfigLoader config) {
        this.dataStore = dataStore;
        this.config = config;
    }

    /**
     * Saves all system data to their respective CSV files.
     */
    public void saveAll() {
        CSVDataLoader.saveUsers(config.get("usersFile"), dataStore.getUsers());
        CSVDataLoader.saveProjects(config.get("projectsFile"), dataStore.getProjects());
        CSVDataLoader.saveApplications(config.get("applicationsFile"), dataStore.getApplications());
        CSVDataLoader.saveEnquiries(config.get("enquiriesFile"), dataStore.getEnquiries());
        CSVDataLoader.saveBookings(config.get("bookingsFile"), dataStore.getBookings());
        CSVDataLoader.saveRegistrations(config.get("registrationsFile"), dataStore.getRegistrations());

    }
}