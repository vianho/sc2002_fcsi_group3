package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.ArrayList;
import java.util.List;

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

    public void showProfile(User user) {
        showMessage("Name: " + user.getName());
        showMessage("NRIC: " + user.getNric());
        showMessage("Age: " + user.getAge());
        showMessage("Marital Status: " + user.getMaritalStatus());
        List<String> eligibleFor = new ArrayList<>();
        if (user.isEligibleFor2R()) {
            eligibleFor.add(FlatType.TWO_ROOM.getDisplayName());
            if (user.isEligibleForAny()) {
                eligibleFor.add(FlatType.THREE_ROOM.getDisplayName());
            }
        }
        showMessage("You are eligible to apply for the following flat types: " +
                (eligibleFor.isEmpty() ? "None" : String.join(", ", eligibleFor)));
    }
}
