package sc2002.fcsi.grp3.dto;

import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;

public class FlatBookingReportRow {
    private final String applicantName;
    private final int applicantAge;
    private final MaritalStatus maritalStatus;
    private final FlatType flatType;
    private final String projectName;
    private final String neighbourhood;

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

    public String getApplicantName() { return applicantName; }
    public int getApplicantAge() { return applicantAge; }
    public MaritalStatus getMaritalStatus() { return maritalStatus; }
    public FlatType getFlatType() { return flatType; }
    public String getProjectName() { return projectName; }
    public String getNeighbourhood() { return neighbourhood; }
}
