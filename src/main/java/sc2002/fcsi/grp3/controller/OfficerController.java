package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.*;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.service.*;
import sc2002.fcsi.grp3.view.ApplicantView;
import sc2002.fcsi.grp3.view.EnquiryViewOfficer;
import sc2002.fcsi.grp3.view.OfficerView;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.time.LocalDate;

public class OfficerController implements IBaseController {
    private final OfficerView view;
    private final EnquiryViewOfficer enquiryViewOfficer;
    private final ProjectService projectService;
    private final RegistrationService registrationService;
    private final BookingService bookingService;
    private final EnquiryService enquiryService;
    private final UserService userService;
    private Scanner sc = new Scanner(System.in);
    private Optional<Project> optionalProject;
    private Optional<User> optionalUser;
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
    private final EnquiryControllerOfficer enquiryControllerOfficer;



    public OfficerController(
            OfficerView view,
            EnquiryViewOfficer enquiryViewOfficer,
            ProjectService projectService,
            ApplicationService applicationService,
            RegistrationService registrationService,
            EnquiryService enquiryService,
            BookingService bookingService,
            UserService userService
    ) {
        this.view = view;
        this.enquiryViewOfficer = enquiryViewOfficer;
        this.projectService = projectService;
        this.applicationService = applicationService;
        this.registrationService = registrationService;
        this.enquiryService = enquiryService;
        this.bookingService = bookingService;
        this.userService = userService;

        this.enquiryControllerOfficer = new EnquiryControllerOfficer(enquiryViewOfficer, enquiryService);
    }

    @Override
    public void start() {
        int choice;
        do {
            String[] options = {
                    "View as an Applicant",
                    "View as an Officer",
                    "Logout"};
            choice = view.showMenuAndGetChoice("Menu", options);
            switch (choice) {
//                case 1 -> viewProjects();
                  case 2 -> officerMenu();
                  case 3 -> logout();
                  case 4 -> test();

                default -> view.showMessage("Invalid choice.");
            }
        } while (choice != 3);

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
            choice = view.showMenuAndGetChoice("Officer Menu", options);
            switch (choice) {
                  case 1 -> joinProject();
                  case 2 -> registrationStatus();
                  case 3 -> viewHandled();
                  case 4 -> viewEnquiries();
                  case 5 -> flatBooking();
                  case 6 -> view.showMessage("Loading...");
                  default -> view.showMessage("Invalid choice.");
            }
        } while (choice != 6);
    }


    //Logout from User
    private void logout() {
        Session.logout();
        view.showMessage("Logging out...");
    }

    //View Registration Status
    private void registrationStatus(){
        User user = Session.getCurrentUser();
        view.showMessage("Your registration for....");
        registrationService.getProjectName(user);
        view.showMessage("Is Currently....");
        registrationService.getStatus(user);

        registrationService.setStatus(user);


    }

    //View Handled Project
    private void viewHandled(){
        User user = Session.getCurrentUser();
        Project Hproj = registrationService.getHandledProject(user);

        view.showHandledProject(Hproj);
    }

    private void viewEnquiries(){
        enquiryControllerOfficer.start();
    }



    //Book Flats for Applicants
    private void flatBooking(){

        User user = Session.getCurrentUser();
        do {
            view.showMessage("Enter NRIC of Applicant (Enter '0' to Exit) :");

            nric = sc.next();
            optionalUser = userService.findByNRIC(nric);
            found = applicationService.findApplication(nric.toUpperCase());

            if(found != null){
                view.showMessage("NRIC Found");
                break;
            }
            else{
                view.showMessage("Not found");
            }

        }while(!(nric.equals("0")));

        if(nric.equals("0")){
            return;
        }

        //Return if APPROVED already
        if(found.getStatus() == ApplicationStatus.BOOKED){
            view.showMessage("Application already booked..");
            return;
        }

        if(found.getStatus() == ApplicationStatus.SUCCESSFUL) {

            //Search for flatType
            proj = found.getProject();
            List<Flat> Lftype = proj.getFlats();
            flatType = found.getFlatType();

            for (Flat flat : Lftype) {
                if (flat.getType() == flatType) {
//                    view.showMessage("Room Type:");
//                    view.showMessage(String.valueOf(flatType.getDisplayName()));
//                    view.showMessage("Number of roomType: ");
//                    view.showMessage(String.valueOf(flat.getUnitsAvailable()));

                    if(flat.getUnitsAvailable() <= 0){
                        view.showMessage("No Available flats left, Returning to menu.....");
                        break;
                    }
                    else {
                        flat.reduceUnitsAvailable();

                        found.setStatus(ApplicationStatus.BOOKED);
                        found.setFlatType(found.getFlatType());

                        view.showMessage("Application Status now: ");
                        view.showMessage(String.valueOf(found.getStatus()));

                        view.showMessage("Number of roomType left: ");
                        view.showMessage(String.valueOf(flat.getUnitsAvailable()));

                        flatVar = flat;
                        break;
                    }

                }
                else{
                    view.showMessage("Application from NRIC not found");
                }
            }

            //Create a Booking for applicant

            if (optionalUser.isPresent()){
                Auser = optionalUser.get();
            }
            else {
                view.showMessage("No Such User");
            }

            bookingService.addBooking(flatVar, proj, Auser, user);

        }

    }



    //Join project as Officer
    private void joinProject(){
        User user = Session.getCurrentUser();
        view.showProjects(projectService.getVisibleProjects(user));
        int choice;
        //int num = projectService.getProjectSize();
        do{
            view.showMessage("Select project from list by ID (0 to exit):");
            choice = sc.nextInt();
            if(choice == 0) return;
            optionalProject = projectService.getProjectById(choice);
            if (optionalProject.isPresent()){
                proj = optionalProject.get();
                break;
            }
            else {
                view.showMessage("No Such Project");
            }

        } while (true);

        //Checking OfficerSlots
        if(proj.getOfficerCount() == 0){
            view.showMessage("No Officer Slot Left");
            return;
        }


        //Checking Application Period
        openingDate = proj.getApplicationOpeningDate();
        closingDate = proj.getApplicationClosingDate();

        if(Now.isBefore(openingDate) || Now.isAfter(closingDate)){
            view.showMessage("Unable to join due to not within application period");
            return;
        }


                                //Checking if Officer applied to project as Applicant


        //Create Project Registration with 'PENDING' Status
        registrationService.Join(proj, user, Now);


        view.showMessage("Succesfully registered for....");
        view.showMessage(proj.getName());

    }




    private void test(){
//        User user = Session.getCurrentUser();
//        if(Session.isLoggedIn()) {
//            String str = user.getNric();
//            view.showMessage(str);
//        }
//        else {
//            view.showMessage("lost");
//        }
        view.showBooking(bookingService.getBookings());
    }

}
