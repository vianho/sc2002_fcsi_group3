package sc2002.fcsi.grp3.model.enums;

/**
 * The SortDirection enum represents the direction in which sorting can be performed.
 * It includes ascending and descending options.
 */
public enum SortDirection {

    /**
     * Sort in ascending order.
     */
    ASCENDING("Ascending"),

    /**
     * Sort in descending order.
     */
    DESCENDING("Descending");

    private final String displayName;

    /**
     * Constructs a SortDirection with the specified display name.
     *
     * @param displayName the display name of the sort direction
     */
    SortDirection(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the sort direction.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
