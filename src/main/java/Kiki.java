/**
 * The main class of the Kiki application.
 */
public class Kiki {
    /**
     * Greets the user and exits the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        String name = "Kiki";
        String horizontalLine = "____________________________________________________________";
        System.out.println(horizontalLine);
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }
}
