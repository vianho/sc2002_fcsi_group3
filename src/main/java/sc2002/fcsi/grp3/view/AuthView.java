package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.view.helper.Prompter;

/**
 * The AuthView class provides methods for displaying and interacting with authentication-related data.
 * It includes functionality for prompting login credentials and showing login results.
 */
public class AuthView extends BaseView {

    /**
     * Constructs an AuthView with the specified prompter.
     *
     * @param prompt the prompter used for user input and output
     */
    public AuthView(Prompter prompt) {
        super(prompt);
    }

    /**
     * Displays the login header.
     */
    public void showHeader() {
        prompt.showTitle("Login");
    }

    /**
     * Prompts the user to enter their NRIC.
     *
     * @return the entered NRIC
     */
    public String promptNRIC() {
        return prompt.promptString("Enter NRIC: ");
    }

    /**
     * Prompts the user to enter their password.
     *
     * @return the entered password
     */
    public String promptPassword() {
        return prompt.promptString("Enter Password: ");
    }

    /**
     * Displays a success message upon successful login.
     *
     * @param user the user who successfully logged in
     */
    public void showLoginSuccess(User user) {
        prompt.showMessage("Login successful. Welcome, " + user.getName() + "!");
    }

    /**
     * Displays an error message upon failed login.
     */
    public void showLoginFailure() {
        prompt.showError("Invalid NRIC or password.");
    }
}