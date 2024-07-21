package Game;

import GUI.GUIConfig;
import javafx.scene.image.Image;

public enum PieceType {
  KING("KING", 'K', 0),
  QUEEN("QUEEN", 'Q', 9),
  ROOK("ROOK", 'R', 5),
  BISHOP("BISHOP",'B', 3),
  KNIGHT("KNIGHT",'N', 3),
  PAWN("PAWN", 'P', 1);

  private final String displayName;
  private final char shortName;
  private final int value;

  PieceType(String displayName, char shortName, int value) {
    this.displayName = displayName;
    this.shortName = shortName;
    this.value = value;
  }

  public String getDisplayName() {
    return displayName;
  }

  public char getShortName() {
    return shortName;
  }

  public int getValue() {
    return value;
  }
}

