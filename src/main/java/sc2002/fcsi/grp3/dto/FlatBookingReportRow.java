package sc2002.fcsi.grp3.dto;

import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;

/**
 * The FlatBookingReportRow class represents a single row in the flat booking report.
 * It contains details about the applicant, the flat type, and the associated project.
 */
public class FlatBookingReportRow {

    private final String applicantName;
    private final int applicantAge;
    private final MaritalStatus maritalStatus;
    private final FlatType flatType;
    private final String projectName;
    private final String neighbourhood;

    /**
     * Constructs a FlatBookingReportRow with the specified details.
     *
     * @param applicantName the name of the applicant
     * @param applicantAge  the age of the applicant
     * @param maritalStatus the marital status of the applicant
     * @param flatType      the type of flat booked
     * @param projectName   the name of the project
     * @param neighbourhood the neighbourhood of the project
     */
    public FlatBookingReportRow(
            String applicantName,
            int applicantAge,
            MaritalStatus maritalStatus,
            FlatType flatType,
            String projectName,
            String neighbourhood
    ) {
        this.applicantName = applicantName;
        this.applicantAge = applicantAge;
        this.maritalStatus = maritalStatus;
        this.flatType = flatType;
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
    }

    /**
     * Gets the name of the applicant.
     *
     * @return the applicant's name
     */
    public String getApplicantName() { return applicantName; }

    /**
     * Gets the age of the applicant.
     *
     * @return the applicant's age
     */
    public int getApplicantAge() { return applicantAge; }

    /**
     * Gets the marital status of the applicant.
     *
     * @return the applicant's marital status
     */
    public MaritalStatus getMaritalStatus() { return maritalStatus; }

    /**
     * Gets the type of flat booked by the applicant.
     *
     * @return the flat type
     */
    public FlatType getFlatType() { return flatType; }

    /**
     * Gets the name of the project associated with the booking.
     *
     * @return the project name
     */
    public String getProjectName() { return projectName; }

    /**
     * Gets the neighbourhood of the project associated with the booking.
     *
     * @return the neighbourhood
     */
    public String getNeighbourhood() { return neighbourhood; }
}
