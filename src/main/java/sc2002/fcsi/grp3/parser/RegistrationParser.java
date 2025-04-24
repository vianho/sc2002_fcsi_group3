package sc2002.fcsi.grp3.parser;

import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Registration;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.EnquiryStatus;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;

import java.time.LocalDate;
import java.util.Map;

public class RegistrationParser implements  IBaseParser<Registration> {

    private final Map<Integer, Project> projectMap;
    private final Map<String, User> userMap;

    public RegistrationParser(Map<Integer, Project> projectMap, Map<String, User> userMap){

        this.projectMap = projectMap;
        this.userMap = userMap;
    }

    @Override
    public Registration parse(String[] tokens){
        if (tokens.length != 5) return null;
        String id = tokens[0].trim();
        Project project = projectMap.get(Integer.parseInt(tokens[1].trim()));
        User applicant = userMap.get(tokens[2].trim());
        RegistrationStatus status = RegistrationStatus.valueOf(tokens[3].trim());
        LocalDate submittedAt = LocalDate.parse(tokens[4].trim(), dtFormatter);


        if (project == null || applicant == null) return null;

        return new Registration(id, project, applicant, status, submittedAt);
    }

}
