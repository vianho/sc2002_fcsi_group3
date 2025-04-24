package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.*;
import sc2002.fcsi.grp3.model.enums.*;

import java.util.*;
import java.util.stream.Stream;

/**
 * The ProjectService class manages operations related to projects.
 * It provides methods to retrieve, filter, update, and manage projects and their associated data.
 */
public class ProjectService {

    private final DataStore db;

    /**
     * Constructs a ProjectService with the specified data store.
     *
     * @param db the data store containing project data
     */
    public ProjectService(DataStore db) {
        this.db = db;
    }

    /**
     * Retrieves all visible projects for the specified user.
     * Includes projects the user is eligible for and projects they have applied to.
     *
     * @param user the user for whom visible projects are retrieved
     * @return a list of visible projects
     */
    public List<Project> getVisibleProjects(User user) {
        List<Project> visibleProjects = db.getProjects()
                .stream()
                .filter(Project::isVisible)
                .filter(Project::isApplicationOpen)
                .toList();

        if (user.isEligibleFor2R()) {
            visibleProjects = visibleProjects
                    .stream()
                    .filter(p -> p.hasAvailableFlatType(FlatType.TWO_ROOM))
                    .toList();
        }

        List<Project> appliedProjects = db.getApplications().stream()
                .filter(app -> app.getApplicant().equals(user))
                .map(Application::getProject)
                .distinct()
                .toList();

        return Stream.concat(visibleProjects.stream(), appliedProjects.stream())
                .distinct()
                .toList();
    }

    /**
     * Filters and sorts a list of projects based on the specified filter and sort options.
     *
     * @param user       the user applying the filter
     * @param projects   the list of projects to filter
     * @param filter     the filter criteria
     * @param sortOption the sorting criteria
     * @return a list of filtered and sorted projects
     */
    public List<Project> filterProjects(User user, List<Project> projects, ProjectFilter filter, ProjectSortOption sortOption) {
        Stream<Project> stream = projects.stream()
                .filter(p -> filter.getNeighbourhood() == null ||
                        p.getNeighbourhood().equalsIgnoreCase(filter.getNeighbourhood()))

                .filter(p -> filter.getApplicationOpeningAfter() == null ||
                        !p.getApplicationOpeningDate().isAfter(filter.getApplicationOpeningAfter()))

                .filter(p -> filter.getApplicationClosingBefore() == null ||
                        !p.getApplicationClosingDate().isBefore(filter.getApplicationClosingBefore()))

                .filter(p -> filter.getMinSellingPrice() == null ||
                        p.getFlats().stream().anyMatch(f -> f.getSellingPrice() >= filter.getMinSellingPrice()))

                .filter(p -> filter.getMaxSellingPrice() == null ||
                        p.getFlats().stream().anyMatch(f -> f.getSellingPrice() <= filter.getMaxSellingPrice()))

                .filter(p -> p.getFlats().stream().anyMatch(f ->
                        // Flat must be of a selected type (or no type selected)
                        (filter.getflatTypes() == null || filter.getflatTypes().isEmpty() || filter.getflatTypes().contains(f.getType()))
                                // user must be eligible for flat
                                && f.getType().isEligible(user)
                                // price filter
                                && (filter.getMinSellingPrice() == null || f.getSellingPrice() >= filter.getMinSellingPrice())
                                && (filter.getMaxSellingPrice() == null || f.getSellingPrice() <= filter.getMaxSellingPrice())
                        )
                );
        Comparator<Project> comparator = switch (sortOption.getKey()) {
            case NAME -> Comparator.comparing(Project::getName);
            case APPLICATION_OPENING_DATE -> Comparator.comparing(Project::getApplicationOpeningDate);
            case APPLICATION_CLOSING_DATE -> Comparator.comparing(Project::getApplicationClosingDate);
            case PRICE -> Comparator.comparing(p ->
                    p.getFlats().stream()
                            .map(Flat::getSellingPrice)
                            .min(Float::compare)
                            .orElse(Float.MAX_VALUE)
            );
        };

        return stream.sorted(sortOption.applyTo(comparator)).toList();
    }

