package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.dto.ProjectFlatRow;
import sc2002.fcsi.grp3.model.Application;
import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.User;
import sc2002.fcsi.grp3.service.ApplicationService;
import sc2002.fcsi.grp3.service.EnquiryService;
import sc2002.fcsi.grp3.service.AuthService;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.service.result.ActionResult;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.util.ProjectViewUtils;
import sc2002.fcsi.grp3.util.Validator;
import sc2002.fcsi.grp3.view.ApplicantViews;

import java.util.*;

public class ApplicantController implements IBaseController {
    private final ApplicantViews views;

    private final AuthService authService;
    private final ProjectService projectService;
    private final ApplicationService applicationService;
    private final EnquiryService enquiryService;

    public ApplicantController(
            ApplicantViews views,
            AuthService authService,
            ProjectService projectService,
            ApplicationService applicationService,
            EnquiryService enquiryService
    ) {
        this.views = views;
        this.authService = authService;
        this.projectService = projectService;
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
    }

    public void start() {
        int choice;
        String[] options = {
                "View available projects",
                "Applications",
                "My Enquiries",
                "My Account",
                "Logout"
        };
        do {
            choice = views.sharedView().showMenuAndGetChoice("Applicant Menu", options);
            switch (choice) {
                case 1 -> viewProjects();
                case 2 -> applicationActions();
                case 3 -> viewEnquiries();
                case 4 -> accountSettings();
                case 5 -> logout();
                default -> views.sharedView().showInvalidChoice();
            }
        } while (choice != options.length);
    }

    private void logout() {
        Session.logout();
        views.sharedView().showMessage("Logging out...");
    }

    private void viewProjects() {
        ProjectViewerController projectViewerController = new ProjectViewerController(
                views.sharedView(),
                views.projectView(),
                projectService
        );
        projectViewerController.start();
    }

    private void applicationActions() {
        ApplicationController applicationController = new ApplicationController(
                views.sharedView(),
                views.applicationView(),
                views.projectView(),
                projectService,
                applicationService
        );
        applicationController.start();
    }

    private void accountSettings() {
        AccountController accountController = new AccountController(
                authService,
                views.sharedView(),
                views.accountView()
        );
        accountController.start();
    }

    private void viewEnquiries(){
        ApplicantEnquiryController enquiryController = new ApplicantEnquiryController(
                views.sharedView(),
                views.enquiryView(),
                enquiryService
        );
        enquiryController.start();
    }
}
