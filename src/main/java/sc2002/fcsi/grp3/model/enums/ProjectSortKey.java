package sc2002.fcsi.grp3.model.enums;

/**
 * The ProjectSortKey enum represents the keys by which projects can be sorted.
 * Each key has a user-friendly display name.
 */
public enum ProjectSortKey {

    /**
     * Sort projects by name.
     */
    NAME("Name"),

    /**
     * Sort projects by their application opening date.
     */
    APPLICATION_OPENING_DATE("Application Opening Date"),

    /**
     * Sort projects by their application closing date.
     */
    APPLICATION_CLOSING_DATE("Application Closing Date"),

    /**
     * Sort projects by price.
     */
    PRICE("Price");

    private final String displayName;

    /**
     * Constructs a ProjectSortKey with the specified display name.
     *
     * @param displayName the display name of the sort key
     */
    ProjectSortKey(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the sort key.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
