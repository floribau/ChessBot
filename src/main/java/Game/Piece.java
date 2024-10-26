package Game;

import GUI.GUIConfig;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class Piece {

  public static final Map<PieceTuple, Integer> pieceCounters = new HashMap<>();
  private final String id;
  private final PieceType type;
  private final PlayerColor color;
  private boolean hasMoved;
  private boolean enPassantPossible;

  public Piece(PieceType type, PlayerColor color) {
    this.id = type.getDisplayName() + "_" + color + getNextId(type, color);
    this.type = type;
    this.color = color;
    this.hasMoved = false;
    this.enPassantPossible = false;
  }

  private Piece(Piece piece) {
    this.id = piece.id;
    this.type = piece.type;
    this.color = piece.color;
    this.hasMoved = piece.hasMoved;
    this.enPassantPossible = piece.enPassantPossible;
  }

  private synchronized static int getNextId(PieceType type, PlayerColor color) {
    PieceTuple tuple = new PieceTuple(type, color);
    int nextId = pieceCounters.getOrDefault(tuple, 0);
    pieceCounters.put(tuple, nextId + 1);
    return nextId;
  }

  /**
   * Provides a deep copy of the Piece instance
   */
  public Piece copyPiece() {
    return new Piece(this);
  }

  public String getId() {
    return this.id;
  }

  public PieceType getType() {
    return this.type;
  }

  public boolean isType(PieceType type) {
    return this.type == type;
  }

  public PlayerColor getColor() {
    return this.color;
  }

  public void setHasMoved() {
    hasMoved = true;
  }

  public boolean hasMoved() {
    return this.hasMoved;
  }

  public void setEnPassantPossible() {
    this.enPassantPossible = true;
  }

  public void resetEnPassantPossible() {
    this.enPassantPossible = false;
  }

  public boolean isEnPassantPossible() {
    return this.enPassantPossible;
  }

  public int getValue() {
    return this.type.getValue();
  }

  public Image getImage() {
    Image img = null;

    if (color.equals(PlayerColor.WHITE)) {
      switch (type) {
        case KING -> img = GUIConfig.KING_WHITE;
        case QUEEN -> img = GUIConfig.QUEEN_WHITE;
        case ROOK -> img = GUIConfig.ROOK_WHITE;
        case KNIGHT -> img = GUIConfig.KNIGHT_WHITE;
        case BISHOP -> img = GUIConfig.BISHOP_WHITE;
        case PAWN -> img = GUIConfig.PAWN_WHITE;
      }
    } else {
      switch (type) {
        case KING -> img = GUIConfig.KING_BLACK;
        case QUEEN -> img = GUIConfig.QUEEN_BLACK;
        case ROOK -> img = GUIConfig.ROOK_BLACK;
        case KNIGHT -> img = GUIConfig.KNIGHT_BLACK;
        case BISHOP -> img = GUIConfig.BISHOP_BLACK;
        case PAWN -> img = GUIConfig.PAWN_BLACK;
      }
    }
    return img;
  }

  public boolean isFriendlyPawn(PlayerColor color) {
    return type == PieceType.PAWN && this.color == color;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Piece p = (Piece) o;
    return id.equals(p.id);
  }

}
