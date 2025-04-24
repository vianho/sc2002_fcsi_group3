package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.dto.FlatBookingReportRow;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;

import java.util.List;

/**
 * The ReportFilter class represents the criteria used to filter flat booking reports.
 * It includes attributes such as marital status, age range, flat types, and neighbourhood.
 */
public class ReportFilter {

    private MaritalStatus maritalStatus;
    private Integer minAge;
    private Integer maxAge;
    private List<FlatType> flatTypes;
    private String neighbourhood;

    /**
     * Constructs an empty ReportFilter with no criteria.
     */
    public ReportFilter() {}

    // Getters

    /**
     * Gets the marital status to filter by.
     *
     * @return the marital status
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Gets the minimum age to filter by.
     *
     * @return the minimum age
     */
    public Integer getMinAge() {
        return minAge;
    }

    /**
     * Gets the maximum age to filter by.
     *
     * @return the maximum age
     */
    public Integer getMaxAge() {
        return maxAge;
    }

    /**
     * Gets the list of flat types to filter by.
     *
     * @return the list of flat types
     */
    public List<FlatType> getFlatTypes() {
        return flatTypes;
    }

    /**
     * Gets the neighbourhood to filter by.
     *
     * @return the neighbourhood
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    // Setters

    /**
     * Sets the marital status to filter by.
     *
     * @param maritalStatus the marital status to set
     */
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Sets the minimum age to filter by.
     *
     * @param minAge the minimum age to set
     */
    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    /**
     * Sets the maximum age to filter by.
     *
     * @param maxAge the maximum age to set
     */
    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * Sets the list of flat types to filter by.
     *
     * @param flatTypes the list of flat types to set
     */
    public void setFlatTypes(List<FlatType> flatTypes) {
        this.flatTypes = flatTypes;
    }

    /**
     * Sets the neighbourhood to filter by.
     *
     * @param neighbourhood the neighbourhood to set
     */
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

