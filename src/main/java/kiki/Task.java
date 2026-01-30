package kiki;

/**
 * Task class with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new Task with the given description.
     *
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if done, " " if not done.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task formatted for file storage.
     *
     * @return A formatted string.
     */
    public String toFileString() {
        return String.format("| %d | %s", isDone ? 1 : 0, description);
    }

    /**
     * Returns the description of the task.
     *
     * @return The description string of the task.
     */
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), this.description);
    }
}
