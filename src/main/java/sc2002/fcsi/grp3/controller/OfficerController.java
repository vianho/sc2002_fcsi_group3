package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.*;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.service.*;
import sc2002.fcsi.grp3.service.EnquiryService;
import sc2002.fcsi.grp3.util.ProjectViewUtils;
import sc2002.fcsi.grp3.view.OfficerViews;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.time.LocalDate;

public class OfficerController implements IBaseController {
    private final OfficerViews views;
    private final AuthService authService;
    private final ProjectService projectService;
    private final RegistrationService registrationService;
    private final BookingService bookingService;
    private final EnquiryService enquiryService;
    private final UserService userService;

    private Scanner sc = new Scanner(System.in);
    private Optional<Project> optionalProject;
    private Optional<User> optionalUser;
    private List<String> projOfficers;
    private Project proj;
    private User Auser;
    private Flat flatVar;
    private LocalDate closingDate, openingDate;
    private final LocalDate Now = LocalDate.now();
    private Application found = null;
    private String nric;
    private final ApplicationService applicationService;
    private FlatType flatType;
    private RegistrationStatus registrationStatus;
    private final OfficerEnquiryController enquiryControllerOfficer ;


    public OfficerController(
            OfficerViews views,
            AuthService authService,
            ProjectService projectService,
            ApplicationService applicationService,
            RegistrationService registrationService,
            EnquiryService enquiryService,
            BookingService bookingService,
            UserService userService
    ) {
        this.views = views;
        this.authService = authService;
        this.projectService = projectService;
        this.applicationService = applicationService;
        this.registrationService = registrationService;
        this.enquiryService = enquiryService;
        this.bookingService = bookingService;
        this.userService = userService;

        this.enquiryControllerOfficer = new OfficerEnquiryController(views.sharedView(), views.enquiryView(), enquiryService);
    }

    @Override
    public void start() {
        int choice;
        String[] options = {
                "View as an Applicant",
                "View as an Officer",
                "Account Settings",
                "Logout",
        };
        do {
            choice = views.sharedView().showMenuAndGetChoice("Menu", options);
            switch (choice) {
                case 1 -> applicantActions();
                case 2 -> officerMenu();
                case 3 -> accountSettings();
                case 4 -> logout();
                default -> views.sharedView().showMessage("Invalid choice.");
            }
        } while (choice != options.length);

    }

