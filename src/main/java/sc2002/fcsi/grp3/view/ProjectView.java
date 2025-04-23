package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.dto.ProjectFlatRow;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.List;

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
                row.project().getApplicationOpeningDate().toString(),
                row.project().getApplicationClosingDate().toString()
        )).toList();
        prompt.showTable(headers, rows);
    }
}
