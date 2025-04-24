package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.dto.FlatBookingReportRow;
import sc2002.fcsi.grp3.model.ReportFilter;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;
import sc2002.fcsi.grp3.service.ReportService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.ReportView;
import sc2002.fcsi.grp3.view.SharedView;

import java.util.List;

public class ReportViewerController implements IBaseController {
    private final SharedView sharedView;
    private final ReportView view;
    private final ReportService reportService;

    public ReportViewerController(SharedView sharedView, ReportView view, ReportService reportService) {
        this.sharedView = sharedView;
        this.view = view;
        this.reportService = reportService;
    }

    public void start() {
        int choice;
        String[] options = {
                "Create filter",
                "Update filter",
                "Clear filter",
                "Back to report view",
                "Back"
        };

        do {
            sharedView.clear();
            sharedView.showTitle("Applicant Booking Report");

            ReportFilter filter = Session.get("reportFilter", ReportFilter.class);
            if (filter == null) filter = new ReportFilter();

            view.showCurrentFilter(filter);
            view.showMessage("");

            List<FlatBookingReportRow> report = reportService.getFlatBookingReport(filter);
            view.showBookingReport(report);
            view.showRecordCount(report.size());
            sharedView.pressEnterToContinue();

            choice = sharedView.showMenuAndGetChoice("Filter Menu", options);
            switch (choice) {
                case 1 -> createFilter();
                case 2 -> updateFilter(filter);
                case 3 -> Session.remove("reportFilter");
                case 4, 5 -> {}
                default -> sharedView.showInvalidChoice();
            }
        } while (choice != options.length);
    }

    private void createFilter() {
        sharedView.showTitle("Create Filter");

        MaritalStatus maritalStatus = view.promptMaritalStatus();
        List<FlatType> flatTypes = view.promptFlatTypes();
        Integer minAge = view.promptMinAge();
        Integer maxAge = view.promptMaxAge();
        String neighbourhood = view.promptNeighbourhood();

        if (minAge != null && maxAge != null && maxAge < minAge) {
            sharedView.showError("Max age must be greater than or equal to min age.");
            return;
        }

        ReportFilter filter = new ReportFilter();
        filter.setMaritalStatus(maritalStatus);
        filter.setFlatTypes(flatTypes);
        filter.setMinAge(minAge);
        filter.setMaxAge(maxAge);
        filter.setNeighbourhood(neighbourhood.isBlank() ? null : neighbourhood);

        Session.set("reportFilter", filter);
        sharedView.showMessage("Filter created.");
    }

    private void updateFilter(ReportFilter filter) {
        String[] options = {
                "Marital Status",
                "Flat Type",
                "Min Age",
                "Max Age",
                "Neighbourhood",
                "Clear All",
                "Done"
        };

        int choice;
        do {
            choice = sharedView.showMenuAndGetChoice("Update Filter", options);
            switch (choice) {
                case 1 -> filter.setMaritalStatus(view.promptMaritalStatus());
                case 2 -> filter.setFlatTypes(view.promptFlatTypes());
                case 3 -> filter.setMinAge(view.promptMinAge());
                case 4 -> filter.setMaxAge(view.promptMaxAge());
                case 5 -> filter.setNeighbourhood(view.promptNeighbourhood());
                case 6 -> {
                    filter.setMaritalStatus(null);
                    filter.setFlatTypes(null);
                    filter.setMinAge(null);
                    filter.setMaxAge(null);
                    filter.setNeighbourhood(null);
                }
                case 7 -> {}
                default -> sharedView.showInvalidChoice();
            }
        } while (choice != 7);

        Session.set("reportFilter", filter);
        sharedView.showMessage("Filter updated.");
    }
}
