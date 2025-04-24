package sc2002.fcsi.grp3.view.helper;

import java.util.List;

/**
 * Utility class for printing a formatted ASCII table to the console.
 * Handles dynamic column widths based on content and headers.
 */
public class TablePrinter {

    /**
     * Prints a formatted table with headers and rows.
     * @param headers List of column headers.
     * @param rows List of rows, each row is a list of strings.
     */
    public static void printTable(List<String> headers, List<List<String>> rows) {
        int[] columnWidths = getColumnWidths(headers, rows);

        // Print header
        printRow(headers, columnWidths);
        printSeparator(columnWidths);

        // Print rows
        for (List<String> row : rows) {
            printRow(row, columnWidths);
        }
    }

    /**
     * Prints a single row of cells, padded to match column widths.
     *
     * @param row          the list of strings representing the row
     * @param columnWidths the computed width of each column
     */
    private static void printRow(List<String> row, int[] columnWidths) {
        StringBuilder builder = new StringBuilder("|");
        for (int i = 0; i < row.size(); i++) {
            String cell = row.get(i);
            builder.append(" ").append(padRight(cell, columnWidths[i])).append(" |");
        }
        System.out.println(builder);
    }

    /**
     * Prints a horizontal separator based on the column widths.
     *
     * @param columnWidths the computed width of each column
     */
    private static void printSeparator(int[] columnWidths) {
        StringBuilder builder = new StringBuilder("+");
        for (int width : columnWidths) {
            builder.append("-".repeat(width + 2)).append("+");
        }
        System.out.println(builder);
    }

    /**
     * Computes the maximum width needed for each column based on headers and row content.
     *
     * @param headers list of column headers
     * @param rows    list of rows, where each row is a list of strings
     * @return array of column widths
     */
    private static int[] getColumnWidths(List<String> headers, List<List<String>> rows) {
        int columns = headers.size();
        int[] widths = new int[columns];

        for (int i = 0; i < columns; i++) {
            widths[i] = headers.get(i).length();
        }

        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                widths[i] = Math.max(widths[i], row.get(i).length());
            }
        }

        return widths;
    }

    /**
     * Pads a string with spaces on the right to reach a desired length.
     *
     * @param text   the string to pad
     * @param length the target length
     * @return the padded string
     */
    private static String padRight(String text, int length) {
        if (text.length() >= length) return text;
        return text + " ".repeat(length - text.length());
    }
}
