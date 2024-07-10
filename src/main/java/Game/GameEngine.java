package Game;

import AI.AIPlayer;
import GUI.ChessBoardController;
import Util.Position;
import java.util.List;
import java.util.ArrayList;

public class GameEngine {
  private static Board currentBoard;

  private static Player player1;
  private static Player player2;
  private static Player currentPlayer;
  private static ChessBoardController controller;

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

    playGame();
  }

  public synchronized static void playGame(){
    while(!isGameOver()) {
      currentPlayer.makeMove();
      switchCurrentPlayer();
    }
  }

  public synchronized  static void switchCurrentPlayer(){
    if (currentPlayer == player1) {
      currentPlayer = player2;
    } else {
      currentPlayer = player1;
    }
  }

  public static Piece getPieceById(String id) {
    for(Piece piece : currentBoard.getPieces()){
      if(piece.getId() == id){
        return piece;
      }
    }
    return null;
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
    for(Piece piece : currentBoard.getPieces()){
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

  public static void activateColorButtons(PlayerColor color){
    for(Piece piece : calcMovablePieces(color, currentBoard)){
      controller.activateButton(currentBoard.getPositionOfPiece(piece.getId()));
    }
  }

  //TODO implement
  public static boolean isGameOver(){
    return true;
  }

}
