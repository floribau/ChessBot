package GUI;

import Game.GameEngine;
import Game.Piece;
import Game.PlayerColor;
import Util.Position;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class GameSceneController {

  @FXML
  private Button rect00, rect01, rect02, rect03, rect04, rect05, rect06, rect07;
  @FXML
  private Button rect10, rect11, rect12, rect13, rect14, rect15, rect16, rect17;
  @FXML
  private Button rect20, rect21, rect22, rect23, rect24, rect25, rect26, rect27;
  @FXML
  private Button rect30, rect31, rect32, rect33, rect34, rect35, rect36, rect37;
  @FXML
  private Button rect40, rect41, rect42, rect43, rect44, rect45, rect46, rect47;
  @FXML
  private Button rect50, rect51, rect52, rect53, rect54, rect55, rect56, rect57;
  @FXML
  private Button rect60, rect61, rect62, rect63, rect64, rect65, rect66, rect67;
  @FXML
  private Button rect70, rect71, rect72, rect73, rect74, rect75, rect76, rect77;
  @FXML
  private ImageView image00, image01, image02, image03, image04, image05, image06, image07;
  @FXML
  private ImageView image10, image11, image12, image13, image14, image15, image16, image17;
  @FXML
  private ImageView image20, image21, image22, image23, image24, image25, image26, image27;
  @FXML
  private ImageView image30, image31, image32, image33, image34, image35, image36, image37;
  @FXML
  private ImageView image40, image41, image42, image43, image44, image45, image46, image47;
  @FXML
  private ImageView image50, image51, image52, image53, image54, image55, image56, image57;
  @FXML
  private ImageView image60, image61, image62, image63, image64, image65, image66, image67;
  @FXML
  private ImageView image70, image71, image72, image73, image74, image75, image76, image77;
  @FXML
  private Button knightPromotion, bishopPromotion, rookPromotion, queenPromotion;
  @FXML
  private ImageView knightImage, bishopImage, rookImage, queenImage;
  @FXML
  private ImageView backgroundImageView;

  private Button[][] boardSquares;
  private ImageView[][] imageViews;
  private Position selectedFrom;
  private Position selectedTo;
  private char selectedPromotion = '0';

  public GameSceneController() {
  }


  @FXML
  public synchronized void initialize() {
    boardSquares = new Button[][]{
        {rect00, rect01, rect02, rect03, rect04, rect05, rect06, rect07},
        {rect10, rect11, rect12, rect13, rect14, rect15, rect16, rect17},
        {rect20, rect21, rect22, rect23, rect24, rect25, rect26, rect27},
        {rect30, rect31, rect32, rect33, rect34, rect35, rect36, rect37},
        {rect40, rect41, rect42, rect43, rect44, rect45, rect46, rect47},
        {rect50, rect51, rect52, rect53, rect54, rect55, rect56, rect57},
        {rect60, rect61, rect62, rect63, rect64, rect65, rect66, rect67},
        {rect70, rect71, rect72, rect73, rect74, rect75, rect76, rect77},
    };
    imageViews = new ImageView[][]{
        {image00, image01, image02, image03, image04, image05, image06, image07},
        {image10, image11, image12, image13, image14, image15, image16, image17},
        {image20, image21, image22, image23, image24, image25, image26, image27},
        {image30, image31, image32, image33, image34, image35, image36, image37},
        {image40, image41, image42, image43, image44, image45, image46, image47},
        {image50, image51, image52, image53, image54, image55, image56, image57},
        {image60, image61, image62, image63, image64, image65, image66, image67},
        {image70, image71, image72, image73, image74, image75, image76, image77},
    };
    Platform.runLater(() -> {
      backgroundImageView.fitWidthProperty().bind(backgroundImageView.getScene().widthProperty());
      backgroundImageView.fitHeightProperty().bind(backgroundImageView.getScene().heightProperty());
    });
    changeGUISettings();
  }

  public synchronized boolean isFlipBoard(){
    return !GameEngine.getPlayer1().isHumanPlayer() && GameEngine.getPlayer2().isHumanPlayer();
  }


  @FXML
  public synchronized void repaint(String[][] board){
    if(isFlipBoard()) {
      repaintFlipped(board);
    } else {
      for (int i=0; i<=7; i++){
        for (int j=0; j<=7; j++){
          if(!board[i][j].equals("") && board[i][j] != null){
            Piece piece = GameEngine.getCurrentBoard().getPieceById(board[i][j]);
            Image image = piece.getImage();
            imageViews[i][j].setImage(image);
          } else {
            imageViews[i][j].setImage(null);
          }
        }
      }
    }
  }

  /**
   * used if human player plays as black against AI player
   * @param board the board that should be painted
   */
  @FXML
  public synchronized void repaintFlipped(String[][] board){
    for (int i=0; i<=7; i++){
      for (int j=0; j<=7; j++){
        if(!board[i][j].equals("") && board[i][j] != null){
          Piece piece = GameEngine.getCurrentBoard().getPieceById(board[i][j]);
          Image image = piece.getImage();
          imageViews[7-i][7-j].setImage(image);
        } else {
          imageViews[7-i][7-j].setImage(null);
        }
      }
    }
  }

  @FXML
  public synchronized void changeGUISettings() {
    backgroundImageView.setImage(GUIConfig.getLayout().getBackground());
    for (int i=0; i<=7; i++){
      for (int j=0; j<=7; j++){
        Button rect = boardSquares[i][j];
        if ((i+j)%2 == 0) {
          rect.setStyle("-fx-background-color: " + GUIConfig.colorToHexString(GUIConfig.getLayout().getWhiteColor()));
        } else {
          rect.setStyle("-fx-background-color: " + GUIConfig.colorToHexString(GUIConfig.getLayout().getBlackColor()));
        }
      }
    }
  }

  public synchronized void activateButton(Position pos){
    int rowPos = isFlipBoard() ? 7 - pos.row : pos.row;
    int colPos = isFlipBoard() ? 7 - pos.col : pos.col;
    boardSquares[rowPos][colPos].setDisable(false);
  }

  public synchronized void disableAllButtons(){
    for (int i=0; i<=7; i++){
      for (int j=0; j<=7; j++){
        boardSquares[i][j].setDisable(true);
      }
    }
  }

  public synchronized  void resetSelectedButtons(){
    this.selectedFrom = null;
    this.selectedTo = null;
  }

  public synchronized void handleMoveSelection(Position pos){
    if(selectedFrom == null) {
      selectedFrom = pos;
    } else if(selectedFrom.equals(pos)) {
      resetSelectedButtons();
    } else if(selectedTo == null) {
      selectedTo = pos;
    }
  }

  public synchronized void buttonClicked(Event e) {
    Button b = (Button) e.getSource();
    Position sourcePos = getPositionOfButton(b);
    handleMoveSelection(sourcePos);
  }

  public synchronized void handlePromotionButton(Event e) {
    Button b = (Button) e.getSource();
    if (b.equals(knightPromotion)){
      selectedPromotion = 'N';
    } else if (b.equals(bishopPromotion)) {
      selectedPromotion = 'B';
    } else if (b.equals(rookPromotion)) {
      selectedPromotion = 'R';
    } else if (b.equals(queenPromotion)) {
      selectedPromotion = 'Q';
    } else {
      new UnsupportedOperationException().printStackTrace();
      selectedPromotion = '0';
    }
  }

  public void handleGameOver(PlayerColor color) {
    // TODO redo work
  }

  public void handleGameOver() {
    // TODO redo work
  }

  public synchronized void activatePromotionButtons(PlayerColor color){
    selectedPromotion = '0';

    knightPromotion.setDisable(false);
    bishopPromotion.setDisable(false);
    rookPromotion.setDisable(false);
    queenPromotion.setDisable(false);
    knightPromotion.setVisible(true);
    bishopPromotion.setVisible(true);
    rookPromotion.setVisible(true);
    queenPromotion.setVisible(true);

    knightImage.setImage(color.equals(PlayerColor.WHITE) ? GUIConfig.KNIGHT_WHITE : GUIConfig.KNIGHT_BLACK);
    bishopImage.setImage(color.equals(PlayerColor.WHITE) ? GUIConfig.BISHOP_WHITE : GUIConfig.BISHOP_BLACK);
    rookImage.setImage(color.equals(PlayerColor.WHITE) ? GUIConfig.ROOK_WHITE : GUIConfig.ROOK_BLACK);
    queenImage.setImage(color.equals(PlayerColor.WHITE) ? GUIConfig.QUEEN_WHITE : GUIConfig.QUEEN_BLACK);
    knightImage.setVisible(true);
    bishopImage.setVisible(true);
    rookImage.setVisible(true);
    queenImage.setVisible(true);
  }

  public synchronized void deactivatePromotionButtons(){
    knightPromotion.setDisable(true);
    bishopPromotion.setDisable(true);
    rookPromotion.setDisable(true);
    queenPromotion.setDisable(true);
    knightPromotion.setVisible(false);
    bishopPromotion.setVisible(false);
    rookPromotion.setVisible(false);
    queenPromotion.setVisible(false);

    knightImage.setImage(null);
    bishopImage.setImage(null);
    rookImage.setImage(null);
    queenImage.setImage(null);
    knightImage.setVisible(false);
    bishopImage.setVisible(false);
    rookImage.setVisible(false);
    queenImage.setVisible(false);

    selectedPromotion = '0';
  }

  public synchronized Position getPositionOfButton(Button b) {
    for (int i=0; i<=7; i++){
      for (int j=0; j<=7; j++){
        if (boardSquares[i][j].equals(b)){
          if (isFlipBoard()) {
            return new Position(7-i, 7-j);
          } else {
            return new Position(i,j);
          }
        }
      }
    }
    return null;
  }

  public Position getSelectedFrom(){
    return this.selectedFrom;
  }

  public Position getSelectedTo(){
    return this.selectedTo;
  }

  public char getSelectedPromotion(){return this.selectedPromotion;}

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
