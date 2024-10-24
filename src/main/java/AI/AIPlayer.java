package AI;
import Game.Board;
import Game.GameEngine;
import Game.GamePhase;
import Game.Move;
import Game.Player;
import Game.PlayerColor;
import java.util.Comparator;
// import java.util.HashMap;
import java.util.List;

public class AIPlayer extends Player {
  // private final HashMap<Board, Float> transpositionTable;

  public AIPlayer(PlayerColor color) {
    super(color, false);
    // transpositionTable = new HashMap<>();
  }

  public synchronized Move makeMove() {
    return findBestMove(GameEngine.getCurrentBoard(), 3);
  }

  public float minimax(Board board, int depth, float alpha, float beta, PlayerColor player) {
    boolean isWhitePlayer = player == PlayerColor.WHITE;
    board.calcPossibleMoves(player);

    /*
    if(transpositionTable.containsKey(board)) {
      System.out.println("Position known");
      return transpositionTable.get(board);
    }
    */

    if (depth == 0 || board.isCheckmate(player) || board.isStalemate(player)) {
      board.calcPossibleMoves(player.getOppositeColor());
      int neg = this.getColor().equals(PlayerColor.WHITE) ? 1 : -1;
      return AIHeuristics.evaluateBoard(board);
    }

    List<Move> moves = board.getPossibleMoves(player);
    orderMoves(board, moves);
    float resEval;

    if (isWhitePlayer) {
      float maxEval = Float.NEGATIVE_INFINITY;
      for (Move m : board.getPossibleMoves(player)) {
        Board newBoard = board.calcMoveToBoard(m);
        maxEval = Float.max(maxEval, minimax(newBoard, depth - 1, alpha, beta, player.getOppositeColor()));
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
        minEval = Float.min(minEval, minimax(newBoard, depth - 1, alpha, beta, player.getOppositeColor()));
        beta = Float.min(beta, minEval);
        if (beta <= alpha) {
          break;
        }
      }
      resEval = minEval;
    }
    // transpositionTable.put(board, resEval);
    return resEval;
  }

  public Move findBestMove(Board board, int depth) {
    PlayerColor player = this.getColor();
    boolean isWhitePlayer = player == PlayerColor.WHITE;
    board.calcPossibleMoves(player);

    Move bestMove = null;
    float bestEval = isWhitePlayer ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
    float alpha = Float.NEGATIVE_INFINITY;
    float beta = Float.POSITIVE_INFINITY;

    List<Move> moves = board.getPossibleMoves(player);
    orderMoves(board, moves);

    for (Move m : moves) {
      Board newBoard = board.calcMoveToBoard(m);
      float eval = minimax(newBoard, depth - 1, alpha, beta, player.getOppositeColor());
      if ((isWhitePlayer && eval > bestEval) || (!isWhitePlayer && eval < bestEval)) {
        // || (GameEngine.getPhase() != GamePhase.END_GAME && eval == maxEval && Math.random() >= 0.5)
        bestEval = eval;
        bestMove = m;
      }
    }
    System.out.println("Best move for " + this.getColor() + " is " + bestMove + ", score: " + bestEval);
    return bestMove;
  }

  private void orderMoves(Board board, List<Move> moves) {
    moves.sort(new Comparator<>() {
      @Override
      public int compare(Move o1, Move o2) {
        return scoreMove(o2) - scoreMove(o1);
      }

      private int scoreMove(Move move) {
        if (board.isCapture(move)) {
          int movedValue = move.getMovedPiece().getValue();
          int capturedValue = board.getPieceById(board.getPieceAt(move.getNewPosition()))
              .getValue();
          return 10 + capturedValue - movedValue;
        }
        if (move.isPromotion()) {
          return GameEngine.getPhase() == GamePhase.END_GAME ? 9 : 5;
        }
        if (board.isMoveCheck(move)) {
          return GameEngine.getPhase() == GamePhase.OPENING ? 2 : 20;
        }
        return 1;
      }
    });
  }

}
