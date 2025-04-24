package sc2002.fcsi.grp3.view.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;

import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * Utility class for prompting and displaying user input/output in the CLI.
 * Includes support for typed and optional input, menus, tables, and formatting.
 */
public class Prompter {
    private final Scanner sc;
    final static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Constructs a {@code Prompter} using standard input.
     */
    public Prompter() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Attempts to clear the console screen.
     */
    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Displays a CLI table using headers and rows.
     *
     * @param headers the column headers
     * @param rows    the table rows
     */
    public void showTable(List<String> headers, List<List<String>> rows) {
        TablePrinter.printTable(headers, rows);
    }

    /**
     * Displays a stylized title with borders.
     *
     * @param title the title to display
     */
    public void showTitle(String title) {
        System.out.print("\n");
        String border = "=".repeat(title.length() + 4);
        System.out.println(border);
        System.out.println("| " + title + " |");
        System.out.println(border);
    }

    /**
     * Prompts the user with a menu and returns the selected option index.
     *
     * @param title  the menu title
     * @param options the list of option strings
     * @param prompt the prompt message
     * @return the selected option index (1-based)
     */
    public int menuPromptInt(String title, String[] options, String prompt) {
        showTitle(title);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("(%d). %s\n", i + 1, options[i]);
        }
        return promptInt(prompt);
    }

    /**
     * Prompts for a string input.
     *
     * @param msg the message to display
     * @return the trimmed input string
     */
    public String promptString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    /**
     * Prompts for an optional string input (returns null if blank).
     *
     * @param msg the message to display
     * @return the trimmed input or null
     */
    public String prompStringOptional(String msg) {
        String res = promptString(msg);
        if (res.isBlank()) {
            return null;
        }
        return res;
    }

    /**
     * Repeatedly prompts until a valid result is returned from the supplier.
     *
     * @param promptFn the supplier that may throw
     * @param errorMsg the error message to show on failure
     * @return the result from the supplier
     * @param <T> the return type
     */
    public <T> T promptWithRetry(Supplier<T> promptFn, String errorMsg) {
        while (true) {
            try {
                return promptFn.get();
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    /**
     * Prompts for an integer input with retry on invalid input.
     *
     * @param msg the message to display
     * @return the valid integer input
     */
    public int promptInt(String msg) {
        return promptWithRetry(() ->
                        Integer.parseInt(promptString(msg)),
                "Invalid number, please try again."
        );
    }

    /**
     * Prompts for an optional integer input (returns null if blank).
     *
     * @param msg the message to display
     * @return the valid integer input or null if the input is blank
     */
    public Integer promptIntOptional(String msg) {
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine().trim();

            if (input.isBlank()) return null;

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    /**
     * Prompts for a float input with retry on invalid input.
     *
     * @param msg the message to display
     * @return the valid float input
     */
    public float promptFloat(String msg) {
        return promptWithRetry(() ->
                        Float.parseFloat(promptString(msg)),
                "Invalid decimal point number, please try again."
        );
    }

    /**
     * Prompts for an optional float input (returns null if blank).
     *
     * @param msg the message to display
     * @return the valid float input or null if the input is blank
     */
    public Float promptFloatOptional(String msg) {
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine().trim();

            if (input.isBlank()) return null;

            try {
                return Float.parseFloat(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid decimal point number. Please try again.");
            }
        }
    }

    /**
     * Prompts for a date input in the format DD-MM-YYYY with retry on invalid input.
     *
     * @param msg the message to display
     * @return the valid LocalDate object
     */
    public LocalDate promptDate(String msg) {
        return promptWithRetry(() ->
                LocalDate.parse(promptString(msg), dtFormatter),
                "Invalid date, please enter the date in DD-MM-YYYY format."
        );
    }

    /**
     * Prompts for an optional date input in the format DD-MM-YYYY (returns null if blank).
     *
     * @param msg the message to display
     * @return the valid LocalDate object or null if the input is blank or invalid
     */
    public LocalDate promptDateOptional(String msg) {
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine().trim();

            if (input.isBlank()) return null;

            try {
                return LocalDate.parse(input, dtFormatter);
            } catch (NumberFormatException e) {
                System.out.println("Invalid date. Please try again.");
            }
        }
    }

    /**
     * Prompts for a password-like hidden input.
     * Warns if console is unavailable and falls back to visible input.
     *
     * @param msg the prompt message
     * @return the entered string
     */
    public String promptHiddenInput(String msg) {
        Console console = System.console();
        if (console == null) {
            showWarning("WARNING: Your password will be visible as you type.");
            return promptString(msg);
        }
        char[] chars = console.readPassword(msg);
        return new String(chars);
    }

    /**
     * Prompts for a comma-separated list of optional flat types.
     * Returns null if the input is blank. Retries on invalid flat type input.
     *
     * @param msg the message to display
     * @return a List of {@link FlatType} or null if input is blank
     */
    public List<FlatType> promptFlatTypesOptional(String msg) {
        System.out.print(msg);
        String input = sc.nextLine().trim();

        if (input.isBlank()) return null;

        String[] parts = input.split(",");
        List<FlatType> types = new ArrayList<>();

        for (String part : parts) {
            try {
                types.add(FlatType.fromCode(part.trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid flat type: " + part.trim());
                return promptFlatTypesOptional(msg); // retry entire input
            }
        }
        return types;
    }

    /**
     * Prompts for a single {@link FlatType} with retry on invalid input.
     *
     * @param msg the message to display
     * @return the valid {@link FlatType}
     */
    public FlatType promptFlatType(String msg) {
        return promptWithRetry(() ->
                        FlatType.fromCode(promptString(msg).trim()),
                "Invalid flat type.");
    }

    /**
     * Prompts for an optional {@link MaritalStatus} (returns null if blank).
     * Retries on invalid marital status input.
     *
     * @param msg the message to display
     * @return the valid {@link MaritalStatus} or null if input is blank
     */
    public MaritalStatus promptMaritalStatusOptional(String msg) {
        System.out.print(msg);
        MaritalStatus maritalStatus;
        String input = sc.nextLine().trim();

        if (input.isBlank()) return null;

        try {
            maritalStatus = MaritalStatus.fromString(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid marital status: " + input.trim());
            return promptMaritalStatusOptional(msg); // retry entire input
        }
        return maritalStatus;
    }

    /**
     * Prompts for a single {@link MaritalStatus} with retry on invalid input.
     *
     * @param msg the message to display
     * @return the valid {@link MaritalStatus}
     */
    public MaritalStatus promptMaritalStatus(String msg) {
        return promptWithRetry(() ->
                        MaritalStatus.fromString(promptString(msg).trim()),
                "Invalid marital status.");
    }

    /**
     * Displays a simple message to the user.
     *
     * @param msg the message to display
     */
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Displays a formatted message to the user.
     *
     * @param format the format string (as in {@link String#format(String, Object...)})
     * @param args   the arguments to the format string
     */
    public void showMessagef(String format, Object ... args) {
        System.out.printf(format + "\n", args);
    }

    /**
     * Displays a warning message to the user, prefixed with "[Warning]".
     *
     * @param msg the warning message to display
     */
    public void showWarning(String msg) {
        System.out.println("[Warning] " + msg);
    }

    /**
     * Displays an error message to the user, prefixed with "[Error]".
     *
     * @param msg the error message to display
     */
    public void showError(String msg) {
        System.out.println("[Error] " + msg);
    }

    /**
     * Prompts the user to press Enter to continue.
     */
    public void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

    /**
     * Asks the user for confirmation with a yes/no prompt.
     *
     * @param msg the confirmation message
     * @return true if user confirms with 'y', false if 'n'
     */
    public boolean confirm(String msg) {
        while (true) {
            System.out.print(msg + " (y/n): ");
            String input = new Scanner(System.in).nextLine().trim().toLowerCase();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }
}
