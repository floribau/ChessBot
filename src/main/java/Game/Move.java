package Game;

import Util.Position;

public class Move {
  private String move = null;
  private final Position oldPos;
  private final Position newPos;


  public Move(Position oldPos, Position newPos) {
    this.oldPos = oldPos;
    this.newPos = newPos;

    Piece piece = GameEngine.getCurrentBoard().getPieceById(GameEngine.getCurrentBoard().getPieceAt(oldPos));
    PlayerColor color = piece.getColor();
    String pieceIdAtDestination = GameEngine.getCurrentBoard().getPieceAt(newPos);

    // Castle notation
    if(piece.getType() == PieceType.KING_WHITE || piece.getType() == PieceType.KING_BLACK){
      if(newPos.row == oldPos.row){
        if(newPos.col == oldPos.col + 2) {
          this.move = "O-O";
        }
        else if(newPos.col == oldPos.col -2) {
          this.move = "O-O-O";
        }
      }
    }

    // normal move notation
    if (this.move == null) {
      String moveStr = piece.getType().getShortName() + "";
      moveStr += oldPos.getChessCoordinate();
      moveStr += newPos.getChessCoordinate();
      this.move = moveStr;
    }
  }

  public Position getOldPosition(){
    return this.oldPos;
  }

  public Position getNewPosition(){
    return this.newPos;
  }

  public String getMoveString(){
    return this.move;
  }

  public boolean equals(Move m) {
    return move.equals(m.move);
  }

}
