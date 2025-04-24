package sc2002.fcsi.grp3.parser;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.time.LocalDate;
import java.util.Map;

/**
 * The ApplicationParser class is responsible for parsing application data from CSV tokens.
 * It maps project and user references using provided maps.
 */
public class ApplicationParser implements IBaseParser<Application> {

    private final Map<Integer, Project> projectMap;
    private final Map<String, User> userMap;

    /**
     * Constructs an ApplicationParser with the specified project and user maps.
     *
     * @param projectMap a map of project IDs to Project objects
     * @param userMap    a map of user NRICs to User objects
     */
    public ApplicationParser(Map<Integer, Project> projectMap, Map<String, User> userMap) {
        this.projectMap = projectMap;
        this.userMap = userMap;
    }

    /**
     * Parses a CSV row into an Application object.
     *
     * @param tokens the CSV row tokens
     * @return the parsed Application object, or null if the row is invalid
     */
    @Override
    public Application parse(String[] tokens) {
        if (tokens.length != 6) return null;

        int id = Integer.parseInt(tokens[0].trim());
        Project project = projectMap.get(Integer.parseInt(tokens[1].trim()));
        User user = userMap.get(tokens[2].trim());
        FlatType flatType = FlatType.fromCode(tokens[3].trim());
        ApplicationStatus status = ApplicationStatus.valueOf(tokens[4].trim());
        LocalDate submittedAt = LocalDate.parse(tokens[5].trim(), dtFormatter);

        return new Application(
                id,
                project,
                user,
                flatType,
                status,
                submittedAt
        );
    }
}
