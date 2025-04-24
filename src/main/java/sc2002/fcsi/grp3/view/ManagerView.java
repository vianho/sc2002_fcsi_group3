package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.Registration;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public String promptString(String msg) {
        return prompt.promptString(msg);
    }

    public int promptInt(String msg) {
        return prompt.promptInt(msg);
    }

    public LocalDate promptDate(String msg) {
        return prompt.promptDate(msg);
    }
    
    public float promptFloat(String msg) {
        return prompt.promptFloat(msg);
    }

    
    public Project  getNewProjectDetails(String managerNric) {
        prompt.showTitle("Create New Project");

        // Prompt for project details
        String name = promptString("Enter project name: ");
        String neighbourhood = promptString("Enter project neighbourhood: ");
        LocalDate applicationOpeningDate = promptDate("Enter application opening date (YYYY-MM-DD): ");
        LocalDate applicationClosingDate = promptDate("Enter application closing date (YYYY-MM-DD): ");
        int totalOfficerSlots = promptInt("Enter total officer slots: ");

        // Prompt for flats
        List<Flat> flats = getFlatDetails();

        // Create and return the new project
        return new Project(
                name,
                neighbourhood,
                false, // Default visibility is false
                applicationOpeningDate,
                applicationClosingDate,
                managerNric, // Set the manager's NRIC
                totalOfficerSlots,
                flats, List.of()
        );
    }
    public void showProjects(String title, List<Project> projects) {
        prompt.showTitle(title); // Use the provided title
        if (projects.isEmpty()) {
            prompt.showMessage("No projects available.");
        } else {
            List<String> headers = List.of("ID", "Name", "Neighbourhood", "Visible", "Manager NRIC", "Application Open", "Application Close", "Officer Slots");
            List<List<String>> rows = projects.stream()
                    .map(project -> List.of(
                            String.valueOf(project.getId()),
                            project.getName(),
                            project.getNeighbourhood(),
                            project.isVisible() ? "Yes" : "No",
                            project.getManagerNric(),
                            project.getApplicationOpeningDate().toString(),
                            project.getApplicationClosingDate().toString(),
                            project.getOfficerNrics().size() + "/" + project.getTotalOfficerSlots() // Display current/total officer slots
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

        showProjects("Your Projects",projects);

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

        // Display project details in a structured format
        prompt.showTitle("Project Details");
        StringBuilder details = new StringBuilder();
        details.append("ID: ").append(project.getId()).append("\n")
               .append("Name: ").append(project.getName()).append("\n")
               .append("Neighbourhood: ").append(project.getNeighbourhood()).append("\n")
               .append("Visible: ").append(project.isVisible() ? "Yes" : "No").append("\n")
               .append("Manager NRIC: ").append(project.getManagerNric()).append("\n")
               .append("Application Opening Date: ").append(project.getApplicationOpeningDate()).append("\n")
               .append("Application Closing Date: ").append(project.getApplicationClosingDate()).append("\n")
               .append("Officer Slots: ").append(project.getOfficerNrics().size()).append("/")
               .append(project.getTotalOfficerSlots()).append("\n")
               .append("Assigned Officers: ").append(
                       project.getOfficerNrics().isEmpty() ? "None" : String.join(", ", project.getOfficerNrics())
               ).append("\n");

        // Display flat details if available
        if (project.getFlats() != null && !project.getFlats().isEmpty()) {
            details.append("\nFlats:\n");
            details.append(String.format("%-15s %-20s %-15s\n", "Flat Type", "Units Available", "Selling Price"));
            details.append("----------------------------------------------------------\n");
            project.getFlats().forEach(flat -> {
                details.append(String.format("%-15s %-20d $%-15.2f\n",
                        flat.getType().getDisplayName(),
                        flat.getUnitsAvailable(),
                        flat.getSellingPrice()));
            });
        } else {
            details.append("\nNo flats available for this project.\n");
        }

        // Display the formatted details
        prompt.showMessage(details.toString());
        prompt.pressEnterToContinue();
    }

    // Display pending HDB Officer registrations
    public void showPendingOfficerRegistrations(List<Registration> registrations) {
        prompt.showTitle("Pending HDB Officer Registrations");
        if (registrations.isEmpty()) {
            prompt.showMessage("No pending registrations.");
        } else {
            List<String> headers = List.of("ID", "Officer Name", "Project Name", "Status");
            List<List<String>> rows = registrations.stream()
                    .map(reg -> List.of(
                            String.valueOf(reg.getId()),
                            reg.getApplicant().getName(),
                            reg.getProject().getName(),
                            reg.getStatus().toString()
                    ))
                    .toList();
            prompt.showTable(headers, rows);
        }
    }

    // Prompt to approve/reject an HDB Officer registration
    public Registration promptOfficerRegistrationApproval(List<Registration> registrations) {
        showPendingOfficerRegistrations(registrations);
        int id = prompt.promptInt("Enter Registration ID to approve/reject (0 to cancel): ");
        return registrations.stream()
                .filter(reg -> reg.getId().equals(String.valueOf(id)))
                .findFirst()
                .orElse(null);
    }

    // Display pending Applicant BTO applications
    public void showPendingBTOApplications(List<Application> applications) {
        prompt.showTitle("Pending Applicant BTO Applications");
        if (applications.isEmpty()) {
            prompt.showMessage("No pending applications.");
        } else {
            List<String> headers = List.of("ID", "Applicant Name", "Flat Type", "Status");
            List<List<String>> rows = applications.stream()
                    .map(app -> List.of(
                            String.valueOf(app.getId()),
                            app.getApplicant().getName(),
                            app.getFlatType().getDisplayName(),
                            app.getStatus().toString()
                    ))
                    .toList();
            prompt.showTable(headers, rows);
        }
    }

    // Prompt to approve/reject an Applicant BTO application
    public Application promptBTOApplicationApproval(List<Application> applications) {
        showPendingBTOApplications(applications);
        int id = prompt.promptInt("Enter Application ID to approve/reject (0 to cancel): ");
        return applications.stream()
                .filter(app -> app.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Prompt to approve/reject an HDB Officer registration
    public String promptApprovalDecision(String message) {
        return prompt.promptString(message + " (approve/reject): ").toLowerCase();
    }

    // Display messages for success or failure
    public void showApprovalMessage(boolean success, String successMessage, String failureMessage) {
        if (success) {
            prompt.showMessage(successMessage);
        } else {
            prompt.showMessage(failureMessage);
        }
    }

    public List<Flat> getFlatDetails() {
        List<Flat> flats = new ArrayList<>();
        boolean addMoreFlats = true; // Initialize to true to enter the loop

        do {
            String flatTypeCode = promptString("Enter flat type (e.g., 2R, 3R): ");
            int unitsAvailable = promptInt("Enter number of units available: ");
            float sellingPrice = promptFloat("Enter selling price: ");
            FlatType flatType = FlatType.fromCode(flatTypeCode);

            if (flatType == null) {
                showMessage("Invalid flat type. Please try again.");
                continue;
            }

            flats.add(new Flat(flatType, unitsAvailable, sellingPrice));

            String addMore = promptString("Add another flat? (yes/no): ").trim().toLowerCase();
            addMoreFlats = addMore.equals("yes");
        } while (addMoreFlats);

        return flats;
    }

    public Application selectApplication(List<Application> applications) {
        if (applications.isEmpty()) {
            prompt.showMessage("No applications available.");
            return null;
        }

        // Display the list of applications with details
        String[] options = applications.stream()
                .map(app -> "Application ID: " + app.getId() +
                            ", Applicant: " + app.getApplicant().getName() +
                            ", Flat Type: " + app.getFlatType().getDisplayName()) // Ensure FlatType is displayed correctly
                .toArray(String[]::new); // Convert List<String> to String[]

        // Prompt the manager to select an application
        int choice = prompt.menuPrompt("Select an Application", options, "Enter your choice (1-" + applications.size() + "): ");
        if (choice < 1 || choice > applications.size()) {
            prompt.showMessage("Invalid choice. Please select a valid application.");
            return null;
        }

        // Return the selected application
        return applications.get(choice - 1);
    }

    public void showApprovedOfficerRegistrations(List<Registration> registrations) {
        prompt.showTitle("Approved HDB Officer Registrations");
        if (registrations.isEmpty()) {
            prompt.showMessage("No approved registrations found.");
        } else {
            List<String> rows = registrations.stream()
                    .map(reg -> "Officer Name: " + reg.getApplicant().getName() +
                                ", Officer NRIC: " + reg.getApplicant().getNric() +
                                ", Project Name: " + reg.getProject().getName())
                    .toList();

            String[] options = rows.toArray(String[]::new); // Convert List<String> to String[]

            prompt.menuPrompt("Approved Registrations", options, "Press Enter to continue...");
        }
        prompt.pressEnterToContinue();
    }

    public Registration selectRegistration(List<Registration> registrations) {
        if (registrations.isEmpty()) {
            prompt.showMessage("No registrations available.");
            return null;
        }

        // Display the list of registrations with details
        String[] options = registrations.stream()
                .map(reg -> "Registration ID: " + reg.getId() +
                            ", Officer Name: " + reg.getApplicant().getName() +
                            ", Officer NRIC: " + reg.getApplicant().getNric())
                .toArray(String[]::new); // Convert List<String> to String[]

        // Prompt the manager to select a registration
        int choice = prompt.menuPrompt("Select a Registration", options, "Enter your choice: ");
        if (choice < 1 || choice > registrations.size()) {
            prompt.showMessage("Invalid choice.");
            return null;
        }

        // Return the selected registration
        return registrations.get(choice - 1);
    }
}
