package AI;
import Game.Board;
import Game.GameEngine;
import Game.GamePhase;
import Game.Move;
import Game.Player;
import Game.PlayerColor;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class AIPlayer extends Player {
  private static final HashMap<Board, TranspositionTableEntry> TRANSPOSITION_TABLE = new HashMap<>();
  private static final int INITIAL_DEPTH = 3;
  private static int posCount = 0;
  private static int duplicateCount = 0;

  public AIPlayer(PlayerColor color) {
    super(color, false);
  }

  public synchronized Move makeMove() {
    return findBestMove(GameEngine.getCurrentBoard(), INITIAL_DEPTH);
  }

  public float minimax(Board board, int depth, float alpha, float beta, PlayerColor player) {

    if (TRANSPOSITION_TABLE.containsKey(board)) {
      TranspositionTableEntry entry = TRANSPOSITION_TABLE.get(board);
      if (entry.depth() >= depth) {
        duplicateCount++;
        return entry.score();
      }
    }

    boolean isWhitePlayer = player == PlayerColor.WHITE;
    board.calcPossibleMoves(player);

    if (depth == 0 || board.isCheckmate(player) || board.isStalemate(player)) {
      posCount++;
      board.calcPossibleMoves(player.getOppositeColor());
      float score = AIHeuristics.eval(board);
      TRANSPOSITION_TABLE.put(board, new TranspositionTableEntry(depth, score));
      return score;
    }

    List<Move> moves = board.getPossibleMoves(player);
    orderMoves(board, moves);
    float resEval;

    if (isWhitePlayer) {
      float maxEval = Float.NEGATIVE_INFINITY;
      float eval;
      for (Move m : board.getPossibleMoves(player)) {
        Board newBoard = board.calcMoveToBoard(m);

        // null-window pruning after certain depth
        // if (depth <= INITIAL_DEPTH - 2) {
        eval = minimax(newBoard, depth - 1, alpha, alpha + 1, player.getOppositeColor());
        // }
        // regular minimax call
        // if (depth > INITIAL_DEPTH - 2 || eval > alpha) {
        if (eval > alpha) {
          eval = minimax(newBoard, depth - 1, alpha, beta, player.getOppositeColor());
        }

        maxEval = Float.max(maxEval, eval);
        alpha = Float.max(alpha, maxEval);
        if (beta <= alpha) {
          break;
        }
      }
      resEval = maxEval;
    } else {
      float minEval = Float.POSITIVE_INFINITY;
      float eval;
      for (Move m : board.getPossibleMoves(player)) {
        Board newBoard = board.calcMoveToBoard(m);

        // null-window pruning after certain depth
        // if (depth <= INITIAL_DEPTH - 2) {
        eval = minimax(newBoard, depth - 1, beta - 1, beta, player.getOppositeColor());
        // }
        // regular minimax call
        // if (depth > INITIAL_DEPTH - 2 || eval < beta) {
        if (eval < beta) {
          eval = minimax(newBoard, depth - 1, alpha, beta, player.getOppositeColor());
        }

        minEval = Float.min(minEval, eval);
        beta = Float.min(beta, minEval);
        if (beta <= alpha) {
          break;
        }
      }
      resEval = minEval;
    }

    if (!TRANSPOSITION_TABLE.containsKey(board) || TRANSPOSITION_TABLE.get(board).depth() < depth) {
      TRANSPOSITION_TABLE.put(board, new TranspositionTableEntry(depth, resEval));
    }
    return resEval;
  }

  public Move findBestMove(Board board, int depth) {
    posCount = 0;
    duplicateCount = 0;

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
    System.out.println("Positions evaluated: " + posCount + ", duplicates eliminated: " + duplicateCount);
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
