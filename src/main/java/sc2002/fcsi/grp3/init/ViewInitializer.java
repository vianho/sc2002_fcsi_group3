package sc2002.fcsi.grp3.init;

import sc2002.fcsi.grp3.view.*;
import sc2002.fcsi.grp3.view.helper.Prompter;

/**
 * The ViewInitializer class is responsible for initializing and providing access to various views in the application.
 * It uses a shared prompter to ensure consistent user input and output across all views.
 */
public class ViewInitializer {

    private final Prompter sharedPrompt = new Prompter();

    /**
     * Retrieves the SharedView instance.
     *
     * @return the SharedView
     */
    public SharedView getSharedView() {
        return new SharedView(sharedPrompt);
    }

    /**
     * Retrieves the MainMenuView instance.
     *
     * @return the MainMenuView
     */
    public MainMenuView getMainMenuView() {
        return new MainMenuView(sharedPrompt);
    }

    /**
     * Retrieves the AuthView instance.
     *
     * @return the AuthView
     */
    public AuthView getAuthView() {
        return new AuthView(sharedPrompt);
    }

    /**
     * Retrieves the ApplicantViews instance, which contains views specific to applicants.
     *
     * @return the ApplicantViews
     */
    public ApplicantViews getApplicantViews() {
        return new ApplicantViews(
                new SharedView(sharedPrompt),
                new ProjectView(sharedPrompt),
                new ApplicationView(sharedPrompt),
                new EnquiryView(sharedPrompt),
                new AccountView(sharedPrompt)
        );
    }

    /**
     * Retrieves the OfficerViews instance, which contains views specific to officers.
     *
     * @return the OfficerViews
     */
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

    /**
     * Retrieves the ManagerView instance.
     *
     * @return the ManagerView
     */
    public ManagerView getManagerView() {
        return new ManagerView(sharedPrompt);
    }

    /**
     * Retrieves the EnquiryView instance.
     *
     * @return the EnquiryView
     */
    public EnquiryView getEnquiryView() {
        return new EnquiryView(sharedPrompt);
    }

    /**
     * Retrieves the AccountView instance.
     *
     * @return the AccountView
     */
    public AccountView getAccountView() { return new AccountView(sharedPrompt); }

    /**
     * Retrieves the ReportView instance.
     *
     * @return the ReportView
     */
    public ReportView getReportView() { return new ReportView(sharedPrompt); }
}

