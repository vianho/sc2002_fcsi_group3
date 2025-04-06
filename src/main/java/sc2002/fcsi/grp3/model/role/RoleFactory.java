package sc2002.fcsi.grp3.model.role;

public class RoleFactory {
    public static IRole fromString(String roleName) {
        if (roleName == null) return null;

        return switch (roleName.trim().toLowerCase()) {
            case "applicant" -> new ApplicantRole();
            case "officer" -> new OfficerRole();
            case "manager" -> new ManagerRole();
            default -> throw new IllegalArgumentException("Unknown role: " + roleName);
        };
    }
}
