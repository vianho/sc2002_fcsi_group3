package sc2002.fcsi.grp3.util;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.dto.ProjectFlatRow;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewUtils {

    public static List<ProjectFlatRow> flattenEligibleFlats(List<Project> projects, User user) {
        List<ProjectFlatRow> rows = new ArrayList<>();

        for (Project project : projects) {
            for (Flat flat : project.getFlats()) {
                if (flat.getType().isEligible(user)) {
                    rows.add(new ProjectFlatRow(project, flat));
                }
            }
        }
        return rows;
    }
}