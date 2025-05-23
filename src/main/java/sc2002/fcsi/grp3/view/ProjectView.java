package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.dto.ProjectFlatRow;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.ProjectFilter;
import sc2002.fcsi.grp3.model.ProjectSortOption;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The ProjectView class provides methods for displaying and interacting with project-related data.
 * It includes functionality for showing project details, flats, filters, and sorting options.
 */
public class ProjectView extends BaseView {

    /**
     * Constructs a ProjectView with the specified prompter.
     *
     * @param prompt the prompter used for user input and output
     */
    public ProjectView(Prompter prompt) {
        super(prompt);
    }

    /**
     * Displays a list of project flats with their details.
     *
     * @param flats the list of project flats to display
     */
    public void showProjectFlats(List<ProjectFlatRow> flats) {
        List<String> headers = List.of("ID", "Name", "Neighbourhood", "Flat Type", "Units Available", "Selling Price (S$)", "Application Opening Date", "Application Closing Date");
        List<List<String>> rows = flats.stream().map(row -> List.of(
                String.valueOf(row.project().getId()),
                row.project().getName(),
                row.project().getNeighbourhood(),
                row.flat().getType().getDisplayName(),
                String.valueOf(row.flat().getUnitsAvailable()),
                String.valueOf(row.flat().getSellingPrice()),
                row.project().getApplicationOpeningDate().format(dtFormatter),
                row.project().getApplicationClosingDate().format(dtFormatter)
        )).toList();
        prompt.showTable(headers, rows);
    }

    /**
     * Displays a list of projects with their details.
     *
     * @param projects the list of projects to display
     */
    public void showProjects(List<Project> projects) {
        if (projects.isEmpty()) {
            prompt.showMessage("No projects available.");
        } else {
            List<String> headers = List.of("ID", "Name", "Neighbourhood", "Visible", "Manager NRIC", "Application Open", "Application Close");
            List<List<String>> rows = projects.stream()
                    .map(project -> List.of(
                            String.valueOf(project.getId()),
                            project.getName(),
                            project.getNeighbourhood(),
                            project.isVisible() ? "Yes" : "No",
                            project.getManagerNric(),
                            project.getApplicationOpeningDate().toString(),
                            project.getApplicationClosingDate().toString()
                    ))
                    .toList();
            prompt.showTable(headers, rows);
        }
        prompt.pressEnterToContinue();
    }

    /**
     * Displays the details of a specific project in a tabular format.
     *
     * @param project the project whose details are to be displayed
     */
    public void showProjectDetailsTable(Project project) {
        prompt.showTitle("Project Details");

        if (project == null) {
            prompt.showMessage("Project not found.");
            return;
        }
        List<String> headers = List.of("ID", "Name", "Neighbourhood", "Flat Types", "Application Opening Date", "Application Closing Date");

        List<String> row = List.of(
                String.valueOf(project.getId()),
                project.getName(),
                project.getNeighbourhood(),
                project.getFlats().stream()
                        .map(f -> f.getType().getDisplayName())
                        .collect(Collectors.joining(", ")),
                project.getApplicationOpeningDate().toString(),
                project.getApplicationClosingDate().toString()
        );

        prompt.showTable(headers, List.of(row));
    }

    /**
     * Displays the current filter settings.
     *
     * @param filter the current filter settings
     */
    public void showCurrentFilter(ProjectFilter filter) {
        prompt.showMessage("Current Filters:");
        prompt.showMessage("- Neighbourhood: " + (filter.getNeighbourhood() == null ? "Any" : filter.getNeighbourhood()));
        prompt.showMessage("- Open After: " + (filter.getApplicationOpeningAfter() == null ? "Any" : filter.getApplicationOpeningAfter()));
        prompt.showMessage("- Close Before: " + (filter.getApplicationClosingBefore() == null ? "Any" : filter.getApplicationClosingBefore()));
        prompt.showMessage("- Min Price: " + (filter.getMinSellingPrice() == null ? "Any" : String.format("%.2f", filter.getMinSellingPrice())));
        prompt.showMessage("- Max Price: " + (filter.getMaxSellingPrice() == null ? "Any" : String.format("%.2f", filter.getMaxSellingPrice())));
        prompt.showMessage("- Flat Types: " +
                (filter.getflatTypes() == null || filter.getflatTypes().isEmpty() ? "Any"
                        : filter.getflatTypes().stream()
                        .map(FlatType::getDisplayName)
                        .collect(Collectors.joining(", ")))
        );
    }

    /**
     * Displays the current sorting option.
     *
     * @param sortOption the current sorting option
     */
    public void showCurrentSortOption(ProjectSortOption sortOption) {
        prompt.showMessagef("Current sort option: %s %s", sortOption.getKey().getDisplayName(), sortOption.getDirection().getDisplayName());
    }

    /**
     * Prompts the user to enter a neighbourhood filter.
     *
     * @return the entered neighbourhood, or null if left blank
     */
    public String promptNeighbourhood() {
        return prompt.prompStringOptional("Neighbourhood (blank for any): ");
    }

    /**
     * Prompts the user to enter a filter for application opening dates after a specific date.
     *
     * @return the entered date, or null if left blank
     */
    public LocalDate promptApplicationOpeningAfter() {
        return prompt.promptDateOptional("Application Open After (DD/MM/YYYY) (blank for any): ");
    }

    /**
     * Prompts the user to enter a filter for application closing dates before a specific date.
     *
     * @return the entered date, or null if left blank
     */
    public LocalDate promptApplicationClosingBefore() {
        return prompt.promptDateOptional("Application Close Before (DD/MM/YYYY) (blank for any): ");
    }

    /**
     * Prompts the user to enter a list of flat types to filter by.
     *
     * @return the list of flat types, or null if left blank
     */
    public List<FlatType> promptFlatTypes() {
        return prompt.promptFlatTypesOptional("Flat Types (e.g. 2R,3R - leave blank for any): ");
    }

    /**
     * Prompts the user to enter a minimum price filter.
     *
     * @return the entered minimum price, or null if left blank
     */
    public Float promptMinPrice() {
        return prompt.promptFloatOptional("Minimum Price (blank for any): ");
    }

    /**
     * Prompts the user to enter a maximum price filter.
     *
     * @return the entered maximum price, or null if left blank
     */
    public Float promptMaxPrice() {
        return prompt.promptFloatOptional("Maximum Price (blank for any): ");
    }
}
