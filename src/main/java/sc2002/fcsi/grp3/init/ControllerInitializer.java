package sc2002.fcsi.grp3.init;

import sc2002.fcsi.grp3.controller.ControllerFactory;
import sc2002.fcsi.grp3.controller.MainMenuController;
import sc2002.fcsi.grp3.datastore.DataStore;

/**
 * The ControllerInitializer class is responsible for initializing and providing access to controllers.
 * It uses the ControllerFactory to create controllers with the required dependencies.
 */
public class ControllerInitializer {

    private final ControllerFactory factory;

    /**
     * Constructs a ControllerInitializer with the required dependencies.
     *
     * @param store    the data store containing application data
     * @param viewInit the initializer for setting up views
     */
    public ControllerInitializer(DataStore store, ViewInitializer viewInit) {
        this.factory = new ControllerFactory(store, viewInit);
    }

    /**
     * Retrieves the MainMenuController instance.
     *
     * @return the MainMenuController
     */
    public MainMenuController getMainMenuController() {
        return factory.createMainMenuController();
    }
}