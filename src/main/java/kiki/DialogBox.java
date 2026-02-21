package kiki;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 * This custom control is defined in FXML and loaded dynamically.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a new DialogBox with the specified text and image.
     * This constructor loads the layout from the FXML file, sets the text and image,
     * and applies a circular clip to the profile picture to improve the GUI.
     *
     * @param text The text to be displayed in the dialog bubble.
     * @param img  The image representing the speaker.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        Circle clip = new Circle(30, 30, 30);
        displayPicture.setClip(clip);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     * This is used to differentiate the application's responses from the user's input.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Factory method to create a DialogBox for the user.
     * The dialog bubble is styled with a distinct background color (light green)
     * and rounded corners to visually differentiate the user's input from the bot's responses.
     *
     * @param text The user's input text.
     * @param img  The user's profile image.
     * @return A DialogBox representing the user's message, styled as a chat bubble.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.dialog.setStyle("-fx-background-color: #DCF8C6; -fx-padding: 15px; -fx-background-radius: 15px; -fx-text-fill: black;");
        return db;
    }

    /**
     * Factory method to create a DialogBox for Kiki (the bot).
     * The dialog box is flipped to place the profile picture on the left.
     * The dialog bubble is styled with a distinct background color (light gray)
     * and rounded corners to visually distinguish Kiki's replies.
     *
     * @param text The bot's response text.
     * @param img  The bot's profile image.
     * @return A DialogBox representing the bot's message, styled as a chat bubble.
     */
    public static DialogBox getKikiDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        db.dialog.setStyle("-fx-background-color: #D4E6F1; -fx-padding: 15px; -fx-background-radius: 15px; -fx-text-fill: black;");
        return db;
    }
}