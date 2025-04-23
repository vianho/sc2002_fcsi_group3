package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.view.helper.Prompter;

public abstract class BaseView {
    protected final Prompter prompt;

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
