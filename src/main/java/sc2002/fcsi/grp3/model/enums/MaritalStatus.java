package sc2002.fcsi.grp3.model.enums;

/**
 * Represents the marital status of a user.
 */
public enum MaritalStatus {
    SINGLE,
    MARRIED;

    /**
     * Returns a user-friendly display name for the marital status.
     * Example: {@code MaritalStatus.SINGLE.getDisplayName()} → {@code "Single"}
     *
     * @return the display name of the marital status
     */
    public String getDisplayName() {
        String lower = this.name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    /**
     * Parses a string to a {@code MaritalStatus}, case-insensitively.
     *
     * @param input the input string (e.g. "single", "MARRIED")
     * @return the corresponding {@code MaritalStatus}
     * @throws IllegalArgumentException if the input is invalid or null/blank
     */
    public static MaritalStatus fromString(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Marital status cannot be null or blank.");
        }

        return switch (input.trim().toUpperCase()) {
            case "SINGLE" -> SINGLE;
            case "MARRIED" -> MARRIED;
            default -> throw new IllegalArgumentException("Invalid marital status: " + input);
        };
    }
}
