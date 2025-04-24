package sc2002.fcsi.grp3;

import sc2002.fcsi.grp3.init.SystemInitializer;

/**
 * The {@code Main} class is the entry point of the application.
 * It initializes and starts the system.
 */
public class Main {

    /**
     * The main method which serves as the entry point of the application.
     *
     * @param args Command-line arguments passed to the program (not used here)
     */
    public static void main(String[] args) {
        System.out.println("Working dir: " + System.getProperty("user.dir"));
        SystemInitializer systemInitializer = new SystemInitializer();
        systemInitializer.startSystem();
    }
}