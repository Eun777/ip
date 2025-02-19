import eunai.EunAi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
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

    private EunAi eunAI;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image eunAiImage = new Image(this.getClass().getResourceAsStream("/images/DaEunAI.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the EunAI instance */
    public void setEunAI(EunAi e) {
        eunAI = e;
        showWelcomeMessage();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing EunAI's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = eunAI.processUserInput(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getEunAiDialog(response, eunAiImage) // Renamed from getDukeDialog
        );
        userInput.clear();
    }

    private void showWelcomeMessage() {
        String welcomeMessage = "Hellooo, I'm EunAI! ✨\nWhat's up? Need some help or just here to vibe lol";
        dialogContainer.getChildren().add(DialogBox.getEunAiDialog(welcomeMessage, eunAiImage));
    }

}
