package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Booking;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.List;

public class BookingView extends BaseView {
    public BookingView(Prompter prompt) {
        super(prompt);
    }

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
