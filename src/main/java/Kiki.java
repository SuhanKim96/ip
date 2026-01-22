import java.util.Scanner;

/**
 * The main class of the Kiki application.
 */
public class Kiki {
    /**
     * Greets the user, echoes commands, and exits when "bye" is entered.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        String name = "Kiki";
        String horizontalLine = "____________________________________________________________";
        String indent = "    ";
        Scanner sc = new Scanner(System.in);

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
            }

            System.out.println(indent + horizontalLine);
            System.out.println(indent + input);
            System.out.println(indent + horizontalLine);
        }
    }

}
