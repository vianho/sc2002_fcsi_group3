package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Booking;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountView extends BaseView {
    public AccountView(Prompter prompt) {
        super(prompt);
    }

    public String promptPassword(String msg) {
        return prompt.promptHiddenInput(msg);
    }

    public void showPasswordChangeSuccess() {
        showMessage("Password changed successfully.");
    }

    public void showProfile(User user, Project managedProject, Booking bookedFlat) {
        showMessage("Name: " + user.getName());
        showMessage("NRIC: " + user.getNric());
        showMessage("Age: " + user.getAge());
        showMessage("Marital Status: " + user.getMaritalStatus());
        List<String> eligibleFor = new ArrayList<>();
        if (managedProject == null && bookedFlat == null) {
            if (user.isEligibleFor2R()) {
                eligibleFor.add(FlatType.TWO_ROOM.getDisplayName());
                if (user.isEligibleForAny()) {
                    eligibleFor.add(FlatType.THREE_ROOM.getDisplayName());
                }
            }
            showMessage("You are eligible to apply for the following flat types: " +
                    (eligibleFor.isEmpty() ? "None" : String.join(", ", eligibleFor)));
            return;
        }

        if (managedProject != null) {
            prompt.showMessagef("Current Managed Project: %s in %s\n",
                    managedProject.getName(),
                    managedProject.getNeighbourhood());
        }

        if (bookedFlat != null) {
            prompt.showMessagef("Booked flat: %s in %s\n",
                    bookedFlat.getFlatType().getType().getDisplayName(),
                    bookedFlat.getProjectId().getName()
            );
        }
    }
}
