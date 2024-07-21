package AI;
import Game.Board;
import Game.GameEngine;
import Game.Move;
import Game.Player;
import Game.PlayerColor;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class AIPlayer extends Player {
  private HashMap<Board, Float> transpositionTable;

  public AIPlayer(PlayerColor color) {
    super(color, false);
    transpositionTable = new HashMap<>();
  }

  public synchronized Move makeMove() {
    return findBestMove(GameEngine.getCurrentBoard(), 3);
  }

  public float minimax(Board board, int depth, float alpha, float beta, boolean maximizingPlayer) {
    PlayerColor player = maximizingPlayer ? this.getColor() : this.getColor().getOppositeColor();
    board.calcPossibleMoves(player);

    if(transpositionTable.containsKey(board)) {
      System.out.println("Position known");
      return transpositionTable.get(board);
    }

    if (depth == 0 || board.isCheckmate(player) || board.isStalemate(player)) {
      int neg = this.getColor().equals(PlayerColor.WHITE) ? 1 : -1;
      return neg * AIHeuristics.evaluateBoard(board);
    }

    List<Move> moves = board.getPossibleMoves(player);
    // orderMoves(board, moves);
    float resEval;

    if (maximizingPlayer) {
      float maxEval = Float.NEGATIVE_INFINITY;
      for (Move m : board.getPossibleMoves(player)) {
        Board newBoard = board.calcMoveToBoard(m);
        maxEval = Float.max(maxEval, minimax(newBoard, depth - 1, alpha, beta, false));
        alpha = Float.max(alpha, maxEval);
        if (beta <= alpha) {
          break;
        }
      }
      resEval = maxEval;
    } else {
      float minEval = Float.POSITIVE_INFINITY;
      for (Move m : board.getPossibleMoves(player)) {
        Board newBoard = board.calcMoveToBoard(m);
        minEval = Float.min(minEval, minimax(newBoard, depth - 1, alpha, beta, true));
        beta = Float.min(beta, minEval);
        if (beta <= alpha) {
          break;
        }
      }
      resEval = minEval;
    }
    transpositionTable.put(board, resEval);
    return resEval;
  }

  public Move findBestMove(Board board, int depth) {
    board.calcPossibleMoves(this.getColor());

    Move bestMove = null;
    float maxEval = Float.NEGATIVE_INFINITY;
    float alpha = Float.NEGATIVE_INFINITY;
    float beta = Float.POSITIVE_INFINITY;

    List<Move> moves = board.getPossibleMoves(this.getColor());
    // orderMoves(board, moves);

    for (Move m : board.getPossibleMoves(this.getColor())) {
      Board newBoard = board.calcMoveToBoard(m);
      float eval = minimax(newBoard, depth - 1, alpha, beta, false);
      if (eval > maxEval || (eval == maxEval && Math.random() >= 0.5)) {
        maxEval = eval;
        bestMove = m;
      }
    }
    System.out.println("Best move for " + this.getColor() + " is " + bestMove + ", score: " + maxEval);
    return bestMove;
  }

  private void orderMoves(Board board, List<Move> moves) {
    Collections.sort(moves, new Comparator<>() {
      @Override
      public int compare(Move o1, Move o2) {
        return scoreMove(o2) - scoreMove(o1);
      }

      private int scoreMove(Move move) {
        if (board.isCapture(move)) {
          int movedValue = move.getMovedPiece().getValue();
          int capturedValue = board.getPieceById(board.getPieceAt(move.getNewPosition())).getValue();
          return 10 + capturedValue - movedValue;
        }
        if (move.isPromotion()) {
          return 5;
        }
        if (board.isMoveCheck(move)) {
          return 2;
        }
        return 1;
      }
    });
  }

}
