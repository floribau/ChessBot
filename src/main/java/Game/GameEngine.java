package Game;

import GUI.ChessBoardController;
import Util.Position;
import java.util.List;
import java.util.ArrayList;

public class GameEngine {
  private static Board currentBoard;
  private static List<Piece> pieces;
  private static PlayerColor humanPlayerColor;
  private static PlayerColor currentPlayerColor;
  private static ChessBoardController controller;

  public synchronized static void initBoard(){
    pieces = new ArrayList<>();
    currentBoard = new Board();

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

    currentBoard.setPieceAt(new Position(0,0), rook_black_0.getId());
    currentBoard.setPieceAt(new Position(0, 1), knight_black_0.getId());
    currentBoard.setPieceAt(new Position(0, 2), bishop_black_0.getId());
    currentBoard.setPieceAt(new Position(0, 3), queen_black.getId());
    currentBoard.setPieceAt(new Position(0, 4), king_black.getId());
    currentBoard.setPieceAt(new Position(0, 5), bishop_black_1.getId());
    currentBoard.setPieceAt(new Position(0, 6), knight_black_1.getId());
    currentBoard.setPieceAt(new Position(0, 7), rook_black_1.getId());

    currentBoard.setPieceAt(new Position(1, 0), pawn_black_0.getId());
    currentBoard.setPieceAt(new Position(1, 1), pawn_black_1.getId());
    currentBoard.setPieceAt(new Position(1, 2), pawn_black_2.getId());
    currentBoard.setPieceAt(new Position(1, 3), pawn_black_3.getId());
    currentBoard.setPieceAt(new Position(1, 4), pawn_black_4.getId());
    currentBoard.setPieceAt(new Position(1, 5), pawn_black_5.getId());
    currentBoard.setPieceAt(new Position(1, 6), pawn_black_6.getId());
    currentBoard.setPieceAt(new Position(1, 7), pawn_black_7.getId());

    currentBoard.setPieceAt(new Position(7, 0), rook_white_0.getId());
    currentBoard.setPieceAt(new Position(7, 1), knight_white_0.getId());
    currentBoard.setPieceAt(new Position(7, 2), bishop_white_0.getId());
    currentBoard.setPieceAt(new Position(7, 3), queen_white.getId());
    currentBoard.setPieceAt(new Position(7, 4), king_white.getId());
    currentBoard.setPieceAt(new Position(7, 5), bishop_white_1.getId());
    currentBoard.setPieceAt(new Position(7, 6), knight_white_1.getId());
    currentBoard.setPieceAt(new Position(7, 7), rook_white_1.getId());

    currentBoard.setPieceAt(new Position(6, 0), pawn_white_0.getId());
    currentBoard.setPieceAt(new Position(6, 1), pawn_white_1.getId());
    currentBoard.setPieceAt(new Position(6, 2), pawn_white_2.getId());
    currentBoard.setPieceAt(new Position(6, 3), pawn_white_3.getId());
    currentBoard.setPieceAt(new Position(6, 4), pawn_white_4.getId());
    currentBoard.setPieceAt(new Position(6, 5), pawn_white_5.getId());
    currentBoard.setPieceAt(new Position(6, 6), pawn_white_6.getId());
    currentBoard.setPieceAt(new Position(6, 7), pawn_white_7.getId());

    controller.disableAllButtons();
    if(currentPlayerColor == humanPlayerColor) {
      activateHumanButtons();
    }
    controller.repaint(currentBoard.getBoard());
  }

  public static void startGame(ChessBoardController controller){
   humanPlayerColor = Math.random() >= 0.5 ? PlayerColor.WHITE : PlayerColor.BLACK;
   startGame(humanPlayerColor, controller);
  }

  public synchronized static void startGame(PlayerColor playerColor, ChessBoardController controller){
    GameEngine.humanPlayerColor = playerColor;
    GameEngine.currentPlayerColor = PlayerColor.WHITE;
    GameEngine.controller = controller;
    System.out.println("Controller at line 108: " + controller);
    initBoard();
  }

  public static Piece getPieceById(String id) {
    for(Piece piece : pieces){
      if(piece.getId() == id){
        return piece;
      }
    }
    return null;
  }

  public static PlayerColor getHumanPlayerColor(){
    return humanPlayerColor;
  }

  public static Board getCurrentBoard(){
    return currentBoard;
  }

  public static List<Board> calcPossibleMoves(PlayerColor player){
    return calcPossibleMoves(player, currentBoard);
  }

  public static List<Board> calcPossibleMoves(PlayerColor player, Board board){
    // TODO add check if king is checked
    List<Board> moves = new ArrayList<>();
    for(Piece piece : calcMovablePieces(player, board)){
      if (piece.getColor() == player){
        // TODO do something to calculate possible moves
        // use isMovePossible()
      }
    }
    return moves;
  }

  public static List<Piece> calcMovablePieces(PlayerColor player, Board board) {
    List<Piece> movablePieces = new ArrayList<>();
    for(Piece piece : pieces){
      if(piece.getColor() == player) {
        // TODO check if piece is movable
        movablePieces.add(piece);
      }
    }
    return movablePieces;
  }

  public static boolean isMovePossible(){
    return true;
  }

  public static void activateHumanButtons(){
    for(Piece piece : calcMovablePieces(PlayerColor.WHITE, currentBoard)){
      controller.activateButton(currentBoard.getPositionOfPiece(piece.getId()));
    }
  }

}
