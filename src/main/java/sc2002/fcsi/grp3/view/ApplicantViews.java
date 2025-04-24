package sc2002.fcsi.grp3.view;

/**
 * The ApplicantViews record encapsulates all views accessible to an applicant.
 *
 * @param sharedView      the shared view for common functionalities
 * @param projectView     the view for managing and viewing projects
 * @param applicationView the view for managing applications
 * @param enquiryView     the view for managing enquiries
 * @param accountView     the view for managing account-related functionalities
 */
public record ApplicantViews(
        SharedView sharedView,
        ProjectView projectView,
        ApplicationView applicationView,
        EnquiryView enquiryView,
        AccountView accountView
) {}
