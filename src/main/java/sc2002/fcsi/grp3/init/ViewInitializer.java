package sc2002.fcsi.grp3.init;

import sc2002.fcsi.grp3.view.*;
import sc2002.fcsi.grp3.view.helper.Prompter;

public class ViewInitializer {
    private final Prompter sharedPrompt = new Prompter();

    public MainMenuView getMainMenuView() {
        return new MainMenuView(sharedPrompt);
    }

    public AuthView getAuthView() {
        return new AuthView(sharedPrompt);
    }

    public ApplicantViews getApplicantViews() {
        return new ApplicantViews(
                new SharedView(sharedPrompt),
                new ProjectView(sharedPrompt),
                new ApplicationView(sharedPrompt),
                new AccountView(sharedPrompt)
        );
    }

    public OfficerView getOfficerView() {
        return new OfficerView(sharedPrompt);
    }

    public ManagerView getManagerView() {
        return new ManagerView(sharedPrompt);
    }

    public EnquiryViewApplicant getEnquiryView() { return new EnquiryViewApplicant(sharedPrompt); }

    public EnquiryViewOfficer getEnquiryViewOfficer() { return new EnquiryViewOfficer(sharedPrompt); }

    public EnquiryViewManager getEnquiryViewManager() { return new EnquiryViewManager(sharedPrompt); }

    public ProjectView getProjectView() { return new ProjectView(sharedPrompt); }
}

