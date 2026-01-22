import java.util.Scanner;

/**
 * The main class of the Kiki application.
 */
public class Kiki {
    private static final String NAME = "Kiki";
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String INDENT = "    ";

    // 2. STATE (The data we need to modify)
    private static Task[] taskList = new Task[100];
    private static int taskCount = 0;

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

            if (input.equals("bye")) {
                System.out.println(INDENT + HORIZONTAL_LINE);
                System.out.println(INDENT + "Bye. Hope to see you again soon!");
                System.out.println(INDENT + HORIZONTAL_LINE);
                break;
            } else if (input.equals("list")) {
                System.out.println(INDENT + HORIZONTAL_LINE);
                System.out.println(INDENT + "Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(INDENT + (i + 1) + "." + taskList[i]);
                }
                System.out.println(INDENT + HORIZONTAL_LINE);
            } else if (inputLine[0].equals("mark")) {
                int index = Integer.parseInt(inputLine[1]) - 1;
                taskList[index].markAsDone();

                System.out.println(INDENT + HORIZONTAL_LINE);
                System.out.println(INDENT + "Nice! I've marked this task as done:");
                System.out.println(INDENT + "  " + taskList[index]);
                System.out.println(INDENT + HORIZONTAL_LINE);
            } else if (inputLine[0].equals("unmark")) {
                int index = Integer.parseInt(inputLine[1]) - 1;
                taskList[index].markAsNotDone();

                System.out.println(INDENT + HORIZONTAL_LINE);
                System.out.println(INDENT + "OK, I've marked this task as not done yet:");
                System.out.println(INDENT + "  " + taskList[index]);
                System.out.println(INDENT + HORIZONTAL_LINE);
            } else {
                addTask(inputLine[0], inputLine[1]);
            }
        }

        sc.close();
    }

    /**
     * Adds a new task to the task list based on the command type.
     *
     * @param command   The type of task to add.
     * @param arguments The details of the task.
     */
    private static void addTask(String command, String arguments) {
        Task newTask = null;

        if (command.equals("todo")) {
            newTask = new Todo(arguments);
        } else if (command.equals("deadline")) {
            String[] parts = arguments.split(" /by ");
            newTask = new Deadline(parts[0], parts[1]);
        } else if (command.equals("event")) {
            // arguments: "project meeting /from Mon 2pm /to 4pm"
            String[] parts = arguments.split(" /from ");
            String description = parts[0];
            String[] times = parts[1].split(" /to ");
            newTask = new Event(description, times[0], times[1]);
        }
        taskList[taskCount] = newTask;
        taskCount++;

        System.out.println(INDENT + HORIZONTAL_LINE);
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + newTask);
        System.out.println(INDENT + "Now you have " + taskCount + " tasks in the list.");
        System.out.println(INDENT + HORIZONTAL_LINE);
    }
}
