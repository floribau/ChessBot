package Game;

import GUI.ChessBoardController;

public class Player {
  private PlayerColor color;
  private boolean humanPlayer;
  private ChessBoardController controller;

  public Player(PlayerColor color, boolean humanPlayer) {
    this.color = color;
    this.humanPlayer = humanPlayer;
  }

  public void setController(){
    controller = GameEngine.getController();
  }

  public PlayerColor getColor() {
    return color;
  }

  public boolean isHumanPlayer() {
    return humanPlayer;
  }

  public synchronized Move makeMove(){
    controller.resetSelectedButtons();
    while(controller.getSelectedFrom() == null || controller.getSelectedTo() == null) {
      System.out.println(controller.getSelectedFrom() + " " + controller.getSelectedTo());
      if(controller.getSelectedFrom() == null) {
        System.out.println("Landed in selectedFrom is null");
        GameEngine.activateColorButtons(this.color);
      }
      else {
        System.out.println("Landed in selectedTo is null");
        controller.disableAllButtons();
        controller.activateButton(controller.getSelectedTo());
        String selectedPieceId = GameEngine.getCurrentBoard().getPieceAt(controller.getSelectedFrom());
        for(Move move : GameEngine.calcMovesForPiece(selectedPieceId, GameEngine.getCurrentBoard())){
          controller.activateButton(move.getNewPosition());
        }
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e){
        // ignore
      }
    }
    controller.resetSelectedButtons();
    return new Move(controller.getSelectedFrom(), controller.getSelectedTo());
  }
}
