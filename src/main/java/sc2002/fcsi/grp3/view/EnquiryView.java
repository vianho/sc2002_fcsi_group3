package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Enquiry;
import sc2002.fcsi.grp3.view.helper.Prompter;

import java.util.List;
import java.util.Scanner;

/**
 * The {@code EnquiryView} handles the view for applicants to interact with enquiries.
 * <p>This class is responsible for:</p>
 * <ul>
 *     <li>Presenting enquiry details in a readable tabular format</li>
 *     <li>Prompting applicants to add new enquiries</li>
 *     <li>Prompting applicants to edit/delete new enquiries</li>
 *     <li>Showing success and error messages</li>
 * </ul>
 *
 * <p>It uses the {@link Prompter} utility to render the CLI interface components.</p>
 */
public class EnquiryView extends BaseView {
    /**
     * Constructs an {@code EnquiryView} instance using SharedPromptView
     * @param prompt the SharedPromptView for displaying messages, errors and tables.
     */
    public EnquiryView(Prompter prompt) {
        super(prompt);
    }

    /**
     * Displays the table of all of the applicant's past enquiries
     * @param enquiries A list of {@link Enquiry } to be displayed.
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
     * @param enquiries A list of {@link Enquiry } to be displayed.
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
     * Displays a compacted table of enquiries from the user. This is used to display enquiries for the edit and delete
     * portion of the enquiry.
     * @param enquiries A list of {@link Enquiry } to be displayed.
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
     * Displays the table of enquiries pending replies for the manager to choose from.
     * These enquiries are based off the project that the manager is handling only.
     * @param enquiries A list of {@link Enquiry } to be displayed.
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
     * Method to truncate the string in the case where the content is too long, making the table neater.
     * @param text The text to truncate.
     * @param length The maximum allowable length of string to be displayed.
     * @return The truncated text or "(empty)" if the string is null.
     */
    private String truncate(String text, int length){
        if(text == null) return "(empty)";
        return text.length() > length ? text.substring(0, length - 3) + "..." : text;
    }

    /**
     * Prompts the applicant to input an EnquiryID to reply to.
     * @return The EnquiryID value.
     */

    public int promptEnquiryId() {
        return prompt.promptInt("Enter Enquiry ID: ");
    }

    /**
     * Prompts the applicant to enter their title of their new enquiry.
     * @return The title entered by the applicant.
     */
    public String promptTitle() {
        return prompt.promptString("Enter enquiry title: ");
    }

    /**
     * Prompts the applicant to enter the content of their new enquiry.
     * @return The content entered by the applicant.
     */
    public String promptContent() {
        return prompt.promptString("Enter enquiry content: ");
    }

    /**
     * Prompts the applicant to enter the projectID related to the enquiry they are asking about.
     * @return The projectID entered by the applicant.
     */
    public int promptProjectId() {
        return prompt.promptInt("Enter Project ID: ");
    }

    /**
     * Prompts the officer to enter their reply to the selected Enquiry.
     * @return The reply entered by the officer.
     */
    public String promptReply() {
        return prompt.promptString("Enter enquiry reply: ");
    }
}
