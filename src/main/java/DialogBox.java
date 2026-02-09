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

public class DialogBox extends HBox {
    @FXML private Label dialog;
    @FXML private ImageView displayPicture;

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
    }

    private void flip() {
        javafx.collections.ObservableList<javafx.scene.Node> tmp = javafx.collections.FXCollections.observableArrayList(this.getChildren());
        java.util.Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(javafx.geometry.Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label"); // Adds the Duke-specific style
    }

    public static DialogBox getUserDialog(String text, Image img) { return new DialogBox(text, img); }
    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}