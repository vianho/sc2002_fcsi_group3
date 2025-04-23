package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Booking;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;

import java.util.List;
import java.util.stream.Collectors;

public class OfficerView {
    private final SharedPromptView prompt;

    public OfficerView(SharedPromptView prompt) {
        this.prompt = prompt;
    }

    //Show Menu and Get Choice
    public int showMenuAndGetChoice(String title, String[] options) {
        return prompt.menuPrompt(title, options, "> ");
    }

    //Print Message
    public void showMessage(String msg) {
        System.out.println(msg);
    }


    //Show List of Projects
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
    }
    public void showBooking(List<Booking> bookings) {
        prompt.showTitle("Bookings");
        if (bookings.isEmpty()) {
            prompt.showMessage("No available Booking.");
        } else {
            List<String> headers = List.of("ID", "FlatType", "PorjID", "ANRIC", "ONRIC");
            List<List<String>> rows = bookings.stream()
                    .map(p -> List.of(
                            String.valueOf(p.getId()),
                            p.getFlatType().toString(),
                            p.getProjectId().toString()
//                            p.getApplicant().toString(),
//                            p.getOfficer().toString()
                    ))
                    .toList();
            prompt.showTable(headers, rows);
        }
        prompt.pressEnterToContinue();
    }
    public void showHandledProject(Project project) {
        prompt.showTitle("Project Details");

        if (project == null) {
            prompt.showMessage("Project not found.");
            return;
        }
        List<String> headers = List.of("ID", "Name", "Neighbourhood", "Flat Types", "Application Opening Date", "Application Closing Date");

        List<String> row = List.of(
                String.valueOf(project.getId()),
                project.getName(),
                project.getNeighbourhood(),
                project.getFlats().stream()
                        .map(f -> f.getType().getDisplayName())
                        .collect(Collectors.joining(", ")),
                project.getApplicationOpeningDate().toString(),
                project.getApplicationClosingDate().toString()
        );

        prompt.showTable(headers, List.of(row));  // wrap single row in a List
    }


}
