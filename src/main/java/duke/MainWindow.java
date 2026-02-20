package duke;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main application window.
 * Handles user input, displays dialog boxes, and connects the UI to the Fuzzy logic.
 */
public class MainWindow extends AnchorPane {

    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private Fuzzy fuzzy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initializes the UI components after FXML loading.
     * Enables smooth touchpad scrolling and auto-scroll to the bottom.
     */
    @FXML
    public void initialize() {
        // Make dialog container fill the width of the ScrollPane
        scrollPane.setFitToWidth(true);

        // Enable touchpad and mouse drag scrolling anywhere in the chat area
        scrollPane.setPannable(true);

        // Remove horizontal scrollbar (chat apps don't need it)
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Automatically scroll to bottom when new messages are added
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Fuzzy logic instance and displays the greeting message.
     *
     * @param f Fuzzy chatbot logic instance.
     */
    public void setFuzzy(Fuzzy f) {
        this.fuzzy = f;
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(fuzzy.getGreeting(), dukeImage)
        );
    }

    /**
     * Handles user input events.
     * Sends the user's message to the chatbot and displays both the user message
     * and the chatbot response in the dialog container.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = fuzzy.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );

        userInput.clear();

        // Exit after "bye"
        if (input.trim().equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}