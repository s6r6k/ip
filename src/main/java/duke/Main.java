package duke;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    // Initialize your ACTUAL logic engine here
    private Fuzzy fuzzy = new Fuzzy();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Setting window limits as requested
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            // Pass the Fuzzy instance to the controller
            fxmlLoader.<MainWindow>getController().setFuzzy(fuzzy);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}