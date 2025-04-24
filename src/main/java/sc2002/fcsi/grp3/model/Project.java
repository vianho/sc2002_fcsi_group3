package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.FlatType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Project class represents a housing project.
 * It contains details such as the project name, neighbourhood, application dates, flats, and officer assignments.
 */
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
    private static int nextProjectId = 1;

    /**
     * Constructs a Project with auto-generated ID and specified details.
     *
     * @param name                  the name of the project
     * @param neighbourhood         the neighbourhood of the project
     * @param isVisible             whether the project is visible
     * @param applicationOpeningDate the application opening date
     * @param applicationClosingDate the application closing date
     * @param managerNric           the NRIC of the manager
     * @param totalOfficerSlots     the total number of officer slots
     * @param flats                 the list of flats in the project
     * @param officerNrics          the list of officer NRICs assigned to the project
     */
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
        this.officerNrics = new ArrayList<>(officerNrics);
    }

    /**
     * Constructs a Project with a specified ID and details.
     *
     * @param id                    the unique ID of the project
     * @param name                  the name of the project
     * @param neighbourhood         the neighbourhood of the project
     * @param isVisible             whether the project is visible
     * @param applicationOpeningDate the application opening date
     * @param applicationClosingDate the application closing date
     * @param managerNric           the NRIC of the manager
     * @param totalOfficerSlots     the total number of officer slots
     * @param flats                 the list of flats in the project
     * @param officerNrics          the list of officer NRICs assigned to the project
     */
    public Project(
            int id,
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
        this.id = id;
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.isVisible = isVisible;
        this.flats = flats;
        this.applicationOpeningDate = applicationOpeningDate;
        this.applicationClosingDate = applicationClosingDate;
        this.managerNric = managerNric;
        this.totalOfficerSlots = totalOfficerSlots;
        this.officerNrics = officerNrics;
    }

    // Getters

    /**
     * Gets the unique ID of the project.
     *
     * @return the project ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the project.
     *
     * @return the project name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the neighbourhood of the project.
     *
     * @return the neighbourhood
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Checks if the project is visible.
     *
     * @return true if the project is visible, false otherwise
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Checks if the application period for the project is still open.
     *
     * @return true if the application period is open, false otherwise
     */
    public boolean isApplicationOpen() {
        return applicationClosingDate.isAfter(LocalDate.now());
    }

    /**
     * Gets the list of flats in the project.
     *
     * @return the list of flats
     */
    public List<Flat> getFlats() {
        return flats;
    }

    /**
     * Gets the application opening date.
     *
     * @return the application opening date
     */
    public LocalDate getApplicationOpeningDate() {
        return applicationOpeningDate;
    }

    /**
     * Gets the application closing date.
     *
     * @return the application closing date
     */
    public LocalDate getApplicationClosingDate() {
        return applicationClosingDate;
    }

    /**
     * Gets the NRIC of the manager.
     *
     * @return the manager's NRIC
     */
    public String getManagerNric() {
        return managerNric;
    }

    /**
     * Gets the total number of officer slots.
     *
     * @return the total officer slots
     */
    public int getTotalOfficerSlots() {
        return totalOfficerSlots;
    }

    /**
     * Gets the current number of officers assigned to the project.
     *
     * @return the number of assigned officers
     */
    public int getOfficerCount() {
        return officerNrics.size();
    }

    /**
     * Gets the list of officer NRICs assigned to the project.
     *
     * @return the list of officer NRICs
     */
    public List<String> getOfficerNrics() {
        return officerNrics;
    }

    /**
     * Checks if the project has available units for the specified flat type.
     *
     * @param type the flat type to check
     * @return true if there are available units, false otherwise
     */
    public boolean hasAvailableFlatType(FlatType type) {
        return flats.stream()
                .anyMatch(flat -> flat.getType() == type && flat.getUnitsAvailable() > 0);
    }

    // Setters

    /**
     * Sets the name of the project.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the neighbourhood of the project.
     *
     * @param neighbourhood the neighbourhood to set
     */
    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    /**
     * Sets the visibility of the project.
     *
     * @param visible true to make the project visible, false otherwise
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Sets the list of flats in the project.
     *
     * @param flats the list of flats to set
     */
    public void setFlats(List<Flat> flats) {
        this.flats = flats;
    }

    /**
     * Sets the application opening date.
     *
     * @param applicationOpeningDate the opening date to set
     */
    public void setApplicationOpeningDate(LocalDate applicationOpeningDate) {
        this.applicationOpeningDate = applicationOpeningDate;
    }

    /**
     * Sets the application closing date.
     *
     * @param applicationClosingDate the closing date to set
     */
    public void setApplicationClosingDate(LocalDate applicationClosingDate) {
        this.applicationClosingDate = applicationClosingDate;
    }

    /**
     * Sets the NRIC of the manager.
     *
     * @param nric the manager's NRIC to set
     */
    public void setManagerNric(String nric) {
        this.managerNric = managerNric;
    }

    /**
     * Sets the total number of officer slots.
     *
     * @param totalOfficerSlots the total officer slots to set
     */
    public void setTotalOfficerSlots(int totalOfficerSlots) {
        this.totalOfficerSlots = totalOfficerSlots;
    }

    /**
     * Sets the list of officer NRICs assigned to the project.
     *
     * @param officerNrics the list of officer NRICs to set
     */
    public void setOfficerNrics(List<String> officerNrics) {
        this.officerNrics = officerNrics;
    }

    /**
     * Sets the next project ID.
     *
     * @param id the next project ID to set
     */
    public static void setNextProjectId(int id) {
        nextProjectId = id;
    }

    /**
     * Assigns an officer to the project if there are available slots and the officer is not already assigned.
     *
     * @param nric the NRIC of the officer to assign
     * @return true if the officer was successfully assigned, false otherwise
     */
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

    /**
     * Returns a string representation of the project.
     *
     * @return a string containing the project ID, name, neighbourhood, and application dates
     */
    @Override
    public String toString() {
        return  id + "\t" + name +
                '\t' + neighbourhood + '\t' + applicationOpeningDate +
                "\t" + applicationClosingDate;
    }
}
