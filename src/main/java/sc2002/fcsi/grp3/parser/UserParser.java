package sc2002.fcsi.grp3.parser;

import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;
import sc2002.fcsi.grp3.model.role.IRole;
import sc2002.fcsi.grp3.model.role.RoleFactory;

public class UserParser implements IBaseParser<User> {
    @Override
    public User parse(String[] tokens) {
        if (tokens.length != 6) return null;

        String nric = tokens[0].trim();
        String name = tokens[1].trim();
        int age = Integer.parseInt(tokens[2].trim());
        MaritalStatus maritalStatus = MaritalStatus.valueOf(tokens[3].trim().toUpperCase());
        String password = tokens[4].trim();
        IRole role = RoleFactory.fromString(tokens[5].trim());
        return new User(nric, name, age, password, maritalStatus, role);
    }
}
