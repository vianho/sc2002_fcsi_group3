package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Enquiry;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.EnquiryStatus;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnquiryService {
    private final DataStore db;

    public EnquiryService(DataStore db) {
        this.db = db;
    }

    public List<Enquiry> getEnquiries() {
        return db.getEnquiries();
    }

    public List<Enquiry> getOwnEnquiries(User user) {
        return db.getEnquiries()
                .stream()
                .filter(enq -> enq.getCreatedBy().getNric().equalsIgnoreCase(user.getNric()))
                .collect(Collectors.toList());
    }

    public List<Enquiry> getEnquiriesHandledByOfficer(User officer){
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getRelatedProject().getOfficerNrics().contains(officer.getNric()))
                .collect(Collectors.toList());
    }

    public List<Enquiry> getUnrepliedEnquiriesHandledByOfficer(User officer){
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getRelatedProject().getOfficerNrics().contains(officer.getNric()))
                .filter(e -> !e.isReplied())
                .collect(Collectors.toList());
    }

    public Optional<Enquiry> getUnrepliedEnquiriesHandledByOfficerById(User officer, int id ){
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getId() == id)
                .filter(e -> e.getRelatedProject().getOfficerNrics().contains(officer.getNric()))
                .filter(e -> !e.isReplied())
                .findFirst();
    }
    public List<Enquiry> getAllEnquiriesManager(User manager) {
        return db.getEnquiries();
    }

    public List<Enquiry> getUnrepliedEnquiriesHandledByManager(User manager){
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getRelatedProject().getManagerNric().equals(manager.getNric()))
                .filter(e -> !e.isReplied())
                .collect(Collectors.toList());
    }

    public Optional<Enquiry> getUnrepliedEnquiriesHandledByManagerById(User manager, int id ){
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getId() == id)
                .filter(e -> e.getRelatedProject().getManagerNric().equals(manager.getNric()))
                .filter(e -> !e.isReplied())
                .findFirst();
    }

    public Optional<Enquiry> getOwnEnquiryById(User user, int id) {
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getId() == id && e.getCreatedBy().getNric().equals(user.getNric()))
                .findFirst();
    }

    public boolean createEnquiry(User user, Project project, String title, String content) {
        Enquiry enquiry = new Enquiry(title, content, user, project);
        try{
            db.addEnquiry(enquiry);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean editEnquiry(Enquiry enquiry, String newTitle, String newContent) {

        if(enquiry.isReplied()){
            return false;
        }
        enquiry.setTitle(newTitle);
        enquiry.setContent(newContent);
        enquiry.setLastUpdatedAt(LocalDate.now());
        return true;
    }


    public boolean deleteEnquiry(User user, int enquiryId) {
        Optional<Enquiry> enqOpt = getOwnEnquiryById(user, enquiryId);
        if (enqOpt.isEmpty()) return false;

        Enquiry enquiry = enqOpt.get();

        if (enquiry.isReplied()) return false;

        db.removeEnquiry(enquiry);
        return true;

    }

    public boolean replyToEnquiry(Enquiry enquiry, User repliedBy, String replyText){
        if (enquiry.isReplied()) return false;

        enquiry.setReply(replyText);
        enquiry.setRepliedBy(repliedBy);
        enquiry.setStatus(EnquiryStatus.REPLIED);
        enquiry.setLastUpdatedAt(LocalDate.now());

        return true;
    }

    public Optional<Project> getProjectById(int id) {
        return db.getProjects().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }



}
