package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.*;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.util.List;
import java.util.stream.Collectors;

public class BookingService {
    private final DataStore db;

    public BookingService(DataStore db) {
        this.db = db;
    }

    public List<Booking> getBookings() {
        return db.getBookings();
    }

    public void addBooking(Flat flatType, Project id, User Anric, User Onric){

        Booking book = new Booking(flatType, id, Anric, Onric);
        db.addBooking(book);

//        Registration reg = new Registration(project, officer, today);
//        db.addRegistration(reg);
    }

}