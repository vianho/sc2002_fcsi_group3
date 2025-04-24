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

public class OfficerEnquiryController implements IBaseController {
    private final SharedView sharedView;
    private final EnquiryView view;
    private final EnquiryService service;


    public OfficerEnquiryController(SharedView sharedView, EnquiryView view, EnquiryService service) {
        this.sharedView = sharedView;
        this.view = view;
        this.service = service;
    }

    public void start(){
        int choice;
        String[] options  = {
                "View Assigned Project Enquiries",
                "Reply to Enquiry",
                "Back"
        };
        do{
            choice = sharedView.showMenuAndGetChoice("Officer Enquiry Menu", options);
            switch(choice){
                case 1 -> viewAssignedEnquiries();
                case 2 -> replyEnquiry();
                default -> view.showError("Invalid choice!");
            }
        } while (choice != options.length);
    }

    private void viewAssignedEnquiries(){
        User officer = Session.getCurrentUser();
        view.showEnquiriesOfficerManager(service.getEnquiriesHandledByOfficer(officer));
    }

    private void replyEnquiry(){
        User officer = Session.getCurrentUser();
        List<Enquiry> notReplied = service.getUnrepliedEnquiriesHandledByOfficer(officer);
        if(notReplied.isEmpty()){
            view.showError("No unreplied enquiries available. ");
            return;
        }

        view.showAvailableEnquiriesOfficerManager(notReplied);
        int id = view.promptEnquiryId();
        Optional<Enquiry> enquiryOpt = service.getUnrepliedEnquiriesHandledByOfficerById(officer, id);

        if (enquiryOpt.isEmpty()){
            view.showError("Enquiry not found or not in your assigned projects.");
            return;
        }
        String reply = view.promptReply();
        if (!Validator.isNonEmpty(reply)){
            view.showError("Reply cannot be empty.");
            return;
        }
        ActionResult<Enquiry> result = service.replyToEnquiry(enquiryOpt.get(), officer, reply);
        if (result.isSuccess()){
            view.showMessage(result.getMessage());
        }
        else{
            view.showError(result.getMessage());
        }
    }

}
