import GUI.ChessBoardController;
import Game.GameEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public synchronized void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChessBoard.fxml"));
    Parent root = loader.load();
    ChessBoardController controller = loader.getController();
    primaryStage.setTitle("Chess");
    primaryStage.setScene(new Scene(root, 480, 480));
    primaryStage.show();
    GameEngine.startGame(controller);
  }

  public static void main(String[] args) {

    launch(args);
  }
}