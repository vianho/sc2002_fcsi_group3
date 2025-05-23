package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.Enquiry;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.EnquiryService;
import sc2002.fcsi.grp3.service.result.ActionResult;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.util.Validator;
import sc2002.fcsi.grp3.view.EnquiryView;
import sc2002.fcsi.grp3.view.SharedView;


import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing enquiries assigned to a manager.
 * Allows the manager to view and reply to enquiries related to their projects.
 */
public class ManagerEnquiryController implements IBaseController {

    private final SharedView sharedView;
    private final EnquiryView view;
    private final EnquiryService service;

    /**
     * Constructs a ManagerEnquiryController with the required dependencies.
     *
     * @param sharedView the shared view for displaying common UI elements
     * @param view       the view for displaying enquiry-related UI elements
     * @param service    the service for managing enquiry-related operations
     */
    public ManagerEnquiryController(SharedView sharedView, EnquiryView view, EnquiryService service) {
        this.sharedView = sharedView;
        this.view = view;
        this.service = service;
    }

    /**
     * Starts the manager enquiry menu, allowing the manager to view or reply to enquiries.
     */
    public void start() {
        int choice;
        String[] options = {
                "View All Enquiries Submitted",
                "Reply to Enquiry for the project you are managing",
                "Back"
        };

        do {
            choice = sharedView.showMenuAndGetChoice("Manager Enquiry Menu", options);
            switch (choice) {
                case 1 -> viewAssignedEnquiries();
                case 2 -> replyEnquiry();
                case 3 -> {
                }
                default -> view.showError("Invalid choice!");
            }
        } while (choice != 3);
    }

    /**
     * Displays all enquiries assigned to the manager for the projects they are managing.
     */
    private void viewAssignedEnquiries() {
        User manager = Session.getCurrentUser();
        view.showEnquiriesOfficerManager(service.getAllEnquiriesManager(manager));
    }

    /**
     * Allows the manager to reply to an enquiry for the projects they are managing.
     * Prompts the manager to select an enquiry and provide a reply.
     */
    private void replyEnquiry() {
        User manager = Session.getCurrentUser();
        List<Enquiry> notReplied = service.getUnrepliedEnquiriesHandledByManager(manager);
        if (notReplied.isEmpty()) {
            view.showError("No unreplied enquiries available for the project you are managing!");
            return;
        }

        view.showAvailableEnquiriesOfficerManager(notReplied);
        int id = view.promptEnquiryId();
        Optional<Enquiry> enquiryOpt = service.getUnrepliedEnquiriesHandledByManagerById(manager, id);

        if (enquiryOpt.isEmpty()) {
            view.showError("Enquiry not found or not in your assigned projects.");
            return;
        }
        String reply = view.promptReply();
        if (!Validator.isNonEmpty(reply)) {
            view.showError("Reply cannot be empty.");
            return;
        }
        ActionResult<Enquiry> result = service.replyToEnquiry(enquiryOpt.get(), manager, reply);
        if (result.isSuccess()) {
            view.showMessage(result.getMessage());
        } else {
            view.showError(result.getMessage());
        }
    }
}
