package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.FlatType;

import java.time.LocalDate;
import java.util.List;

/**
 * The ProjectFilter class represents the criteria used to filter projects.
 * It includes attributes such as neighbourhood, application dates, flat types, and price range.
 */
public class ProjectFilter {

    private String neighbourhood;
    private LocalDate applicationOpeningAfter;
    private LocalDate applicationClosingBefore;
    private List<FlatType> flatTypes;
    private Float minSellingPrice;
    private Float maxSellingPrice;

    /**
     * Constructs an empty ProjectFilter with no criteria.
     */
    public ProjectFilter() {}

    /**
     * Constructs a ProjectFilter with the specified criteria.
     *
     * @param neighbourhood          the neighbourhood to filter by
     * @param applicationOpeningAfter the earliest application opening date
     * @param applicationClosingBefore the latest application closing date
     * @param flatTypes              the list of flat types to filter by
     * @param minSellingPrice        the minimum selling price
     * @param maxSellingPrice        the maximum selling price
     */
    public ProjectFilter(
            String neighbourhood,
            LocalDate applicationOpeningAfter,
            LocalDate applicationClosingBefore,
            List<FlatType> flatTypes,
            Float minSellingPrice,
            Float maxSellingPrice
    ) {
        this.neighbourhood = neighbourhood;
        this.applicationOpeningAfter = applicationOpeningAfter;
        this.applicationClosingBefore = applicationClosingBefore;
        this.flatTypes = flatTypes;
        this.minSellingPrice = minSellingPrice;
        this.maxSellingPrice = maxSellingPrice;
    }

    // Getters

    /**
     * Gets the neighbourhood to filter by.
     *
     * @return the neighbourhood
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Gets the earliest application opening date to filter by.
     *
     * @return the application opening date
     */
    public LocalDate getApplicationOpeningAfter() {
        return applicationOpeningAfter;
    }

    /**
     * Gets the latest application closing date to filter by.
     *
     * @return the application closing date
     */
    public LocalDate getApplicationClosingBefore() {
        return applicationClosingBefore;
    }

    /**
     * Gets the list of flat types to filter by.
     *
     * @return the list of flat types
     */
    public List<FlatType> getflatTypes() {
        return flatTypes;
    }

    /**
     * Gets the minimum selling price to filter by.
     *
     * @return the minimum selling price
     */
    public Float getMinSellingPrice() {
        return minSellingPrice;
    }

    /**
     * Gets the maximum selling price to filter by.
     *
     * @return the maximum selling price
     */
    public Float getMaxSellingPrice() {
        return maxSellingPrice;
    }

    // Setters

    /**
     * Sets the neighbourhood to filter by.
     *
     * @param neighbourhood the neighbourhood to set
     */
    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    /**
     * Sets the earliest application opening date to filter by.
     *
     * @param applicationOpeningAfter the application opening date to set
     */
    public void setApplicationOpeningAfter(LocalDate applicationOpeningAfter) {
        this.applicationOpeningAfter = applicationOpeningAfter;
    }

    /**
     * Sets the latest application closing date to filter by.
     *
     * @param applicationClosingBefore the application closing date to set
     */
    public void setApplicationClosingBefore(LocalDate applicationClosingBefore) {
        this.applicationClosingBefore = applicationClosingBefore;
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
     * Sets the minimum selling price to filter by.
     *
     * @param minSellingPrice the minimum selling price to set
     */
    public void setMinSellingPrice(Float minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
    }

    /**
     * Sets the maximum selling price to filter by.
     *
     * @param maxSellingPrice the maximum selling price to set
     */
    public void setMaxSellingPrice(Float maxSellingPrice) {
        this.maxSellingPrice = maxSellingPrice;
    }
}

