package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.view.helper.Prompter;

public class MainMenuView extends BaseView {
    public MainMenuView(Prompter prompt) {
        super(prompt);
    }

    public void showExitMessage() {
        showMessage("Thank you for using the BTO system.");
    }

    public void showUnknownRole() {
        showError("You have not been assigned a role. Please contact the HDB office to resolve this.");
    }
}
