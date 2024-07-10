package AI;
import Game.Move;
import Game.Player;
import Game.PlayerColor;

public class AIPlayer extends Player {
  public AIPlayer(PlayerColor color) {
    super(color, false);
  }

  public synchronized Move makeMove(){
    return null;
  }
}
