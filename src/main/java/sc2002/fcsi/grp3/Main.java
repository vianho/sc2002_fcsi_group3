package sc2002.fcsi.grp3;

import sc2002.fcsi.grp3.controller.MainMenuController;
import sc2002.fcsi.grp3.init.ControllerInitializer;
import sc2002.fcsi.grp3.init.ViewInitializer;
import sc2002.fcsi.grp3.io.ConfigLoader;
import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.init.SystemInitializer;

/**
 * The {@code Main} class is the entry point of the application.
 * This is a basic Hello World example used to demonstrate the structure
 * of a Java application with JavaDoc comments.
 * hello
 */
public class Main {
    /**
     * The main method which serves as the entry point of the application.
     *
     * @param args Command-line arguments passed to the program (not used here)
     */
    public static void main(String[] args) {
        System.out.println("Working dir: " + System.getProperty("user.dir"));
        ConfigLoader config = new ConfigLoader("config.properties");
        DataStore db = DataStore.getInstance();
        SystemInitializer.initialize(db, config);

        ViewInitializer viewInit = new ViewInitializer();
        ControllerInitializer controllerInit = new ControllerInitializer(db, viewInit);

        MainMenuController menu = controllerInit.getMainMenuController();
        menu.start();

    }
}