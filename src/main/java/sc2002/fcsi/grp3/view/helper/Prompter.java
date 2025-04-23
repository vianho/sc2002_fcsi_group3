package sc2002.fcsi.grp3.view.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

public class Prompter {
    private final Scanner sc;
    final static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public Prompter() {
        this.sc = new Scanner(System.in);
    }

    public void clear() {
        // not sure how to clear screen yet :(((((
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Utility method to show a table in CLI.
     *
     * @param headers list of column headers
     * @param rows    list of rows, each row a list of strings
     */
    public void showTable(List<String> headers, List<List<String>> rows) {
        TablePrinter.printTable(headers, rows);
    }

    public void showTitle(String title) {
        System.out.print("\n");
        String border = "=".repeat(title.length() + 4);
        System.out.println(border);
        System.out.println("| " + title + " |");
        System.out.println(border);
    }

    public int menuPromptInt(String title, String[] options, String prompt) {
        showTitle(title);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("(%d). %s\n", i + 1, options[i]);
        }
        return promptInt(prompt);
    }

    public String promptString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public String prompStringOptional(String msg) {
        String res = promptString(msg);
        if (res.isBlank()) {
            return null;
        }
        return res;
    }

    public <T> T promptWithRetry(Supplier<T> promptFn, String errorMsg) {
        while (true) {
            try {
                return promptFn.get();
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public int promptInt(String msg) {
        return promptWithRetry(() ->
                        Integer.parseInt(promptString(msg)),
                "Invalid number, please try again."
        );
    }

    public Integer promptIntOptional(String msg) {
        while (true) {
            String input = sc.nextLine().trim();

            if (input.isBlank()) return null;

            try {
                System.out.print(msg);
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
    public float promptFloat(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Float.parseFloat(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid floating-point number.");
            }
        }
    }

    public String promptString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public float promptFloat(String msg) {
        return promptWithRetry(() ->
                        Float.parseFloat(promptString(msg)),
                "Invalid decimal point number, please try again."
        );
    }

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

    public LocalDate promptDate(String msg) {
        return promptWithRetry(() ->
                LocalDate.parse(promptString(msg), dtFormatter),
                "Invalid date, please enter the date in DD-MM-YYYY format."
        );
    }

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

    public String promptHiddenInput(String msg) {
        Console console = System.console();
        if (console == null) {
            showWarning("WARNING: Your password will be visible as you type.");
            return promptString(msg);
        }
        char[] chars = console.readPassword(msg);
        return new String(chars);
    }

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

    public FlatType promptFlatType(String msg) {
        return promptWithRetry(() ->
                        FlatType.fromCode(promptString(msg).trim()),
                "Invalid flat type.");
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showMessagef(String format, Object ... args) {
        System.out.printf(format + "\n", args);
    }

    public void showWarning(String msg) {
        System.out.println("[Warning] " + msg);
    }

    public void showError(String msg) {
        System.out.println("[Error] " + msg);
    }

    public void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

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
