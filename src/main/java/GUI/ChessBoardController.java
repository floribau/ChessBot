package GUI;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


public class ChessBoardController {

  @FXML
  private Rectangle rect00;
  @FXML
  private Rectangle rect01;
  @FXML
  private Rectangle rect02;
  @FXML
  private Rectangle rect03;
  @FXML
  private Rectangle rect04;
  @FXML
  private Rectangle rect05;
  @FXML
  private Rectangle rect06;
  @FXML
  private Rectangle rect07;
  @FXML
  private Rectangle rect10;
  @FXML
  private Rectangle rect11;
  @FXML
  private Rectangle rect12;
  @FXML
  private Rectangle rect13;
  @FXML
  private Rectangle rect14;
  @FXML
  private Rectangle rect15;
  @FXML
  private Rectangle rect16;
  @FXML
  private Rectangle rect17;
  @FXML
  private Rectangle rect20;
  @FXML
  private Rectangle rect21;
  @FXML
  private Rectangle rect22;
  @FXML
  private Rectangle rect23;
  @FXML
  private Rectangle rect24;
  @FXML
  private Rectangle rect25;
  @FXML
  private Rectangle rect26;
  @FXML
  private Rectangle rect27;
  @FXML
  private Rectangle rect30;
  @FXML
  private Rectangle rect31;
  @FXML
  private Rectangle rect32;
  @FXML
  private Rectangle rect33;
  @FXML
  private Rectangle rect34;
  @FXML
  private Rectangle rect35;
  @FXML
  private Rectangle rect36;
  @FXML
  private Rectangle rect37;
  @FXML
  private Rectangle rect40;
  @FXML
  private Rectangle rect41;
  @FXML
  private Rectangle rect42;
  @FXML
  private Rectangle rect43;
  @FXML
  private Rectangle rect44;
  @FXML
  private Rectangle rect45;
  @FXML
  private Rectangle rect46;
  @FXML
  private Rectangle rect47;
  @FXML
  private Rectangle rect50;
  @FXML
  private Rectangle rect51;
  @FXML
  private Rectangle rect52;
  @FXML
  private Rectangle rect53;
  @FXML
  private Rectangle rect54;
  @FXML
  private Rectangle rect55;
  @FXML
  private Rectangle rect56;
  @FXML
  private Rectangle rect57;
  @FXML
  private Rectangle rect60;
  @FXML
  private Rectangle rect61;
  @FXML
  private Rectangle rect62;
  @FXML
  private Rectangle rect63;
  @FXML
  private Rectangle rect64;
  @FXML
  private Rectangle rect65;
  @FXML
  private Rectangle rect66;
  @FXML
  private Rectangle rect67;
  @FXML
  private Rectangle rect70;
  @FXML
  private Rectangle rect71;
  @FXML
  private Rectangle rect72;
  @FXML
  private Rectangle rect73;
  @FXML
  private Rectangle rect74;
  @FXML
  private Rectangle rect75;
  @FXML
  private Rectangle rect76;
  @FXML
  private Rectangle rect77;
  private Rectangle[][] boardSquares;
  @FXML
  private ImageView image00;


  @FXML
  public void initialize() {
    boardSquares = new Rectangle[][]{
        {rect00, rect01, rect02, rect03, rect04, rect05, rect06, rect07},
        {rect10, rect11, rect12, rect13, rect14, rect15, rect16, rect17},
        {rect20, rect21, rect22, rect23, rect24, rect25, rect26, rect27},
        {rect30, rect31, rect32, rect33, rect34, rect35, rect36, rect37},
        {rect40, rect41, rect42, rect43, rect44, rect45, rect46, rect47},
        {rect50, rect51, rect52, rect53, rect54, rect55, rect56, rect57},
        {rect60, rect61, rect62, rect63, rect64, rect65, rect66, rect67},
        {rect70, rect71, rect72, rect73, rect74, rect75, rect76, rect77},
    };
    repaint();
  }


  @FXML
  public void repaint() {
    for (int i=0; i<=7; i++){
      for (int j=0; j<=7; j++){
        Rectangle rect = boardSquares[i][j];
        System.out.println(rect);
        if ((i+j)%2 == 0) {
          rect.setFill(GUIConfig.WHITE_SQUARE_COLOR);
        } else {
          rect.setFill(GUIConfig.BLACK_SQUARE_COLOR);
        }
      }
    }
    // Image image = new Image(GUIConfig.KING_WHITE); // Replace with your image URL
    image00.setImage(GUIConfig.KING_WHITE);
  }
}
