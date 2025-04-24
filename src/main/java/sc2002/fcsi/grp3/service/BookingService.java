package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.*;

import java.util.List;
import java.util.Optional;

public class BookingService {
    private final DataStore db;

    public BookingService(DataStore db) {
        this.db = db;
    }

    public List<Booking> getBookings() {
        return db.getBookings();
    }

    public Optional<Booking> getBookingByUser(User user) {
        return db.getBookings().stream()
                .filter(b -> b.getApplicant().getNric().equals(user.getNric()))
                .findFirst();
    }

    public void addBooking(Flat flatType, Project id, User Anric, User Onric){

        Booking book = new Booking(flatType, id, Anric, Onric);
        db.addBooking(book);

//        Registration reg = new Registration(project, officer, today);
//        db.addRegistration(reg);
    }

}