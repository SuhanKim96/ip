package kiki;

import java.time.LocalDate;

/**
 * Event class representing a task that starts at a specific time and ends at a specific time.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    /**
     * Creates a new Event task.
     *
     * @param description The task description.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     * @throws KikiException If the date formats are invalid.
     */
    public Event(String description, String from, String to) throws KikiException {
        super(description);
        this.from = DateHandler.parse(from);
        this.to = DateHandler.parse(to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + DateHandler.format(this.from) +
                " to: " + DateHandler.format(this.to) + ")";
    }

    @Override
    public String toFileString() {
        return "E " + super.toFileString() + " | " + this.from + " | " + this.to;
    }
}