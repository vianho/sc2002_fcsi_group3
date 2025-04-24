package sc2002.fcsi.grp3.parser;


import sc2002.fcsi.grp3.model.Enquiry;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.EnquiryStatus;


import java.time.LocalDate;
import java.util.Map;

/**
 * The EnquiryParser class is responsible for parsing enquiry data from CSV tokens.
 * It maps project and user references using provided maps.
 */
public class EnquiryParser implements IBaseParser<Enquiry> {

    private final Map<Integer, Project> projectMap;
    private final Map<String, User> userMap;

    /**
     * Constructs an EnquiryParser with the specified project and user maps.
     *
     * @param projectMap a map of project IDs to Project objects
     * @param userMap    a map of user NRICs to User objects
     */
    public EnquiryParser(Map<Integer, Project> projectMap, Map<String, User> userMap) {
        this.projectMap = projectMap;
        this.userMap = userMap;
    }

    /**
     * Parses a CSV row into an Enquiry object.
     *
     * @param tokens the CSV row tokens
     * @return the parsed Enquiry object, or null if the row is invalid
     */
    @Override
    public Enquiry parse(String[] tokens) {
        if(tokens.length != 10) return null;
        int id = Integer.parseInt(tokens[0].trim());
        String title = tokens[1].trim();
        String content = tokens[2].trim();
        String reply = tokens[3].isBlank() ? null : tokens[3].trim();
        User createdBy = userMap.get(tokens[4].trim());
        Project relatedProject = projectMap.get(Integer.parseInt(tokens[5].trim()));
        User repliedBy = tokens[6].isBlank() ? null : userMap.get(tokens[6].trim());
        EnquiryStatus status = EnquiryStatus.valueOf(tokens[7].trim());
        LocalDate createdAt = LocalDate.parse(tokens[8].trim(), dtFormatter);
        LocalDate lastUpdatedAt = LocalDate.parse(tokens[9].trim(), dtFormatter);

        return new Enquiry(
                id,
                title,
                content,
                reply,
                createdBy,
                relatedProject,
                repliedBy,
                status,
                createdAt,
                lastUpdatedAt
        );
    }
}