    /**
     * Retrieves all projects managed by the specified officer.
     *
     * @param officerNric the NRIC of the officer
     * @return a list of projects managed by the officer
     */
    public List<Project> getProjectsManagedByOfficer(String officerNric) {
        return db.getProjects()
                .stream()
                .filter(project -> project.getOfficerNrics().contains(officerNric))
                .toList();
    }

    /**
     * Retrieves all projects managed by the specified manager.
     *
     * @param managerNric the NRIC of the manager
     * @return a list of projects managed by the manager
     */
    public List<Project> getProjectsManagedBy(String managerNric) {
        return db.getProjects()
                .stream()
                .filter(project -> project.getManagerNric().equals(managerNric))
                .toList();
    }

    /**
     * Retrieves all projects from the data store.
     *
     * @return a list of all projects
     */
    public List<Project> getAllProjects() {
        return db.getProjects();
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id the ID of the project
     * @return an Optional containing the project, or empty if not found
     */
    public Optional<Project> getProjectById(int id) {
        return db.getProjects()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    /**
     * Retrieves all available flats in a project for the specified user.
     *
     * @param user    the user for whom available flats are retrieved
     * @param project the project containing the flats
     * @return a list of available flats
     */
    public List<Flat> getAvailableFlats(User user, Project project) {
        if (project == null) {
            return List.of(); // Return an empty list if the project is null
        }

        List<Flat> availableFlats = project.getFlats()
                .stream()
                .filter(flat -> flat.getUnitsAvailable() > 0)
                .toList();

        if (user.isEligibleForAny()) {
            return availableFlats;
        }

        if (user.isEligibleFor2R()) {
            return availableFlats
                    .stream()
                    .filter(flat -> flat.getType() == FlatType.TWO_ROOM)
                    .toList();
        }

        return List.of();
    }

    /**
     * Sets the visibility of a project.
     *
     * @param project       the project to update
     * @param newVisibility the new visibility status
     * @return true if the visibility was updated successfully
     */
    public boolean setProjectVisibility(Project project, boolean newVisibility) {
        project.setVisible(newVisibility);
        return true;
    }

    /**
     * Creates a new project and assigns it to a manager.
     *
     * @param project the project to create
     * @param Nric    the NRIC of the manager
     */
    public void createProject(Project project, String Nric) {
        project.setManagerNric(Nric);
        db.addProject(project);
    }

    /**
     * Deletes a project by its ID.
     *
     * @param projectId the ID of the project to delete
     * @return true if the project was deleted successfully, false otherwise
     */
    public boolean deleteProject(int projectId) {
        List<Project> updatedProjects = db.getProjects()
                .stream()
                .filter(project -> project.getId() != projectId)
                .toList();

        if (updatedProjects.size() == db.getProjects().size()) {
            return false; // No project was deleted
        }

        db.setProjects(updatedProjects);
        return true;
    }

    /**
     * Retrieves the total number of projects in the data store.
     *
     * @return the total number of projects
     */
    public int getProjectSize() {
        return db.getProjects().size();
    }

    /**
     * Retrieves all pending officer registrations for a specific project.
     *
     * @param projectId the ID of the project
     * @return a list of pending officer registrations
     */
    public List<Registration> getPendingOfficerRegistrations(int projectId) {
        return db.getRegistrations().stream()
                .filter(reg -> reg.getProject().getId() == projectId)
                .filter(reg -> reg.getStatus() == RegistrationStatus.PENDING)
                .toList();
    }

    /**
     * Updates the status of an officer registration.
     *
     * @param registration the registration to update
     * @param status       the new status
     * @return true if the update was successful, false otherwise
     */
    public boolean updateOfficerRegistrationStatus(Registration registration, RegistrationStatus status) {
        registration.setStatus(status);
        if (status == RegistrationStatus.APPROVED) {
            // Attempt to assign the officer to the project
            boolean success = registration.getProject().assignOfficer(registration.getApplicant().getNric());
            return success; // Return true if the officer was successfully assigned
        }
        return true; // Return true for non-approval statuses
    }

    /**
     * Retrieves all pending BTO applications for a specific project.
     *
     * @param projectId the ID of the project
     * @return a list of pending BTO applications
     */
    public List<Application> getPendingBTOApplications(int projectId) {
        return db.getApplications().stream()
                .filter(app -> app.getProject().getId() == projectId)
                .filter(app -> app.getStatus() == ApplicationStatus.PENDING)
                .toList();
    }

    /**
     * Updates the status of a BTO application.
     *
     * @param application the application to update
     * @param status      the new status
     * @return true if the update was successful, false otherwise
     */
    public boolean updateBTOApplicationStatus(Application application, ApplicationStatus status) {
        if (status == ApplicationStatus.SUCCESSFUL) {
            FlatType flatType = application.getFlatType();
            Flat flat = application.getProject().getFlats().stream()
                    .filter(f -> f.getType() == flatType && f.getUnitsAvailable() > 0)
                    .findFirst()
                    .orElse(null);

            if (flat == null) {
                return false; // No available units for the flat type
            }

            flat.reduceUnitsAvailable();
        }
        application.setStatus(status);
        return true;
    }

    /**
     * Retrieves all pending withdrawal requests for a specific project.
     *
     * @param projectId the ID of the project
     * @return a list of pending withdrawal requests
     */
    public List<Application> getPendingWithdrawalRequests(int projectId) {
        return db.getApplications().stream()
                .filter(app -> app.getProject().getId() == projectId)
                .filter(app -> app.getStatus() == ApplicationStatus.WITHDRAWAL_REQUESTED)
                .toList();
    }

    /**
     * Updates the status of a withdrawal request.
     *
     * @param application the application to update
     * @param approve     true to approve the request, false to reject it
     */
    public void updateWithdrawalRequest(Application application, boolean approve) {
        if (approve) {
            application.setStatus(ApplicationStatus.WITHDRAWN);
        }
    }

    /**
     * Updates the details of an existing project.
     *
     * @param projectId      the ID of the project to update
     * @param updatedProject the updated project details
     * @return true if the update was successful, false otherwise
     */
    public boolean updateProject(int projectId, Project updatedProject) {
        Project existingProject = db.getProjects().stream()
                .filter(project -> project.getId() == projectId)
                .findFirst()
                .orElse(null);

        if (existingProject == null) {
            return false; // Project not found
        }

        // Check for overlapping application periods
        boolean hasOverlap = db.getProjects().stream()
                .filter(project -> project.getId() != projectId) // Exclude the current project
                .anyMatch(project ->
                        !updatedProject.getApplicationClosingDate().isBefore(project.getApplicationOpeningDate()) &&
                                !updatedProject.getApplicationOpeningDate().isAfter(project.getApplicationClosingDate())
                );

        if (hasOverlap) {
            return false; // Overlapping application periods detected
        }

        // Update project details
        existingProject.setName(updatedProject.getName());
        existingProject.setNeighbourhood(updatedProject.getNeighbourhood());
        existingProject.setApplicationOpeningDate(updatedProject.getApplicationOpeningDate());
        existingProject.setApplicationClosingDate(updatedProject.getApplicationClosingDate());
        existingProject.setFlats(updatedProject.getFlats());
        existingProject.setTotalOfficerSlots(updatedProject.getTotalOfficerSlots());
        return true;
    }

    /**
     * Updates the total number of officer slots for a project.
     *
     * @param project  the project to update
     * @param newSlots the new number of officer slots
     * @return true if the update was successful, false otherwise
     */
    public boolean updateTotalOfficerSlots(Project project, int newSlots) {
        if (newSlots < 0 || newSlots > 10) {
            return false; // Invalid number of officer slots
        }
        project.setTotalOfficerSlots(newSlots);
        return true;
    }

    /**
     * Retrieves all approved officer registrations for a specific project.
     *
     * @param projectId the ID of the project
     * @return a list of approved officer registrations
     */
    public List<Registration> getApprovedOfficerRegistrations(int projectId) {
        return db.getRegistrations().stream()
                .filter(reg -> reg.getProject().getId() == projectId) // Match the project ID
                .filter(reg -> reg.getStatus() == RegistrationStatus.APPROVED) // Filter by APPROVED status
                .toList();
    }

    /**
     * Generates a new unique project ID.
     *
     * @return the new project ID
     */
    public int generateNewProjectId() {
        return db.getProjects().stream()
                .mapToInt(Project::getId)
                .max()
                .orElse(0) + 1; // Increment the highest existing ID by 1
    }
}
