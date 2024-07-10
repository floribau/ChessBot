package Game;

import Util.Position;

public class Move {
  private String moveString = null;
  private final Position oldPos;
  private final Position newPos;
  private final Piece movedPiece;

  public Move(Position oldPos, Position newPos, Piece movedPiece) {
    this.oldPos = oldPos;
    this.newPos = newPos;
    this.movedPiece = movedPiece;

    PlayerColor color = movedPiece.getColor();
    String pieceIdAtDestination = GameEngine.getCurrentBoard().getPieceAt(newPos);

    // Castle notation
    if(movedPiece.getType() == PieceType.KING_WHITE || movedPiece.getType() == PieceType.KING_BLACK){
      if(newPos.row == oldPos.row){
        if(newPos.col == oldPos.col + 2) {
          this.moveString = "O-O";
        }
        else if(newPos.col == oldPos.col -2) {
          this.moveString = "O-O-O";
        }
      }
    }

    // normal move notation
    if (this.moveString == null) {
      String moveStr = movedPiece.getType().getShortName() + "";
      moveStr += oldPos.getChessCoordinate();
      moveStr += newPos.getChessCoordinate();
      this.moveString = moveStr;
    }
  }

  public Position getOldPosition(){
    return this.oldPos;
  }

  public Position getNewPosition(){
    return this.newPos;
  }

  public String getMoveString(){
    return this.moveString;
  }

  public Piece getMovedPiece(){
    return this.movedPiece;
  }

  public boolean equals(Move m) {
    return moveString.equals(m.moveString);
  }

  public String toString(){
    return this.moveString;
  }

}
