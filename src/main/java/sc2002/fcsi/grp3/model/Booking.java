package sc2002.fcsi.grp3.model;

import java.time.LocalDate;

public class Booking {
    private int id;
    private Flat flat;
    private User applicant;
    private User officer ; 
    private Application application;
    private LocalDate bookingDate;


    public Booking(int id, Flat flat, User applicant, User officer, Application application, LocalDate bookingDate){
        this.id = id;
        this.flat = flat;
        this.applicant = applicant;
        this.officer = officer;
        this.application = application;
        this.bookingDate = bookingDate;
    }

    public int getId(){
        return this.id;
    }

    public Flat getFlat(){
        return this.flat;
    }

    public User getApplicant(){
        return this.applicant;
    }

    public User getOfficer(){
        return this.officer;
    }

    public Application getApplication(){
        return this.application;
    }

    public LocalDate getBookingDate(){
        return this.bookingDate;
    }
}
