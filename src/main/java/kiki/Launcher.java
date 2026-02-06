package kiki;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 * This class serves as the entry point for the application, delegating execution
 * to the Main class to avoid JavaFX runtime module errors.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
