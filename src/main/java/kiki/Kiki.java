package kiki;

/**
 * The main class of the Kiki application.
 */
public class Kiki {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private static final String INDENT = "    ";
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    /**
     * Initializes the application components.
     * It attempts to load existing tasks from save file.
     */
    public Kiki() {
        ui = new Ui();
        storage = new Storage();
        try {
            tasks = new TaskList(storage.load());
            if (tasks.size() == 0) {
                ui.showLine();
                System.out.println(INDENT + "No saved file found. Starting with a new list.");
                ui.showLine();
            } else {
                ui.showLine();
                System.out.println(INDENT + "Saved file found. Successfully loaded " + tasks.size() + " tasks from file.");
                ui.showLine();
            }
        } catch (KikiException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main program loop.
     * Runs the program until the "bye" command is entered.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command command = Parser.parseCommand(fullCommand);
                String arguments = Parser.parseArguments(fullCommand);
                switch (command) {
                    case BYE:
                        ui.showMessage("Bye. Hope to see you again soon!");
                        isExit = true;
                        break;
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
            } catch (KikiException | NumberFormatException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.close();
    }

    /**
     * Adds a new task to the task list based on the command type.
     *
     * @param command   The type of task to add.
     * @param arguments The details of the task.
     * @throws KikiException If arguments are invalid or missing.
     */
    private void addTask(Command command, String arguments) throws KikiException {
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

        ui.showLine();
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + newTask);
        System.out.println(INDENT + "Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }

    /**
     * Prints all the tasks currently stored in the taskList.
     */
    private void listTask() {
        ui.showLine();
        System.out.println(INDENT + "Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(INDENT + (i + 1) + "." + tasks.get(i));
        }
        ui.showLine();
    }

    /**
     * Marks a specific task as done or not done based on the user's input.
     *
     * @param argument The string containing the index of the task.
     * @param isDone   True to mark the task as done, false to mark it as not done.
     * @throws KikiException If the argument is empty or the task index is out of bounds.
     */
    private void markTask(String argument, boolean isDone) throws KikiException {
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
            ui.showLine();
            System.out.println(INDENT + "Nice! I've marked this task as done:");
            System.out.println(INDENT + "  " + task);
            ui.showLine();
        } else {
            task.markAsNotDone();
            ui.showLine();
            System.out.println(INDENT + "OK, I've marked this task as not done yet:");
            System.out.println(INDENT + "  " + task);
            ui.showLine();
        }

        storage.save(tasks.getAllTasks());
    }

    /**
     * Deletes a specific task from the list.
     *
     * @param argument The string containing the index of the task to delete.
     * @throws KikiException If the argument is empty or the task index is out of bounds.
     */
    private void deleteTask(String argument) throws KikiException {
        if (argument.isEmpty()) {
            throw new KikiException("Which task? Please tell me the task number to delete.");
        }

        int index = Integer.parseInt(argument) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new KikiException("I couldn't find that task! Please check the list again.");
        }

        Task removedTask = tasks.delete(index);
        storage.save(tasks.getAllTasks());

        ui.showLine();
        System.out.println(INDENT + "Noted. I've removed this task:");
        System.out.println(INDENT + "  " + removedTask);
        System.out.println(INDENT + "Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }

    /**
     * The main entry point of the Kiki application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        new Kiki().run();
    }
}
