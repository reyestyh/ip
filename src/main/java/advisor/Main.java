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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setAdvisor(advisor);

            // Update data file when window is closed with 'X'
            stage.setOnCloseRequest(event -> {
                event.consume();
                fxmlLoader.<MainWindow>getController().onWindowClose();
                javafx.application.Platform.runLater(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ignored) {
                        // ignore
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
