package AI;

import Game.Board;
import Game.Piece;
import Game.PlayerColor;

public class AIHeuristics {

  public static float evaluateBoard(Board board) {
    board.calcPossibleMoves(PlayerColor.WHITE);
    board.calcPossibleMoves(PlayerColor.BLACK);
    if (board.isCheckmate(PlayerColor.WHITE)) {
      return Float.NEGATIVE_INFINITY;
    }
    if (board.isCheckmate(PlayerColor.BLACK)) {
      return Float.POSITIVE_INFINITY;
    }
    if (board.isStalemate(PlayerColor.WHITE) || board.isStalemate(PlayerColor.BLACK)) {
      return 0;
    }
    return scoreMaterial(board);
    // TODO implement relative scoring
  }

  private static int scoreMaterial(Board board) {
    int score = 0;
    for (Piece p : board.getPieces()) {
      if (p.getColor().equals(PlayerColor.WHITE)) {
        score += p.getValue();
      } else {
        score -= p.getValue();
      }
    }
    return score;
  }

  private static int scorePieceActivity(Board board) {
    int score = 0;
    for (Piece p : board.getPieces()) {
      int mobility = board.calcPossibleMovesForPiece(p.getId()).size();
      score += (p.getColor() == PlayerColor.WHITE) ? mobility : -mobility;
    }
    return score;
  }

  private static int scoreKingSafety(Board board) {
    // TODO implement
    return 0;
  }

  private static int scorePawnStructure(Board board) {
    // TODO implement
    return 0;
  }

  private static int scoreDevelopment(Board board) {
    // TODO implement
    return 0;
  }

  private static int scoreCenterControl(Board board) {
    // TODO implement
    return 0;
  }
}
