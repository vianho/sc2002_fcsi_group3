package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.view.ManagerView;

public class ManagerController implements IBaseController {
    private final ManagerView view;

    public ManagerController(ManagerView view) {
        this.view = view;
    }

    @Override
    public void start() {}
}
