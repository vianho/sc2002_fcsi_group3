package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.view.helper.Prompter;

public class ViewTemplate {
    private final Prompter prompt;

    public ViewTemplate(Prompter prompt) {
        this.prompt = prompt;
    }

    public int showMainMenuAndGetChoice() {
        String[] options = {"Option 1","Option 2","Logout"};
        return prompt.menuPromptInt("NewRole Menu", options, "> ");
    }

    // Add specific prompt methods here
    public void showMessage(String message) {
        prompt.showMessage(message);
    }

    public String promptInput(String question) {
        return prompt.promptString(question);
    }
}
