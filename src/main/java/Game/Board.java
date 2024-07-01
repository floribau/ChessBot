package Game;

import Util.Position;

public class Board {
  String[][] board;

  public Board(){
    this.board = new String[8][8];
  }

  public Board(String[][] board){
    this.board = new String[8][8];
    for(int i=0; i<=7; i++) {
      for(int j=0; j<=7; j++) {
        System.out.println(board[i][j]);
        this.board[i][j] = board[i][j];
      }
    }
  }

  public void move(Position oldPos, Position newPos){
    String pieceId = this.getPieceAt(oldPos);
    this.setPieceAt(oldPos, "");
    this.setPieceAt(newPos, pieceId);
  }

  public String[][] getBoard(){
    return this.board;
  }

  public String getPieceAt(Position pos) {
    return board[pos.x][pos.y];
  }

  public void setPieceAt(Position pos, String pieceId) {
    board[pos.x][pos.y] = pieceId;
  }

  public Position getPositionOfPiece(String pieceId) {
    for(int i=0; i<=7; i++) {
      for(int j=0; j<=7; j++) {
        if(board[i][j] == pieceId) {
          return new Position(i, j);
        }
      }
    }
    return null;
  }

  public String toString(){
    String res = "";
    for(int i=0; i<=7; i++) {
      for(int j=0; j<=7; j++) {
        res += "[" + board[i][j] + "]";
      }
      res += "\n";
    }
    return res;
  }

}
