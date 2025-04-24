package sc2002.fcsi.grp3.init;

import sc2002.fcsi.grp3.view.*;
import sc2002.fcsi.grp3.view.helper.Prompter;

public class ViewInitializer {
    private final Prompter sharedPrompt = new Prompter();

    public SharedView getSharedView() {
        return new SharedView(sharedPrompt);
    }

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
                new EnquiryView(sharedPrompt),
                new AccountView(sharedPrompt)
        );
    }

    public OfficerViews getOfficerViews() {
        return new OfficerViews(
            new SharedView(sharedPrompt),
            new ProjectView(sharedPrompt),
            new ApplicationView(sharedPrompt),
            new EnquiryView(sharedPrompt),
            new AccountView(sharedPrompt),
            new BookingView(sharedPrompt)
        );
    }

    public ManagerView getManagerView() {
        return new ManagerView(sharedPrompt);
    }

    public EnquiryView getEnquiryView() {
        return new EnquiryView(sharedPrompt);
    }

    public AccountView getAccountView() { return new AccountView(sharedPrompt); }
}

