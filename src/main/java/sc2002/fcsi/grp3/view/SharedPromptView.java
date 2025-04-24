package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.view.helper.TablePrinter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class SharedPromptView {
    private final Scanner sc;

    public SharedPromptView() {
        this.sc = new Scanner(System.in);
    }

    public void clear() {
        // not sure how to clear screen yet :(((((
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Utility method to show a table in CLI.
     * @param headers list of column headers
     * @param rows list of rows, each row a list of strings
     */
    public void showTable(List<String> headers, List<List<String>> rows) {
        TablePrinter.printTable(headers, rows);
    }

    public void showTitle(String title) {
        // clear();
        System.out.print("\n");
        String border = "=".repeat(title.length() + 4);
        System.out.println(border);
        System.out.println("| " + title + " |");
        System.out.println(border);
    }

    public int menuPrompt(String title, String[] options, String prompt) {
        showTitle(title);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("(%d). %s\n", i+1, options[i]);
        }
        return promptInt(prompt);
    }

    public int promptInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine().trim());
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

    public LocalDate promptDate(String msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                System.out.print(msg);
                String input = sc.nextLine().trim();
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }
    public String promptString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showError(String msg) {
        System.out.println("[Error] " + msg);
    }

    public void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }
}
