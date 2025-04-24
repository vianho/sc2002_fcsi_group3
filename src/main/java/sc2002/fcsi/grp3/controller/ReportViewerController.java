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

/**
 * The ReportViewerController class handles the viewing and filtering of applicant booking reports.
 * It provides functionality for creating, updating, and clearing filters for generating reports.
 */
public class ReportViewerController implements IBaseController {

    private final SharedView sharedView;
    private final ReportView view;
    private final ReportService reportService;

    /**
     * Constructs a ReportViewerController with the necessary dependencies.
     *
     * @param sharedView    the shared view for displaying common UI elements
     * @param view          the view for displaying report-related UI elements
     * @param reportService the service for managing report-related operations
     */
    public ReportViewerController(SharedView sharedView, ReportView view, ReportService reportService) {
        this.sharedView = sharedView;
        this.view = view;
        this.reportService = reportService;
    }

    /**
     * Starts the report viewer menu, allowing the user to filter and view applicant booking reports.
     */
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

    /**
     * Prompts the user to create a new filter for the report.
     * The filter criteria include marital status, flat types, age range, and neighbourhood.
     */
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

    /**
     * Allows the user to update an existing filter for the report.
     * The user can modify individual filter criteria or clear all filters.
     *
     * @param filter the current filter to update
     */
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
