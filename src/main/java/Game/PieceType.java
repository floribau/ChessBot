package Game;

import GUI.GUIConfig;
import javafx.scene.image.Image;

public enum PieceType {
  KING_WHITE("KING_WHITE", GUIConfig.KING_WHITE, 'K'),
  KING_BLACK("KING_BLACK", GUIConfig.KING_BLACK, 'K'),
  QUEEN_WHITE("QUEEN_WHITE", GUIConfig.QUEEN_WHITE, 'Q'),
  QUEEN_BLACK("QUEEN_BLACK", GUIConfig.QUEEN_BLACK, 'Q'),
  ROOK_WHITE("ROOK_WHITE", GUIConfig.ROOK_WHITE, 'R'),
  ROOK_BLACK("ROOK_BLACK", GUIConfig.ROOK_BLACK, 'R'),
  BISHOP_WHITE("BISHOP_WHITE", GUIConfig.BISHOP_WHITE, 'B'),
  BISHOP_BLACK("BISHOP_BLACK", GUIConfig.BISHOP_BLACK, 'B'),
  KNIGHT_WHITE("KNIGHT_WHITE", GUIConfig.KNIGHT_WHITE, 'N'),
  KNIGHT_BLACK("KNIGHT_BLACK", GUIConfig.KNIGHT_BLACK, 'N'),
  PAWN_WHITE("PAWN_WHITE", GUIConfig.PAWN_WHITE, 'P'),
  PAWN_BLACK("PAWN_BLACK", GUIConfig.PAWN_BLACK, 'P');

  private final String displayName;
  private final Image image;
  private final char shortName;

  PieceType(String displayName, Image image, char shortName) {
    this.displayName = displayName;
    this.image = image;
    this.shortName = shortName;
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
}

