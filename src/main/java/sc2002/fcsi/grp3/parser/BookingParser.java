package sc2002.fcsi.grp3.parser;

import sc2002.fcsi.grp3.model.Booking;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.time.LocalDate;
import java.util.Map;

public class BookingParser implements IBaseParser<Booking> {
    private final Map<Integer, Project> projectMap;
    private final Map<String, User> userMap;


    public BookingParser(Map<Integer, Project> projectMap, Map<String, User> userMap){
        this.projectMap = projectMap;
        this.userMap = userMap;

    }

    @Override
    public Booking parse(String[] tokens){
        if(tokens.length != 6) return null;

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
