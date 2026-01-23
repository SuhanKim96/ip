import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class of the Kiki application.
 */
public class Kiki {
    private static final String NAME = "Kiki";
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String INDENT = "    ";

    private static ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Greets the user, stores tasks, manages them, and exits when "bye" is entered.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(INDENT + HORIZONTAL_LINE);
        System.out.println(INDENT + "Hello! I'm " + NAME);
        System.out.println(INDENT + "What can I do for you?");
        System.out.println(INDENT + HORIZONTAL_LINE);

        while (true) {
            String input = sc.nextLine();
            String[] inputLine = input.split(" ", 2);
            String commandString = inputLine[0];
            String arguments = inputLine.length > 1 ? inputLine[1] : "";

            try {
                Command command;
                try {
                    command = Command.valueOf(commandString.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new KikiException("I'm sorry, I don't understand that command.");
                }
                switch (command) {
                    case BYE:
                        System.out.println(INDENT + HORIZONTAL_LINE);
                        System.out.println(INDENT + "Bye. Hope to see you again soon!");
                        System.out.println(INDENT + HORIZONTAL_LINE);
                        sc.close();
                        return;
                    case LIST:
                        listTask();
                        break;
                    case MARK:
                        markTask(arguments, true);
                        break;
                    case UNMARK:
                        markTask(arguments, false);
                        break;
                    case DELETE:
                        deleteTask(arguments);
                        break;
                    case TODO:
                    case DEADLINE:
                    case EVENT:
                        addTask(command, arguments);
                        break;
                }
            } catch (KikiException e) {
                System.out.println(INDENT + HORIZONTAL_LINE);
                System.out.println(INDENT + "Oh no! " + e.getMessage());
                System.out.println(INDENT + HORIZONTAL_LINE);
            } catch (NumberFormatException e) {
                System.out.println(INDENT + HORIZONTAL_LINE);
                System.out.println(INDENT + "Error: That's not a valid number! Please enter a valid numeric task ID.");
                System.out.println(INDENT + HORIZONTAL_LINE);
            }
        }
    }

    /**
     * Adds a new task to the task list based on the command type.
     *
     * @param command   The type of task to add.
     * @param arguments The details of the task.
     * @throws KikiException If arguments are invalid or missing.
     */
    private static void addTask(Command command, String arguments) throws KikiException {
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
        taskList.add(newTask);

        System.out.println(INDENT + HORIZONTAL_LINE);
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + newTask);
        System.out.println(INDENT + "Now you have " + taskList.size() + " tasks in the list.");
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    /**
     * Prints all the tasks currently stored in the taskList.
     */
    private static void listTask() {
        System.out.println(INDENT + HORIZONTAL_LINE);
        System.out.println(INDENT + "Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(INDENT + (i + 1) + "." + taskList.get(i));
        }
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    /**
     * Marks a specific task as done or not done based on the user's input.
     *
     * @param argument The string containing the index of the task.
     * @param isDone   True to mark the task as done, false to mark it as not done.
     * @throws KikiException If the argument is empty or the task index is out of bounds.
     */
    private static void markTask(String argument, boolean isDone) throws KikiException {
        if (argument.isEmpty()) {
            throw new KikiException("Which task? Please tell me the task number to " + (isDone ? "mark." : "unmark."));
        }

        int index = Integer.parseInt(argument) - 1;

        if (index < 0 || index >= taskList.size()) {
            throw new KikiException("I couldn't find that task! Please check the list again.");
        }

        Task task = taskList.get(index);

        if (isDone) {
            task.markAsDone();
            System.out.println(INDENT + HORIZONTAL_LINE);
            System.out.println(INDENT + "Nice! I've marked this task as done:");
            System.out.println(INDENT + "  " + task);
            System.out.println(INDENT + HORIZONTAL_LINE);
        } else {
            task.markAsNotDone();
            System.out.println(INDENT + HORIZONTAL_LINE);
            System.out.println(INDENT + "OK, I've marked this task as not done yet:");
            System.out.println(INDENT + "  " + task);
            System.out.println(INDENT + HORIZONTAL_LINE);
        }
    }

    /**
     * Deletes a specific task from the list.
     *
     * @param argument The string containing the index of the task to delete.
     * @throws KikiException If the argument is empty or the task index is out of bounds.
     */
    private static void deleteTask(String argument) throws KikiException {
        if (argument.isEmpty()) {
            throw new KikiException("Which task? Please tell me the task number to delete.");
        }

        int index = Integer.parseInt(argument) - 1;

        if (index < 0 || index >= taskList.size()) {
            throw new KikiException("I couldn't find that task! Please check the list again.");
        }

        Task removedTask = taskList.remove(index);

        System.out.println(INDENT + HORIZONTAL_LINE);
        System.out.println(INDENT + "Noted. I've removed this task:");
        System.out.println(INDENT + "  " + removedTask);
        System.out.println(INDENT + "Now you have " + taskList.size() + " tasks in the list.");
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

}
