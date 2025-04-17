package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicantView {
    private final SharedPromptView prompt;

    public ApplicantView(SharedPromptView prompt) {
        this.prompt = prompt;
    }

    public int showMenuAndGetChoice(String title, String[] options) {
        return prompt.menuPrompt(title, options, "> ");
    }

    public void showProjects(List<Project> projects) {
        prompt.showTitle("Projects");
        if (projects.isEmpty()) {
            prompt.showMessage("No available projects.");
        } else {
            List<String> headers = List.of("ID", "Name", "Neighbourhood", "Flat Types", "Application Opening Date", "Application Closing Date");
            List<List<String>> rows = projects.stream()
                    .map(p -> List.of(
                            String.valueOf(p.getId()),
                            p.getName(),
                            p.getNeighbourhood(),
                            p.getFlats().stream()
                                    .map(f -> f.getType().getDisplayName())
                                    .collect(Collectors.joining(", ")),
                            p.getApplicationOpeningDate().toString(),
                            p.getApplicationClosingDate().toString()
                    ))
                    .toList();
            prompt.showTable(headers, rows);
        }
        prompt.pressEnterToContinue();
    }

    public int promptProjectId() {
        return prompt.promptInt("Enter Project ID to apply: ");
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showFlatOptions(List<Flat> flats) {
        if (flats.isEmpty()) {
            prompt.showMessage("No available flats.");
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
        prompt.showTitle("Applications");
        if (applications.isEmpty()) {
            prompt.showMessage("No applications found.");
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
        prompt.pressEnterToContinue();
    }

    public void showApplicationSuccess() {
        prompt.showMessage("Application submitted successfully!");
    }

    public void showApplicationFailure(String msg) {
        prompt.showError(msg);
    }
}
