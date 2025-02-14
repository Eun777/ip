package eunai;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Represents a utility class used for parsing and formatting date strings.
 * Supports multiple date formats for flexible input handling.
 */
public class DateParser {

    private static final List<String> DATE_FORMATS = List.of(
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd", // 2019-12-02
            "yyyy-MM-dd'T'HH:mm", // 2019-12-02T18:00 (ISO format)
            "yyyy-MM-dd'T'HH:mm:ss", // 2025-02-01T00:00:00
            "dd/MM/yy HHmm", // 02/12/19 1800
            "d/M/yyyy HHmm", // 2/12/2019 1800
            "d/M/yyyy", // 2/12/2019
            "MMM dd yyyy HHmm", // Dec 02 2019 1800
            "MMM dd yyyy", // Dec 02 2019
            "dd/MM/yyyy HHmm", // 02/12/2019 1800
            "dd/MM/yyyy" // 02/12/2019
    );

    /**
     * Parses a date string into a LocalDateTime object.
     * @param dateInput The date string to be parsed.
     * @return The parsed date and time as a LocalDateTime object.
     */
    public static LocalDateTime parseDate(String dateInput) {
        for (String format : DATE_FORMATS) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            try {
                if (format.contains("HHmm") || format.contains("HH:mm")) {
                    return LocalDateTime.parse(dateInput, formatter);
                } else {
                    return LocalDate.parse(dateInput, formatter).atStartOfDay();
                }
            } catch (DateTimeParseException ignored) {
                // Try the next format
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + dateInput);
    }

    /**
     * Formats a LocalDateTime object into a String with the required format.
     * The output format is "MMM dd yyyy HH:mm"}, e.g. "Dec 02 2019 18:00".
     * @param dateTime The LocalDateTime object to be formatted.
     * @return A formatted date-time String.
     */
    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("Cannot format null date.");
        }
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy hh:mm a");
        return dateTime.format(displayFormatter);
    }

}
