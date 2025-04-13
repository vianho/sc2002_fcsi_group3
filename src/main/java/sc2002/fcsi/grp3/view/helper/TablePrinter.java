package sc2002.fcsi.grp3.view.helper;

import java.util.List;

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

    private static void printRow(List<String> row, int[] columnWidths) {
        StringBuilder builder = new StringBuilder("|");
        for (int i = 0; i < row.size(); i++) {
            String cell = row.get(i);
            builder.append(" ").append(padRight(cell, columnWidths[i])).append(" |");
        }
        System.out.println(builder);
    }

    private static void printSeparator(int[] columnWidths) {
        StringBuilder builder = new StringBuilder("+");
        for (int width : columnWidths) {
            builder.append("-".repeat(width + 2)).append("+");
        }
        System.out.println(builder);
    }

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

    private static String padRight(String text, int length) {
        if (text.length() >= length) return text;
        return text + " ".repeat(length - text.length());
    }
}
