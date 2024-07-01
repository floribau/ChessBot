package Game;

import java.util.HashMap;
import java.util.Map;

public class Piece {
  private static final Map<PieceType, Integer> pieceCounters = new HashMap<>();
  private final String id;
  private final PieceType type;
  private final PlayerColor color;

  public Piece(PieceType type, PlayerColor color){
    this.id = type.getDisplayName() + getNextId(type);
    this.type = type;
    this.color = color;
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

}
