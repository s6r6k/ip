package duke;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Entry point for the Fuzzy application.
 * This class launches the JavaFX UI and connects the UI to the main logic.
 */
public class Main extends Application {

    // Initialize your ACTUAL logic engine here
    private Fuzzy fuzzy = new Fuzzy();

    /**
     * Starts the JavaFX application.
     * Loads the main window layout and injects the Fuzzy logic instance.
     *
     * @param stage Primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setFuzzy(fuzzy);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
