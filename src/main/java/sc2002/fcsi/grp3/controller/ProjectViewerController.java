package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.dto.ProjectFlatRow;
import sc2002.fcsi.grp3.model.*;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.ProjectSortKey;
import sc2002.fcsi.grp3.model.enums.SortDirection;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.util.ProjectViewUtils;
import sc2002.fcsi.grp3.view.ProjectView;
import sc2002.fcsi.grp3.view.SharedView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The ProjectViewerController class handles the viewing and filtering of projects.
 * It provides functionality for creating, updating, and clearing filters, as well as sorting projects.
 */
public class ProjectViewerController implements IBaseController {

    private final SharedView sharedView;
    private final ProjectView view;
    private final ProjectService projectService;

    /**
     * Constructs a ProjectViewerController with the necessary dependencies.
     *
     * @param sharedView     the shared view for displaying common UI elements
     * @param view           the view for displaying project-related UI elements
     * @param projectService the service for managing project-related operations
     */
    public ProjectViewerController(
            SharedView sharedView,
            ProjectView view,
            ProjectService projectService
    ) {
        this.sharedView = sharedView;
        this.view = view;
        this.projectService = projectService;
    }

    /**
     * Starts the project viewer menu, allowing the user to filter, sort, and view projects.
     */
    public void start() {
        int choice;
        String[] options = {
                "Create filter",
                "Update filter",
                "Clear filter",
                "Sort projects",
                "Back to project view",
                "Back"
        };
        User user = Session.getCurrentUser();

        do {
            sharedView.clear();
            sharedView.showTitle("Projects");
            ProjectFilter filter = Session.get("projectFilter", ProjectFilter.class);
            if (filter == null) {
                filter = new ProjectFilter();
            }

            ProjectSortOption sortOption = Session.get("sortOption", ProjectSortOption.class);
            if (sortOption == null) {
                sortOption = ProjectSortOption.DEFAULT;
            }

            view.showCurrentFilter(filter);
            view.showCurrentSortOption(sortOption);
            view.showMessage("\n");

            viewProjects(user, filter, sortOption);
            sharedView.pressEnterToContinue();

            choice = sharedView.showMenuAndGetChoice("Filter Menu", options);
            switch (choice) {
                case 1 -> createFilter();
                case 2 -> updateFilter(filter);
                case 3 -> Session.remove("projectFilter");
                case 4 -> sortProjects(sortOption);
                case 5, 6 -> {}
                default -> sharedView.showInvalidChoice();
            }
        } while (choice != options.length);
    }

    /**
     * Retrieves the list of projects visible to the user.
     * If the list is not cached in the session, it fetches the projects from the project service and caches them.
     *
     * @param user the current user
     * @return a list of visible projects
     */
    private List<Project> getVisibleProjects(User user) {
        List<Project> visibleProjects = Session.getList("visibleProjects", Project.class);

        if (visibleProjects == null) {
            visibleProjects = projectService.getVisibleProjects(user);
            Session.put("visibleProjects", visibleProjects);
        }

        if (visibleProjects.isEmpty()) {
            sharedView.showMessage("No projects found.");
        }

        return visibleProjects;
    }

    /**
     * Retrieves the list of projects filtered and sorted based on the user's preferences.
     *
     * @param user       the current user
     * @param projects   the list of projects to filter
     * @param filter     the filter criteria
     * @param sortOption the sorting criteria
     * @return a list of filtered and sorted projects
     */
    private List<Project> getFilteredProjects(User user, List<Project> projects, ProjectFilter filter, ProjectSortOption sortOption) {
        List<Project> filteredProjects = projectService.filterProjects(user, projects, filter, sortOption);
        if (filteredProjects.isEmpty()) {
            sharedView.showMessage("No projects found. Please adjust your filter.");
        }
        return filteredProjects;
    }

    /**
     * Displays the list of projects based on the current filter and sort options.
     *
     * @param user       the current user
     * @param filter     the filter criteria
     * @param sortOption the sorting criteria
     */
    private void viewProjects(User user, ProjectFilter filter, ProjectSortOption sortOption) {
        List<Project> filteredProjects = getFilteredProjects(user, getVisibleProjects(user), filter, sortOption);
        List<ProjectFlatRow> rows = ProjectViewUtils.flattenEligibleFlats(filteredProjects, user);
        view.showProjectFlats(rows);
    }

