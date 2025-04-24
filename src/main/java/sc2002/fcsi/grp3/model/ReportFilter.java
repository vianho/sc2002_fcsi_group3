package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.dto.FlatBookingReportRow;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;

import java.util.List;

public class ReportFilter {
    private MaritalStatus maritalStatus;
    private Integer minAge;
    private Integer maxAge;
    private List<FlatType> flatTypes;
    private String neighbourhood;

    public ReportFilter() {}

    // getters

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public List<FlatType> getFlatTypes() {
        return flatTypes;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    // setters

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public void setFlatTypes(List<FlatType> flatTypes) {
        this.flatTypes = flatTypes;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    /**
     * Checks whether a given {@link FlatBookingReportRow} entry matches the filter criteria
     * specified in this {@code ReportFilter}.
     *
     * <p>The filtering logic includes:
     * <ul>
     *     <li><b>Marital Status</b>: If set, the entry's marital status must match</li>
     *     <li><b>Flat Types</b>: If set, the entry’s flat type must be in the list</li>
     *     <li><b>Min Age</b>: If set, applicant's age must be greater than or equal to this</li>
     *     <li><b>Max Age</b>: If set, applicant's age must be less than or equal to this</li>
     *     <li><b>Neighbourhood</b>: If set and non-blank, the entry's neighbourhood must match exactly (case-insensitive)</li>
     * </ul>
     *
     * @param entry the report row to evaluate against this filter
     * @return {@code true} if the entry satisfies all non-null/non-zero filter fields; {@code false} otherwise
     */
    public boolean matches(FlatBookingReportRow entry) {
        // Match marital status (if filter is set)
        if (maritalStatus != null && entry.getMaritalStatus() != maritalStatus) {
            return false;
        }

        // Match flat type (if filter is set)
        if (flatTypes != null && !flatTypes.isEmpty() && !flatTypes.contains(entry.getFlatType())) {
            return false;
        }

        // Match minimum age (if minAge filter is set)
        if (minAge != null && entry.getApplicantAge() < minAge) {
            return false;
        }

        // Match maximum age (if maxAge filter is set)
        if (maxAge != null && entry.getApplicantAge() > maxAge) {
            return false;
        }

        // Match neighbourhood (if filter is set)
        if (neighbourhood != null && !neighbourhood.isBlank()
                && !entry.getNeighbourhood().equalsIgnoreCase(neighbourhood)) {
            return false;
        }

        return true;
    }
}

