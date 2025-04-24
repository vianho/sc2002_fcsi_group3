package sc2002.fcsi.grp3.io;

import sc2002.fcsi.grp3.model.*;
import sc2002.fcsi.grp3.parser.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The CSVDataLoader class is responsible for loading and saving data to and from CSV files.
 * It implements the IDataLoader interface and provides methods for handling users, projects, applications,
 * enquiries, bookings, and registrations.
 */
public class CSVDataLoader implements IDataLoader {

    private final String userFilePath;
    private final String projectFilePath;
    private final String applicationFilePath;
    private final String enquiryFilePath;
    private final String bookingFilePath;
    private final String registrationFilePath;
    private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private List<Project> projects;
    private static Map<Integer, Project> projectMap;
    private List<User> users;
    private Map<String, User> userMap;
    private List<Application> applications;
    private List<Enquiry> enquiries;
    private List<Booking> bookings;
    private List<Registration> registrations;

    /**
     * Constructs a CSVDataLoader with the specified file paths for different data types.
     *
     * @param userFilePath         the file path for user data
     * @param projectFilePath      the file path for project data
     * @param applicationFilePath  the file path for application data
     * @param enquiryFilePath      the file path for enquiry data
     * @param bookingFilePath      the file path for booking data
     * @param registrationFilePath the file path for registration data
     */
    public CSVDataLoader(
            String userFilePath,
            String projectFilePath,
            String applicationFilePath,
            String enquiryFilePath,
            String bookingFilePath,
            String registrationFilePath
    ) {
        this.userFilePath = userFilePath;
        this.projectFilePath = projectFilePath;
        this.applicationFilePath = applicationFilePath;
        this.enquiryFilePath = enquiryFilePath;
        this.bookingFilePath = bookingFilePath;
        this.registrationFilePath = registrationFilePath;
    }

