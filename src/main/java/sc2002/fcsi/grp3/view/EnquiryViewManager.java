package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Enquiry;

import java.util.List;



/**
 * The {@code EnquiryViewManager} handles the view for managers to interact with enquiries.
 * <p>This class is responsible for:</p>
 * <ul>
 *     <li>Displaying menus for enquiry-related actions</li>
 *     <li>Presenting enquiry details in a readable tabular format</li>
 *     <li>Prompting managers to reply to enquiries</li>
 *     <li>Showing success and error messages</li>
 * </ul>
 *
 * <p>It uses the {@link SharedPromptView} utility to render the CLI interface components.</p>
 */

public class EnquiryViewManager {


    private final SharedPromptView prompt;


    /**
     * Constructs an {@code EnquiryViewManager} instance using SharedPromptView
     * @param prompt the SharedPromptView for displaying messages, errors and tables.
     */

    public EnquiryViewManager(SharedPromptView prompt) {
        this.prompt = prompt;
    }

    /**
     * Shows the menu for Managers to see the various enquiry options.
     * @return An integer related to the Manager's selected menu item.
     */

    public int showEnquiryMenuAndGetChoice(){
        String[] options  = {"View All Enquiries Submitted", "Reply to Enquiry for the project you are managing", "Back"};
        return prompt.menuPrompt("Manager Enquiry Menu", options, "> ");
    }

    /**
     * Displays the table of all enquiries submitted.
     * @param enquiries A list of {@link Enquiry } to be displayed.
     */
    public void showEnquiries(List<Enquiry> enquiries) {
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
     * Displays the table of enquiries pending replies for the manager to choose from.
     * These enquiries are based off the project that the manager is handling only.
     * @param enquiries A list of {@link Enquiry } to be displayed.
     */
    public void showAvailableEnquiries(List<Enquiry> enquiries) {
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
     * Prompts the manager to input an EnquiryID to reply to.
     * @return The EnquiryID value.
     */

    public int promptEnquiryId() {
        return prompt.promptInt("Enter Enquiry ID: ");
    }


    /**
     * Prompts the manager to enter their reply to the selected Enquiry.
     * @return The reply entered by the manager.
     */
    public String promptReply() {
        return prompt.promptString("Enter enquiry reply: ");
    }

    /**
     * Displays a message to the manager.
     * @param msg The message to display.
     */
    public void showMessage(String msg) {
        prompt.showMessage(msg);
    }

    /**
     * Displays an error message to the manager.
     * @param msg The error to display.
     */
    public void showError(String msg) {
        prompt.showError(msg);
    }
}
