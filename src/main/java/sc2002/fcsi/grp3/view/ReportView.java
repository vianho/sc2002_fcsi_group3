package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.dto.FlatBookingReportRow;
import sc2002.fcsi.grp3.model.ReportFilter;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.List;
import java.util.stream.Collectors;

public class ReportView extends BaseView {
    public ReportView(Prompter prompt) {
        super(prompt);
    }

    public void showCurrentFilter(ReportFilter filter) {
        prompt.showTitle("Current Filter");

        String maritalStatus = filter.getMaritalStatus() == null ? "Any" : filter.getMaritalStatus().getDisplayName();
        String flatType = (filter.getFlatTypes() == null || filter.getFlatTypes().isEmpty() ? "Any"
                : filter.getFlatTypes().stream()
                .map(FlatType::getDisplayName)
                .collect(Collectors.joining(", ")));
        String minAge = filter.getMinAge() == null ? "Any" : filter.getMinAge().toString();
        String maxAge = filter.getMaxAge() == null ? "Any" : filter.getMaxAge().toString();
        String neighbourhood = filter.getNeighbourhood() == null || filter.getNeighbourhood().isBlank() ? "Any" : filter.getNeighbourhood();

        prompt.showMessagef("- Marital Status: %s", maritalStatus);
        prompt.showMessagef("- Flat Type: %s", flatType);
        prompt.showMessagef("- Min Age: %s", minAge);
        prompt.showMessagef("- Max Age: %s", maxAge);
        prompt.showMessagef("- Neighbourhood: %s", neighbourhood);
    }

    public void showBookingReport(List<FlatBookingReportRow> rows) {
        if (rows.isEmpty()) {
            prompt.showMessage("No records found.");
            return;
        }

        List<String> headers = List.of("Name", "Age", "Marital Status", "Flat Type", "Project", "Neighbourhood");
        List<List<String>> tableRows = rows.stream()
                .map(row -> List.of(
                        row.getApplicantName(),
                        String.valueOf(row.getApplicantAge()),
                        row.getMaritalStatus().getDisplayName(),
                        row.getFlatType().getDisplayName(),
                        row.getProjectName(),
                        row.getNeighbourhood()
                ))
                .toList();

        prompt.showTable(headers, tableRows);
    }

    public MaritalStatus promptMaritalStatus() {
        return prompt.promptMaritalStatusOptional("Enter marital status (Single/Married, blank for any): ");
    }

    public List<FlatType> promptFlatTypes() {
        return prompt.promptFlatTypesOptional("Enter flat type (2R/3R, blank for any): ");
    }

    public Integer promptMinAge() {
        return prompt.promptIntOptional("Enter minimum age (blank for any): ");
    }

    public Integer promptMaxAge() {
        return prompt.promptIntOptional("Enter maximum age (blank for any): ");
    }

    public String promptNeighbourhood() {
        return prompt.promptString("Enter neighbourhood (blank for any): ");
    }

    public void showRecordCount(int count) {
        prompt.showMessage("Records found: " + count);
    }
}
