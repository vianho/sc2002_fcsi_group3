package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Enquiry;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.EnquiryStatus;
import sc2002.fcsi.grp3.service.result.ActionResult;

import javax.swing.*;
import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The EnquiryService class manages operations related to enquiries.
 * It provides methods to create, retrieve, edit, delete, and reply to enquiries.
 */
public class EnquiryService {

    private final DataStore db;

    /**
     * Constructs an EnquiryService with the specified data store.
     *
     * @param db the data store containing enquiry data
     */
    public EnquiryService(DataStore db) {
        this.db = db;
    }

    /**
     * Retrieves all enquiries from the data store.
     *
     * @return a list of all enquiries
     */
    public List<Enquiry> getEnquiries() {
        return db.getEnquiries();
    }

    /**
     * Retrieves all enquiries created by the specified user.
     *
     * @param user the user whose enquiries are to be retrieved
     * @return a list of enquiries created by the user
     */
    public List<Enquiry> getOwnEnquiries(User user) {
        return db.getEnquiries()
                .stream()
                .filter(enq -> enq.getCreatedBy().getNric().equalsIgnoreCase(user.getNric()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all enquiries handled by the specified officer.
     *
     * @param officer the officer handling the enquiries
     * @return a list of enquiries handled by the officer
     */
    public List<Enquiry> getEnquiriesHandledByOfficer(User officer){
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getRelatedProject().getOfficerNrics().contains(officer.getNric()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all unreplied enquiries handled by the specified officer.
     *
     * @param officer the officer handling the enquiries
     * @return a list of unreplied enquiries handled by the officer
     */
    public List<Enquiry> getUnrepliedEnquiriesHandledByOfficer(User officer){
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getRelatedProject().getOfficerNrics().contains(officer.getNric()))
                .filter(e -> !e.isReplied())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an unreplied enquiry by its ID, handled by the specified officer.
     *
     * @param officer the officer handling the enquiry
     * @param id      the ID of the enquiry
     * @return an Optional containing the unreplied enquiry, or empty if not found
     */
    public Optional<Enquiry> getUnrepliedEnquiriesHandledByOfficerById(User officer, int id ){
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getId() == id)
                .filter(e -> e.getRelatedProject().getOfficerNrics().contains(officer.getNric()))
                .filter(e -> !e.isReplied())
                .findFirst();
    }
    /**
     * Retrieves all enquiries visible to the specified manager.
     *
     * @param manager the manager viewing the enquiries
     * @return a list of all enquiries visible to the manager
     */
    public List<Enquiry> getAllEnquiriesManager(User manager) {
        return db.getEnquiries();
    }

    /**
     * Retrieves all unreplied enquiries handled by the specified manager.
     *
     * @param manager the manager handling the enquiries
     * @return a list of unreplied enquiries handled by the manager
     */
    public List<Enquiry> getUnrepliedEnquiriesHandledByManager(User manager){
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getRelatedProject().getManagerNric().equals(manager.getNric()))
                .filter(e -> !e.isReplied())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an unreplied enquiry by its ID, handled by the specified manager.
     *
     * @param manager the manager handling the enquiry
     * @param id      the ID of the enquiry
     * @return an Optional containing the unreplied enquiry, or empty if not found
     */
    public Optional<Enquiry> getUnrepliedEnquiriesHandledByManagerById(User manager, int id ){
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getId() == id)
                .filter(e -> e.getRelatedProject().getManagerNric().equals(manager.getNric()))
                .filter(e -> !e.isReplied())
                .findFirst();
    }

    /**
     * Retrieves an enquiry by its ID, created by the specified user.
     *
     * @param user the user who created the enquiry
     * @param id   the ID of the enquiry
     * @return an Optional containing the enquiry, or empty if not found
     */
    public Optional<Enquiry> getOwnEnquiryById(User user, int id) {
        return db.getEnquiries()
                .stream()
                .filter(e -> e.getId() == id && e.getCreatedBy().getNric().equals(user.getNric()))
                .findFirst();
    }

    /**
     * Creates a new enquiry for the specified project.
     *
     * @param user    the user creating the enquiry
     * @param project the project related to the enquiry
     * @param title   the title of the enquiry
     * @param content the content of the enquiry
     * @return true if the enquiry was created successfully, false otherwise
     */
    public boolean createEnquiry(User user, Project project, String title, String content) {
        Enquiry enquiry = new Enquiry(title, content, user, project);
        try{
            db.addEnquiry(enquiry);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Edits an existing enquiry with new title and content.
     *
     * @param enquiry   the enquiry to edit
     * @param newTitle  the new title for the enquiry
     * @param newContent the new content for the enquiry
     * @return true if the enquiry was edited successfully, false otherwise
     */
    public boolean editEnquiry(Enquiry enquiry, String newTitle, String newContent) {

        if(enquiry.isReplied()){
            return false;
        }
        enquiry.setTitle(newTitle);
        enquiry.setContent(newContent);
        enquiry.setLastUpdatedAt(LocalDate.now());
        return true;
    }

    /**
     * Deletes an enquiry by its ID, created by the specified user.
     *
     * @param user       the user who created the enquiry
     * @param enquiryId  the ID of the enquiry to delete
     * @return true if the enquiry was deleted successfully, false otherwise
     */
    public boolean deleteEnquiry(User user, int enquiryId) {
        Optional<Enquiry> enqOpt = getOwnEnquiryById(user, enquiryId);
        if (enqOpt.isEmpty()) return false;

        Enquiry enquiry = enqOpt.get();

        if (enquiry.isReplied()) return false;

        db.removeEnquiry(enquiry);
        return true;

    }

    /**
     * Submits a reply to the specified enquiry.
     *
     * @param enquiry   the enquiry to reply to
     * @param repliedBy the user replying to the enquiry
     * @param replyText the reply text
     * @return an ActionResult indicating success or failure of the reply
     */
    public ActionResult<Enquiry> replyToEnquiry(Enquiry enquiry, User repliedBy, String replyText) {
        if (enquiry.isReplied()) return ActionResult.failure("Failed to reply to enquiry.");

        enquiry.setReply(replyText);
        enquiry.setRepliedBy(repliedBy);
        enquiry.setStatus(EnquiryStatus.REPLIED);
        enquiry.setLastUpdatedAt(LocalDate.now());

        return ActionResult.success("Reply submitted successfully!");
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id the ID of the project
     * @return an Optional containing the project, or empty if not found
     */
    public Optional<Project> getProjectById(int id) {
        return db.getProjects().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }



}
