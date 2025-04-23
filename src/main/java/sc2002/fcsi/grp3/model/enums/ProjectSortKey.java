package sc2002.fcsi.grp3.model.enums;

public enum ProjectSortKey {
    NAME("Name"),
    APPLICATION_OPENING_DATE("Application Opening Date"),
    APPLICATION_CLOSING_DATE("Application Closing Date"),
    PRICE("Price");

    private final String displayName;

    ProjectSortKey(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
