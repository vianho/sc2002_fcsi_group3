package sc2002.fcsi.grp3.parser;

import java.time.format.DateTimeFormatter;

/**
 * The IBaseParser interface defines a generic parser for converting CSV tokens into objects.
 *
 * @param <T> the type of object to be parsed
 */
public interface IBaseParser<T> {

    /**
     * A shared date-time formatter for parsing dates in the format "dd/MM/yyyy".
     */
    final static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Parses a CSV row into an object of type T.
     *
     * @param tokens the CSV row tokens
     * @return the parsed object of type T
     */
    T parse(String[] tokens);
}
