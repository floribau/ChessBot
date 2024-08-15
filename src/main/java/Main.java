import GUI.StartSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public synchronized void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScene.fxml"));
    Parent root = loader.load();

    StartSceneController controller = loader.getController();
    controller.setStage(primaryStage);

    primaryStage.setTitle("Chess Engine");
    primaryStage.setScene(new Scene(root, 1280, 720));
    primaryStage.setMaximized(true);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}