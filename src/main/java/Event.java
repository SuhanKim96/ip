/**
 * Event class representing a task that starts at a specific time and ends at a specific time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates a new Event task.
     *
     * @param description The task description.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }

    @Override
    public String toFileString() {
        return "E " + super.toFileString() + " | " + this.from + " | " + this.to;
    }
}