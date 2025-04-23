package sc2002.fcsi.grp3.model;

import java.time.LocalDate;

public class Booking {
    private int id;
    private Flat flatType;
    private Project projectId;
    private User applicantNric;
    private User officerNric;
    private LocalDate bookingDate;
    private static int nextBookingId;


    public Booking(int id, Flat flatType, Project projectId, User applicantNric, User officerNric, LocalDate bookingDate){
        this.id = id;
        this.flatType = flatType;
        this.projectId = projectId;
        this.applicantNric = applicantNric;
        this.officerNric = officerNric;
        this.bookingDate = bookingDate;
    }
    public Booking(Flat flatType, Project projectId, User applicantNric, User officerNric){
        this.id = ++nextBookingId;
        this.flatType = flatType;
        this.projectId = projectId;
        this.applicantNric = applicantNric;
        this.officerNric = officerNric;
        this.bookingDate = LocalDate.now();
    }

    public int getId(){
        return this.id;
    }

    public Flat getFlatType(){
        return this.flatType;
    }

    public Project getProjectId(){ return this.projectId;}

    public User getApplicant(){
        return this.applicantNric;
    }

    public User getOfficer(){
        return this.officerNric;
    }

    public static void setNextBookingId(int id) {
        nextBookingId = id;
    }

    public LocalDate getBookingDate(){
        return this.bookingDate;
    }
}