    /**
     * Prompts the user to create a new filter for projects.
     * The filter criteria include neighbourhood, application dates, flat types, and price range.
     */
    private void createFilter() {
        sharedView.showTitle("Create Filter");
        String neighbourhood = view.promptNeighbourhood();
        LocalDate applicationOpenAfter = view.promptApplicationOpeningAfter();
        LocalDate applicationClosingBefore = view.promptApplicationClosingBefore();
        List<FlatType> flatTypes = view.promptFlatTypes();
        Float minPrice = view.promptMinPrice();
        Float maxPrice = view.promptMaxPrice();

        while (maxPrice != null && minPrice != null && maxPrice < minPrice) {
            sharedView.showMessage("Maximum price must be more than minimum price, minimum price: " + minPrice);
            maxPrice = view.promptMaxPrice();
        }

        try {
            ProjectFilter filter = new ProjectFilter(
                    neighbourhood,
                    applicationOpenAfter,
                    applicationClosingBefore,
                    flatTypes,
                    minPrice,
                    maxPrice
            );
            Session.set("projectFilter", filter);
        } catch (Exception e) {
            sharedView.showError("Failed to create filter: " + e.getMessage());
        }
    }

    /**
     * Allows the user to update an existing filter for projects.
     * The user can modify individual filter criteria or clear all filters.
     *
     * @param filter the current filter to update
     */
    private void updateFilter(ProjectFilter filter) {
        int choice;
        String[] options = {
                "Neighbourhood",
                "Application Opening After",
                "Application Closing Before",
                "Flat Types",
                "Min Price",
                "Max Price",
                "Clear all",
                "Done",
        };
        do {
            choice = sharedView.showMenuAndGetChoice("Update Filter", options);
            switch (choice) {
                case 1 -> filter.setNeighbourhood(view.promptNeighbourhood());
                case 2 -> filter.setApplicationOpeningAfter(view.promptApplicationOpeningAfter());
                case 3 -> filter.setApplicationClosingBefore(view.promptApplicationClosingBefore());
                case 4 -> filter.setFlatTypes(view.promptFlatTypes());
                case 5 -> {
                    Float minPrice = view.promptMinPrice();
                    while (minPrice != null && filter.getMaxSellingPrice() != null && minPrice > filter.getMaxSellingPrice()) {
                        sharedView.showMessage("Minimum price must be less than the maximum price, maximum price: " + filter.getMinSellingPrice());
                        minPrice = view.promptMinPrice();
                    }
                    filter.setMinSellingPrice(minPrice);
                }
                case 6 -> {
                    Float maxPrice = view.promptMaxPrice();
                    while (maxPrice != null && filter.getMinSellingPrice() != null && maxPrice < filter.getMinSellingPrice()) {
                        sharedView.showMessage("Maximum price must be more than minimum price, minimum price: " + filter.getMinSellingPrice());
                        maxPrice = view.promptMaxPrice();
                    }
                    filter.setMaxSellingPrice(maxPrice);
                }
                case 7 -> filter = new ProjectFilter();
                case 8 -> {}
                default -> sharedView.showInvalidChoice();
            }
        } while (choice != options.length);

        Session.set("projectFilter", filter);
        sharedView.showMessage("Filter Updated.");
    }

    /**
     * Prompts the user to select sorting options for projects.
     * The user can choose a sort key and direction (ascending or descending).
     *
     * @param sortOption the current sorting option to update
     */
    private void sortProjects(ProjectSortOption sortOption) {
        sharedView.showTitle("Sort Projects");
        int choice;
        String[] options = {
                "Project Name",
                "Application Opening Date",
                "Application Closing Date",
                "Price",
                "Cancel"
        };

        String[] direction = {
                SortDirection.ASCENDING.getDisplayName(),
                SortDirection.DESCENDING.getDisplayName(),
        };

        while (true) {
            choice = sharedView.showMenuAndGetChoice("Sort Key", options);
            if (choice > 0 && choice <= options.length) {
                switch (choice) {
                    case 1 -> sortOption.setKey(ProjectSortKey.NAME);
                    case 2 -> sortOption.setKey(ProjectSortKey.APPLICATION_OPENING_DATE);
                    case 3 -> sortOption.setKey(ProjectSortKey.APPLICATION_CLOSING_DATE);
                    case 4 -> sortOption.setKey(ProjectSortKey.PRICE);
                    case 5 -> {}
                    default -> sharedView.showInvalidChoice();
                };
                break;
            }
        }

        while (true) {
            choice = sharedView.showMenuAndGetChoice("Direction", direction);
            if (choice > 0 && choice <= options.length) {
                switch (choice) {
                    case 1 -> sortOption.setDirection(SortDirection.ASCENDING);
                    case 2 -> sortOption.setDirection(SortDirection.DESCENDING);
                    default -> sharedView.showInvalidChoice();
                }
                break;
            }
        }
        Session.set("sortOption", sortOption);
    }
}
