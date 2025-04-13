package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectView {
    private final SharedPromptView prompt;

    public ProjectView(SharedPromptView prompt) {
        this.prompt = prompt;
    }

    public void showProjects(List<Project> projects) {
        if (projects.isEmpty()) {
            prompt.showMessage("No projects found.");
        } else {
            prompt.showMessage("Projects:");
            for (Project project : projects) {
                prompt.showMessage(String.format("ID: %d | Name: %s | Neighborhood: %s | Flat types: %s | Visible: %s",
                        project.getId(),
                        project.getName(),
                        project.getNeighbourhood(),
                        project.getFlats()
                                .stream()
                                .map(flat -> flat.getType().getDisplayName())
                                .collect(Collectors.joining(", ")),
                        project.isVisible() ? "Yes" : "No"));
            }
        }
        prompt.pressEnterToContinue();
    }
}
