package sc2002.fcsi.grp3.util;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.dto.ProjectFlatRow;

import java.util.ArrayList;
import java.util.List;

/**
 * The ProjectViewUtils class provides utility methods for processing and displaying project data.
 */
public class ProjectViewUtils {

    /**
     * Flattens a list of projects into a list of ProjectFlatRow objects, including only flats eligible for the specified user.
     *
     * @param projects the list of projects to process
     * @param user     the user for whom eligibility is checked
     * @return a list of ProjectFlatRow objects containing eligible flats
     */
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