package AI;

import Game.Board;
import Game.GameEngine;
import Game.GamePhase;
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

    if (GameEngine.getPhase() == GamePhase.MIDDLE_GAME) {
      AIConfig.developmentWeight = 0;
      AIConfig.centerControlWeight = 0;
    }
    if (GameEngine.getPhase() == GamePhase.END_GAME) {
      AIConfig.kingSafetyWeight = 0;
      AIConfig.materialWeight = 3;
    }

    return AIConfig.materialWeight * scoreMaterial(board)
        + AIConfig.mobilityWeight * scoreMobility(board)
        + AIConfig.pawnStructureWeight * scorePawnStructure(board)
        + AIConfig.centerControlWeight * scoreCenterControl(board)
        + AIConfig.developmentWeight * scoreDevelopment(board)
        + AIConfig.kingSafetyWeight * scoreKingSafety(board);
  }

  private static int scoreMaterial(Board board) {
    if (AIConfig.materialWeight == 0) return 0;
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
    if (AIConfig.mobilityWeight == 0) return 0;
    int score = 0;
    for (Piece p : board.getPieces()) {
      int mobility = board.calcPossibleMovesForPiece(p.getId()).size();
      score += (p.getColor() == PlayerColor.WHITE) ? mobility : -mobility;
    }
    return score;
  }

  private static int scoreKingSafety(Board board) {
    if (AIConfig.kingSafetyWeight == 0) return 0;
    int score = 0;
    score += evaluateKingSafety(board, PlayerColor.WHITE);
    score -= evaluateKingSafety(board, PlayerColor.BLACK);
    return score;
  }

  private static int evaluateKingSafety(Board board, PlayerColor color) {
    Position kingPos = board.getKingPosition(color);
    int direction = color == PlayerColor.WHITE ? -1 : 1;
    int score = 0;
    for (int colOffset = -1; colOffset <= 1; colOffset++) {
      for (int rowOffset = 1; rowOffset <= 2; rowOffset++) {
        if (Position.isOnBoard(kingPos.row + direction * rowOffset, kingPos.col + colOffset) && board.isSquareOccupied(new Position(kingPos.row + direction * rowOffset, kingPos.col + colOffset))) {
          Piece piece = board.getPieceById(board.getPieceAt(new Position(kingPos.row + direction * rowOffset, kingPos.col + colOffset)));
          if (piece.isFriendlyPawn(color)) {
            score++;
            break;
          }
        }
      }
    }
    if (board.hasCastled(color)) {
      score += 3;
    }
    return score;
  }

  private static int scorePawnStructure(Board board) {
    if (AIConfig.pawnStructureWeight == 0) return 0;
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
    if (AIConfig.developmentWeight == 0) return 0;
    boolean isPieceWhite;
    int score = 0;
    for (Piece p : board.getPieces()) {
      isPieceWhite = p.getColor() == PlayerColor.WHITE;
      if (p.getType() == PieceType.KNIGHT || p.getType() == PieceType.BISHOP) {
        int centerDistance = Position.calcCenterDistance(board.getPositionOfPiece(p.getId()));
        score += (isPieceWhite ? 1 : -1) * Math.abs(3 - centerDistance);
      }
      if (p.hasMoved() && p.getType() != PieceType.PAWN) {
        score += isPieceWhite ? 1 : -1;
      }
    }
    return score;
  }

  private static int scoreCenterControl(Board board) {
    if (AIConfig.centerControlWeight == 0) return 0;
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
