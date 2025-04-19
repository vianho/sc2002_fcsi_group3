package sc2002.fcsi.grp3.init;

import sc2002.fcsi.grp3.io.ConfigLoader;
import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.io.CSVDataLoader;
import sc2002.fcsi.grp3.service.SystemSaver;

public class SystemInitializer {
    private final DataStore dataStore;
    private final ConfigLoader config;
    private final ViewInitializer viewInitializer;
    private final ControllerInitializer controllerInitializer;
    private final SystemSaver systemSaver;

    public SystemInitializer() {
        this.dataStore = DataStore.getInstance();
        this.config = new ConfigLoader("config.properties");
        this.viewInitializer = new ViewInitializer();
        this.controllerInitializer = new ControllerInitializer(dataStore, viewInitializer);
        this.systemSaver = new SystemSaver(dataStore, config);
        registerShutdownHook();
    }

    private void loadData() {
        String usersPath = config.get("usersFile");
        String projectsPath = config.get("projectsFile");
        String applicationsPath = config.get("applicationsFile");
        String enquiriesPath = config.get("enquiriesFile");



        CSVDataLoader loader = new CSVDataLoader(
                usersPath,
                projectsPath,
                applicationsPath,
                enquiriesPath);
        dataStore.setUsers(loader.loadUsers());
        dataStore.setProjects(loader.loadProjects());
        dataStore.setApplications(loader.loadApplications());
        dataStore.setEnquiries(loader.loadEnquiries());

        System.out.println("[SystemInitializer] Loaded " + dataStore.getUsers().size() + " users.");
        System.out.println("[SystemInitializer] Loaded " + dataStore.getProjects().size() + " projects.");
        System.out.println("[SystemInitializer] Loaded " + dataStore.getApplications().size() + " applications.");
        System.out.println("[SystemInitializer] Loaded " + dataStore.getEnquiries().size() + " enquiries.");
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("[SystemSaver] Saving system state...");
            systemSaver.saveAll();
        }));
    }

    public void startSystem() {
        loadData();
        controllerInitializer.getMainMenuController().start();
    }
}
