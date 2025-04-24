package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.ArrayList;
import java.util.List;

public class ApplicationView extends BaseView {
    public ApplicationView(Prompter prompt) {
        super(prompt);
    }

    public String promptProjectId() {
        return prompt.promptString("Enter Project ID to apply for (enter to cancel): ");
    }

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

    public int getFlatChoice() {
        return prompt.promptInt("Enter flat choice (0 to exit): ");
    }

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
