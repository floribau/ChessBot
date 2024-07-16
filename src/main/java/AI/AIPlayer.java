package AI;
import Game.Board;
import Game.GameEngine;
import Game.Move;
import Game.Player;
import Game.PlayerColor;
import Util.ScoringEntry;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public class AIPlayer extends Player {
  public AIPlayer(PlayerColor color) {
    super(color, false);
  }

  public synchronized Move makeMove() {
    return chooseBestMove(GameEngine.getCurrentBoard());
  }

  public Move chooseBestMove(Board board) {
    TreeSet<ScoringEntry> scores = new TreeSet<>();
    for (Move m : board.calcPossibleMoves(this.getColor())) {
      scores.add(new ScoringEntry(m, AIUtil.scoreBoard(board.calcMoveToBoard(m))));
    }
    switch (this.getColor()) {
      case WHITE -> {
        System.out.println("Best move for white: " + scores.first());
        return scores.first().move();
      }
      case BLACK -> {
        System.out.println("Best move for black: " + scores.last());
        return scores.last().move();
      }
    }
    return null;
  }
}
