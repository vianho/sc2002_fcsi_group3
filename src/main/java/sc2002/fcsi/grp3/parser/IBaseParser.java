package sc2002.fcsi.grp3.parser;

import java.time.format.DateTimeFormatter;

public interface IBaseParser<T> {
    final static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    T parse(String[] tokens);
}
