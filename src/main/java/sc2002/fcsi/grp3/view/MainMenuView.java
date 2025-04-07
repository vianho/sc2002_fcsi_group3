package sc2002.fcsi.grp3.view;

public class MainMenuView {
    private final SharedPromptView prompt;

    public MainMenuView(SharedPromptView prompt) {
        this.prompt = prompt;
    }

    public int showMainMenuAndGetChoice() {
        String[] options = {"Login", "Exit"};
        return prompt.menuPrompt("BTO Management System", options, "> ");
    }

    public void showMessage(String message) {
        prompt.showMessage(message);
    }

    public void showExitMessage() {
        prompt.showMessage("Thank you for using the BTO system.");
    }

    public void showInvalidChoice() {
        prompt.showError("Invalid choice. Please try again.");
    }

    public void showUnknownRole() {
        prompt.showError("You have not been assigned a role. Please contact HDB office.");
    }
}
