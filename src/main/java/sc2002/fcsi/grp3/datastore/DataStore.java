package sc2002.fcsi.grp3.datastore;

import sc2002.fcsi.grp3.model.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The DataStore class serves as an in-memory database for storing application data.
 * It maintains lists of users, projects, applications, registrations, enquiries, and bookings.
 * This class follows the Singleton design pattern to ensure a single instance is used throughout the application.
 */
public final class DataStore {

    private static DataStore instance;
    private List<User> users = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private List<Application> applications = new ArrayList<>();
    private List<Registration> registrations = new ArrayList<>();
    private List<Enquiry> enquiries = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private DataStore() {
    }

    /**
     * Returns the singleton instance of the DataStore.
     *
     * @return the singleton instance of DataStore
     */
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    /**
     * Retrieves the list of users.
     *
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets the list of users.
     *
     * @param users the list of users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Retrieves the list of projects.
     *
     * @return the list of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the list of projects.
     *
     * @param projects the list of projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Adds a project to the list of projects.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
     * Retrieves the list of applications.
     *
     * @return the list of applications
     */
    public List<Application> getApplications() { return applications; }

    /**
     * Sets the list of applications.
     *
     * @param applications the list of applications to set
     */
    public void setApplications(List<Application> applications) { this.applications = applications; }

    /**
     * Adds an application to the list of applications.
     *
     * @param application the application to add
     */
    public void addApplication(Application application) {
        applications.add(application);
    }

    /**
     * Adds a registration to the list of registrations.
     *
     * @param registration the registration to add
     */
    public void addRegistration(Registration registration){registrations.add(registration);}

    /**
     * Sets the list of registrations.
     *
     * @param registration the list of registrations to set
     */
    public void setRegistrations(List<Registration> registration){this.registrations = registration;}

    /**
     * Retrieves the list of registrations.
     *
     * @return the list of registrations
     */
    public List<Registration> getRegistrations(){ return registrations; }

    /**
     * Retrieves the list of enquiries.
     *
     * @return the list of enquiries
     */
    public List<Enquiry> getEnquiries() {return enquiries;}

    /**
     * Sets the list of enquiries.
     *
     * @param enquiries the list of enquiries to set
     */
    public void setEnquiries(List<Enquiry> enquiries){ this.enquiries = enquiries;}

    /**
     * Adds an enquiry to the list of enquiries.
     *
     * @param enquiry the enquiry to add
     */
    public void addEnquiry(Enquiry enquiry){ enquiries.add(enquiry);}

    /**
     * Removes an enquiry from the list of enquiries.
     *
     * @param enquiry the enquiry to remove
     */
    public void removeEnquiry(Enquiry enquiry){ enquiries.remove(enquiry);}

    /**
     * Retrieves the list of bookings.
     *
     * @return the list of bookings
     */
    public List<Booking> getBookings() {return bookings;}

    /**
     * Sets the list of bookings.
     *
     * @param bookings the list of bookings to set
     */
    public void setBookings(List<Booking> bookings) { this.bookings = bookings;}

    /**
     * Adds a booking to the list of bookings.
     *
     * @param booking the booking to add
     */
    public void addBooking(Booking booking){ bookings.add(booking);}
}
