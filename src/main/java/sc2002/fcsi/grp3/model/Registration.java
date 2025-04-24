package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.FlatType;
import java.time.LocalDate;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;

/**
 * The Registration class represents a user's registration for a project.
 * It contains details such as the applicant, project, and registration status.
 */
public class Registration {

    private String id;
    private Project project;
    private User applicant;
    private RegistrationStatus status;
    private LocalDate submittedAt;
    private static int nextRegistrationId;

    /**
     * Constructs a Registration with all specified details.
     *
     * @param id          the unique ID of the registration
     * @param project     the project the registration is for
     * @param applicant   the user submitting the registration
     * @param status      the current status of the registration
     * @param submittedAt the date the registration was submitted
     */
    public Registration(
            String id,
            Project project,
            User applicant,
            RegistrationStatus status,
            LocalDate submittedAt
    ) {
        this.id = id;
        this.project = project;
        this.applicant = applicant;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    /**
     * Constructs a Registration with default status.
     * The registration ID is auto-generated.
     *
     * @param project     the project the registration is for
     * @param applicant   the user submitting the registration
     * @param submittedAt the date the registration was submitted
     */
    public Registration(
            Project project,
            User applicant,
            LocalDate submittedAt
    ) {
        this.id = getNextRegistrationId();
        this.project = project;
        this.applicant = applicant;
        this.status = RegistrationStatus.PENDING;
        this.submittedAt = submittedAt;
    }

    // Getters

    /**
     * Gets the unique ID of the registration.
     *
     * @return the registration ID
     */
    public String getId() {
        return id;
    }

//    public FlatType getFlatType() {
//        return flatType;
//    }

    /**
     * Gets the project associated with the registration.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Gets the applicant who submitted the registration.
     *
     * @return the applicant
     */
    public User getApplicant() {
        return applicant;
    }

    /**
     * Gets the current status of the registration.
     *
     * @return the registration status
     */
    public RegistrationStatus getStatus() {
        return status;
    }

    /**
     * Gets the date the registration was submitted.
     *
     * @return the submission date
     */
    public LocalDate getSubmittedAt() {
        return submittedAt;
    }

    // Setters

    /**
     * Sets the date the registration was submitted.
     *
     * @param submittedAt the submission date to set
     */
    public void setSubmittedAt(LocalDate submittedAt) {
        this.submittedAt = submittedAt;
    }

    /**
     * Sets the status of the registration.
     *
     * @param status the status to set
     */
    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    /**
     * Sets the applicant who submitted the registration.
     *
     * @param applicant the applicant to set
     */
    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    /**
     * Sets the project associated with the registration.
     *
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Sets the unique ID of the registration.
     *
     * @param id the registration ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the next registration ID.
     *
     * @param id the next registration ID to set
     */
    public static void setNextRegistrationId(int id) {
        nextRegistrationId = id;
    }

    /**
     * Gets the next auto-generated registration ID.
     *
     * @return the next registration ID
     */
    public static String getNextRegistrationId() {
        return String.valueOf(++nextRegistrationId);
    }

//    public void setFlatType(FlatType flatType) {
//        this.flatType = flatType;
//    }
}
