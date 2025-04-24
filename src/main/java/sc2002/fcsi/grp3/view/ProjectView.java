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

public class ProjectView extends BaseView {
    public ProjectView(Prompter prompt) {
        super(prompt);
    }

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

    // filters
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

    public void showCurrentSortOption(ProjectSortOption sortOption) {
        prompt.showMessagef("Current sort option: %s %s", sortOption.getKey().getDisplayName(), sortOption.getDirection().getDisplayName());
    }

    public String promptNeighbourhood() {
        return prompt.prompStringOptional("Neighbourhood (blank for any): ");
    }

    public LocalDate promptApplicationOpeningAfter() {
        return prompt.promptDateOptional("Application Open After (DD/MM/YYYY) (blank for any): ");
    }

    public LocalDate promptApplicationClosingBefore() {
        return prompt.promptDateOptional("Application Close Before (DD/MM/YYYY) (blank for any): ");
    }

    public List<FlatType> promptFlatTypes() {
        return prompt.promptFlatTypesOptional("Flat Types (e.g. 2R,3R - leave blank for any): ");
    }

    public Float promptMinPrice() {
        return prompt.promptFloatOptional("Minimum Price (blank for any): ");
    }

    public Float promptMaxPrice() {
        return prompt.promptFloatOptional("Maximum Price (blank for any): ");
    }
}
