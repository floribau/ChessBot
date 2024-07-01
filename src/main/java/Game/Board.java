package Game;

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

  public void move(int xOld, int xNew, int yOld, int yNew){
    String pieceId = this.getPieceAt(xOld, yOld);
    this.setPieceAt(xOld, yOld, "");
    this.setPieceAt(xNew, yNew, pieceId);
  }

  public String[][] getBoard(){
    return this.board;
  }

  public String getPieceAt(int xPos, int yPos) {
    return board[xPos][yPos];
  }

  public void setPieceAt(int xPos, int yPos, String pieceId) {
    board[xPos][yPos] = pieceId;
  }

}
