package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.view.helper.Prompter;

public class SharedView extends BaseView {
    public SharedView(Prompter prompt) {
        super(prompt);
    }

    public void showTitle(String title) {
        prompt.showTitle(title);
    }

    public void pressEnterToContinue() {
        prompt.pressEnterToContinue();
    }

    public int showMenuAndGetChoice(String title, String[] options) {
        return prompt.menuPromptInt(title, options, "> ");
    }

    public void showInvalidChoice() {
        showError("Invalid choice. Please try again.");
    }

    public boolean getConfirmation(String msg) {
        return prompt.confirm(msg);
    }

    public void clear() {
        prompt.clear();
    }
}
