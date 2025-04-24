package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.FlatType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private final int id;
    private String name;
    private String neighbourhood;
    private boolean isVisible;
    private LocalDate applicationOpeningDate;
    private LocalDate applicationClosingDate;
    private String managerNric;
    private int totalOfficerSlots;
    private List<Flat> flats;
    private List<String> officerNrics;
    
    private static int nextProjectId = 1 ;

    public Project(
            String name,
            String neighbourhood,
            boolean isVisible,
            LocalDate applicationOpeningDate,
            LocalDate applicationClosingDate,
            String managerNric,
            int totalOfficerSlots,
            List<Flat> flats,
            List<String> officerNrics
    ) {
        this.id = nextProjectId++;
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.isVisible = isVisible;
        this.flats = flats;
        this.applicationOpeningDate = applicationOpeningDate;
        this.applicationClosingDate = applicationClosingDate;
        this.managerNric = managerNric;
        this.totalOfficerSlots = totalOfficerSlots;
//        this.officerNrics = officerNrics;

        this.officerNrics = new ArrayList<>(officerNrics);
    }

    public Project(
            int id,
            String name,
            String neighbourhood,
            boolean isVisible,
            LocalDate applicationOpeningDate,
            LocalDate applicationClosingDate,
            String managerNric,
            int totalOfficerSlots,
            List<Flat> flats
    ) {
        this.id = id;
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.isVisible = isVisible;
        this.flats = flats;
        this.applicationOpeningDate = applicationOpeningDate;
        this.applicationClosingDate = applicationClosingDate;
        this.managerNric = managerNric;
        this.totalOfficerSlots = totalOfficerSlots;
    }

    // getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public List<Flat> getFlats() {
        return flats;
    }

    public LocalDate getApplicationOpeningDate() {
        return applicationOpeningDate;
    }

    public LocalDate getApplicationClosingDate() {
        return applicationClosingDate;
    }

    public String getManagerNric() {
        return managerNric;
    }

    public int getTotalOfficerSlots() {
        return totalOfficerSlots;
    }

    public int getOfficerCount() {
        return officerNrics.size();
    }

    public List<String> getOfficerNrics() {
        return officerNrics;
    }

    public boolean hasAvailableFlatType(FlatType type) {
        return flats.stream()
                .anyMatch(flat -> flat.getType() == type && flat.getUnitsAvailable() > 0);
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setFlats(List<Flat> flats) {
        this.flats = flats;
    }

    public void setApplicationOpeningDate(LocalDate applicationOpeningDate) {
        this.applicationOpeningDate = applicationOpeningDate;
    }

    public void setApplicationClosingDate(LocalDate applicationClosingDate) {
        this.applicationClosingDate = applicationClosingDate;
    }

    public void setManagerNric(String nric) {
        this.managerNric = managerNric;
    }

    public void setTotalOfficerSlots(int totalOfficerSlots) {
        this.totalOfficerSlots = totalOfficerSlots;
    }

    public void setOfficerNrics(List<String> officerNrics) {
        this.officerNrics = officerNrics;
    }

    public static void setNextProjectId(int id) {
        nextProjectId = id;
    }

    public boolean assignOfficer(String nric) {
        if (nric == null
                || nric.isEmpty()
                || officerNrics.contains(nric)
                || getOfficerCount() >= totalOfficerSlots
        ) {
            return false;
        }

        officerNrics.add(nric);
        return true;
    }

    @Override
    public String toString() {
        return  id + "\t" + name +
                '\t' + neighbourhood + '\t' + applicationOpeningDate +
                "\t" + applicationClosingDate;
    }
}
