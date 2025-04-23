package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.view.helper.Prompter;

import java.time.format.DateTimeFormatter;

public abstract class BaseView {
    protected final Prompter prompt;
    final static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BaseView(Prompter prompt) {
        this.prompt = prompt;
    }

    public void showMessage(String msg) {
        prompt.showMessage(msg);
    }

    public void showError(String msg) {
        prompt.showError(msg);
    }

    public void showWarning(String msg) {
        prompt.showWarning(msg);
    }
}
