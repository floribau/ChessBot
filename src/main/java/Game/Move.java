package Game;

import Util.Position;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Move {
  private String moveString = null;
  private final Position oldPos;
  private final Position newPos;
  private final Piece movedPiece;

  public Move(Position oldPos, Position newPos, Piece movedPiece) {
    this.oldPos = oldPos;
    this.newPos = newPos;
    this.movedPiece = movedPiece;
    this.moveString = Move.calcMoveString(oldPos, newPos, movedPiece);
  }

  public Move(String moveString, Piece movedPiece) {
    this.moveString = moveString;
    this.movedPiece = movedPiece;

    String coordinatePattern = "([a-h][1-8]).*?([a-h][1-8])";
    Pattern pattern = Pattern.compile(coordinatePattern);
    Matcher matcher = pattern.matcher(this.moveString);
    if (matcher.find()) {
      String oldCoordinate = matcher.group(1);
      String newCoordinate = matcher.group(2);
      this.oldPos = new Position(oldCoordinate);
      this.newPos = new Position(newCoordinate);
      System.out.println();
    } else {
      System.out.println("No coordinates found in the notation.");
      this.oldPos = null;
      this.newPos = null;
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

  /**
   * calculates the string notation for a specified move. The string doesn't include promotions or captures
   * @param oldPos
   * @param newPos
   * @param movedPiece
   * @return
   */
  public static String calcMoveString(Position oldPos, Position newPos, Piece movedPiece){
    // castle notation
    if(movedPiece.getType() == PieceType.KING_WHITE || movedPiece.getType() == PieceType.KING_BLACK){
      if(newPos.row == oldPos.row){
        if(newPos.col == oldPos.col + 2) {
          return "O-O";
        }
        else if(newPos.col == oldPos.col -2) {
          return "O-O-O";
        }
      }
    }
    // normal move notation
    String moveStr = movedPiece.getType().getShortName() + "";
    moveStr += oldPos.getChessCoordinate();
    moveStr += newPos.getChessCoordinate();
    return moveStr;
  }

  public boolean isMultiSquareMove(){
    return Math.abs(oldPos.col - newPos.col) >= 2 || Math.abs(oldPos.row - newPos.row) >= 2;
  }

  public boolean isParallelMove(){
    return oldPos.row == newPos.row || oldPos.col == newPos.col;
  }

  public boolean isDiagonalMove(){
    return Math.abs(oldPos.row - newPos.row) == Math.abs(oldPos.col - newPos.col);
  }

  public boolean isKnightMove(){
    return movedPiece.getType().equals(PieceType.KNIGHT_WHITE) || movedPiece.getType().equals(PieceType.KING_BLACK);
  }

  public boolean isPawnMove(){
    return movedPiece.getType().equals(PieceType.PAWN_WHITE) || movedPiece.getType().equals(PieceType.PAWN_BLACK);
  }

  public boolean isPromotion(){
    return isPawnMove() && moveString.contains("=");
  }

}
