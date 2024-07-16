package Game;

import GUI.ChessBoardController;
import javafx.application.Platform;

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
    Thread gameThread = new Thread(() -> {
      while (!isCheckmate() && !isStalemate()) {
        Move move = currentPlayer.makeMove();
        if (currentBoard.move(move)) {
          Platform.runLater(() -> controller.repaint(currentBoard.getBoard()));
          switchCurrentPlayer();
          currentBoard.resetEnPassantPossible(currentPlayer.getColor());
          currentBoard.calcPossibleMoves(currentPlayer.getColor());
        }
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      // TODO handle Checkmate and Stalemate
      if (isCheckmate()) {
        System.out.println("Checkmate!");
      }
      if (isStalemate()) {
        System.out.println("Stalemate!");
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
    return currentBoard.isCheckmate(currentPlayer.getColor());
  }

  /**
   * Checks if it is a stalemate, i.e., if the current player does not have any move left but is not checked.
   * @return true if it is a stalemate
   */
  public synchronized static boolean isStalemate() {
    return currentBoard.isStalemate(currentPlayer.getColor());
  }

  public synchronized static ChessBoardController getController(){
    return controller;
  }

}
