package sc2002.fcsi.grp3.view;

/**
 * The OfficerViews record encapsulates all views accessible to an officer.
 *
 * @param sharedView      the shared view for common functionalities
 * @param projectView     the view for managing and viewing projects
 * @param applicationView the view for managing applications
 * @param enquiryView     the view for managing enquiries
 * @param accountView     the view for managing account-related functionalities
 * @param bookingView     the view for managing bookings
 */
public record OfficerViews(
        SharedView sharedView,
        ProjectView projectView,
        ApplicationView applicationView,
        EnquiryView enquiryView,
        AccountView accountView,
        BookingView bookingView
) {}
