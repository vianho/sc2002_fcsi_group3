package sc2002.fcsi.grp3.controller;

import sc2002.fcsi.grp3.model.*;
import sc2002.fcsi.grp3.model.enums.ApplicationStatus;
import sc2002.fcsi.grp3.view.OfficerView;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.service.ApplicationService;
import sc2002.fcsi.grp3.service.RegistrationService;
import sc2002.fcsi.grp3.service.ProjectService;
import sc2002.fcsi.grp3.session.Session;
import sc2002.fcsi.grp3.model.enums.RegistrationStatus;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.time.LocalDate;

public class OfficerController implements IBaseController {
    private final OfficerView view;
    private final ProjectService projectService;
    private final RegistrationService registrationService;
    private Scanner sc = new Scanner(System.in);
    private Optional<Project> optionalProject;
    private Project proj;
    private LocalDate closingDate, openingDate;
    private final LocalDate Now = LocalDate.now();
    private Application found = null;
    private String nric;
    private final ApplicationService applicationService;
    private FlatType flatType;
    private RegistrationStatus registrationStatus;


    public OfficerController(
            OfficerView view,
            ProjectService projectService,
            ApplicationService applicationService,
            RegistrationService registrationService
    ) {
        this.view = view;
        this.projectService = projectService;
        this.applicationService = applicationService;
        this.registrationService = registrationService;
    }

    @Override
    public void start() {
        int choice;
        do {
            String[] options = {
                    "View as an Applicant",
                    "View as an Officer",
                    "Logout",
                    "Test"};
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
//                case 4 -> viewEnquiries();
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


    //Book Flats for Applicants
    private void flatBooking(){

        do {
            view.showMessage("Enter NRIC of Applicant (Enter '0' to Exit) :");
            nric = sc.next();
            found = applicationService.findApplication(nric.toUpperCase());

            if(found != null){
                break;
            }

        }while(!(nric.equals("0")));

        if(nric.equals("0")){
            return;
        }

        //Return if APPROVED already
        if(found.getStatus() == ApplicationStatus.SUCCESSFUL){
            view.showMessage("Application already approved..");
            return;
        }

        //Search for flatType
        proj = found.getProject();
        List<Flat> Lftype = proj.getFlats();
        flatType = found.getFlatType();

        for(Flat flat : Lftype){
            if(flat.getType() == flatType){
                view.showMessage("Room Type:");
                view.showMessage(String.valueOf(flatType.getDisplayName()));
                view.showMessage("Number of roomType: ");
                view.showMessage(String.valueOf(flat.getUnitsAvailable()));
                flat.reduceUnitsAvailable();


                found.setStatus(ApplicationStatus.SUCCESSFUL);
                found.setFlatType(found.getFlatType());

                view.showMessage("Application Status now: ");
                view.showMessage(String.valueOf(found.getStatus()));

                view.showMessage("Number of roomType left: ");
                view.showMessage(String.valueOf(flat.getUnitsAvailable()));
            }
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
        User user = Session.getCurrentUser();
        if(Session.isLoggedIn()) {
            String str = user.getNric();
            view.showMessage(str);
        }
        else {
            view.showMessage("lost");
        }
    }

}
