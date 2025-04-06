package sc2002.fcsi.grp3.util;

public class Validator {
    public static boolean isValidNRIC(String nric) {
        return nric != null && nric.matches("^[ST]\\d{7}[A-Z]$");
    }

    // Add more as needed
}

