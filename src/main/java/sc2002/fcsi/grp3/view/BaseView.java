package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.view.helper.Prompter;

import java.time.format.DateTimeFormatter;

/**
 * The BaseView class serves as a base class for all views in the application.
 * It provides common functionality for displaying messages, errors, and warnings.
 */
public abstract class BaseView {

    protected final Prompter prompt;
    final static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Constructs a BaseView with the specified prompter.
     *
     * @param prompt the prompter used for user input and output
     */
    public BaseView(Prompter prompt) {
        this.prompt = prompt;
    }

    /**
     * Displays a general message to the user.
     *
     * @param msg the message to display
     */
    public void showMessage(String msg) {
        prompt.showMessage(msg);
    }

    /**
     * Displays an error message to the user.
     *
     * @param msg the error message to display
     */
    public void showError(String msg) {
        prompt.showError(msg);
    }

    /**
     * Displays a warning message to the user.
     *
     * @param msg the warning message to display
     */
    public void showWarning(String msg) {
        prompt.showWarning(msg);
    }
}
