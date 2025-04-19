package sc2002.fcsi.grp3.controller;
import sc2002.fcsi.grp3.model.Enquiry;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.EnquiryService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.EnquiryViewOfficer;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnquiryControllerOfficer {
    private final EnquiryViewOfficer view;
    private final EnquiryService service;


    public EnquiryControllerOfficer(EnquiryViewOfficer view, EnquiryService service) {
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
        User officer = Session.getCurrentUser();
        view.showEnquiries(service.getEnquiriesHandledByOfficer(officer));
    }

    private void replyEnquiry(){
        User officer = Session.getCurrentUser();
        List<Enquiry> notReplied = service.getUnrepliedEnquiriesHandledByOfficer(officer);
        if(notReplied.isEmpty()){
            view.showError("No unreplied enquiries available. ");
            return;
        }

        view.showAvailableEnquiries(notReplied);
        int id = view.promptEnquiryId();
        Optional<Enquiry> enquiryOpt = service.getUnrepliedEnquiriesHandledByOfficerById(officer, id);

        if (enquiryOpt.isEmpty()){
            view.showError("Enquiry not found or not in your assigned projects.");
            return;
        }
        String reply = view.promptReply();
        boolean success = service.replyToEnquiry(enquiryOpt.get(), officer, reply);
        if (success){
            view.showMessage("Reply submitted successfully!");
        }
        else{
            view.showError("Failed to reply to enquiry.");
        }
    }

}
