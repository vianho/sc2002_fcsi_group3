package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.view.helper.Prompter;


public class AuthView extends BaseView {
    public AuthView(Prompter prompt) {
        super(prompt);
    }

    public void showHeader() {
        prompt.showTitle("Login");
    }

    public String promptNRIC() {
        return prompt.promptString("Enter NRIC: ");
    }

    public String promptPassword() {
        return prompt.promptString("Enter Password: ");
    }

    public void showLoginSuccess(User user) {
        prompt.showMessage("Login successful. Welcome, " + user.getName() + "!");
    }

    public void showLoginFailure() {
        prompt.showError("Invalid NRIC or password.");
    }
}