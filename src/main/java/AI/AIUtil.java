package AI;

import Game.Board;
import Game.Piece;
import Game.PlayerColor;

public class AIUtil {
  public synchronized static float scoreBoard(Board board) {
    board.calcPossibleMoves(PlayerColor.WHITE);
    board.calcPossibleMoves(PlayerColor.BLACK);
    if (board.isCheckmate(PlayerColor.WHITE)) return Float.NEGATIVE_INFINITY;
    if (board.isCheckmate(PlayerColor.BLACK)) return Float.POSITIVE_INFINITY;
    if (board.isStalemate(PlayerColor.WHITE) || board.isStalemate(PlayerColor.BLACK)) return 0;
    return sumPieces(board);
    // TODO implement relative scoring
  }

  public synchronized static int sumPieces(Board board) {
    int sum = 0;
    for (Piece p : board.getPieces()) {
      if(p.getColor().equals(PlayerColor.WHITE)) {
        sum += p.getValue();
      }
      else {
        sum -= p.getValue();
      }
    }
    return sum;
  }
}
