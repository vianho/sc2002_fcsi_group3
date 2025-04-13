package sc2002.fcsi.grp3.model.enums;

import sc2002.fcsi.grp3.model.User;

public enum FlatType {
    TWO_ROOM("2-Room", "2R") {
        @Override
        public boolean isEligible(User user) {
            return user.isEligibleFor2R();
        }
    },
    THREE_ROOM("3-Room", "3R") {
        @Override
        public boolean isEligible(User user) {
            return user.isEligibleForAny();
        }
    };

    private final String displayName;
    private final String code;

    FlatType(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCode() {
        return code;
    }

    public static FlatType fromString(String text) {
        for (FlatType type : FlatType.values()) {
            if (type.displayName.equalsIgnoreCase(text) || type.name().equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown flat type: " + text);
    }

    public static FlatType fromCode(String code) {
        for (FlatType type : FlatType.values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown flat type: " + code);
    }

    public abstract boolean isEligible(User user);
}
