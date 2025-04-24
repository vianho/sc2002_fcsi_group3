package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.dto.FlatBookingReportRow;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.ReportFilter;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.util.List;

/**
 * The ReportService class provides functionality for generating reports.
 * It includes methods to retrieve flat booking reports based on specified filters.
 */
public class ReportService {

    private final DataStore db;

    /**
     * Constructs a ReportService with the specified data store.
     *
     * @param db the data store containing application and project data
     */
    public ReportService(DataStore db) {
        this.db = db;
    }

    /**
     * Generates a flat booking report based on the specified filter.
     * The report includes details of booked flats and applies the filter criteria if provided.
     *
     * @param filter the filter criteria for the report, or null to include all booked flats
     * @return a list of FlatBookingReportRow objects representing the report
     */
    public List<FlatBookingReportRow> getFlatBookingReport(ReportFilter filter) {
        return db.getApplications().stream()
                .filter(app -> app.getStatus() == ApplicationStatus.BOOKED)
                .map(app -> {
                    User u = app.getApplicant();
                    FlatType f = app.getFlatType();
                    Project p = app.getProject();
                    return new FlatBookingReportRow(
                            u.getName(),
                            u.getAge(),
                            u.getMaritalStatus(),
                            f,
                            p.getName(),
                            p.getNeighbourhood()
                    );
                })
                .filter(row -> filter == null || filter.matches(row))
                .toList();
    }
}
