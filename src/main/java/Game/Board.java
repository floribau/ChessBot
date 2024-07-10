package Game;

import Util.Position;
import java.util.ArrayList;
import java.util.List;

public class Board {
  String[][] board;
  private List<Piece> pieces;

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
  
  public void initBoard(){
    pieces = new ArrayList<>();

    Piece king_white = new Piece(PieceType.KING_WHITE, PlayerColor.WHITE);
    Piece queen_white = new Piece(PieceType.QUEEN_WHITE, PlayerColor.WHITE);
    Piece rook_white_0 = new Piece(PieceType.ROOK_WHITE, PlayerColor.WHITE);
    Piece rook_white_1 = new Piece(PieceType.ROOK_WHITE, PlayerColor.WHITE);
    Piece bishop_white_0 = new Piece(PieceType.BISHOP_WHITE, PlayerColor.WHITE);
    Piece bishop_white_1 = new Piece(PieceType.BISHOP_WHITE, PlayerColor.WHITE);
    Piece knight_white_0 = new Piece(PieceType.KNIGHT_WHITE, PlayerColor.WHITE);
    Piece knight_white_1 = new Piece(PieceType.KNIGHT_WHITE, PlayerColor.WHITE);
    Piece pawn_white_0 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_1 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_2 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_3 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_4 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_5 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_6 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_7 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);

    pieces.add(king_white);
    pieces.add(queen_white);
    pieces.add(rook_white_0);
    pieces.add(rook_white_1);
    pieces.add(bishop_white_0);
    pieces.add(bishop_white_1);
    pieces.add(knight_white_0);
    pieces.add(knight_white_1);
    pieces.add(pawn_white_0);
    pieces.add(pawn_white_1);
    pieces.add(pawn_white_2);
    pieces.add(pawn_white_3);
    pieces.add(pawn_white_4);
    pieces.add(pawn_white_5);
    pieces.add(pawn_white_6);
    pieces.add(pawn_white_7);

    Piece king_black = new Piece(PieceType.KING_BLACK, PlayerColor.BLACK);
    Piece queen_black = new Piece(PieceType.QUEEN_BLACK, PlayerColor.BLACK);
    Piece rook_black_0 = new Piece(PieceType.ROOK_BLACK, PlayerColor.BLACK);
    Piece rook_black_1 = new Piece(PieceType.ROOK_BLACK, PlayerColor.BLACK);
    Piece bishop_black_0 = new Piece(PieceType.BISHOP_BLACK, PlayerColor.BLACK);
    Piece bishop_black_1 = new Piece(PieceType.BISHOP_BLACK, PlayerColor.BLACK);
    Piece knight_black_0 = new Piece(PieceType.KNIGHT_BLACK, PlayerColor.BLACK);
    Piece knight_black_1 = new Piece(PieceType.KNIGHT_BLACK, PlayerColor.BLACK);
    Piece pawn_black_0 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_1 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_2 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_3 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_4 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_5 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_6 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_7 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);

    pieces.add(king_black);
    pieces.add(queen_black);
    pieces.add(rook_black_0);
    pieces.add(rook_black_1);
    pieces.add(bishop_black_0);
    pieces.add(bishop_black_1);
    pieces.add(knight_black_0);
    pieces.add(knight_black_1);
    pieces.add(pawn_black_0);
    pieces.add(pawn_black_1);
    pieces.add(pawn_black_2);
    pieces.add(pawn_black_3);
    pieces.add(pawn_black_4);
    pieces.add(pawn_black_5);
    pieces.add(pawn_black_6);
    pieces.add(pawn_black_7);

    this.setPieceAt(new Position(0,0), rook_black_0.getId());
    this.setPieceAt(new Position(0, 1), knight_black_0.getId());
    this.setPieceAt(new Position(0, 2), bishop_black_0.getId());
    this.setPieceAt(new Position(0, 3), queen_black.getId());
    this.setPieceAt(new Position(0, 4), king_black.getId());
    this.setPieceAt(new Position(0, 5), bishop_black_1.getId());
    this.setPieceAt(new Position(0, 6), knight_black_1.getId());
    this.setPieceAt(new Position(0, 7), rook_black_1.getId());

    this.setPieceAt(new Position(1, 0), pawn_black_0.getId());
    this.setPieceAt(new Position(1, 1), pawn_black_1.getId());
    this.setPieceAt(new Position(1, 2), pawn_black_2.getId());
    this.setPieceAt(new Position(1, 3), pawn_black_3.getId());
    this.setPieceAt(new Position(1, 4), pawn_black_4.getId());
    this.setPieceAt(new Position(1, 5), pawn_black_5.getId());
    this.setPieceAt(new Position(1, 6), pawn_black_6.getId());
    this.setPieceAt(new Position(1, 7), pawn_black_7.getId());

    this.setPieceAt(new Position(7, 0), rook_white_0.getId());
    this.setPieceAt(new Position(7, 1), knight_white_0.getId());
    this.setPieceAt(new Position(7, 2), bishop_white_0.getId());
    this.setPieceAt(new Position(7, 3), queen_white.getId());
    this.setPieceAt(new Position(7, 4), king_white.getId());
    this.setPieceAt(new Position(7, 5), bishop_white_1.getId());
    this.setPieceAt(new Position(7, 6), knight_white_1.getId());
    this.setPieceAt(new Position(7, 7), rook_white_1.getId());

    this.setPieceAt(new Position(6, 0), pawn_white_0.getId());
    this.setPieceAt(new Position(6, 1), pawn_white_1.getId());
    this.setPieceAt(new Position(6, 2), pawn_white_2.getId());
    this.setPieceAt(new Position(6, 3), pawn_white_3.getId());
    this.setPieceAt(new Position(6, 4), pawn_white_4.getId());
    this.setPieceAt(new Position(6, 5), pawn_white_5.getId());
    this.setPieceAt(new Position(6, 6), pawn_white_6.getId());
    this.setPieceAt(new Position(6, 7), pawn_white_7.getId());
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

  public List<Piece> getPieces() {
    return pieces;
  }
}
