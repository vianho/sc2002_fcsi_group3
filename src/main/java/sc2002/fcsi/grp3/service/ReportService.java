package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.dto.FlatBookingReportRow;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.ReportFilter;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.util.List;

public class ReportService {
    private final DataStore db;

    public ReportService(DataStore db) {
        this.db = db;
    }

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
