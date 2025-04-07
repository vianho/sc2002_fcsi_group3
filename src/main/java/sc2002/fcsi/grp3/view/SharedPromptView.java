package sc2002.fcsi.grp3.view;

import java.util.Scanner;

public class SharedPromptView {
    private final Scanner sc;

    public SharedPromptView() {
        this.sc = new Scanner(System.in);
    }

    public int menuPrompt(String title, String[] options, String prompt) {
        System.out.println("\n=== " + title + " ===");
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
