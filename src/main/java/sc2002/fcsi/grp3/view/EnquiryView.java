package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Enquiry;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.List;

/**
 * The {@code EnquiryView} handles the view for applicants to interact with enquiries.
 * <p>This class is responsible for:</p>
 * <ul>
 *     <li>Presenting enquiry details in a readable tabular format</li>
 *     <li>Prompting applicants to add new enquiries</li>
 *     <li>Prompting applicants to edit/delete enquiries</li>
 *     <li>Showing success and error messages</li>
 * </ul>
 *
 * <p>It uses the {@link Prompter} utility to render the CLI interface components.</p>
 */
public class EnquiryView extends BaseView {

    /**
     * Constructs an {@code EnquiryView} instance using the specified prompter.
     *
     * @param prompt the prompter for displaying messages, errors, and tables
     */
    public EnquiryView(Prompter prompt) {
        super(prompt);
    }

    /**
     * Displays the table of all of the applicant's past enquiries.
     *
     * @param enquiries a list of {@link Enquiry} to be displayed
     */
    public void showEnquiriesApplicant(List<Enquiry> enquiries) {
        if (enquiries.isEmpty()) {
            showMessage("You have no enquiries.");
        } else {
            List<String> headers = List.of("ID", "Title", "Content", "Reply","Replied By", "Status", "Related Project", "Created At", "Last Updated");
            List<List<String>> rows = enquiries.stream().map(e ->
                    List.of(
                            String.valueOf(e.getId()),
                            truncate(e.getTitle(), 30),
                            truncate(e.getContent(), 30),
                            e.getReply() == null || e.getReply().isBlank()? "(no reply)" : truncate(e.getReply(), 30),
                            e.getRepliedBy() == null ? "(not replied)" : e.getRepliedBy().getName(),
                            e.getStatus().toString(),
                            e.getRelatedProject().getName(),
                            e.getCreatedAt().toString(),
                            e.getLastUpdatedAt().toString()
                    )
            ).toList();
            prompt.showTable(headers, rows);
        }
        prompt.pressEnterToContinue();
    }

    /**
     * Displays the table of all enquiries submitted.
     *
     * @param enquiries a list of {@link Enquiry} to be displayed
     */
    public void showEnquiriesOfficerManager(List<Enquiry> enquiries) {
        if (enquiries.isEmpty()) {
            prompt.showMessage("There are no enquiries.");
        } else {
            List<String> headers = List.of("ID", "Title", "Content", "Created By", "Reply","Replied By", "Status", "Related Project", "Created At", "Last Updated");
            List<List<String>> rows = enquiries.stream().map(e ->
                    List.of(
                            String.valueOf(e.getId()),
                            truncate(e.getTitle(), 30),
                            truncate(e.getContent(), 30),
                            e.getCreatedBy().getName(),
                            e.getReply() == null || e.getReply().isBlank()? "(no reply)" : truncate(e.getReply(), 30),
                            e.getRepliedBy() == null ? "(not replied)" : e.getRepliedBy().getName(),
                            e.getStatus().toString(),
                            e.getRelatedProject().getName(),
                            e.getCreatedAt().toString(),
                            e.getLastUpdatedAt().toString()
                    )
            ).toList();
            prompt.showTable(headers, rows);
        }
        prompt.pressEnterToContinue();
    }

    /**
     * Displays a compacted table of enquiries from the user.
     * This is used to display enquiries for the edit and delete portion of the enquiry.
     *
     * @param enquiries a list of {@link Enquiry} to be displayed
     */
    public void showAvailableEnquiriesApplicant(List<Enquiry> enquiries) {
        if (enquiries.isEmpty()) {
            prompt.showMessage("You have no enquiries.");
        } else {
            List<String> headers = List.of("ID", "Title", "Content", "Reply","Replied By", "Last Updated");
            List<List<String>> rows = enquiries.stream().map(e ->
                    List.of(
                            String.valueOf(e.getId()),
                            truncate(e.getTitle(), 30),
                            truncate(e.getContent(), 30),
                            e.getReply() == null || e.getReply().isBlank()? "(no reply)" : truncate(e.getReply(), 30),
                            e.getRepliedBy() == null ? "(not replied)" : e.getRepliedBy().getName(),
                            e.getLastUpdatedAt().toString()
                    )
            ).toList();
            prompt.showTable(headers, rows);
        }
    }

    /**
     * Displays the table of enquiries pending replies for the manager or officer to choose from.
     * These enquiries are based on the project that the manager or officer is handling.
     *
     * @param enquiries a list of {@link Enquiry} to be displayed
     */
    public void showAvailableEnquiriesOfficerManager(List<Enquiry> enquiries) {
        if (enquiries.isEmpty()) {
            prompt.showMessage("There are no enquiries.");
        } else {
            List<String> headers = List.of("ID", "Title", "Content", "Created By", "Reply","Replied By", "Last Updated");
            List<List<String>> rows = enquiries.stream().map(e ->
                    List.of(
                            String.valueOf(e.getId()),
                            truncate(e.getTitle(), 30),
                            truncate(e.getContent(), 30),
                            e.getCreatedBy().getName(),
                            e.getReply() == null || e.getReply().isBlank()? "(no reply)" : truncate(e.getReply(), 30),
                            e.getRepliedBy() == null ? "(not replied)" : e.getRepliedBy().getName(),
                            e.getLastUpdatedAt().toString()
                    )
            ).toList();
            prompt.showTable(headers, rows);
        }
    }

    /**
     * Truncates the string if the content is too long, making the table neater.
     *
     * @param text   the text to truncate
     * @param length the maximum allowable length of the string to be displayed
     * @return the truncated text or "(empty)" if the string is null
     */
    private String truncate(String text, int length) {
        if(text == null) return "(empty)";
        return text.length() > length ? text.substring(0, length - 3) + "..." : text;
    }

    /**
     * Prompts the user to input an Enquiry ID to reply to.
     *
     * @return the Enquiry ID value
     */
    public int promptEnquiryId() {
        return prompt.promptInt("Enter Enquiry ID: ");
    }

    /**
     * Prompts the user to enter the title of their new enquiry.
     *
     * @return the title entered by the user
     */
    public String promptTitle() {
        return prompt.promptString("Enter enquiry title: ");
    }

    /**
     * Prompts the user to enter the content of their new enquiry.
     *
     * @return the content entered by the user
     */
    public String promptContent() {
        return prompt.promptString("Enter enquiry content: ");
    }

    /**
     * Prompts the user to enter the Project ID related to the enquiry they are asking about.
     *
     * @return the Project ID entered by the user
     */
    public int promptProjectId() {
        return prompt.promptInt("Enter Project ID: ");
    }

    /**
     * Prompts the officer to enter their reply to the selected enquiry.
     *
     * @return the reply entered by the officer
     */
    public String promptReply() {
        return prompt.promptString("Enter enquiry reply: ");
    }
}
