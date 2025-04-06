package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Project;

import java.util.List;

public class ApplicantView {
    private final SharedPromptView prompt;

    public ApplicantView(SharedPromptView prompt) {
        this.prompt = prompt;
    }

    public int showApplicantMenuAndGetChoice() {
        String[] options = {
                "View Available Projects",
                "Apply for a Project",
                "View My Applications",
                "View My Enquiries",
                "Logout"};
        return prompt.menuPrompt("Applicant Menu", options, "> ");
    }

    public void showProjects(List<Project> projects) {
        if (projects.isEmpty()) {
            prompt.showMessage("No available projects.");
        } else {
            prompt.showMessage("\n=== Available projects ===");
            for (Project project : projects) {
                prompt.showMessage(project.getId() + ": " + project.getName() + " at " + project.getNeighbourhood());
            }
        }
    }

    public int promptProjectId() {
        return prompt.promptInt("Enter Project ID to apply: ");
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
