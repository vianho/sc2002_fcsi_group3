package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Project;
import java.util.List;

public class ManagerView {
    private final SharedPromptView prompt;

    public ManagerView(SharedPromptView prompt) {
        this.prompt = prompt;
    }
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public int showMenuAndGetChoice(String title, String[] options) {
        return prompt.menuPrompt(title, options, "> ");
    }

    public void showProjects(List<Project> projects) {
        prompt.showTitle("All Projects");
        if (projects.isEmpty()) {
            prompt.showMessage("No projects available.");
        } else {
            List<String> headers = List.of("ID", "Name", "Neighbourhood", "Visible", "Manager NRIC", "Application Open", "Application Close");
            List<List<String>> rows = projects.stream()
                    .map(project -> List.of(
                            String.valueOf(project.getId()),
                            project.getName(),
                            project.getNeighbourhood(),
                            project.isVisible() ? "Yes" : "No",
                            project.getManagerNric(),
                            project.getApplicationOpeningDate().toString(),
                            project.getApplicationClosingDate().toString()
                    ))
                    .toList();
            prompt.showTable(headers, rows);
        }
        prompt.pressEnterToContinue();
    }


    public Project selectProject(List<Project> projects) {
        if (projects.isEmpty()) {
            prompt.showMessage("No projects available to select.");
            return null;
        }

        showProjects(projects);

        int projectId = prompt.promptInt("Enter the ID of the project to select: ");
        return projects.stream()
            .filter(project -> project.getId() == projectId)
            .findFirst()
            .orElse(null);
    }

    public boolean confirmDeletion(Project project) {
        if (project == null) {
            showMessage("No project selected for deletion.");
            return false;
        }
        showMessage("Do you confirm you want to delete the project: " + project.getName() + " (ID: " + project.getId() + ")?");
        String confirmation = prompt.promptString("Enter 'yes' to confirm or 'no' to cancel: ").trim().toLowerCase();

        return confirmation.equals("yes");
    }


    public void showProjectDetails(Project project) {
        if (project == null) {
            prompt.showMessage("No project details available.");
            return;
        }

        prompt.showTitle("Project Details");
        prompt.showMessage("ID: " + project.getId());
        prompt.showMessage("Name: " + project.getName());
        prompt.showMessage("Neighbourhood: " + project.getNeighbourhood());
        prompt.showMessage("Visible: " + (project.isVisible() ? "Yes" : "No"));
        prompt.showMessage("Manager NRIC: " + project.getManagerNric());
        prompt.showMessage("Application Opening Date: " + project.getApplicationOpeningDate());
        prompt.showMessage("Application Closing Date: " + project.getApplicationClosingDate());
        prompt.showMessage("Total Officer Slots: " + project.getTotalOfficerSlots());
        prompt.showMessage("Assigned Officers: " + String.join(", ", project.getOfficerNrics()));

        if (project.getFlats() != null && !project.getFlats().isEmpty()) {
            prompt.showMessage("Flats:");
            List<String> headers = List.of("Flat Type", "Units Available", "Selling Price");
            List<List<String>> rows = project.getFlats().stream()
                    .map(flat -> List.of(
                            flat.getType().getDisplayName(),
                            String.valueOf(flat.getUnitsAvailable()),
                            String.format("%.2f", flat.getSellingPrice())
                    ))
                    .toList();
            prompt.showTable(headers, rows);
        } else {
            prompt.showMessage("No flats available for this project.");
        }

        prompt.pressEnterToContinue();
    }



}