    /**
     * Reads lines from a CSV file and processes each row using the provided row handler.
     *
     * @param filepath   the path to the CSV file
     * @param rowHandler a consumer to process each row of the CSV file
     */
    protected static void readCSVLines(String filepath, Consumer<String[]> rowHandler) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",", -1);
                rowHandler.accept(tokens);
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + filepath);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads user data from the CSV file and returns a list of users.
     *
     * @return a list of users
     */
    @Override
    public List<User> loadUsers() {
        users = new ArrayList<>();
        UserParser userParser = new UserParser();

        readCSVLines(userFilePath, tokens -> {
            try {
                User user = userParser.parse(tokens);
                if (user != null) users.add(user);
            } catch (Exception e) {
                System.out.println("Invalid user row: " + Arrays.toString(tokens));
            }
        });
        this.userMap = users.stream()
                .collect(Collectors.toMap(User::getNric, u -> u));
        return users;
    }

    /**
     * Saves the list of users to the specified CSV file.
     *
     * @param filePath the file path to save the user data
     * @param users    the list of users to save
     */
    public static void saveUsers(String filePath, List<User> users) {
        String tmpFile = filePath + ".tmp";
        try (FileWriter writer = new FileWriter(tmpFile)) {
            // Write header
            writer.write("Name,NRIC,Age,Marital Status,Password,Role\n");
            for (User user : users) {
                writer.write(String.format("%s,%s,%d,%s,%s,%s\n",
                        user.getName(),
                        user.getNric(),
                        user.getAge(),
                        user.getMaritalStatus().toString(),
                        user.getPassword(),
                        user.getRoleName()));
            }
        } catch (IOException e) {
            System.out.println("Failed to save users: " + e.getMessage());
            new java.io.File(tmpFile).delete();
            return;
        }
        try {
            Files.move(Paths.get(tmpFile), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to move temp file to final destination: " + e.getMessage());
        }
    }

    /**
     * Loads project data from the CSV file and returns a list of projects.
     *
     * @return a list of projects
     */
    @Override
    public List<Project> loadProjects() {
        projects = new ArrayList<>();
        ProjectParser projectParser = new ProjectParser();

        readCSVLines(projectFilePath, tokens -> {
            try {
                Project project = projectParser.parse(tokens);
                if (project != null) projects.add(project);
            } catch (Exception e) {
                System.out.println("Invalid project row: " + Arrays.toString(tokens));
                System.out.println(e.getMessage());
                throw e;
            }
        });

        projectMap = projects.stream()
                .collect(Collectors.toMap(Project::getId, p -> p));


        int maxProjectId = projects.stream()
                .mapToInt(Project::getId)
                .max()
                .orElse(1);
        Project.setNextProjectId(maxProjectId);
        return projects;
    }

    /**
     * Saves the list of projects to the specified CSV file.
     *
     * @param filePath the file path to save the project data
     * @param projects the list of projects to save
     */
    public static void saveProjects(String filePath, List<Project> projects) {
        String tmpFile = filePath + ".tmp";
        try (FileWriter writer = new FileWriter(tmpFile)) {
            writer.write("id,Project Name,Neighborhood,Visible,Flat Types,Available Units,Selling Price,Application opening date,Application closing date,Manager,Officer Slot,Officer NRICs\n");
            for (Project project : projects) {
                List<Flat> flats = project.getFlats();

                String flatTypes = flats
                        .stream()
                        .map(flat -> flat.getType().getCode())
                        .collect(Collectors.joining(";"));

                String availableUnits = flats
                        .stream()
                        .map(flat -> Integer.toString(flat.getUnitsAvailable()))
                        .collect(Collectors.joining(";"));

                String sellingPrice = flats
                        .stream()
                        .map(flat -> String.format("%.2f", flat.getSellingPrice()))
                        .collect(Collectors.joining(";"));

                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                        project.getId(),
                        project.getName(),
                        project.getNeighbourhood(),
                        project.isVisible(),
                        flatTypes.trim(),
                        availableUnits.trim(),
                        sellingPrice.trim(),
                        project.getApplicationOpeningDate().format(dtFormatter),
                        project.getApplicationClosingDate().format(dtFormatter),
                        project.getManagerNric(),
                        project.getTotalOfficerSlots(),
                        // todo: String.join() returns error from project.getOfficerNrics is empty
                        String.join(";", project.getOfficerNrics())));
            }
        } catch (IOException e) {
            System.out.println("Failed to save projects: " + e.getMessage());
            new java.io.File(tmpFile).delete();
            return;
        }
        try {
            Files.move(Paths.get(tmpFile), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to move temp file to final destination: " + e.getMessage());
        }
    }

    /**
     * Loads application data from the CSV file and returns a list of applications.
     *
     * @return a list of applications
     */
    public List<Application> loadApplications() {
        this.applications = new ArrayList<>();
        ApplicationParser applicationParser = new ApplicationParser(projectMap, userMap);
        readCSVLines(applicationFilePath, tokens -> {
            try {
                Application application = applicationParser.parse(tokens);
                if (application != null) {
                    applications.add(application);
                }
            } catch (Exception e) {
                System.out.println("Invalid application row: " + Arrays.toString(tokens));
                System.out.println(e.getMessage());
                throw e;
            }
        });

        int maxApplicationId = applications.stream()
                .mapToInt(Application::getId)
                .max()
                .orElse(1);
        maxApplicationId = maxApplicationId + 1;
        Application.setNextId(maxApplicationId);

        return applications;
    }

    /**
     * Saves the list of applications to the specified CSV file.
     *
     * @param filePath     the file path to save the application data
     * @param applications the list of applications to save
     */
    public static void saveApplications(String filePath, List<Application> applications) {
        String tmpFile = filePath + ".tmp";
        try (FileWriter writer = new FileWriter(tmpFile)) {
            writer.write("id,projectId,userNric,flatType,applicationStatus,submittedAt\n");
            for (Application app : applications) {
                writer.write(String.format("%d,%d,%s,%s,%s,%s\n",
                        app.getId(),
                        app.getProject().getId(),
                        app.getApplicant().getNric(),
                        app.getFlatType().getCode(),
                        app.getStatus(),
                        app.getSubmittedAt().format(dtFormatter)));
            }
        } catch (IOException e) {
            System.out.println("Failed to save applications: " + e.getMessage());
            new java.io.File(tmpFile).delete();
            return;
        }
        try {
            Files.move(Paths.get(tmpFile), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to move temp file to final destination: " + e.getMessage());
        }
    }

    /**
     * Loads enquiry data from the CSV file and returns a list of enquiries.
     *
     * @return a list of enquiries
     */
    public List<Enquiry> loadEnquiries() {
        this.enquiries = new ArrayList<>();
        EnquiryParser enquiryParser = new EnquiryParser(projectMap, userMap);

        readCSVLines(enquiryFilePath, tokens -> {
            try {
                Enquiry enquiry = enquiryParser.parse(tokens);
                if (enquiry != null) {
                    enquiries.add(enquiry);
                }
            } catch (Exception e) {
                System.out.println("Invalid enquiry row: " + Arrays.toString(tokens));
                System.out.println(e.getMessage());
                throw e;
            }
        });


        int maxEnquiryId = enquiries.stream()
                .mapToInt(Enquiry::getId)
                .max()
                .orElse(1);
        Enquiry.setNextEnquiryId(maxEnquiryId);

        return enquiries;

    }

    /**
     * Saves the list of enquiries to the specified CSV file.
     *
     * @param filePath  the file path to save the enquiry data
     * @param enquiries the list of enquiries to save
     */
    public static void saveEnquiries(String filePath, List<Enquiry> enquiries) {
        String tmpFile = filePath + ".tmp";
        try (FileWriter writer = new FileWriter(tmpFile)) {
            writer.write("id,title,content,reply,createdBy,relatedProject,repliedBy,status,createdAt,lastUpdatedAt\n");
            for (Enquiry enq : enquiries) {
                String reply = enq.getReply() == null || enq.getReply().isBlank() ? "" : enq.getReply();
                String repliedBy = enq.getRepliedBy() == null ? "" : enq.getRepliedBy().getNric();
                writer.write(String.format("%d,%s,%s,%s,%s,%d,%s,%s,%s,%s\n",
                        enq.getId(),
                        enq.getTitle(),
                        enq.getContent(),
                        reply,
                        enq.getCreatedBy().getNric(),
                        enq.getRelatedProject().getId(),
                        repliedBy,
                        enq.getStatus(),
                        enq.getCreatedAt().format(dtFormatter),
                        enq.getLastUpdatedAt().format(dtFormatter)));
            }
        } catch (IOException e) {
            System.out.println("Failed to save enquiries! " + e.getMessage());
            new java.io.File(tmpFile).delete();
            return;
        }
        try {
            Files.move(Paths.get(tmpFile), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to move temp file to final destination: " + e.getMessage());
        }
    }

    /**
     * Loads booking data from the CSV file and returns a list of bookings.
     *
     * @return a list of bookings
     */
    public List<Booking> loadBookings() {
        this.bookings = new ArrayList<>();
        BookingParser bookingParser = new BookingParser(projectMap, userMap);

        readCSVLines(bookingFilePath, tokens -> {
            try {
                Booking booking = bookingParser.parse(tokens);
                if (booking != null) {
                    bookings.add(booking);
                }
            } catch (Exception e) {
                System.out.println("Invalid booking row: " + Arrays.toString(tokens));
                System.out.println(e.getMessage());
                throw e;
            }
        });

        int maxBookingId = bookings.stream()
                .mapToInt(Booking::getId)
                .max()
                .orElse(0);
        Booking.setNextBookingId(maxBookingId);

        return bookings;
    }

    /**
     * Saves the list of bookings to the specified CSV file.
     *
     * @param filePath  the file path to save the booking data
     * @param bookings  the list of bookings to save
     */
    public static void saveBookings(String filePath, List<Booking> bookings) {
        String tmpFile = filePath + ".tmp";
        try (FileWriter writer = new FileWriter(tmpFile)) {
            writer.write("id,flatType,projectId,applicantNric,officerNric,bookingDate\n");
            for (Booking book : bookings) {
                writer.write(String.format("%d,%s,%d,%s,%s,%s\n",
                        book.getId(),
                        book.getFlatType().getType().getCode(),
                        book.getProjectId().getId(),
                        book.getApplicant().getNric(),
                        book.getOfficer().getNric(),
                        book.getBookingDate().format(dtFormatter)));
            }
        } catch (IOException e) {
            System.out.println("Failed to save bookings! " + e.getMessage());
            new java.io.File(tmpFile).delete();
            return;
        }
        try {
            Files.move(Paths.get(tmpFile), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to move temp file to final destination: " + e.getMessage());
        }
    }

    /**
     * Loads registration data from the CSV file and returns a list of registrations.
     *
     * @return a list of registrations
     */
    public List<Registration> loadRegistrations() {
        this.registrations = new ArrayList<>();
        RegistrationParser registrationParser = new RegistrationParser(projectMap, userMap);
        readCSVLines(registrationFilePath, tokens -> {
            try {
                Registration registration = registrationParser.parse(tokens);
                if (registration != null) {
                    registrations.add(registration);
                }
            } catch (Exception e) {
                System.out.println("Invalid registration row: " + Arrays.toString(tokens));
                System.out.println(e.getMessage());
                throw e;
            }
        });

        int maxRegistrationId = registrations.stream()
                .mapToInt(r -> {
                    try {
                        return Integer.parseInt(r.getId().trim());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);
        Registration.setNextRegistrationId(maxRegistrationId);
        return registrations;
    }

    /**
     * Saves the list of registrations to the specified CSV file.
     *
     * @param filePath      the file path to save the registration data
     * @param registrations the list of registrations to save
     */
    public static void saveRegistrations(String filePath, List<Registration> registrations) {
        String tmpFile = filePath + ".tmp";
        try (FileWriter writer = new FileWriter(tmpFile)) {
            writer.write("id,project,applicant,status,submittedAt\n");
            for (Registration reg : registrations) {
                writer.write(String.format("%s,%d,%s,%s,%s\n",
                        reg.getId(),
                        reg.getProject().getId(),
                        reg.getApplicant().getNric(),
                        reg.getStatus().name(),
                        reg.getSubmittedAt().format(dtFormatter)));
            }
        } catch (IOException e) {
            System.out.println("Failed to save registrations! " + e.getMessage());
            new java.io.File(tmpFile).delete();
            return;
        }
        try {
            Files.move(Paths.get(tmpFile), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to move temp file to final destination: " + e.getMessage());
        }
    }
}
