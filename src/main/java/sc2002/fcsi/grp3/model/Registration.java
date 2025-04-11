package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.FlatType;
import java.time.LocalDate;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;

public class Registration {
    private String id;
    private Project project;
    private User applicant;
    private FlatType flatType;
    private ApplicationStatus status;
    private LocalDate submittedAt;

    public Registration (
            String id,
            Project project,
            User applicant,
            FlatType flatType,
            ApplicationStatus status,
            LocalDate submittedAt
    ) {
        this.id = id;
        this.project = project;
        this.applicant = applicant;
        this.flatType = flatType;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    //Getters

    public String getId() {
        return id;
    }
    public FlatType getFlatType() {
        return flatType;
    }

    public Project getProject() {
        return project;
    }

    public User getApplicant() {
        return applicant;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDate getSubmittedAt() {
        return submittedAt;
    }

    //Setters

    public void setSubmittedAt(LocalDate submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }
}
