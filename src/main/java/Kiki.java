import java.util.Scanner;

/**
 * The main class of the Kiki application.
 */
public class Kiki {
    /**
     * Greets the user, stores tasks, lists them, and exits when "bye" is entered.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        String name = "Kiki";
        String horizontalLine = "____________________________________________________________";
        String indent = "    ";
        Scanner sc = new Scanner(System.in);
        String[] taskList = new String[100];
        int taskCount = 0;

        System.out.println(indent + horizontalLine);
        System.out.println(indent + "Hello! I'm " + name);
        System.out.println(indent + "What can I do for you?");
        System.out.println(indent + horizontalLine);

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println(indent + horizontalLine);
                System.out.println(indent + "Bye. Hope to see you again soon!");
                System.out.println(indent + horizontalLine);
                break;
            } else if (input.equals("list")) {
                System.out.println(indent + horizontalLine);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(indent + (i + 1) + ". " + taskList[i]);
                }
                System.out.println(indent + horizontalLine);
            } else {
                taskList[taskCount] = input;
                taskCount++;
                System.out.println(indent + horizontalLine);
                System.out.println(indent + "added: " + input);
                System.out.println(indent + horizontalLine);
            }
        }

        sc.close();

    }
}
