package Util;

public class Position {
  public final int row;
  public final int col;

  public Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public Position(String chessCoordinate){
    col = chessCoordinate.charAt(0)-97;
    row = 8 - Character.getNumericValue(chessCoordinate.charAt(1));
  }

  public String toString(){
    return getChessCoordinate();
  }

  public boolean equals(Position p) {
    return this.row == p.row && this.col == p.col;
  }

  public String getChessCoordinate(){
    char colChar = (char) (col + 97);
    int rowInt = 8 - row;
    return "" + colChar + rowInt;
  }

  public static boolean isOnBoard(int row, int col) {
    return row >= 0 && row <= 7 && col >= 0 && col <= 7;
  }

}
