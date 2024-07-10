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
      if(controller.getSelectedFrom() == null) {
        controller.disableAllButtons();
        GameEngine.activateColorButtons(this.color);
      }
      else {
        controller.disableAllButtons();
        controller.activateButton(controller.getSelectedFrom());
        String selectedPieceId = GameEngine.getCurrentBoard().getPieceAt(controller.getSelectedFrom());
        for(Move move : GameEngine.calcMovesForPiece(selectedPieceId, GameEngine.getCurrentBoard())){
          controller.activateButton(move.getNewPosition());
        }
      }
      try {
        Thread.sleep(50);
      } catch (InterruptedException e){
        // ignore
      }
    }
    Move m = new Move(controller.getSelectedFrom(), controller.getSelectedTo(), GameEngine.getCurrentBoard()
        .getPieceById(GameEngine.getCurrentBoard().getPieceAt(controller.getSelectedFrom())));
    controller.resetSelectedButtons();
    return m;
  }
}
