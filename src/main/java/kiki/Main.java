package kiki;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The GUI entry point for the Kiki application.
 * This class sets up the JavaFX stage, loads the FXML layout, and links the
 * backend logic (Kiki) with the frontend controller (MainWindow).
 */
public class Main extends Application {

    private Kiki kiki = new Kiki();

    /**
     * Starts the JavaFX application.
     * It loads the main window layout from FXML, creates the scene, and injects
     * the Kiki instance into the controller to enable interaction.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setKiki(kiki);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
