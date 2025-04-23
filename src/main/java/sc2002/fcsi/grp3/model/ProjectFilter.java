package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.FlatType;

import java.time.LocalDate;
import java.util.List;

public class ProjectFilter {
    private String neighbourhood;
    private LocalDate applicationOpeningAfter;
    private LocalDate applicationClosingBefore;
    private List<FlatType> flatTypes;
    private Float minSellingPrice;
    private Float maxSellingPrice;

    public ProjectFilter() {}

    public ProjectFilter(
            String neighbourhood,
            LocalDate applicationOpeningAfter,
            LocalDate applicationClosingBefore,
            List<FlatType> flatTypes,
            Float minSellingPrice,
            Float maxSellingPrice) {
        this.neighbourhood = neighbourhood;
        this.applicationOpeningAfter = applicationOpeningAfter;
        this.applicationClosingBefore = applicationClosingBefore;
        this.flatTypes = flatTypes;
        this.minSellingPrice = minSellingPrice;
        this.maxSellingPrice = maxSellingPrice;
    }

    // getters

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public LocalDate getApplicationOpeningAfter() {
        return applicationOpeningAfter;
    }

    public LocalDate getApplicationClosingBefore() {
        return applicationClosingBefore;
    }

    public List<FlatType> getflatTypes() {
        return flatTypes;
    }

    public Float getMinSellingPrice() {
        return minSellingPrice;
    }

    public Float getMaxSellingPrice() {
        return maxSellingPrice;
    }

    // setters

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void setApplicationOpeningAfter(LocalDate applicationOpeningAfter) {
        this.applicationOpeningAfter = applicationOpeningAfter;
    }

    public void setApplicationClosingBefore(LocalDate applicationClosingBefore) {
        this.applicationClosingBefore = applicationClosingBefore;
    }

    public void setFlatTypes(List<FlatType> flatTypes) {
        this.flatTypes = flatTypes;
    }

    public void setMinSellingPrice(Float minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
    }

    public void setMaxSellingPrice(Float maxSellingPrice) {
        this.maxSellingPrice = maxSellingPrice;
    }
}

