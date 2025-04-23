package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.datastore.DataStore;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.model.permission.ApplicantApplicationPermission;
import sc2002.fcsi.grp3.model.permission.IApplicationPermission;
import sc2002.fcsi.grp3.model.permission.ManagerApplicationPermission;
import sc2002.fcsi.grp3.model.permission.OfficerApplicationPermission;
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
        return new LoginController(authService, viewInit.getAuthView());
    }

    public IBaseController createControllerForUser(User user) {
        String roleName = user.getRoleName();
        UserService userService = new UserService(store.getUsers());


        return switch (roleName) {
            case "Applicant" -> {
                ApplicantApplicationPermission appPermission = new ApplicantApplicationPermission();
                yield new ApplicantController(
                    viewInit.getApplicantViews(),
                    new AuthService(userService),
                    new ProjectService(store),
                    new ApplicationService(appPermission, store),
                    new EnquiryService(store)
                );
            }
            case "Officer" ->{
                OfficerApplicationPermission appPermission = new OfficerApplicationPermission();
                yield new OfficerController(
                    viewInit.getOfficerView(),
                    new ProjectService(store),
                    new ApplicationService(appPermission, store),
                    new RegistrationService(store));
            }
            case "Manager" -> {
                ManagerApplicationPermission appPermission = new ManagerApplicationPermission();
                yield new ManagerController(viewInit.getManagerView());
            }
            default -> throw new IllegalStateException("Unknown role: " + roleName);
        };
    }
}
