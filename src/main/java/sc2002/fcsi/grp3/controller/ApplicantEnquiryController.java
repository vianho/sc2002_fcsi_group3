package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.Enquiry;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.EnquiryView;
import sc2002.fcsi.grp3.service.EnquiryService;
import sc2002.fcsi.grp3.util.Validator;
import sc2002.fcsi.grp3.view.SharedView;

import java.util.List;
import java.util.Optional;

/**
 * The ApplicantEnquiryController class handles all operations related to managing enquiries for applicants.
 * It provides functionality for viewing, adding, editing, and deleting enquiries.
 * This controller interacts with the EnquiryService and EnquiryView to facilitate the applicant's tasks.
 */
public class ApplicantEnquiryController {

    private final SharedView sharedView;
    private final EnquiryView view;
    private final EnquiryService service;

    /**
     * Constructs an ApplicantEnquiryController with the necessary dependencies.
     *
     * @param sharedView The SharedView for displaying shared UI elements.
     * @param view       The EnquiryView for displaying enquiry-specific UI.
     * @param service    The EnquiryService for managing enquiries.
     */
    public ApplicantEnquiryController(SharedView sharedView, EnquiryView view, EnquiryService service) {
        this.sharedView = sharedView;
        this.view = view;
        this.service = service;
    }

    /**
     * Starts the enquiry management menu for the applicant and handles navigation to different enquiry-related functionalities.
     */
    public void start() {
        int choice;
        String[] options = {
                "View All Enquiries",
                "Add New Enquiry",
                "Edit Enquiry",
                "Delete Enquiry",
                "Back to Main Menu"
        };
        User user = Session.getCurrentUser();
        do {
            choice = sharedView.showMenuAndGetChoice("Enquiry Menu", options);
            switch (choice) {
                case 1 -> viewEnquiries(user);
                case 2 -> addEnquiry(user);
                case 3 -> editEnquiry(user);
                case 4 -> deleteEnquiry(user);
                case 5 -> {} // Exit to main menu
                default -> sharedView.showInvalidChoice();
            }
        } while (choice != options.length);
    }

    /**
     * Displays all enquiries submitted by the current user.
     *
     * @param user The current user.
     */
    public void viewEnquiries(User user) {
        view.showEnquiriesApplicant(service.getOwnEnquiries(user));
    }

    /**
     * Allows the applicant to add a new enquiry for a specific project.
     *
     * @param user The current user.
     */
    private void addEnquiry(User user) {
        int projectId = view.promptProjectId();
        Optional<Project> project = service.getProjectById(projectId);

        if (project.isEmpty()) {
            view.showError("Project not found!");
            return;
        }

        String title = view.promptTitle();
        if (!Validator.isNonEmpty(title)) {
            view.showError("Title cannot be empty.");
            return;
        }

        String content = view.promptContent();
        if (!Validator.isNonEmpty(content)) {
            view.showError("Content cannot be empty.");
            return;
        }

        boolean success = service.createEnquiry(user, project.get(), title, content);
        if (success) {
            view.showMessage("Enquiry submitted successfully!");
        } else {
            view.showError("Unable to submit enquiry.");
        }
    }

    /**
     * Allows the applicant to edit an existing enquiry they have submitted.
     * Enquiries that have already been replied to cannot be edited.
     *
     * @param user The current user.
     */
    private void editEnquiry(User user) {
        List<Enquiry> ownEnquiries = service.getOwnEnquiries(user);

        if (ownEnquiries.isEmpty()) {
            view.showError("You have no enquiries to edit.");
            return;
        }

        view.showMessage("Available Enquiries:");
        view.showAvailableEnquiriesApplicant(ownEnquiries);
        int id = view.promptEnquiryId();
        Optional<Enquiry> enquiryOpt = service.getOwnEnquiryById(user, id);

        if (enquiryOpt.isEmpty()) {
            view.showError("Enquiry not found or access denied.");
            return;
        }

        Enquiry enquiry = enquiryOpt.get();
        if (enquiry.isReplied()) {
            view.showError("This enquiry has already been replied to and cannot be edited.");
            return;
        }

        String newTitle = view.promptTitle();
        if (!Validator.isNonEmpty(newTitle)) {
            view.showError("Title cannot be empty.");
            return;
        }

        String newContent = view.promptContent();
        if (!Validator.isNonEmpty(newContent)) {
            view.showError("Content cannot be empty.");
            return;
        }

        boolean success = service.editEnquiry(enquiry, newTitle, newContent);
        if (success) {
            view.showMessage("Enquiry updated.");
        } else {
            view.showMessage("Failed to update enquiry!");
        }
    }

    /**
     * Allows the applicant to delete an existing enquiry they have submitted.
     * Enquiries that have already been replied to cannot be deleted.
     *
     * @param user The current user.
     */
    private void deleteEnquiry(User user) {
        List<Enquiry> ownEnquiries = service.getOwnEnquiries(user);

        if (ownEnquiries.isEmpty()) {
            view.showError("You have no enquiries to delete.");
            return;
        }

        view.showMessage("Available Enquiries:");
        view.showAvailableEnquiriesApplicant(ownEnquiries);
        int id = view.promptEnquiryId();

        Optional<Enquiry> enquiryOpt = service.getOwnEnquiryById(user, id);
        if (enquiryOpt.isEmpty()) {
            view.showError("Enquiry not found or access denied.");
            return;
        }

        Enquiry enquiry = enquiryOpt.get();

        if (enquiry.isReplied()) {
            view.showError("This enquiry has already been replied to and cannot be deleted.");
            return;
        }

        boolean success = service.deleteEnquiry(user, id);
        if (success) {
            view.showMessage("Enquiry deleted successfully.");
        } else {
            view.showError("Failed to delete the enquiry.");
        }
    }
}
