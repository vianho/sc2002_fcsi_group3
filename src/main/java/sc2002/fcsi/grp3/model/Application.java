package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.time.LocalDate;

public class Application {
    private final int id;
    private final Project project;
    private final User applicant;
    private final FlatType flatType;
    private ApplicationStatus status;
    private final LocalDate submittedAt;

    public Application(int id, Project project, User applicant, FlatType flatType, LocalDate submittedAt) {
        this.id = id;
        this.project = project;
        this.applicant = applicant;
        this.flatType = flatType;
        this.status = ApplicationStatus.PENDING;
        this.submittedAt = submittedAt;
    }

    // getters

    public int getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public User getApplicant() {
        return applicant;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDate getSubmittedAt() {
        return submittedAt;
    }

    // setters

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
