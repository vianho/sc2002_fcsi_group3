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

    public void viewProjects(){

    }

    public project selectProject(){

    }

    public boolean confirmDeletion(){

        return ;
    }

    public List<Project> getProjectManagedBy(String Nric){


        return Project;
    }

}
