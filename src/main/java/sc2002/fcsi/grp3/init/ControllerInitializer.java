package sc2002.fcsi.grp3.init;

import sc2002.fcsi.grp3.controller.ControllerFactory;
import sc2002.fcsi.grp3.controller.MainMenuController;
import sc2002.fcsi.grp3.datastore.DataStore;

public class ControllerInitializer {
    private final ControllerFactory factory;

    public ControllerInitializer(DataStore store, ViewInitializer viewInit) {
        this.factory = new ControllerFactory(store, viewInit);
    }

    public MainMenuController getMainMenuController() {
        return factory.createMainMenuController();
    }
}