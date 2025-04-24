package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.time.LocalDate;

/**
 * The Application class represents an application submitted by a user for a project.
 * It contains details such as the applicant, project, flat type, and application status.
 */
public class Application {

    private final int id;
    private final Project project;
    private final User applicant;
    private FlatType flatType;
    private ApplicationStatus status;
    private final LocalDate submittedAt;
    private static int nextId;

    /**
     * Constructs an Application with all specified details.
     *
     * @param id          the unique ID of the application
     * @param project     the project the application is for
     * @param applicant   the user submitting the application
     * @param flatType    the type of flat being applied for
     * @param status      the current status of the application
     * @param submittedAt the date the application was submitted
     */
    public Application(
            int id,
            Project project,
            User applicant,
            FlatType flatType,
            ApplicationStatus status,
            LocalDate submittedAt
    ) {
        this.id = id;
        this.project = project;
        this.applicant = applicant;
        this.flatType = flatType;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    /**
     * Constructs an Application with default status and submission date.
     * The application ID is auto-incremented.
     *
     * @param project   the project the application is for
     * @param applicant the user submitting the application
     * @param flatType  the type of flat being applied for
     */
    public Application(
            Project project,
            User applicant,
            FlatType flatType
    ) {
        this.id = nextId++;
        this.project = project;
        this.applicant = applicant;
        this.flatType = flatType;
        this.status = ApplicationStatus.PENDING;
        this.submittedAt = LocalDate.now();
    }

    /**
     * Gets the unique ID of the application.
     *
     * @return the application ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the project associated with the application.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Gets the applicant who submitted the application.
     *
     * @return the applicant
     */
    public User getApplicant() {
        return applicant;
    }

    /**
     * Gets the flat type being applied for.
     *
     * @return the flat type
     */
    public FlatType getFlatType() {
        return flatType;
    }

    /**
     * Gets the current status of the application.
     *
     * @return the application status
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Gets the date the application was submitted.
     *
     * @return the submission date
     */
    public LocalDate getSubmittedAt() {
        return submittedAt;
    }

    /**
     * Sets the flat type for the application.
     *
     * @param flatType the flat type to set
     */
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    /**
     * Sets the status of the application.
     *
     * @param status the status to set
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /**
     * Sets the next ID for applications.
     *
     * @param id the next ID to set
     */
    public static void setNextId(int id) {
        nextId = id;
    }

    /**
     * Checks if the application is active.
     * An application is considered active if its status is PENDING or SUCCESSFUL.
     *
     * @return true if the application is active, false otherwise
     */
    public boolean isActive() {
        return status == ApplicationStatus.PENDING || status == ApplicationStatus.SUCCESSFUL;
    }
}
