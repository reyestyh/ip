package advisor;

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

    private Advisor advisor;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user_icon.png"));
    private Image advisorImage = new Image(this.getClass().getResourceAsStream("/images/duck.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Advisor instance */
    public void setAdvisor(Advisor a) {
        advisor = a;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Advisor's reply and then
     * appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = advisor.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAdvisorDialog(response, advisorImage)
        );
        userInput.clear();
    }

    /**
     * Performs exit functions for Advisor (updating data file) before program is ended.
     */
    public void onWindowClose() {
        handleExit();
    }

    private void handleExit() {
        String input = "bye";
        String response = advisor.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAdvisorDialog(response, advisorImage)
        );

    }
}
