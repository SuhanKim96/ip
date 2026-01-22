import java.util.Scanner;

/**
 * The main class of the Kiki application.
 */
public class Kiki {
    /**
     * Greets the user, stores tasks, manages them, and exits when "bye" is entered.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        String name = "Kiki";
        String horizontalLine = "____________________________________________________________";
        String indent = "    ";
        Scanner sc = new Scanner(System.in);
        Task[] taskList = new Task[100];
        int taskCount = 0;

        System.out.println(indent + horizontalLine);
        System.out.println(indent + "Hello! I'm " + name);
        System.out.println(indent + "What can I do for you?");
        System.out.println(indent + horizontalLine);

        while (true) {
            String input = sc.nextLine();
            String[] inputLine = input.split(" ");

            if (input.equals("bye")) {
                System.out.println(indent + horizontalLine);
                System.out.println(indent + "Bye. Hope to see you again soon!");
                System.out.println(indent + horizontalLine);
                break;
            } else if (input.equals("list")) {
                System.out.println(indent + horizontalLine);
                System.out.println(indent + "Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(indent + (i + 1) + "." + taskList[i]);
                }
                System.out.println(indent + horizontalLine);
            } else if (inputLine[0].equals("mark")) {
                int index = Integer.parseInt(inputLine[1]) - 1;
                taskList[index].markAsDone();

                System.out.println(indent + horizontalLine);
                System.out.println(indent + "Nice! I've marked this task as done:");
                System.out.println(indent + "  " + taskList[index]);
                System.out.println(indent + horizontalLine);
            } else if (inputLine[0].equals("unmark")) {
                int index = Integer.parseInt(inputLine[1]) - 1;
                taskList[index].markAsNotDone();

                System.out.println(indent + horizontalLine);
                System.out.println(indent + "OK, I've marked this task as not done yet:");
                System.out.println(indent + "  " + taskList[index]);
                System.out.println(indent + horizontalLine);
            } else {
                taskList[taskCount] = new Task(input);
                taskCount++;
                System.out.println(indent + horizontalLine);
                System.out.println(indent + "added: " + input);
                System.out.println(indent + horizontalLine);
            }
        }

        sc.close();

    }
}
