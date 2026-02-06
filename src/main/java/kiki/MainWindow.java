package kiki;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 * This class handles user interaction, manages the layout of the chat interface,
 * and updates the dialog container with messages from the user and the application.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Kiki kiki;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image kikiImage = new Image(this.getClass().getResourceAsStream("/images/kiki.png"));

    /**
     * Initializes the main window controller.
     * Binds the scroll pane's vertical value to the height of the dialog container
     * to ensure the view automatically scrolls to the latest message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the Kiki instance for this controller and displays the welcome message.
     *
     * @param k The Kiki instance to handle logic and generate responses.
     */
    public void setKiki(Kiki k) {
        kiki = k;
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(kiki.getWelcomeMessage(), kikiImage)
        );
    }

    /**
     * Handles the user input event.
     * Captures input from the text field, generates a response using the Kiki instance,
     * updates the dialog container with both the user's query and Kiki's reply,
     * and clears the user input field.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = kiki.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, kikiImage)
        );
        userInput.clear();

        if (input.equals("bye")) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1.0));
            pause.setOnFinished(event -> Platform.exit());
            pause.play();
        }
    }
}
