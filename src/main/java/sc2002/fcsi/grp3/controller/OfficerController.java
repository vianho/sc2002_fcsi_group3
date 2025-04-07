package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.view.OfficerView;

public class OfficerController implements IBaseController {
    private final OfficerView view;

    public OfficerController(OfficerView view) {
        this.view = view;
    }

    @Override
    public void start() {}
}
