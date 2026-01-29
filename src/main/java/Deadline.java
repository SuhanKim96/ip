/**
 * Deadline class representing a task that needs to be done before a specific time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates a new Deadline task.
     *
     * @param description The task description.
     * @param by          The deadline.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }

    @Override
    public String toFileString() {
        return "D " + super.toFileString() + " | " + this.by;
    }
}