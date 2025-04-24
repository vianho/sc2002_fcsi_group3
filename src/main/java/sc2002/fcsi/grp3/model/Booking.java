package sc2002.fcsi.grp3.model;

import java.time.LocalDate;

/**
 * The Booking class represents a flat booking made by a user.
 * It contains details such as the flat, project, applicant, officer, and booking date.
 */
public class Booking {

    private int id;
    private Flat flatType;
    private Project projectId;
    private User applicantNric;
    private User officerNric;
    private LocalDate bookingDate;
    private static int nextBookingId;

    /**
     * Constructs a Booking with all specified details.
     *
     * @param id           the unique ID of the booking
     * @param flatType     the flat type being booked
     * @param projectId    the project associated with the booking
     * @param applicantNric the applicant making the booking
     * @param officerNric  the officer handling the booking
     * @param bookingDate  the date the booking was made
     */
    public Booking(int id, Flat flatType, Project projectId, User applicantNric, User officerNric, LocalDate bookingDate) {
        this.id = id;
        this.flatType = flatType;
        this.projectId = projectId;
        this.applicantNric = applicantNric;
        this.officerNric = officerNric;
        this.bookingDate = bookingDate;
    }

    /**
     * Constructs a Booking with default booking date.
     * The booking ID is auto-incremented.
     *
     * @param flatType     the flat type being booked
     * @param projectId    the project associated with the booking
     * @param applicantNric the applicant making the booking
     * @param officerNric  the officer handling the booking
     */
    public Booking(Flat flatType, Project projectId, User applicantNric, User officerNric) {
        this.id = ++nextBookingId;
        this.flatType = flatType;
        this.projectId = projectId;
        this.applicantNric = applicantNric;
        this.officerNric = officerNric;
        this.bookingDate = LocalDate.now();
    }

    /**
     * Gets the unique ID of the booking.
     *
     * @return the booking ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the flat type being booked.
     *
     * @return the flat type
     */
    public Flat getFlatType() {
        return this.flatType;
    }

    /**
     * Gets the project associated with the booking.
     *
     * @return the project
     */
    public Project getProjectId() {
        return this.projectId;
    }

    /**
     * Gets the applicant making the booking.
     *
     * @return the applicant
     */
    public User getApplicant() {
        return this.applicantNric;
    }

    /**
     * Gets the officer handling the booking.
     *
     * @return the officer
     */
    public User getOfficer() {
        return this.officerNric;
    }

    /**
     * Sets the next booking ID.
     *
     * @param id the next booking ID to set
     */
    public static void setNextBookingId(int id) {
        nextBookingId = id;
    }

    /**
     * Gets the date the booking was made.
     *
     * @return the booking date
     */
    public LocalDate getBookingDate() {
        return this.bookingDate;
    }
}
