import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A DateHandler class for managing date parsing and formatting
 */
public class DateHandler {
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");

    /**
     * Parses a string into a LocalDate.
     *
     * @param dateString The string to parse.
     * @return The parsed LocalDate.
     * @throws KikiException If the format is invalid.
     */
    public static LocalDate parse(String dateString) throws KikiException {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new KikiException("Please enter dates in yyyy-mm-dd format.");
        }
    }

    /**
     * Formats a LocalDate into a string.
     *
     * @param date The date to format.
     * @return The formatted string.
     */
    public static String format(LocalDate date) {
        return date.format(OUTPUT_FORMATTER);
    }
}