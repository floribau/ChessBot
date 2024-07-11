package Game;

import java.util.HashMap;
import java.util.Map;

public class Piece {
  private static final Map<PieceType, Integer> pieceCounters = new HashMap<>();
  private final String id;
  private final PieceType type;
  private final PlayerColor color;
  private boolean hasMoved;

  public Piece(PieceType type, PlayerColor color){
    this.id = type.getDisplayName() + getNextId(type);
    this.type = type;
    this.color = color;
    this.hasMoved = false;
  }

  private Piece(Piece piece){
    this.id = piece.id;
    this.type = piece.type;
    this.color = piece.color;
    this.hasMoved = piece.hasMoved;
  }

  /**
   * Provides a deep copy of the Piece instance
   */
  public Piece copyPiece() {
    return new Piece(this);
  }

  private synchronized static int getNextId(PieceType type){
    int nextId = pieceCounters.getOrDefault(type, 0);
    pieceCounters.put(type, nextId + 1);
    return nextId;
  }

  public String getId(){
    return this.id;
  }

  public PieceType getType(){
    return this.type;
  }

  public PlayerColor getColor(){
    return this.color;
  }

  public void setHasMoved(){
    hasMoved = true;
  }

  public boolean hasMoved(){
    return this.hasMoved;
  }

}
