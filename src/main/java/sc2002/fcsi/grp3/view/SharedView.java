package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.view.helper.Prompter;

/**
 * The SharedView class provides common methods for displaying messages, menus, and prompts.
 * It is used across different views for shared functionalities.
 */
public class SharedView extends BaseView {

    /**
     * Constructs a SharedView with the specified prompter.
     *
     * @param prompt the prompter used for user input and output
     */
    public SharedView(Prompter prompt) {
        super(prompt);
    }

    /**
     * Displays a title in the view.
     *
     * @param title the title to display
     */
    public void showTitle(String title) {
        prompt.showTitle(title);
    }

    /**
     * Waits for the user to press Enter to continue.
     */
    public void pressEnterToContinue() {
        prompt.pressEnterToContinue();
    }

    /**
     * Displays a menu with options and prompts the user to select a choice.
     *
     * @param title   the title of the menu
     * @param options the menu options
     * @return the user's choice
     */
    public int showMenuAndGetChoice(String title, String[] options) {
        return prompt.menuPromptInt(title, options, "> ");
    }

    /**
     * Displays an error message for an invalid choice.
     */
    public void showInvalidChoice() {
        showError("Invalid choice. Please try again.");
    }

    /**
     * Prompts the user for confirmation with a yes/no question.
     *
     * @param msg the confirmation message
     * @return true if the user confirms, false otherwise
     */
    public boolean getConfirmation(String msg) {
        return prompt.confirm(msg);
    }

    /**
     * Clears the console or view.
     */
    public void clear() {
        prompt.clear();
    }
}
