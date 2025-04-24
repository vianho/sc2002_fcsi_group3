package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.EnquiryStatus;
import java.time.LocalDate;

/**
 * The Enquiry class represents an enquiry submitted by a user.
 * It contains details such as the title, content, reply, and related project.
 */
public class Enquiry {

    private int ID;
    private String title;
    private String content;
    private String reply;
    private User createdBy;
    private Project relatedProject;
    private User repliedBy;
    private EnquiryStatus status;
    private LocalDate createdAt;
    private LocalDate lastUpdatedAt;
    private static int nextEnquiryId;

    /**
     * Constructs an Enquiry with all specified details.
     *
     * @param ID             the unique ID of the enquiry
     * @param title          the title of the enquiry
     * @param content        the content of the enquiry
     * @param reply          the reply to the enquiry
     * @param createdBy      the user who created the enquiry
     * @param relatedProject the project related to the enquiry
     * @param repliedBy      the user who replied to the enquiry
     * @param status         the status of the enquiry
     * @param createdAt      the date the enquiry was created
     * @param lastUpdatedAt  the date the enquiry was last updated
     */
    public Enquiry(int ID, String title, String content, String reply, User createdBy, Project relatedProject, User repliedBy, EnquiryStatus status, LocalDate createdAt, LocalDate lastUpdatedAt) {
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.reply = reply;
        this.createdBy = createdBy;
        this.relatedProject = relatedProject;
        this.repliedBy = repliedBy;
        this.status = status;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    /**
     * Constructs an Enquiry with default reply and status.
     * The enquiry ID is auto-incremented.
     *
     * @param title          the title of the enquiry
     * @param content        the content of the enquiry
     * @param user           the user who created the enquiry
     * @param project        the project related to the enquiry
     */
    public Enquiry(String title, String content, User user, Project project) {
        this.ID = ++nextEnquiryId;
        this.title = title;
        this.content = content;
        this.reply = null;
        this.createdBy = user;
        this.relatedProject = project;
        this.repliedBy = null;
        this.status = EnquiryStatus.SUBMITTED;
        this.createdAt = LocalDate.now();
        this.lastUpdatedAt = LocalDate.now();
    }

    /**
     * Gets the unique ID of the enquiry.
     *
     * @return the enquiry ID
     */
    public int getId() {
        return this.ID;
    }

    /**
     * Gets the title of the enquiry.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the content of the enquiry.
     *
     * @return the content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Gets the reply to the enquiry.
     *
     * @return the reply
     */
    public String getReply() {
        return this.reply;
    }

    /**
     * Gets the user who created the enquiry.
     *
     * @return the creator of the enquiry
     */
    public User getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Gets the project related to the enquiry.
     *
     * @return the related project
     */
    public Project getRelatedProject() {
        return this.relatedProject;
    }

    /**
     * Gets the user who replied to the enquiry.
     *
     * @return the replier of the enquiry
     */
    public User getRepliedBy() {
        return this.repliedBy;
    }

    /**
     * Gets the status of the enquiry.
     *
     * @return the enquiry status
     */
    public EnquiryStatus getStatus() {
        return this.status;
    }

    /**
     * Gets the date the enquiry was created.
     *
     * @return the creation date
     */
    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Gets the date the enquiry was last updated.
     *
     * @return the last updated date
     */
    public LocalDate getLastUpdatedAt() {
        return this.lastUpdatedAt;
    }

    /**
     * Sets the next enquiry ID.
     *
     * @param id the next enquiry ID to set
     */
    public static void setNextEnquiryId(int id) {
        nextEnquiryId = id;
    }

    /**
     * Sets the title of the enquiry.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the content of the enquiry.
     *
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Sets the reply to the enquiry.
     *
     * @param reply the reply to set
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * Sets the user who created the enquiry.
     *
     * @param user the creator to set
     */
    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    /**
     * Sets the project related to the enquiry.
     *
     * @param project the related project to set
     */
    public void setRelatedProject(Project project) {
        this.relatedProject = project;
    }

    /**
     * Sets the user who replied to the enquiry.
     *
     * @param user the replier to set
     */
    public void setRepliedBy(User user) {
        this.repliedBy = user;
    }

    /**
     * Sets the status of the enquiry.
     *
     * @param status the status to set
     */
    public void setStatus(EnquiryStatus status) {
        this.status = status;
    }

    /**
     * Sets the date the enquiry was created.
     *
     * @param date the creation date to set
     */
    public void setCreatedAt(LocalDate date) {
        this.createdAt = date;
    }

    /**
     * Sets the date the enquiry was last updated.
     *
     * @param date the last updated date to set
     */
    public void setLastUpdatedAt(LocalDate date) {
        this.lastUpdatedAt = date;
    }

    /**
     * Checks if the enquiry has been replied to.
     *
     * @return true if the enquiry has a reply and a replier, false otherwise
     */
    public boolean isReplied() {
        return reply != null && repliedBy != null;
    }

}
