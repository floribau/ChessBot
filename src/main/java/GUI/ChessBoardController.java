package GUI;

import Game.Board;
import Game.GameEngine;
import Game.Piece;
import Game.PlayerColor;
import Util.Position;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;


public class ChessBoardController {

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

  private Button[][] boardSquares;
  private ImageView[][] imageViews;



  @FXML
  public void initialize() {
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

    changeGUISettings();
  }


  @FXML
  public void repaint(String[][] board){
    for (int i=0; i<=7; i++){
      for (int j=0; j<=7; j++){
        if(board[i][j] != "" && board[i][j] != null){
          Piece piece = GameEngine.getPieceById(board[i][j]);
          Image image = piece.getType().getImage();
          imageViews[i][j].setImage(image);
        } else {
          imageViews[i][j].setImage(null);
        }
      }
    }
  }

  public void repaintFlipped(String[][] board){
    for (int i=0; i<=7; i++){
      for (int j=0; j<=7; j++){
        // TODO repaint for black
      }
    }
  }

  public void changeGUISettings() {
    for (int i=0; i<=7; i++){
      for (int j=0; j<=7; j++){
        Button rect = boardSquares[i][j];
        if ((i+j)%2 == 0) {
          rect.setStyle("-fx-background-color: " + GUIConfig.colorToHexString(GUIConfig.WHITE_SQUARE_COLOR));
        } else {
          rect.setStyle("-fx-background-color: " + GUIConfig.colorToHexString(GUIConfig.BLACK_SQUARE_COLOR));
        }
      }
    }
  }

  public void activateButton(Position pos){
    boardSquares[pos.x][pos.y].setDisable(false);
  }

  public void disableAllButtons(){
    for (int i=0; i<=7; i++){
      for (int j=0; j<=7; j++){
        boardSquares[i][j].setDisable(true);
      }
    }
  }

  public void handleButtonPress(){
    Board board = GameEngine.getCurrentBoard();
    // TODO fix logic
    board.move(new Position(0,0), new Position(3,3));
    repaint(board.getBoard());
    System.out.println(board.toString());
  }
}
