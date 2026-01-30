package kiki;

import java.util.ArrayList;

/**
 * TaskList class representing the list of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates a TaskList initialized with a given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list at the specified index.
     *
     * @param index The index of the task to delete.
     * @return The task that was removed.
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the list at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Searches for tasks that contain the specified keyword in the description.
     *
     * @param keyword The keyword to search for.
     * @return An ArrayList containing tasks that match the keyword.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }

    /**
     * Returns the whole ArrayList of tasks.
     *
     * @return The ArrayList containing all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return this.tasks;
    }
}