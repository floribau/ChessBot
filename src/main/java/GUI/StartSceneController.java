package GUI;

import Game.GameEngine;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StartSceneController {

  @FXML
  private ImageView backgroundImageView;
  @FXML
  private Button whiteButton, blackButton, humanButton, aiButton;
  @FXML
  private VBox buttonContainer;

  private Stage stage;

  public void initialize() {
    // Load and set the background image
    Image backgroundImage = GUIConfig.getLayout().getBackground();
    backgroundImageView.setImage(backgroundImage);
    backgroundImageView.setSmooth(true);
    Platform.runLater(() -> {
      backgroundImageView.fitWidthProperty().bind(backgroundImageView.getScene().widthProperty());
      backgroundImageView.fitHeightProperty().bind(backgroundImageView.getScene().heightProperty());
    });
  }

  public void setStage(Stage stage) {
    this.stage = stage;
    stage.widthProperty().addListener((obs, oldVal, newVal) -> {
      updateButtonSizes();
    });
    stage.heightProperty().addListener((obs, oldVal, newVal) -> {
      updateButtonSizes();
    });
  }

  private void updateButtonSizes() {
    double buttonWidth = stage.getWidth() * 0.2;
    double buttonHeight = stage.getHeight() * 0.1;
    double spacing = stage.getHeight() * 0.05;

    buttonContainer.setSpacing(spacing);
    whiteButton.setPrefSize(buttonWidth, buttonHeight);
    blackButton.setPrefSize(buttonWidth, buttonHeight);
    humanButton.setPrefSize(buttonWidth, buttonHeight);
    aiButton.setPrefSize(buttonWidth, buttonHeight);
  }

  public void startWhiteGame(ActionEvent event) throws IOException {
    switchToGameScene(event, true, false);
  }

  public void startBlackGame(ActionEvent event) throws IOException {
    switchToGameScene(event, false, true);
  }

  public void startHumanGame(ActionEvent event) throws IOException {
    switchToGameScene(event, true, true);
  }

  public void startAIGame(ActionEvent event) throws IOException {
    switchToGameScene(event, false, false);
  }

  private void switchToGameScene(ActionEvent event, boolean isHumanPlayer1, boolean isHumanPlayer2) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameScene.fxml"));
    Parent root = loader.load();

    GameSceneController controller = loader.getController();
    GameEngine.startGame(controller, isHumanPlayer1, isHumanPlayer2);

    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root, 1280, 720));
    stage.setMaximized(true);
    stage.show();
  }

  public void openSettings() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SettingsScene.fxml"));
      Parent settingsRoot = fxmlLoader.load();

      Stage settingsStage = new Stage();
      settingsStage.setTitle("Settings");
      settingsStage.initModality(Modality.APPLICATION_MODAL);  // Makes the settings window modal
      settingsStage.setResizable(false);  // Optional: make the settings window non-resizable
      settingsStage.setScene(new Scene(settingsRoot));
      settingsStage.showAndWait();  // Wait until the settings window is closed
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}