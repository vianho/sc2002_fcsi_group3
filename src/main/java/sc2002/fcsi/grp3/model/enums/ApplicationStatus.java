package sc2002.fcsi.grp3.model.enums;

/**
 * The ApplicationStatus enum represents the various statuses an application can have in the system.
 */
public enum ApplicationStatus {

    /**
     * The application is pending and awaiting review.
     */
    PENDING,

    /**
     * The application has been reviewed and marked as successful.
     */
    SUCCESSFUL,

    /**
     * The application has been reviewed and marked as unsuccessful.
     */
    UNSUCCESSFUL,

    /**
     * The application has been approved, and the flat has been booked.
     */
    BOOKED,

    /**
     * The applicant has requested to withdraw the application.
     */
    WITHDRAWAL_REQUESTED,

    /**
     * The application has been withdrawn by the applicant.
     */
    WITHDRAWN,
}
