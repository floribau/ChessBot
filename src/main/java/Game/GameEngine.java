package Game;

import AI.AIPlayer;
import GUI.ChessBoardController;
import Util.Position;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Platform;

public class GameEngine {
  private static Board currentBoard;
  private static Player player1;
  private static Player player2;
  private static Player currentPlayer;
  private static ChessBoardController controller;
  private static Thread gameThread;

  public synchronized static void startGame(ChessBoardController controller){
    // TODO implement logic to select game mode
    GameEngine.player1 = new Player(PlayerColor.WHITE, true);
    GameEngine.currentPlayer = player1;
    GameEngine.player2 = new AIPlayer(PlayerColor.BLACK);

    currentBoard = new Board();
    currentBoard.initBoard();

    GameEngine.controller = controller;
    controller.disableAllButtons();
    controller.repaint(currentBoard.getBoard());

    player1.setController();
    player2.setController();
    playGame();
  }

  public synchronized static void playGame() {
    gameThread = new Thread(() -> {
      while (!isGameOver()) {
        Move move = currentPlayer.makeMove();
        currentBoard.move(move);
        Platform.runLater(() -> controller.repaint(currentBoard.getBoard()));
        switchCurrentPlayer();
        try {
          Thread.sleep(100); // Adjust the sleep time as needed
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });
    gameThread.start();
  }

  public synchronized  static void switchCurrentPlayer(){
    if (currentPlayer == player1) {
      currentPlayer = player2;
    } else {
      currentPlayer = player1;
    }
  }

  public static Player getPlayer1(){
    return player1;
  }

  public static Player getPlayer2() {
    return player2;
  }

  public static Board getCurrentBoard(){
    return currentBoard;
  }

  public synchronized static List<Board> calcPossibleBoards(PlayerColor playerColor, Board board){
    List<Board> boards = new ArrayList<>();
    for(Move move : calcPossibleMoves(playerColor, board)){
      boards.add(calcMoveToBoard(board, move));
    }
    return boards;
  }

  public synchronized static List<Move> calcPossibleMoves(PlayerColor playerColor, Board board) {
    List<Move> moves = new ArrayList<>();
    for(Piece piece : calcMovablePieces(playerColor, board)){
      List<Move> pieceMoves = calcMovesForPiece(piece.getId(), board);
      for (Move move : pieceMoves) {
        moves.add(move);
      }
    }
    return moves;
  }

  public synchronized static Board calcMoveToBoard(Board board, Move move) {
    Board newBoard = new Board(board);
    newBoard.move(move);
    return newBoard;
  }

  public synchronized static List<Move> calcMovesForPiece(String pieceId, Board board){
    List<Move> moves = new ArrayList<>();
    for (Move m : calcMovesOnBoardForPiece(pieceId, board)){
      if(isLegalMove(m, board)){
        moves.add(m);
      }
    }
    return moves;
  }

  /***
   * Calculates all moves on board obeying the move patterns of the piece. Pieces in the way and checks are disregarded.
   * @param pieceId the piece to be moved
   * @param board the board instance on which the moves should be performed
   * @return the list of moves on board
   */
  private synchronized static List<Move> calcMovesOnBoardForPiece(String pieceId, Board board){
    List<Move> moves = new ArrayList<>();
    Position fromPos = board.getPositionOfPiece(pieceId);
    Piece piece = currentBoard.getPieceById(pieceId);
    if (piece.getType() == PieceType.KING_WHITE || piece.getType() == PieceType.KING_BLACK){
      int newRow = fromPos.row+1;
      int newCol = fromPos.col;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row-1;
      newCol = fromPos.col;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row;
      newCol = fromPos.col+1;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row;
      newCol = fromPos.col-1;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row+1;
      newCol = fromPos.col+1;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row+1;
      newCol = fromPos.col-1;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row-1;
      newCol = fromPos.col+1;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row-1;
      newCol = fromPos.col-1;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }

    } else if (piece.getType() == PieceType.QUEEN_WHITE || piece.getType() == PieceType.QUEEN_BLACK) {
      for(Move m : calcParallelMoves(fromPos)){
        moves.add(m);
      }
      for(Move m : calcDiagonalMoves(fromPos)) {
        moves.add(m);
      }

    } else if(piece.getType() == PieceType.ROOK_WHITE || piece.getType() == PieceType.ROOK_BLACK) {
      for(Move m : calcParallelMoves(fromPos)){
         moves.add(m);
      }

    } else if(piece.getType() == PieceType.BISHOP_WHITE || piece.getType() == PieceType.BISHOP_BLACK) {
      for(Move m : calcDiagonalMoves(fromPos)) {
        moves.add(m);
      }

    } else if(piece.getType() == PieceType.KNIGHT_WHITE || piece.getType() == PieceType.KNIGHT_BLACK) {
      int newRow = fromPos.row+2;
      int newCol = fromPos.col+1;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row+2;
      newCol = fromPos.col-1;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row-2;
      newCol = fromPos.col+1;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row-2;
      newCol = fromPos.col-1;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row+1;
      newCol = fromPos.col+2;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row-1;
      newCol = fromPos.col+2;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row+1;
      newCol = fromPos.col-2;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }
      newRow = fromPos.row-1;
      newCol = fromPos.col-2;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(fromPos, new Position(newRow, newCol)));
      }

    } else if(piece.getType() == PieceType.PAWN_WHITE) {
      moves.add(new Move(fromPos, new Position(fromPos.row-1, fromPos.col)));
      if(!piece.hasMoved()) {
        moves.add(new Move(fromPos, new Position(fromPos.row-2, fromPos.col)));
      }
      //captures
      Position leftCapture = new Position(fromPos.row-1, fromPos.col-1);
      Position rightCapture = new Position(fromPos.row-1, fromPos.col+1);
      if (Position.isOnBoard(leftCapture.row, leftCapture.col) && board.getPieceAt(leftCapture) != null){
        moves.add(new Move(fromPos, leftCapture));
      }
      if (Position.isOnBoard(rightCapture.row, rightCapture.col) && board.getPieceAt(rightCapture) != null){
        moves.add(new Move(fromPos, rightCapture));
      }
      // TODO implement en passant

    } else if(piece.getType() == PieceType.PAWN_BLACK) {
      moves.add(new Move(fromPos, new Position(fromPos.row+1, fromPos.col)));
      if(!piece.hasMoved()) {
        moves.add(new Move(fromPos, new Position(fromPos.row+2, fromPos.col)));
      }
      //captures
      Position leftCapture = new Position(fromPos.row+1, fromPos.col-1);
      Position rightCapture = new Position(fromPos.row+1, fromPos.col+1);
      if (Position.isOnBoard(leftCapture.row, leftCapture.col) && board.getPieceAt(leftCapture) != null){
        moves.add(new Move(fromPos, leftCapture));
      }
      if (Position.isOnBoard(rightCapture.row, rightCapture.col) && board.getPieceAt(rightCapture) != null){
        moves.add(new Move(fromPos, rightCapture));
      }
      // TODO implement en passant

    } else {
      // some error occurred
    }
    return moves;
  }

  /**
   * Calculates all parallel moves from the starting position that are still on the board
   *
   * @param pos the starting position
   * @return the list of all parallel moves that are still on the board
   */
  private synchronized static List<Move> calcParallelMoves(Position pos) {
    int col = pos.col;
    int row = pos.row;
    List<Move> moves = new ArrayList<>();
    for(int i=1; i<8; i++) {
      int newCol = col+i;
      if(Position.isOnBoard(row, newCol)){
        moves.add(new Move(pos, new Position(row, newCol)));
      }
      newCol = col-i;
      if(Position.isOnBoard(row, newCol)){
        moves.add(new Move(pos, new Position(row, newCol)));
      }
      int newRow = row+i;
      if(Position.isOnBoard(newRow, col)){
        moves.add(new Move(pos, new Position(newRow, col)));
      }
      newRow = row-i;
      if(Position.isOnBoard(newRow, col)){
        moves.add(new Move(pos, new Position(newRow, col)));
      }
    }
    return moves;
  }

  /**
   * Calculates all diagonal moves from the starting position that are still on the board
   *
   * @param pos the starting position
   * @return the list of all diagonal moves that are still on the board
   */
  private synchronized static List<Move> calcDiagonalMoves(Position pos) {
    int row = pos.col;
    int col = pos.row;
    List<Move> moves = new ArrayList<>();
    for(int i=1; i<=7; i++) {
      int newCol = col+i;
      int newRow = row+i;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(pos, new Position(newRow, newCol)));
      }
      newCol = col+i;
      newRow = row-i;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(pos, new Position(newRow, newCol)));
      }
      newCol = col-i;
      newRow = row+i;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(pos, new Position(newRow, newCol)));
      }
      newCol = col-i;
      newRow = row-i;
      if(Position.isOnBoard(newRow, newCol)){
        moves.add(new Move(pos, new Position(newRow, newCol)));
      }
    }
    return moves;

  }

  private synchronized static boolean isLegalMove(Move move, Board board){
    return true;
  }

  public synchronized static List<Piece> calcMovablePieces(PlayerColor playerColor, Board board) {
    List<Piece> movablePieces = new ArrayList<>();
    for(Piece piece : board.getPieces()){
      if(piece.getColor() == playerColor && calcMovesForPiece(piece.getId(), board).size() > 0) {
        movablePieces.add(piece);
      }
    }
    return movablePieces;
  }


  public synchronized static void activateColorButtons(PlayerColor color){
    for(Piece piece : calcMovablePieces(color, currentBoard)){
      controller.activateButton(currentBoard.getPositionOfPiece(piece.getId()));
    }
  }

  /**
   * Checks if the game is over, i.e., if the current player has no movable pieces left
   * @return true if no piece is movable for the current player
   */
  public synchronized static boolean isGameOver(){
    // return calcMovablePieces(currentPlayer.getColor(), currentBoard).size() == 0;
    return false;
  }

  public synchronized static ChessBoardController getController(){
    return controller;
  }

  public synchronized static boolean isKingChecked(PlayerColor color) {
    PlayerColor opponentColor = color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
    Position kingPosition = currentBoard.getKingPosition(color);
    for (Move m : calcPossibleMoves(opponentColor, currentBoard)){
      if (m.getNewPosition().equals(kingPosition)) {
        return true;
      }
    }
    return false;
  }

}
