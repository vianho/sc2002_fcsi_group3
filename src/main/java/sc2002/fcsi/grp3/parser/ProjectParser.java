package sc2002.fcsi.grp3.parser;

import sc2002.fcsi.grp3.model.Flat;
import sc2002.fcsi.grp3.model.Project;
import sc2002.fcsi.grp3.model.enums.FlatType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The ProjectParser class is responsible for parsing project data from CSV tokens.
 * It includes logic to parse flat details and project attributes.
 */
public class ProjectParser implements IBaseParser<Project> {

    /**
     * Parses flat details from arrays of flat types, available units, and selling prices.
     *
     * @param flatTypes      an array of flat type codes
     * @param availableUnits an array of available unit counts
     * @param sellingPrice   an array of selling prices
     * @return a list of Flat objects
     */
    protected static List<Flat> parseFlats(String[] flatTypes, String[] availableUnits, String[] sellingPrice) {
        List<Flat> flats = new ArrayList<>();
        for (int i = 0; i < flatTypes.length; i++) {

            FlatType type = FlatType.fromCode(flatTypes[i]);

            Flat flat = new Flat(
                    type,
                    Integer.parseInt(availableUnits[i].trim()),
                    Float.parseFloat(sellingPrice[i].trim())
            );
            flats.add(flat);
        }
        return flats;
    }

    /**
     * Parses a CSV row into a Project object.
     *
     * @param tokens the CSV row tokens
     * @return the parsed Project object, or null if the row is invalid
     */
    @Override
    public Project parse(String[] tokens) {
        if (tokens.length != 12) return null;

        int projectId = Integer.parseInt(tokens[0].trim());
        String name = tokens[1].trim();
        String neighborhood = tokens[2].trim();
        boolean visibility = Boolean.parseBoolean(tokens[3].trim());
        String[] flatTypes = tokens[4].trim().split(";");
        String[] availableUnits = tokens[5].trim().split(";");
        String[] sellingPrice = tokens[6].trim().split(";");
        LocalDate applicationOpeningDate = LocalDate.parse(tokens[7].trim(), dtFormatter);
        LocalDate applicationClosingDate = LocalDate.parse(tokens[8].trim(), dtFormatter);
        String managerNric = tokens[9].trim();
        int totalOfficerSlots = Integer.parseInt(tokens[10].trim());
        List<String> officerNrics = new ArrayList<>();
        // initialize officer nric as mutable list
        for (String s : tokens[11].split(";")) {
            if (!s.isEmpty()) {
                officerNrics.add(s);
            }
        }

        List<Flat> flats = parseFlats(flatTypes, availableUnits, sellingPrice);

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
                officerNrics
        );
    }
}
