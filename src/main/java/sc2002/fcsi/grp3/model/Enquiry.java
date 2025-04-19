package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.EnquiryStatus;
import java.time.LocalDate;
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

    public Enquiry(int ID, String title, String content, String reply, User createdBy, Project relatedProject, User repliedBy, EnquiryStatus status, LocalDate createdAt, LocalDate lastUpdatedAt){
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
    public Enquiry(String title, String content, User user,Project project){
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


    public int getId(){
        return this.ID;
    }
    public String getTitle(){
        return this.title;
    }
    public String getContent(){
        return this.content;
    }

    public String getReply(){
        return this.reply;
    }

    public User getCreatedBy(){
        return this.createdBy;
    }

    public Project getRelatedProject(){
        return this.relatedProject;
    }

    public User getRepliedBy(){
        return this.repliedBy;
    }

    public EnquiryStatus getStatus(){
        return this.status;
    }

    public LocalDate getCreatedAt(){
        return this.createdAt;
    }

    public LocalDate getLastUpdatedAt(){
        return this.lastUpdatedAt;
    }

    public static void setNextEnquiryId(int id) {
        nextEnquiryId = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setReply(String reply){
        this.reply = reply;
    }

    public void setCreatedBy(User user){
        this.createdBy = user;
    }

    public void setRelatedProject(Project project){
        this.relatedProject = project;
    }

    public void setRepliedBy(User user){
        this.repliedBy = user;
    }

    public void setStatus(EnquiryStatus status){
        this.status = status;
    }

    public void setCreatedAt(LocalDate date){
        this.createdAt = date;
    }

    public void setLastUpdatedAt(LocalDate date){
        this.lastUpdatedAt = date;
    }

    public boolean isReplied(){
        return reply != null && repliedBy != null;
    }

}
