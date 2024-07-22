package AI;

import Game.Board;
import Game.Piece;
import Game.PieceType;
import Game.PlayerColor;
import Util.Position;
import java.util.ArrayList;
import java.util.List;

public class AIHeuristics {

  public static float evaluateBoard(Board board) {
    if (board.isCheckmate(PlayerColor.WHITE)) {
      return Float.NEGATIVE_INFINITY;
    }
    if (board.isCheckmate(PlayerColor.BLACK)) {
      return Float.POSITIVE_INFINITY;
    }
    if (board.isStalemate(PlayerColor.WHITE) || board.isStalemate(PlayerColor.BLACK)) {
      return 0;
    }
    return AIConfig.materialWeight * scoreMaterial(board)
        + AIConfig.mobilityWeight * scoreMobility(board)
        + AIConfig.pawnStructureWeight * scorePawnStructure(board)
        + AIConfig.centerControlWeight * scoreCenterControl(board)
        + AIConfig.developmentWeight * scoreDevelopment(board);
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

  private static int scoreMobility(Board board) {
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
    int score = 0;
    score += evaluateDoublePawns(board, PlayerColor.WHITE);
    score -= evaluateDoublePawns(board, PlayerColor.BLACK);
    score += evaluateIsolatedPawns(board, PlayerColor.WHITE);
    score -= evaluateIsolatedPawns(board, PlayerColor.BLACK);
    return score;
  }

  private static int evaluateDoublePawns(Board board, PlayerColor color) {
    int score = 0;
    for (int col = 0; col <= 7; col++) {
      int pawnCount = 0;
      for (int row = 0; row <= 7; row++) {
        Position pos = new Position(row, col);
        if (board.isSquareOccupied(pos)) {
          Piece piece = board.getPieceById(board.getPieceAt(pos));
          if (piece.getType() == PieceType.PAWN && piece.getColor() == color) {
            pawnCount++;
          }
        }
      }
      if (pawnCount > 1) {
        score -= pawnCount - 1;
      }
    }
    return score;
  }

  private static int evaluateIsolatedPawns(Board board, PlayerColor color) {
    int score = 0;
    Pawn:
    for (Piece pawn : board.getPieces(color)) {
      int col = board.getPositionOfPiece(pawn.getId()).col;
      if (pawn.getType() == PieceType.PAWN) {
        for (int row = 0; row <= 7; row++) {
          List<Position> positions = new ArrayList<>();
          if (col > 0) {
            // check left col
            Position leftPos = new Position(row, col - 1);
            positions.add(leftPos);
          }
          if (col < 7) {
            // check right col
            Position rightPos = new Position(row, col + 1);
            positions.add(rightPos);
          }
          for (Position pos : positions) {
            if (board.isSquareOccupied(pos)) {
              Piece piece = board.getPieceById(board.getPieceAt(pos));
              if (piece.getType() == PieceType.PAWN && piece.getColor() == color) {
                break Pawn;
              }
            }
          }
        }
        // no pawns in adjacent files => isolated pawn
        score -= 2;
      }
    }
    return score;
  }

  private static int scoreDevelopment(Board board) {
    boolean isPieceWhite;
    int rowDiff;
    int countDevelopedPieces = 0;
    int countDevelopedRows = 0;
    int countUndevelopedPieces = 0;
    for (Piece p : board.getPieces()) {
      isPieceWhite = p.getColor() == PlayerColor.WHITE;
      if (p.getType() == PieceType.KNIGHT || p.getType() == PieceType.BISHOP) {
        Position piecePos = board.getPositionOfPiece(p.getId());
        rowDiff = Math.abs((isPieceWhite ? 7 : 0) - piecePos.row);
        if (rowDiff != 0) {
          countDevelopedPieces += isPieceWhite ? 1 : -1;
        }
        countDevelopedRows += isPieceWhite ? rowDiff : -1 * rowDiff;
      }
      if (!p.hasMoved()) {
        countUndevelopedPieces += isPieceWhite ? 1 : -1;
      }
    }
    return countDevelopedPieces * countDevelopedRows - countUndevelopedPieces;
  }

  private static int scoreCenterControl(Board board) {
    int score = 0;
    Position[] center = {new Position(3, 3), new Position(3, 4), new Position(4, 3),
        new Position(4, 4)};
    for (Position pos : center) {
      if (board.isSquareOccupied(pos)) {
        Piece piece = board.getPieceById(board.getPieceAt(pos));
        score += (piece.getColor() == PlayerColor.WHITE) ? 1 : -1;
      }
    }
    return score;
  }
}
