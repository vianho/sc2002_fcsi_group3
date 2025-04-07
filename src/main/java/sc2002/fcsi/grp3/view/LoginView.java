package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.User;

public class LoginView {
    private final SharedPromptView prompt;

    public LoginView(SharedPromptView prompt) {
        this.prompt = prompt;
    }

    public void showHeader() {
        System.out.println("\n=== Login ===");
    }

    public String promptNRIC() {
        return prompt.promptString("Enter NRIC: ");
    }

    public String promptPassword() {
        return prompt.promptString("Enter Password: ");
    }

    public void showSuccess(User user) {
        prompt.showMessage("Login successful. Welcome, " + user.getName() + "!");
    }

    public void showFailure() {
        prompt.showError("Invalid NRIC or password.");
    }
}