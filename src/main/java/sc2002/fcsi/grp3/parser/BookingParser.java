package sc2002.fcsi.grp3.parser;

import sc2002.fcsi.grp3.model.Booking;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.time.LocalDate;
import java.util.Map;

/**
 * The BookingParser class is responsible for parsing booking data from CSV tokens.
 * It maps project and user references using provided maps.
 */
public class BookingParser implements IBaseParser<Booking> {

    private final Map<Integer, Project> projectMap;
    private final Map<String, User> userMap;

    /**
     * Constructs a BookingParser with the specified project and user maps.
     *
     * @param projectMap a map of project IDs to Project objects
     * @param userMap    a map of user NRICs to User objects
     */
    public BookingParser(Map<Integer, Project> projectMap, Map<String, User> userMap) {
        this.projectMap = projectMap;
        this.userMap = userMap;

    }

    /**
     * Parses a CSV row into a Booking object.
     *
     * @param tokens the CSV row tokens
     * @return the parsed Booking object, or null if the row is invalid
     */
    @Override
    public Booking parse(String[] tokens) {
        if (tokens.length != 6) return null;

        int id = Integer.parseInt(tokens[0].trim());
        FlatType flatType = FlatType.fromCode(tokens[1].trim());
        Project project = projectMap.get(Integer.parseInt(tokens[2].trim()));
        User applicant = userMap.get(tokens[3].trim());
        User officer = userMap.get(tokens[4].trim());
        LocalDate bookingDate = LocalDate.parse(tokens[5].trim(), dtFormatter);

        if (project == null || applicant == null || officer == null) return null;

        Flat flat = project.getFlats().stream()
                .filter(f -> f.getType() == flatType)
                .findFirst()
                .orElse(null);

        if (flat == null) return null;
        return new Booking(id, flat, project, applicant, officer, bookingDate);

    }


}
