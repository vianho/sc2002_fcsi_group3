package sc2002.fcsi.grp3.dto;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Flat;

/**
 * The ProjectFlatRow record represents a combination of a project and a flat.
 * It is used to display project and flat details together in a tabular format.
 *
 * @param project the project associated with the flat
 * @param flat    the flat associated with the project
 */
public record ProjectFlatRow(Project project, Flat flat) {}
