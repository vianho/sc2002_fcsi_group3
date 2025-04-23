package sc2002.fcsi.grp3.util;

public class Validator {
    public static boolean isValidNRIC(String nric) {
        return nric != null && nric.matches("^[ST]\\d{7}[A-Z]$");
    }

    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Add more as needed
    public static boolean isNumeric(String str) {
        return str != null && str.trim().matches("\\d+");
    }

    public static boolean isValidDateFormat(String dateStr) {
        // dd/mm/yyyy
        return dateStr != null && dateStr.matches("^\\d{2}/\\d{2}/\\d{4}$");
    }

    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

}

