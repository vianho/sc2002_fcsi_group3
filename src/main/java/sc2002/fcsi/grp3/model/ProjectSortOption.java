package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.ProjectSortKey;
import sc2002.fcsi.grp3.model.enums.SortDirection;

import java.util.Comparator;

public class ProjectSortOption {
    private ProjectSortKey key;
    private SortDirection direction;
    public static final ProjectSortOption DEFAULT =
            new ProjectSortOption(ProjectSortKey.NAME, SortDirection.ASCENDING);

    public ProjectSortOption(ProjectSortKey key, SortDirection direction) {
        this.key = key;
        this.direction = direction;
    }

    public ProjectSortKey getKey() {
        return key;
    }

    public void setKey(ProjectSortKey key) {
        this.key = key;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }

    public <T> Comparator<T> applyTo(Comparator<T> baseComparator) {
        return direction == SortDirection.DESCENDING
                ? baseComparator.reversed()
                : baseComparator;
    }
}
