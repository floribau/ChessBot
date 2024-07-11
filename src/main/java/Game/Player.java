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
    // TODO change button activation (should be activated in the beginning and then kept active until user input
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
        for(Move move : GameEngine.getCurrentBoard().calcMovesForPiece(selectedPieceId)){
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

    if(m.isPawnMove() && (m.getNewPosition().row == 0 || m.getNewPosition().row == 7)) {
      controller.activatePromotionButtons(color);
      while(controller.getSelectedPromotion() == '0'){
        try {
          Thread.sleep(50);
        } catch (InterruptedException e){
          // ignore
        }
      }
      String moveString = m.getMoveString() + "=" + controller.getSelectedPromotion();
      Piece piece = m.getMovedPiece();
      controller.deactivatePromotionButtons();
      m = new Move(moveString, piece);
    }

    if(m.isPawnMove() && m.isDiagonalMove()) {
      if(GameEngine.getCurrentBoard().getPieceAt(m.getNewPosition()).equals("")) {
        String moveString = m.getMoveString();
        Piece movedPiece = m.getMovedPiece();
        m = new Move(moveString, movedPiece, true);
      }
    }

    return m;
  }
}
