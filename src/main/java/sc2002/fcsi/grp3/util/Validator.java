package sc2002.fcsi.grp3.util;

/**
 * The Validator class provides utility methods for validating various types of input.
 */
public class Validator {

    /**
     * Validates if the given NRIC is in a valid format.
     *
     * @param nric the NRIC to validate
     * @return true if the NRIC is valid, false otherwise
     */
    public static boolean isValidNRIC(String nric) {
        return nric != null && nric.matches("^[ST]\\d{7}[A-Z]$");
    }

    /**
     * Checks if the given input is non-empty.
     *
     * @param input the input to check
     * @return true if the input is non-empty, false otherwise
     */
    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Checks if the given string is numeric.
     *
     * @param str the string to check
     * @return true if the string is numeric, false otherwise
     */
    public static boolean isNumeric(String str) {
        return str != null && str.trim().matches("\\d+");
    }

    /**
     * Validates if the given password is strong.
     * A strong password must be at least 8 characters long and include:
     * - At least one uppercase letter
     * - At least one lowercase letter
     * - At least one digit
     * - At least one special character
     *
     * @param password the password to validate
     * @return true if the password is strong, false otherwise
     */
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

