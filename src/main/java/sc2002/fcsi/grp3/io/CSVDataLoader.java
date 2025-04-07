package sc2002.fcsi.grp3.io;

import sc2002.fcsi.grp3.model.*;
import sc2002.fcsi.grp3.model.enums.FlatType;
import sc2002.fcsi.grp3.model.enums.MaritalStatus;
import sc2002.fcsi.grp3.model.role.IRole;
import sc2002.fcsi.grp3.model.role.RoleFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

public class CSVDataLoader implements IDataLoader {
    private final String usersFilePath;
    private final String projectFilePath;

    public CSVDataLoader(
            String usersFilePath,
            String projectFilePath
    ) {
        this.usersFilePath = usersFilePath;
        this.projectFilePath = projectFilePath;
    }

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

    protected static User parseUser(String[] tokens) {
        if (tokens.length != 6) return null;

        String nric = tokens[0].trim();
        String name = tokens[1].trim();
        int age = Integer.parseInt(tokens[2].trim());
        MaritalStatus maritalStatus;
        if (tokens[3].trim().equalsIgnoreCase("Single")) {
            maritalStatus = MaritalStatus.SINGLE;
        } else if (tokens[3].trim().equalsIgnoreCase("Married")) {
            maritalStatus = MaritalStatus.MARRIED;
        } else {
            maritalStatus = null;
        }
        String password = tokens[4].trim();
        IRole role = RoleFactory.fromString(tokens[5].trim());
        return new User(nric, name, age, password, maritalStatus, role);
    }

    @Override
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();

        readCSVLines(usersFilePath, tokens -> {
            try {
                User user = parseUser(tokens);
                if (user != null) users.add(user);
            } catch (Exception e) {
                System.out.println("Invalid user row: " + Arrays.toString(tokens));
            }
        });

        return users;
    }

    protected static Project parseProject(String[] tokens) {
        if (tokens.length != 12) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int projectId = Integer.parseInt(tokens[0].trim());
        String name = tokens[1].trim();
        String neighborhood = tokens[2].trim();
        boolean visibility = Boolean.parseBoolean(tokens[3].trim());
        String[] flatTypes = tokens[4].trim().split(";");
        String[] availableUnits = tokens[5].trim().split(";");
        String[] sellingPrice = tokens[6].trim().split(";");
        LocalDate applicationOpeningDate = LocalDate.parse(tokens[7].trim(), formatter);
        LocalDate applicationClosingDate = LocalDate.parse(tokens[8].trim(), formatter);
        String managerNric = tokens[9].trim();
        int totalOfficerSlots = Integer.parseInt(tokens[10].trim());
        String[] officerNrics = tokens[11].trim().split(";");

        List<Flat> flats = getFlats(flatTypes, availableUnits, sellingPrice);

        return new Project(
                projectId,
                name,
                neighborhood,
                visibility,
                applicationOpeningDate,
                applicationClosingDate,
                managerNric,
                totalOfficerSlots,
                flats,
                List.of(officerNrics)
        );
    }

    protected static List<Flat> getFlats(String[] flatTypes, String[] availableUnits, String[] sellingPrice) {
        List<Flat> flats = new ArrayList<>();
        for (int i = 0; i < flatTypes.length; i++) {

            FlatType type;
            if (flatTypes[i].equals("2R")) {
                type = FlatType.TWO_ROOM;
            } else if (flatTypes[i].equals("3R")) {
                type = FlatType.THREE_ROOM;
            } else {
                throw new RuntimeException("Invalid flat type: " + flatTypes[i]);
            }

            Flat flat = new Flat(
                    type,
                    Integer.parseInt(availableUnits[i].trim()),
                    Integer.parseInt(sellingPrice[i].trim())
            );
            flats.add(flat);
        }
        return flats;
    }

    @Override
    public List<Project> loadProjects() {
        List<Project> projects = new ArrayList<>();
        readCSVLines(projectFilePath, tokens -> {
            try {
                Project project = parseProject(tokens);
                if (project != null) projects.add(project);
            } catch (Exception e) {
                System.out.println("Invalid project row: " + Arrays.toString(tokens));
                System.out.println(e.getMessage());
                throw e;
            }
        });

        return projects;
    }
}
