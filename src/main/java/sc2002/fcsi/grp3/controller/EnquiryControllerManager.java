package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.Enquiry;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.EnquiryService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.util.Validator;
import sc2002.fcsi.grp3.view.EnquiryViewManager;


import java.util.List;
import java.util.Optional;

public class EnquiryControllerManager implements IBaseController{
    private final EnquiryViewManager view;
    private final EnquiryService service;

    public EnquiryControllerManager(EnquiryViewManager view, EnquiryService service) {
        this.view = view;
        this.service = service;
    }
    public void start(){
        int choice;
        do{
            choice = view.showEnquiryMenuAndGetChoice();
            switch(choice){
                case 1 -> viewAssignedEnquiries();
                case 2 -> replyEnquiry();
                case 3 -> {}
                default -> view.showError("Invalid choice!");
            }
        }while (choice != 3);
    }

    private void viewAssignedEnquiries(){
        User manager = Session.getCurrentUser();
        view.showEnquiries(service.getAllEnquiriesManager(manager));
    }

    private void replyEnquiry(){
        User manager = Session.getCurrentUser();
        List<Enquiry> notReplied = service.getUnrepliedEnquiriesHandledByManager(manager);
        if(notReplied.isEmpty()){
            view.showError("No unreplied enquiries available for the project you are managing!");
            return;
        }

        view.showAvailableEnquiries(notReplied);
        int id = view.promptEnquiryId();
        Optional<Enquiry> enquiryOpt = service.getUnrepliedEnquiriesHandledByManagerById(manager, id);

        if (enquiryOpt.isEmpty()){
            view.showError("Enquiry not found or not in your assigned projects.");
            return;
        }
        String reply = view.promptReply();
        if (!Validator.isNonEmpty(reply)){
            view.showError("Reply cannot be empty.");
            return;
        }
        boolean success = service.replyToEnquiry(enquiryOpt.get(), manager, reply);
        if (success){
            view.showMessage("Reply submitted successfully!");
        }
        else{
            view.showError("Failed to reply to enquiry.");
        }
    }
}
