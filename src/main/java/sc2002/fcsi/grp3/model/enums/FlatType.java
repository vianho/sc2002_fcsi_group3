package sc2002.fcsi.grp3.model.enums;

import sc2002.fcsi.grp3.model.User;

/**
 * The FlatType enum represents the different types of flats available in the system.
 * Each flat type has a display name, a code, and eligibility criteria for users.
 */
public enum FlatType {

    /**
     * Represents a 2-room flat type.
     * Eligibility is determined by the user's specific criteria for 2-room flats.
     */
    TWO_ROOM("2-Room", "2R") {
        @Override
        public boolean isEligible(User user) {
            return user.isEligibleFor2R();
        }
    },

    /**
     * Represents a 3-room flat type.
     * Eligibility is determined by the user's general eligibility for any flat type.
     */
    THREE_ROOM("3-Room", "3R") {
        @Override
        public boolean isEligible(User user) {
            return user.isEligibleForAny();
        }
    };

    private final String displayName;
    private final String code;

    /**
     * Constructs a FlatType with the specified display name and code.
     *
     * @param displayName the display name of the flat type
     * @param code        the code representing the flat type
     */
    FlatType(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    /**
     * Gets the display name of the flat type.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the code representing the flat type.
     *
     * @return the flat type code
     */
    public String getCode() {
        return code;
    }

    /**
     * Converts a string to a FlatType based on its display name or enum name.
     *
     * @param text the string to convert
     * @return the corresponding FlatType
     * @throws IllegalArgumentException if the string does not match any FlatType
     */
    public static FlatType fromString(String text) {
        for (FlatType type : FlatType.values()) {
            if (type.displayName.equalsIgnoreCase(text) || type.name().equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown flat type: " + text);
    }

    /**
     * Converts a code to a FlatType.
     *
     * @param code the code to convert
     * @return the corresponding FlatType
     * @throws IllegalArgumentException if the code does not match any FlatType
     */
    public static FlatType fromCode(String code) {
        for (FlatType type : FlatType.values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown flat type: " + code);
    }

    /**
     * Determines if the given user is eligible for this flat type.
     *
     * @param user the user to check eligibility for
     * @return true if the user is eligible, false otherwise
     */
    public abstract boolean isEligible(User user);
}
