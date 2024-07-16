package Game;

import GUI.GUIConfig;
import javafx.scene.image.Image;

public enum PieceType {
  KING_WHITE("KING_WHITE", GUIConfig.KING_WHITE, 'K', Integer.MAX_VALUE),
  KING_BLACK("KING_BLACK", GUIConfig.KING_BLACK, 'K', Integer.MAX_VALUE),
  QUEEN_WHITE("QUEEN_WHITE", GUIConfig.QUEEN_WHITE, 'Q', 9),
  QUEEN_BLACK("QUEEN_BLACK", GUIConfig.QUEEN_BLACK, 'Q', 9),
  ROOK_WHITE("ROOK_WHITE", GUIConfig.ROOK_WHITE, 'R', 5),
  ROOK_BLACK("ROOK_BLACK", GUIConfig.ROOK_BLACK, 'R', 5),
  BISHOP_WHITE("BISHOP_WHITE", GUIConfig.BISHOP_WHITE, 'B', 3),
  BISHOP_BLACK("BISHOP_BLACK", GUIConfig.BISHOP_BLACK, 'B', 3),
  KNIGHT_WHITE("KNIGHT_WHITE", GUIConfig.KNIGHT_WHITE, 'N', 3),
  KNIGHT_BLACK("KNIGHT_BLACK", GUIConfig.KNIGHT_BLACK, 'N', 3),
  PAWN_WHITE("PAWN_WHITE", GUIConfig.PAWN_WHITE, 'P', 1),
  PAWN_BLACK("PAWN_BLACK", GUIConfig.PAWN_BLACK, 'P', 1);

  private final String displayName;
  private final Image image;
  private final char shortName;
  private final int value;

  PieceType(String displayName, Image image, char shortName, int value) {
    this.displayName = displayName;
    this.image = image;
    this.shortName = shortName;
    this.value = value;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Image getImage() {
    return image;
  }

  public char getShortName() {
    return shortName;
  }

  public int getValue() {
    return value;
  }
}

