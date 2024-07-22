package Game;

import Util.Position;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Move {
  private final String moveString;
  private final Position oldPos;
  private final Position newPos;
  private final Piece movedPiece;
  private final boolean enPassant;

  public Move(Position oldPos, Position newPos, Piece movedPiece, boolean enPassant) {
    this.oldPos = oldPos;
    this.newPos = newPos;
    this.movedPiece = movedPiece;
    this.moveString = Move.calcMoveString(oldPos, newPos, movedPiece);
    this.enPassant = enPassant;
  }

  public Move(String moveString, Piece movedPiece, boolean enPassant) {
    this.moveString = moveString;
    this.movedPiece = movedPiece;
    this.enPassant = enPassant;

    String coordinatePattern = "([a-h][1-8]).*?([a-h][1-8])";
    Pattern pattern = Pattern.compile(coordinatePattern);
    Matcher matcher = pattern.matcher(this.moveString);
    if (matcher.find()) {
      String oldCoordinate = matcher.group(1);
      String newCoordinate = matcher.group(2);
      this.oldPos = new Position(oldCoordinate);
      this.newPos = new Position(newCoordinate);
    } else {
      this.oldPos = null;
      this.newPos = null;
    }
  }

  public Move(Position oldPos, Position newPos, Piece movedPiece) {
    this(oldPos, newPos, movedPiece, false);
  }

  public Move(String moveString, Piece movedPiece) {
    this(moveString, movedPiece, false);
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

  public String toString(){
    return this.moveString;
  }

  /**
   * calculates the string notation for a specified move. The string doesn't include promotions or captures
   * @param oldPos - the original piece position
   * @param newPos - the position to which the piece will be moved
   * @param movedPiece - the piece that is moved
   * @return moveString corresponding to the move specified by the parameters
   */
  public static String calcMoveString(Position oldPos, Position newPos, Piece movedPiece){
    // castle notation
    if(movedPiece.getType() == PieceType.KING){
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

  public boolean isPawnMove(){
    return movedPiece.getType().equals(PieceType.PAWN);
  }

  public boolean isPromotion(){
    return isPawnMove() && moveString.contains("=");
  }

  public boolean isCastle(){
    return moveString.equals("O-O") || moveString.equals("O-O-O");
  }

  public boolean isEnPassant(){
    return this.enPassant;
  }

  public String getPromotionPiece() {
    if (!isPromotion()) {
      return null;
    }
    String symbolPattern = "=([^=])";
    Pattern pattern = Pattern.compile(symbolPattern);
    Matcher matcher = pattern.matcher(moveString);
    if (matcher.find()) {
      return matcher.group(1);
    }
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Move m = (Move) o;
    return moveString.equals(m.moveString);
  }

}
