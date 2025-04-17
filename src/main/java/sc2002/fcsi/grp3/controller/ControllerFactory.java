package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.*;
import sc2002.fcsi.grp3.init.ViewInitializer;

public class ControllerFactory {
    private final DataStore store;
    private final ViewInitializer viewInit;

    public ControllerFactory(DataStore store, ViewInitializer viewInit) {
        this.store = store;
        this.viewInit = viewInit;
    }

    public MainMenuController createMainMenuController() {
        return new MainMenuController(
                viewInit.getMainMenuView(),
                createLoginController(),
                this
        );
    }

    public LoginController createLoginController() {
        UserService userService = new UserService(store.getUsers());
        AuthService authService = new AuthService(userService);
        return new LoginController(authService, viewInit.getLoginView());
    }

    public IBaseController createControllerForUser(User user) {
        String roleName = user.getRoleName();

        return switch (roleName) {
            case "Applicant" -> new ApplicantController(
                    viewInit.getApplicantView(),
                    new ProjectService(store),
                    new ApplicationService(store)
            );
            case "Officer" -> new OfficerController(viewInit.getOfficerView(),
                                                    new ProjectService(store),
                                                    new ApplicationService(store),
                                                    new RegistrationService(store));
            case "Manager" -> new ManagerController(viewInit.getManagerView());
            default -> throw new IllegalStateException("Unknown role: " + roleName);
        };
    }
}
