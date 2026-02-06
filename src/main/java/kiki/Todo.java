package kiki;

/**
 * Todo class representing a task without any date/time attached to it.
 */
public class Todo extends Task {

    /**
     * Creates a new Todo task with the given description.
     *
     * @param description The task description.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T " + super.toFileString();
    }
}
