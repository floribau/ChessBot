package Game;

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
    GameEngine.player2 = new Player(PlayerColor.BLACK, true);

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
      while (!isCheckmate() && !isStalemate()) {
        Move move = currentPlayer.makeMove();
        currentBoard.move(move);
        Platform.runLater(() -> controller.repaint(currentBoard.getBoard()));
        switchCurrentPlayer();
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      // TODO handle Checkmate and Stalemate
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

  public synchronized static void activateColorButtons(PlayerColor color){
    for(Piece piece : currentBoard.calcMovablePieces(color)){
      controller.activateButton(currentBoard.getPositionOfPiece(piece.getId()));
    }
  }

  /**
   * Checks if the current player is checkmated on the current board
   * @return true if it is a checkmate
   */
  public synchronized static boolean isCheckmate(){
    return currentBoard.isKingChecked(currentPlayer.getColor()) && currentBoard.calcPossibleMoves(currentPlayer.getColor()).size() == 0;
  }

  /**
   * Checks if it is a stalemate, i.e., if the current player does not have any move left but is not checked.
   * @return true if it is a stalemate
   */
  public synchronized static boolean isStalemate() {
    return !currentBoard.isKingChecked(currentPlayer.getColor()) && currentBoard.calcPossibleMoves(currentPlayer.getColor()).size() == 0;
  }

  public synchronized static ChessBoardController getController(){
    return controller;
  }

}
