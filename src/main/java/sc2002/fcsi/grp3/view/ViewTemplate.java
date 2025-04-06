package sc2002.fcsi.grp3.view;

public class ViewTemplate {
    private final SharedPromptView prompt;

    public ViewTemplate(SharedPromptView prompt) {
        this.prompt = prompt;
    }

    public int showMainMenuAndGetChoice() {
        String[] options = {"Option 1","Option 2","Logout"};
        return prompt.menuPrompt("NewRole Menu", options, "> ");
    }

    // Add specific prompt methods here
    public void showMessage(String message) {
        prompt.showMessage(message);
    }

    public String promptInput(String question) {
        return prompt.promptString(question);
    }
}
