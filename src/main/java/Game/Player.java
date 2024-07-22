package Game;

import GUI.ChessBoardController;
import Util.Position;

public class Player {
  private final PlayerColor color;
  private final boolean humanPlayer;
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
    GameEngine.activateColorButtons(this.color);
    Position oldSelectedFrom = null;
    while(controller.getSelectedFrom() == null || controller.getSelectedTo() == null) {
      if (controller.getSelectedFrom() == null) {
        if (oldSelectedFrom != null) {
          controller.disableAllButtons();
          GameEngine.activateColorButtons(this.color);
        }
      }
      else {
        controller.disableAllButtons();
        controller.activateButton(controller.getSelectedFrom());
        String selectedPieceId = GameEngine.getCurrentBoard().getPieceAt(controller.getSelectedFrom());
        for(Move move : GameEngine.getCurrentBoard().calcPossibleMovesForPiece(selectedPieceId)){
          controller.activateButton(move.getNewPosition());
        }
      }
      oldSelectedFrom = controller.getSelectedFrom();
      try {
        Thread.sleep(100);
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

    controller.disableAllButtons();
    System.out.println(color + " selected move " + m);
    return m;
  }
}
