package sc2002.fcsi.grp3.view;

import sc2002.fcsi.grp3.model.Enquiry;

import java.util.List;
import java.util.Scanner;


public class EnquiryViewApplicant {
    private final SharedPromptView prompt;

    public EnquiryViewApplicant(SharedPromptView prompt) {
        this.prompt = prompt;
    }

    public int showEnquiryMenuAndGetChoice() {
        String[] options = {"View All Enquiries","Add New Enquiry","Edit Enquiry", "Delete Enquiry", "Back to Main Menu"};
        return prompt.menuPrompt("Enquiry Menu", options, "> ");
    }

    public void showEnquiries(List<Enquiry> enquiries) {
        if (enquiries.isEmpty()) {
            prompt.showMessage("You have no enquiries.");
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

    public void showAvailableEnquiries(List<Enquiry> enquiries) {
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

    private String truncate(String text, int length){
        if(text == null) return "(empty)";
        return text.length() > length ? text.substring(0, length - 3) + "..." : text;
    }


    public int promptEnquiryId() {
        return prompt.promptInt("Enter Enquiry ID: ");
    }
    public String promptTitle() {
        return prompt.promptString("Enter enquiry title: ");
    }

    public String promptContent() {
        return prompt.promptString("Enter enquiry content: ");
    }

    public int promptProjectId() {
        return prompt.promptInt("Enter Project ID: ");
    }

    public void showMessage(String msg) {
        prompt.showMessage(msg);
    }

    public void showError(String msg) {
        prompt.showError(msg);
    }


}
