package advisor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Advisor using FXML.
 */
public class Main extends Application {

    private Advisor advisor = new Advisor();


    @Override
    public void start(Stage stage) {
        int minimumHeight = 220;
        int minimumWidth = 417;
        int closeDelayTime = 1;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Advisor");
            stage.setScene(scene);
            stage.setMinHeight(minimumHeight);
            stage.setMinWidth(minimumWidth);
            fxmlLoader.<MainWindow>getController().setAdvisor(advisor);

            // Update data file when window is closed with 'X'
            stage.setOnCloseRequest(event -> {
                event.consume();
                fxmlLoader.<MainWindow>getController().onWindowClose();
                javafx.application.Platform.runLater(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(closeDelayTime);
                    } catch (InterruptedException ignored) {
                        // ignored as interrupting the closing delay before it ends
                        // does not impact updating of the data file
                    }
                    stage.close();
                });
            });

            fxmlLoader.<MainWindow>getController().onStartup();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
