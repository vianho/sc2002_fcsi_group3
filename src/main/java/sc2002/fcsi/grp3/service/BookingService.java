package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Booking;

import java.util.List;

public class BookingService {
    private final DataStore db;

    public BookingService(DataStore db) {
        this.db = db;
    }

//    public List<Booking> getBookings() {
//        return db.getBookings();
//    }
//
//    public Application getBooking(String nric){
//
//    }
}