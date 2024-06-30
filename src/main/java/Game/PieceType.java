package Game;

import GUI.GUIConfig;
import javafx.scene.image.Image;

public enum PieceType {
  KING_WHITE("KING_WHITE", GUIConfig.KING_WHITE),
  KING_BLACK("KING_BLACK", GUIConfig.KING_BLACK),
  QUEEN_WHITE("QUEEN_WHITE", GUIConfig.QUEEN_WHITE),
  QUEEN_BLACK("QUEEN_BLACK", GUIConfig.QUEEN_BLACK),
  ROOK_WHITE("ROOK_WHITE", GUIConfig.ROOK_WHITE),
  ROOK_BLACK("ROOK_BLACK", GUIConfig.ROOK_BLACK),
  BISHOP_WHITE("BISHOP_WHITE", GUIConfig.BISHOP_WHITE),
  BISHOP_BLACK("BISHOP_BLACK", GUIConfig.BISHOP_BLACK),
  KNIGHT_WHITE("KNIGHT_WHITE", GUIConfig.KNIGHT_WHITE),
  KNIGHT_BLACK("KNIGHT_BLACK", GUIConfig.KNIGHT_BLACK),
  PAWN_WHITE("PAWN_WHITE", GUIConfig.PAWN_WHITE),
  PAWN_BLACK("PAWN_BLACK", GUIConfig.PAWN_BLACK);

  private final String displayName;
  private final Image image;

  PieceType(String displayName, Image image) {
    this.displayName = displayName;
    this.image = image;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Image getImage() {
    return image;
  }
}

