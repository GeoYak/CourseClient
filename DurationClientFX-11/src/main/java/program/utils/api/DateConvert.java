package program.utils.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс конвертации LocalDateTime в строку и обратно
 */
public class DateConvert {
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * Конвертация строки в дату
     *
     * @param date - строка даты
     * @return дата (LocalDate)
     */
    public static LocalDate stringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return LocalDate.parse(date, formatter);
    }
}
