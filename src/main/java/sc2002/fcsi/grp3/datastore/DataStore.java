package sc2002.fcsi.grp3.datastore;

import sc2002.fcsi.grp3.model.*;
import java.util.List;
import java.util.ArrayList;

public final class DataStore {
    private static DataStore instance;
    private List<User> users = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private List<Application> applications = new ArrayList<>();
    private List<Registration> registrations = new ArrayList<>();
    private List<Enquiry> enquiries = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    private DataStore() {
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public List<Application> getApplications() { return applications; }

    public void setApplications(List<Application> applications) { this.applications = applications; }

    public void addApplication(Application application) {
        applications.add(application);
    }

    public void addRegistration(Registration registration){registrations.add(registration);}

    public void setRegistrations(List<Registration> registration){this.registrations = registration;}

    public List<Registration> getRegistrations(){ return registrations; }

    public List<Enquiry> getEnquiries() {return enquiries;}

    public void setEnquiries(List<Enquiry> enquiries){ this.enquiries = enquiries;}

    public void addEnquiry(Enquiry enquiry){ enquiries.add(enquiry);}

    public void removeEnquiry(Enquiry enquiry){ enquiries.remove(enquiry);}

    public List<Booking> getBookings() {return bookings;}

    public void setBookings(List<Booking> bookings) { this.bookings = bookings;}

    public void addBooking(Booking booking){ bookings.add(booking);}
}
