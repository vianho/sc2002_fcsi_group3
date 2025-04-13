package sc2002.fcsi.grp3.view;

public class ApplicationView {
    private final SharedPromptView prompt;

    public ApplicationView(SharedPromptView prompt) {
        this.prompt = prompt;
    }

    public int showMenuAndGetChoice() {
        String[] options = {"View application", "Withdraw application", "Back"};
        return prompt.menuPrompt("Application Menu", options, "> ");
    }

    public int promptApplicationId(String action) {
        String msg = String.format("Enter Application ID to %s: ",action);
        return prompt.promptInt(msg);
    }
}
