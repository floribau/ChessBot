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

  @Override
  public String toString(){
    return getChessCoordinate();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position p = (Position) o;
    return this.row == p.row && this.col == p.col;
  }

  public String getChessCoordinate(){
    char colChar = (char) (col + 97);
    int rowInt = 8 - row;
    return "" + colChar + rowInt;
  }

  public int getDistance(Position pos) {
    // Chebyshev distance
    return Math.max(Math.abs(this.row - pos.row), Math.abs(this.col - pos.col));
  }

  public static boolean isOnBoard(int row, int col) {
    return row >= 0 && row <= 7 && col >= 0 && col <= 7;
  }

  public static int calcCenterDistance(Position pos) {
    Position[] center = {new Position(3, 3), new Position(3, 4), new Position(4, 3), new Position(4, 4)};
    int minDist = Integer.MAX_VALUE;
    for (Position centerPos : center) {
      minDist = Math.min(minDist, centerPos.getDistance(pos));
    }
    return minDist;
  }

}
