package kiki;

import java.util.ArrayList;

/**
 * The main logic controller for the Kiki application.
 * This class handles the interaction between the storage, task list, and the GUI.
 */
public class Kiki {
    private Storage storage;
    private TaskList tasks;

    /**
     * Initializes the application components.
     * Attempts to load existing tasks from the save file upon instantiation.
     */
    public Kiki() {
        storage = new Storage();
        try {
            tasks = new TaskList(storage.load());
        } catch (KikiException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Processes the user input and generates a response.
     * This method is called by the GUI to handle a single user command.
     *
     * @param input The raw command string entered by the user.
     * @return A string response to be displayed in the GUI chat bubble.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parseCommand(input);
            String arguments = Parser.parseArguments(input);
            switch (command) {
            case BYE:
                return "Bye. Hope to see you again soon!";
            case LIST:
                return listTask();
            case MARK:
                return markTask(arguments, true);
            case UNMARK:
                return markTask(arguments, false);
            case DELETE:
                return deleteTask(arguments);
            case FIND:
                return handleFind(arguments);
            case TODO:
            case DEADLINE:
            case EVENT:
                return addTask(command, arguments);
            default:
                throw new KikiException("I'm sorry, but I don't know what that means.");
            }
        } catch (KikiException | NumberFormatException e) {
            return "OOPS!!! " + e.getMessage();
        } catch (Exception e) {
            return "Something went wrong: " + e.getMessage();
        }
    }

    /**
     * Generates the welcome message including the file loading status.
     *
     * @return A string containing the greeting and the status of the data file.
     */
    public String getWelcomeMessage() {
        String status;
        if (tasks.size() == 0) {
            status = "No saved file found. Starting with a new list.";
        } else {
            status = "Saved file found. Successfully loaded " + tasks.size() + " tasks from file.";
        }

        return "Hello! I'm Kiki!\n" + status + "\nWhat can I do for you?";
    }

    /**
     * Adds a new task to the task list and saves the changes.
     *
     * @param command   The type of task to add (TODO, DEADLINE, EVENT).
     * @param arguments The details of the task (description and time).
     * @return A string confirming the task addition and showing the current task count.
     * @throws KikiException If arguments are invalid or missing.
     */
    private String addTask(Command command, String arguments) throws KikiException {
        Task newTask = null;

        if (command == Command.TODO) {
            if (arguments.isEmpty()) {
                throw new KikiException("You can't do nothing! Please tell me what task you want to add.");
            }
            newTask = new Todo(arguments);
        } else if (command == Command.DEADLINE) {
            if (arguments.isEmpty()) {
                throw new KikiException("A deadline needs a description!");
            }
            String[] parts = arguments.split(" /by ");
            if (parts.length < 2) {
                throw new KikiException("When is this due? Please specify a time using \"/by\".");
            }
            newTask = new Deadline(parts[0], parts[1]);
        } else if (command == Command.EVENT) {
            if (arguments.isEmpty()) {
                throw new KikiException("An event needs a description! What is happening?");
            }
            String[] parts = arguments.split(" /from ");
            if (parts.length < 2) {
                throw new KikiException("When does it start? Please specify a start time using \"/from\".");
            }
            String description = parts[0];
            String[] times = parts[1].split(" /to ");
            if (times.length < 2) {
                throw new KikiException("When does it end? Please specify an end time using \"/to\".");
            }
            newTask = new Event(description, times[0], times[1]);
        }

        tasks.addTask(newTask);
        storage.save(tasks.getAllTasks());

        return "Got it. I've added this task:\n"
                + "  " + newTask + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Generates a string representation of all tasks currently in the list.
     *
     * @return A formatted string listing all tasks, or a message if the list is empty.
     */
    private String listTask() {
        if (tasks.size() == 0) {
            return "Your list is empty!";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Marks a specific task as done or not done and saves the changes.
     *
     * @param argument The string containing the index of the task.
     * @param isDone   True to mark the task as done, false to mark it as not done.
     * @return A string confirming the task's new status.
     * @throws KikiException If the argument is empty or the task index is out of bounds.
     */
    private String markTask(String argument, boolean isDone) throws KikiException {
        if (argument.isEmpty()) {
            throw new KikiException("Which task? Please tell me the task number.");
        }

        int index = Integer.parseInt(argument) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new KikiException("I couldn't find that task! Please check the list again.");
        }

        Task task = tasks.get(index);

        if (isDone) {
            task.markAsDone();
            storage.save(tasks.getAllTasks());
            return "Nice! I've marked this task as done:\n  " + task;
        } else {
            task.markAsNotDone();
            storage.save(tasks.getAllTasks());
            return "OK, I've marked this task as not done yet:\n  " + task;
        }
    }

    /**
     * Deletes a specific task from the list and saves the changes.
     *
     * @param argument The string containing the index of the task to delete.
     * @return A string confirming the deletion and showing the remaining task count.
     * @throws KikiException If the argument is empty or the task index is out of bounds.
     */
    private String deleteTask(String argument) throws KikiException {
        if (argument.isEmpty()) {
            throw new KikiException("Which task? Please tell me the task number to delete.");
        }

        int index = Integer.parseInt(argument) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new KikiException("I couldn't find that task! Please check the list again.");
        }

        Task removedTask = tasks.delete(index);
        storage.save(tasks.getAllTasks());

        return "Noted. I've removed this task:\n"
                + "  " + removedTask + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Searches for tasks that contain the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     * @return A string listing the matching tasks, or a message indicating no matches were found.
     */
    private String handleFind(String keyword) {
        ArrayList<Task> foundTasks = tasks.findTasks(keyword);

        if (foundTasks.isEmpty()) {
            return "No matching tasks found.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < foundTasks.size(); i++) {
            sb.append((i + 1)).append(".").append(foundTasks.get(i)).append("\n");
        }
        return sb.toString();
    }
}
