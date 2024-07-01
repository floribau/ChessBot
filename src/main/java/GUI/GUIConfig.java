package GUI;

import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class GUIConfig {
  public static Color WHITE_SQUARE_COLOR = Color.BEIGE;
  public static Color BLACK_SQUARE_COLOR = Color.SADDLEBROWN;

  public static Image KING_WHITE = new Image(ClassLoader.getSystemResource("images/king-white.png").toString());

  public static Image KING_BLACK = new Image(ClassLoader.getSystemResource("images/king-black.png").toString());
  public static Image QUEEN_WHITE = new Image(ClassLoader.getSystemResource("images/queen-white.png").toString());

  public static Image QUEEN_BLACK = new Image(ClassLoader.getSystemResource("images/queen-black.png").toString());
  public static Image ROOK_WHITE = new Image(ClassLoader.getSystemResource("images/rook-white.png").toString());
  public static Image ROOK_BLACK = new Image(ClassLoader.getSystemResource("images/rook-black.png").toString());
  public static Image BISHOP_WHITE = new Image(ClassLoader.getSystemResource("images/bishop-white.png").toString());
  public static Image BISHOP_BLACK = new Image(ClassLoader.getSystemResource("images/bishop-black.png").toString());
  public static Image KNIGHT_WHITE = new Image(ClassLoader.getSystemResource("images/knight-white.png").toString());
  public static Image KNIGHT_BLACK = new Image(ClassLoader.getSystemResource("images/knight-black.png").toString());
  public static Image PAWN_WHITE = new Image(ClassLoader.getSystemResource("images/pawn-white.png").toString());
  public static Image PAWN_BLACK = new Image(ClassLoader.getSystemResource("images/pawn-black.png").toString());

  public static String colorToHexString(Color color){
    int r = (int) (color.getRed() * 255);
    int g = (int) (color.getGreen() * 255);
    int b = (int) (color.getBlue() * 255);

    return String.format("#%02X%02X%02X", r, g, b);
  }
}
