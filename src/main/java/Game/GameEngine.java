package Game;

import AI.AIPlayer;
import GUI.GameSceneController;
import javafx.application.Platform;

public class GameEngine {
  private static Board currentBoard;
  private static Player player1;
  private static Player player2;
  private static Player currentPlayer;
  private static GameSceneController controller;
  private static GamePhase phase;
  private static int moveCount;

  public synchronized static void startGame(GameSceneController controller, boolean humanPlayer1, boolean humanPlayer2){
    GameEngine.player1 = humanPlayer1 ? new Player(PlayerColor.WHITE, true) : new AIPlayer(PlayerColor.WHITE);
    GameEngine.player2 = humanPlayer2 ? new Player(PlayerColor.BLACK, true) : new AIPlayer(PlayerColor.BLACK);
    GameEngine.currentPlayer = player1;

    Piece.pieceCounters.clear();
    currentBoard = new Board();
    currentBoard.initBoard();
    phase = GamePhase.OPENING;
    moveCount = 0;

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
          moveCount++;
          switchGamePhase();
          switchCurrentPlayer();
          currentBoard.resetEnPassantPossible(currentPlayer.getColor());
          currentBoard.calcPossibleMoves(currentPlayer.getColor());
        }
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }

      controller.disableAllButtons();
      if (isCheckmate()) {
        Platform.runLater(() -> controller.handleGameOver(currentPlayer.getColor().getOppositeColor()));
        System.out.println("Checkmate!");
      }
      if (isStalemate()) {
        Platform.runLater(() -> controller.handleGameOver());
        System.out.println("Stalemate!");
      }
      currentBoard = new Board();
      controller.repaint(currentBoard.getBoard());
    });
    gameThread.start();
  }

  private synchronized static void switchCurrentPlayer(){
    if (currentPlayer == player1) {
      currentPlayer = player2;
    } else {
      currentPlayer = player1;
    }
  }

  private synchronized static void switchGamePhase() {
    if (phase == GamePhase.OPENING && moveCount >= 14) {
      phase = GamePhase.MIDDLE_GAME;
      System.out.println("Switched to Middle game");
    } else if (phase == GamePhase.MIDDLE_GAME && currentBoard.countMajorPieces() <= 6) {
      phase = GamePhase.END_GAME;
      System.out.println("Switched to End game");
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

  public synchronized static GameSceneController getController(){
    return controller;
  }

  public synchronized static GamePhase getPhase() {
    return phase;
  }

}
