package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.MaritalStatus;
import sc2002.fcsi.grp3.model.role.IRole;

public class User {
    private final String nric;
    private String name;
    private int age;
    private String password;
    private MaritalStatus maritalStatus;
    private IRole role;

    public User(
            String name,
            String nric,
            int age,
            String password,
            MaritalStatus maritalStatus,
            IRole role) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.password = password;
        this.maritalStatus = maritalStatus;
        this.role = role;
    }

    // getters

    public String getName() {
        return name;
    }

    public String getNric() {
        return nric;
    }

    public int getAge() {
        return age;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public String getPassword() {
        return password;
    }

    public IRole getRole() {
        return role;
    }

    public String getRoleName() {
        return role.getRoleName();
    }

    public boolean isEligibleFor2R() {
        return (maritalStatus.equals(MaritalStatus.MARRIED) && age >= 21)
                || (maritalStatus.equals(MaritalStatus.SINGLE) && age >= 35);
    }

    public boolean isEligibleForAny() {
        return (maritalStatus.equals(MaritalStatus.MARRIED) && age >= 21);
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password =  password;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setRole(IRole role) {
        this.role = role;
    }
}