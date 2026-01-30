package kiki;

import java.time.LocalDate;

/**
 * Deadline class representing a task that needs to be done before a specific time.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Creates a new Deadline task.
     *
     * @param description The task description.
     * @param by          The deadline.
     * @throws KikiException If the date format is invalid.
     */
    public Deadline(String description, String by) throws KikiException {
        super(description);
        this.by = DateHandler.parse(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateHandler.format(this.by) + ")";
    }

    @Override
    public String toFileString() {
        return "D " + super.toFileString() + " | " + this.by;
    }
}