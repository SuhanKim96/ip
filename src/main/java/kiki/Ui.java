package kiki;

import java.util.Scanner;

/**
 * Ui class handling all user interface interactions for the application.
 */
public class Ui {
    private static final String INDENT = "    ";
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private final Scanner sc;

    /**
     * Creates a new Ui instance.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Reads a full line of input from the user.
     *
     * @return The string entered by the user.
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        showLine();
        System.out.println(INDENT + "Hello! I'm Kiki");
        System.out.println(INDENT + "What can I do for you?");
        showLine();
    }

    /**
     * Prints a horizontal divider line.
     */
    public void showLine() {
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    /**
     * Displays an error message indicating that the save file could not be loaded.
     */
    public void showLoadingError() {
        showLine();
        System.out.println(INDENT + "Error loading file. Starting with a new empty list.");
        showLine();
    }

    /**
     * Displays a specific error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        showLine();
        System.out.println(INDENT + "Oh no! " + message);
        showLine();
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        showLine();
        System.out.println(INDENT + message);
        showLine();
    }

    /**
     * Closes the scanner to prevent resource leaks.
     */
    public void close() {
        sc.close();
    }
}