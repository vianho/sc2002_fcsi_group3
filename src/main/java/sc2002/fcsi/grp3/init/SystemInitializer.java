package sc2002.fcsi.grp3.init;

import sc2002.fcsi.grp3.io.ConfigLoader;
import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.io.CSVDataLoader;

public class SystemInitializer {
    public static void initialize(DataStore dataStore, ConfigLoader config) {
        // Load file paths from config
        String usersPath = config.get("usersFile");
        String projectsPath = config.get("projectsFile");

        // Use CSVDataLoader
        CSVDataLoader dataLoader = new CSVDataLoader(usersPath, projectsPath);

        // Load and inject into DataStore
        dataStore.setUsers(dataLoader.loadUsers());
        dataStore.setProjects(dataLoader.loadProjects());

        // You can also validate here if needed
        System.out.println("[SystemInitializer] Loaded " + dataStore.getUsers().size() + " users.");
    }
}
