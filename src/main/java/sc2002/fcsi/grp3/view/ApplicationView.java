package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.ArrayList;
import java.util.List;

/**
 * The ApplicationView class provides methods for displaying and interacting with application-related data.
 * It includes functionality for showing flat options, prompting for project IDs, and displaying applications.
 */
public class ApplicationView extends BaseView {

    /**
     * Constructs an ApplicationView with the specified prompter.
     *
     * @param prompt the prompter used for user input and output
     */
    public ApplicationView(Prompter prompt) {
        super(prompt);
    }

    /**
     * Prompts the user to enter a project ID for applying.
     *
     * @return the entered project ID, or an empty string if canceled
     */
    public String promptProjectId() {
        return prompt.promptString("Enter Project ID to apply for (enter to cancel): ");
    }

    /**
     * Displays a list of available flats with their details.
     *
     * @param flats the list of flats to display
     */
    public void showFlatOptions(List<Flat> flats) {
        if (flats.isEmpty()) {
            showMessage("No available flats.");
        } else {
            List<String> headers = List.of("Choice", "Flat Type", "Units available", "Price (S$)");
            List<List<String>> rows = new ArrayList<>();
            for (int i = 0; i < flats.size(); i++) {
                Flat flat = flats.get(i);
                rows.add(List.of(
                        String.valueOf(i + 1),
                        flat.getType().getDisplayName(),
                        String.valueOf(flat.getUnitsAvailable()),
                        String.format("%.2f", (double) flat.getSellingPrice())
                ));
            }
            prompt.showTable(headers, rows);
        }
    }

    /**
     * Prompts the user to select a flat choice from the displayed options.
     *
     * @return the selected flat choice, or 0 to exit
     */
    public int getFlatChoice() {
        return prompt.promptInt("Enter flat choice (0 to exit): ");
    }

    /**
     * Displays a list of applications with their details.
     *
     * @param applications the list of applications to display
     */
    public void showApplications(List<Application> applications) {
        if (applications.isEmpty()) {
            showMessage("No applications found.");
        } else {
            List<String> headers = List.of("Application ID", "Project Name", "Neighbourhood", "Flat Type", "Status", "Submitted At");
            List<List<String>> rows = applications.stream()
                    .map(app -> List.of(
                            String.valueOf(app.getId()),
                            app.getProject().getName(),
                            app.getProject().getNeighbourhood(),
                            app.getFlatType().getDisplayName(),
                            app.getStatus().toString(),
                            app.getSubmittedAt().toString()
                    ))
                    .toList();
            prompt.showTable(headers, rows);
        }
    }
}
