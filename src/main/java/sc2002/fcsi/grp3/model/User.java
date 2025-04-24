package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.MaritalStatus;
import sc2002.fcsi.grp3.model.role.IRole;

import java.util.Objects;

/**
 * The User class represents a user in the system.
 * It contains details such as the user's NRIC, name, age, marital status, and role.
 */
public class User {

    private final String nric;
    private String name;
    private int age;
    private String password;
    private MaritalStatus maritalStatus;
    private IRole role;

    /**
     * Constructs a User with the specified details.
     *
     * @param name          the name of the user
     * @param nric          the NRIC of the user
     * @param age           the age of the user
     * @param password      the password of the user
     * @param maritalStatus the marital status of the user
     * @param role          the role of the user
     */
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

    // Getters

    /**
     * Gets the name of the user.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the NRIC of the user.
     *
     * @return the user's NRIC
     */
    public String getNric() {
        return nric;
    }

    /**
     * Gets the age of the user.
     *
     * @return the user's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the marital status of the user.
     *
     * @return the user's marital status
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Gets the password of the user.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the role of the user.
     *
     * @return the user's role
     */
    public IRole getRole() {
        return role;
    }

    /**
     * Gets the name of the user's role.
     *
     * @return the role name
     */
    public String getRoleName() {
        return role.getRoleName();
    }

    /**
     * Checks if the user is eligible for a 2-room flat.
     * Eligibility depends on marital status and age.
     *
     * @return true if the user is eligible, false otherwise
     */
    public boolean isEligibleFor2R() {
        return (maritalStatus.equals(MaritalStatus.MARRIED) && age >= 21)
                || (maritalStatus.equals(MaritalStatus.SINGLE) && age >= 35);
    }

    /**
     * Checks if the user is eligible for any flat type.
     * Eligibility depends on marital status and age.
     *
     * @return true if the user is eligible, false otherwise
     */
    public boolean isEligibleForAny() {
        return (maritalStatus.equals(MaritalStatus.MARRIED) && age >= 21);
    }

    // Setters

    /**
     * Sets the name of the user.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the age of the user.
     *
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password =  password;
    }

    /**
     * Sets the marital status of the user.
     *
     * @param maritalStatus the marital status to set
     */
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role to set
     */
    public void setRole(IRole role) {
        this.role = role;
    }

    /**
     * Checks if this user is equal to another object.
     * Two users are considered equal if their NRICs are the same.
     *
     * @param o the object to compare
     * @return true if the users are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(nric, user.nric);
    }

    /**
     * Computes the hash code for the user based on their NRIC.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(nric);
    }
}