    private void applicantActions() {
        int choice;
        String[] options = {
                "View available projects",
                "My Applications",
                "My Enquiries",
                "Back"
        };

        do {
            choice = views.sharedView().showMenuAndGetChoice("Applicant Menu", options);
            switch (choice) {
                case 1 -> viewProjects();
                case 2 -> applicationActions();
                case 3 -> viewEnquiriesAsApplicant();
                case 4 -> {}
                default -> views.sharedView().showInvalidChoice();
            }
        } while (choice != options.length);

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

    private void viewEnquiriesAsApplicant(){
        ApplicantEnquiryController enquiryController = new ApplicantEnquiryController(
                views.sharedView(),
                views.enquiryView(),
                enquiryService
        );
        enquiryController.start();
    }

    private void accountSettings() {
        AccountController accountController = new AccountController(
                authService,
                projectService,
                bookingService,
                views.sharedView(),
                views.accountView()
        );
        accountController.start();
    }


    //Menu for Officers
    private void officerMenu(){
        int choice;
        do {
            String[] options = {
                    "Join a Project",
                    "View My Registration",
                    "View Handled Project",
                    "View/Reply Enquiries",
                    "Flat Booking",
                    "Previous"};
            choice = views.sharedView().showMenuAndGetChoice("Officer Menu", options);
            switch (choice) {
                  case 1 -> joinProject();
                  case 2 -> registrationStatus();
                  case 3 -> viewHandled();
                  case 4 -> viewEnquiries();
                  case 5 -> flatBooking();
                  case 6 -> views.sharedView().showMessage("Loading...");
                  default -> views.sharedView().showMessage("Invalid choice.");
            }
        } while (choice != 6);
    }


    //Logout from User
    private void logout() {
        Session.logout();
        views.sharedView().showMessage("Logging out...");
    }

    //View Registration Status
    private void registrationStatus(){
        User user = Session.getCurrentUser();
//        view.showMessage("Your registration for....");
//        registrationService.getProjectName(user);

        System.out.println("Your registration: " + registrationService.getProjectName(user));

//        view.showMessage("Is Currently....");
//        registrationService.getStatus(user);

        System.out.println("Status: " + registrationService.getStatus(user));

        //registrationService.setStatus(user);

    }

    //View Handled Project
    private void viewHandled(){
        User user = Session.getCurrentUser();
        Project Hproj = registrationService.getHandledProject(user.getNric());

        views.projectView().showProjectDetailsTable(Hproj);
    }

    private void viewEnquiries(){
        enquiryControllerOfficer.start();
    }



    //Book Flats for Applicants
    private void flatBooking(){

        User user = Session.getCurrentUser();
        do {
            views.sharedView().showMessage("Enter NRIC of Applicant (Enter '0' to Exit) :");
            nric = sc.next();
            optionalUser = userService.findByNRIC(nric);
            found = applicationService.findApplication(nric.toUpperCase());

            if(found != null){
                views.sharedView().showMessage("NRIC Found");
                break;
            }
            else{
                views.sharedView().showMessage("Not found");
            }

        }while(!(nric.equals("0")));

        if(nric.equals("0")){
            return;
        }

        //Return if APPROVED already
        if(found.getStatus() == ApplicationStatus.BOOKED){
            views.sharedView().showMessage("Application already booked..");
            return;
        }

        if(found.getStatus() == ApplicationStatus.SUCCESSFUL) {

            //Search for flatType
            proj = found.getProject();
            List<Flat> Lftype = proj.getFlats();
            flatType = found.getFlatType();

            for (Flat flat : Lftype) {
                if (flat.getType() == flatType) {
//                    views.sharedView().showMessage("Room Type:");
//                    views.sharedView().showMessage(String.valueOf(flatType.getDisplayName()));
//                    views.sharedView().showMessage("Number of roomType: ");
//                    views.sharedView().showMessage(String.valueOf(flat.getUnitsAvailable()));

                    if(flat.getUnitsAvailable() <= 0){
                        views.sharedView().showMessage("No Available flats left, Returning to menu.....");
                        break;
                    }
                    else {
                        flat.reduceUnitsAvailable();

                        found.setStatus(ApplicationStatus.BOOKED);
                        found.setFlatType(found.getFlatType());

//                        views.sharedView().showMessage("Application Status now: ");
//                        views.sharedView().showMessage(String.valueOf(found.getStatus()));


                        System.out.println("Application Status now: " + String.valueOf(found.getStatus()));

//                        views.sharedView().showMessage("Number of roomType left: ");
//                        views.sharedView().showMessage(String.valueOf(flat.getUnitsAvailable()));

                        System.out.println("Number of roomType left: " + String.valueOf(flat.getUnitsAvailable()));

                        flatVar = flat;
                        break;
                    }

                }
                else{
                    views.sharedView().showMessage("Application from NRIC not found");
                }
            }

            //Create a Booking for applicant

            if (optionalUser.isPresent()){
                Auser = optionalUser.get();
            }
            else {
                views.sharedView().showMessage("No Such User");
            }

            bookingService.addBooking(flatVar, proj, Auser, user);

        }

    }



    //Join project as Officer
    private void joinProject(){
        User user = Session.getCurrentUser();
        List<Project> projects = projectService.getVisibleProjects(user);
        views.projectView().showProjectFlats(ProjectViewUtils.flattenEligibleFlats(projects, user));
        int choice;
        //int num = projectService.getProjectSize();
        do{
            views.sharedView().showMessage("Select project from list by ID (0 to exit):");
            choice = sc.nextInt();
            if(choice == 0) return;
            optionalProject = projectService.getProjectById(choice);
            if (optionalProject.isPresent()){
                proj = optionalProject.get();
                break;
            }
            else {
                views.sharedView().showMessage("No Such Project");
            }

        } while (true);

        //Checking OfficerSlots
        if(proj.getOfficerCount() == 0){
            views.sharedView().showMessage("No Officer Slot Left");
            return;
        }

        projOfficers = proj.getOfficerNrics();

        for (String nric : projOfficers){
            if(user.getNric().equals(nric)){
                views.sharedView().showMessage("You are already handling this project");
                return;
            }
        }


        //Checking Application Period
        openingDate = proj.getApplicationOpeningDate();
        closingDate = proj.getApplicationClosingDate();

        if(Now.isBefore(openingDate) || Now.isAfter(closingDate)){
            views.sharedView().showMessage("Unable to join due to not within application period");
            return;
        }

        //Checking if Officer applied to project as Applicant
        found = applicationService.findApplication(user.getNric());
        if (found != null) {
            if (found.getProject().getId() == proj.getId()) {
                views.sharedView().showMessage("You currently have an application for this project.");
                return;
            }

        }

        //Create Project Registration with 'PENDING' Status
        registrationService.Join(proj, user, Now);

        views.sharedView().showMessage("Succesfully registered for....");
        views.sharedView().showMessage(proj.getName());

    }




    private void test(){
//        User user = Session.getCurrentUser();
//        if(Session.isLoggedIn()) {
//            String str = user.getNric();
//            views.sharedView().showMessage(str);
//        }
//        else {
//            views.sharedView().showMessage("lost");
//        }
        views.bookingView().showBooking(bookingService.getBookings());
    }

}
