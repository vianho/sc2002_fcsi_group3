package sc2002.fcsi.grp3.init;

import sc2002.fcsi.grp3.view.*;

public class ViewInitializer {
    private final SharedPromptView sharedPrompt = new SharedPromptView();

    public SharedPromptView getSharedPrompt() {
        return sharedPrompt;
    }

    public MainMenuView getMainMenuView() {
        return new MainMenuView(sharedPrompt);
    }

    public LoginView getLoginView() {
        return new LoginView(sharedPrompt);
    }

    public ApplicantView getApplicantView() {
        return new ApplicantView(sharedPrompt);
    }

    public OfficerView getOfficerView() {
        return new OfficerView(sharedPrompt);
    }

    public ManagerView getManagerView() {
        return new ManagerView(sharedPrompt);
    }
}

