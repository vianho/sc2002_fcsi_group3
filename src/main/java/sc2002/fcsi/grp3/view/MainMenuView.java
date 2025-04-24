package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.view.helper.Prompter;

/**
 * The MainMenuView class provides methods for displaying main menu-related messages.
 * It includes functionality for showing exit messages and handling unknown roles.
 */
public class MainMenuView extends BaseView {

    /**
     * Constructs a MainMenuView with the specified prompter.
     *
     * @param prompt the prompter used for user input and output
     */
    public MainMenuView(Prompter prompt) {
        super(prompt);
    }

    /**
     * Displays a message when the user exits the system.
     */
    public void showExitMessage() {
        showMessage("Thank you for using the BTO system.");
    }

    /**
     * Displays an error message when the user has not been assigned a role.
     */
    public void showUnknownRole() {
        showError("You have not been assigned a role. Please contact the HDB office to resolve this.");
    }
}
