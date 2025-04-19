package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Enquiry;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.view.EnquiryViewApplicant;
import sc2002.fcsi.grp3.service.EnquiryService;


import java.util.List;
import java.util.Optional;

public class EnquiryController {
    private final EnquiryViewApplicant view;
    private final EnquiryService service;


    public EnquiryController(EnquiryViewApplicant view, EnquiryService service) {
        this.view = view;
        this.service = service;
    }

    public void start(){
        int choice;
        do{
            choice = view.showEnquiryMenuAndGetChoice();
            switch(choice){
                case 1 -> viewEnquiries();
                case 2 -> addEnquiry();
                case 3 -> editEnquiry();
                case 4 -> deleteEnquiry();
                case 5 -> {}
                default -> view.showMessage("Invalid choice.");
            }
        }while (choice != 5);
    }

    public void viewEnquiries(){
        User user = Session.getCurrentUser();
        view.showEnquiries(service.getOwnEnquiries(user));
    }

    private void addEnquiry(){
        User user = Session.getCurrentUser();
        int projectid = view.promptProjectId();
        Optional<Project> project = service.getProjectById(projectid);

        if (project.isEmpty()){
            view.showError("Project not found!");
            return;
        }
        String title = view.promptTitle();
        String content = view.promptContent();

        boolean success = service.createEnquiry(user, project.get(), title, content);
        if(success){
            view.showMessage("Enquiry submitted successfully!");
        }
        else {
            view.showError("Unable to apply for project.");
        }

    }

    private void editEnquiry() {
        User user = Session.getCurrentUser();
        List<Enquiry> ownEnquiries = service.getOwnEnquiries(user);

        if (ownEnquiries.isEmpty()) {
            view.showError("You have no enquiries to edit.");
            return;
        }


        view.showMessage("Available Enquiries:");
        view.showAvailableEnquiries(service.getOwnEnquiries(user));
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
        String newContent = view.promptContent();
        boolean success = service.editEnquiry(enquiry, newTitle, newContent);
        if (success){
            view.showMessage("Enquiry updated.");
        }
        else{
            view.showMessage("Failed to update Enquiry!");
        }

    }

    private void deleteEnquiry() {
        User user = Session.getCurrentUser();
        List<Enquiry> ownEnquiries = service.getOwnEnquiries(user);

        if (ownEnquiries.isEmpty()) {
            view.showError("You have no enquiries to edit.");
            return;
        }


        view.showMessage("Available Enquiries:");
        view.showAvailableEnquiries(service.getOwnEnquiries(user));
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
