package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Booking;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.List;

/**
 * The BookingView class provides methods for displaying booking-related data.
 * It includes functionality for showing a list of bookings.
 */
public class BookingView extends BaseView {

    /**
     * Constructs a BookingView with the specified prompter.
     *
     * @param prompt the prompter used for user input and output
     */
    public BookingView(Prompter prompt) {
        super(prompt);
    }

    /**
     * Displays a list of bookings with their details.
     *
     * @param bookings the list of bookings to display
     */
    public void showBooking(List<Booking> bookings) {
        prompt.showTitle("Bookings");
        if (bookings.isEmpty()) {
            prompt.showMessage("No available Booking.");
        } else {
            List<String> headers = List.of("ID", "FlatType", "PorjID", "ANRIC", "ONRIC");
            List<List<String>> rows = bookings.stream()
                    .map(p -> List.of(
                            String.valueOf(p.getId()),
                            p.getFlatType().toString(),
                            p.getProjectId().toString()
//                            p.getApplicant().toString(),
//                            p.getOfficer().toString()
                    ))
                    .toList();
            prompt.showTable(headers, rows);
        }
        prompt.pressEnterToContinue();
    }
}
