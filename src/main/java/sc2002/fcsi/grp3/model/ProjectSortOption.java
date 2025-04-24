package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.ProjectSortKey;
import sc2002.fcsi.grp3.model.enums.SortDirection;

import java.util.Comparator;

/**
 * The ProjectSortOption class represents the sorting options for projects.
 * It includes the sort key and direction, and provides functionality to apply sorting.
 */
public class ProjectSortOption {

    private ProjectSortKey key;
    private SortDirection direction;

    /**
     * The default sorting option, which sorts projects by name in ascending order.
     */
    public static final ProjectSortOption DEFAULT =
            new ProjectSortOption(ProjectSortKey.NAME, SortDirection.ASCENDING);

    /**
     * Constructs a ProjectSortOption with the specified sort key and direction.
     *
     * @param key       the key to sort by
     * @param direction the direction of sorting (ascending or descending)
     */
    public ProjectSortOption(ProjectSortKey key, SortDirection direction) {
        this.key = key;
        this.direction = direction;
    }

    /**
     * Gets the sort key.
     *
     * @return the sort key
     */
    public ProjectSortKey getKey() {
        return key;
    }

    /**
     * Sets the sort key.
     *
     * @param key the sort key to set
     */
    public void setKey(ProjectSortKey key) {
        this.key = key;
    }

    /**
     * Gets the sort direction.
     *
     * @return the sort direction
     */
    public SortDirection getDirection() {
        return direction;
    }

    /**
     * Sets the sort direction.
     *
     * @param direction the sort direction to set
     */
    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }

    /**
     * Applies the sort direction to a base comparator.
     *
     * @param baseComparator the base comparator to apply the sort direction to
     * @param <T>            the type of objects being compared
     * @return a comparator with the applied sort direction
     */
    public <T> Comparator<T> applyTo(Comparator<T> baseComparator) {
        return direction == SortDirection.DESCENDING
                ? baseComparator.reversed()
                : baseComparator;
    }
}
