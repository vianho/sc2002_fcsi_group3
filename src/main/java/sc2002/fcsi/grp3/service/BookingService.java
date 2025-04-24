package sc2002.fcsi.grp3.service;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.*;

import java.util.List;
import java.util.Optional;

/**
 * The BookingService class manages operations related to flat bookings.
 * It provides methods to create, retrieve, and manage bookings.
 */
public class BookingService {

    private final DataStore db;

    /**
     * Constructs a BookingService with the specified data store.
     *
     * @param db the data store containing booking data
     */
    public BookingService(DataStore db) {
        this.db = db;
    }

    /**
     * Retrieves all bookings from the data store.
     *
     * @return a list of all bookings
     */
    public List<Booking> getBookings() {
        return db.getBookings();
    }

    /**
     * Retrieves the booking associated with the specified user, if any.
     *
     * @param user the user whose booking is to be retrieved
     * @return an Optional containing the booking, or empty if no booking exists for the user
     */
    public Optional<Booking> getBookingByUser(User user) {
        return db.getBookings().stream()
                .filter(b -> b.getApplicant().getNric().equals(user.getNric()))
                .findFirst();
    }

    /**
     * Adds a new booking to the data store.
     *
     * @param flatType the flat type being booked
     * @param id       the project associated with the booking
     * @param Anric    the applicant's NRIC
     * @param Onric    the officer's NRIC
     */
    public void addBooking(Flat flatType, Project id, User Anric, User Onric){

        Booking book = new Booking(flatType, id, Anric, Onric);
        db.addBooking(book);

//        Registration reg = new Registration(project, officer, today);
//        db.addRegistration(reg);
    }

}