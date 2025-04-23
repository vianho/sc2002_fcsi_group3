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

    
    public Project getNewProjectDetails() {
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
                0, // ID will be assigned by the system
                name,
                neighbourhood,
                false, // Default visibility is false
                applicationOpeningDate,
                applicationClosingDate,
                null, // Manager NRIC will be set later
                totalOfficerSlots,
                flats
    );
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

        String[] options = applications.stream()
                .map(app -> "Application ID: " + app.getId() + ", Applicant: " + app.getApplicant().getName() + ", Flat Type: " + app.getFlatType())
                .toArray(String[]::new); // Convert List<String> to String[]

        int choice = prompt.menuPrompt("Select an Application", options, "Enter your choice: ");
        if (choice < 1 || choice > applications.size()) {
            prompt.showMessage("Invalid choice.");
            return null;
        }

        return applications.get(choice - 1); // Return the selected application
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
