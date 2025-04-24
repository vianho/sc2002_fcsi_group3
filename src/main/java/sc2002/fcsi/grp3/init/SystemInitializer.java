package sc2002.fcsi.grp3.init;

import sc2002.fcsi.grp3.io.ConfigLoader;
import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.io.CSVDataLoader;
import sc2002.fcsi.grp3.service.SystemSaver;

/**
 * The SystemInitializer class is responsible for initializing the system.
 * It loads data from external sources, sets up controllers and views, and registers a shutdown hook to save the system state.
 */
public class SystemInitializer {

    private final DataStore dataStore;
    private final ConfigLoader config;
    private final ViewInitializer viewInitializer;
    private final ControllerInitializer controllerInitializer;
    private final SystemSaver systemSaver;

    /**
     * Constructs a SystemInitializer and initializes the required components.
     * Registers a shutdown hook to save the system state upon termination.
     */
    public SystemInitializer() {
        this.dataStore = DataStore.getInstance();
        this.config = new ConfigLoader("config.properties");
        this.viewInitializer = new ViewInitializer();
        this.controllerInitializer = new ControllerInitializer(dataStore, viewInitializer);
        this.systemSaver = new SystemSaver(dataStore, config);
        registerShutdownHook();
    }

    /**
     * Loads data from CSV files into the DataStore.
     * The file paths are retrieved from the configuration file.
     */
    private void loadData() {
        String usersPath = config.get("usersFile");
        String projectsPath = config.get("projectsFile");
        String applicationsPath = config.get("applicationsFile");
        String enquiriesPath = config.get("enquiriesFile");
        String bookingsPath = config.get("bookingsFile");
        String registrationsPath = config.get("registrationsFile");



        CSVDataLoader loader = new CSVDataLoader(
                usersPath,
                projectsPath,
                applicationsPath,
                enquiriesPath,
                bookingsPath,
                registrationsPath);
        dataStore.setUsers(loader.loadUsers());
        dataStore.setProjects(loader.loadProjects());
        dataStore.setApplications(loader.loadApplications());
        dataStore.setEnquiries(loader.loadEnquiries());
        dataStore.setBookings(loader.loadBookings());
        dataStore.setRegistrations(loader.loadRegistrations());

        System.out.println("[SystemInitializer] Loaded " + dataStore.getUsers().size() + " users.");
        System.out.println("[SystemInitializer] Loaded " + dataStore.getProjects().size() + " projects.");
        System.out.println("[SystemInitializer] Loaded " + dataStore.getApplications().size() + " applications.");
        System.out.println("[SystemInitializer] Loaded " + dataStore.getEnquiries().size() + " enquiries.");
        System.out.println("[SystemInitializer] Loaded " + dataStore.getBookings().size() + " bookings.");
        System.out.println("[SystemInitializer] Loaded " + dataStore.getRegistrations().size() + " registrations.");
    }

    /**
     * Registers a shutdown hook to save the system state when the application terminates.
     */
    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("[SystemSaver] Saving system state...");
            systemSaver.saveAll();
        }));
    }

    /**
     * Starts the system by loading data and launching the main menu controller.
     */
    public void startSystem() {
        loadData();
        controllerInitializer.getMainMenuController().start();
    }
}
