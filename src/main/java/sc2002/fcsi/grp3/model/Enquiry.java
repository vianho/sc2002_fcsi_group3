package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.EnquiryStatus;
import java.time.LocalDate;
public class Enquiry {
    private int ID;
    private String title;
    private String content;
    private String reply;
    private User createdby;
    private Project relatedProject;
    private User repliedby;
    private EnquiryStatus status;
    private LocalDate createdAt;
    private LocalDate lastUpdatedAt;

    public int getID(){
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

    public User getCreatedby(){
        return this.createdby;
    }

    public Project getRelatedProject(){
        return this.relatedProject;
    }

    public User getRepliedby(){
        return this.repliedby;
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




}
