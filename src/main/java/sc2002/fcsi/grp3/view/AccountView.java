package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Booking;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The AccountView class provides methods for displaying and interacting with user account information.
 * It includes functionality for showing profiles, prompting for passwords, and displaying success messages.
 */
public class AccountView extends BaseView {

    /**
     * Constructs an AccountView with the specified prompter.
     *
     * @param prompt the prompter used for user input and output
     */
    public AccountView(Prompter prompt) {
        super(prompt);
    }

    /**
     * Prompts the user to enter a password with a hidden input.
     *
     * @param msg the message to display when prompting for the password
     * @return the entered password
     */
    public String promptPassword(String msg) {
        return prompt.promptHiddenInput(msg);
    }

    /**
     * Displays a success message for a password change.
     */
    public void showPasswordChangeSuccess() {
        showMessage("Password changed successfully.");
    }

    /**
     * Displays the user's profile, including their name, NRIC, age, marital status, and eligibility for flats.
     * If the user manages a project or has booked a flat, those details are also displayed.
     *
     * @param user          the user whose profile is being displayed
     * @param managedProject the project managed by the user, or null if none
     * @param bookedFlat    the flat booked by the user, or null if none
     */
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
