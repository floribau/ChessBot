package AI;

import Game.Board;
import Game.GameEngine;
import Game.GamePhase;
import Game.Move;
import Game.Piece;
import Game.PieceType;
import Game.PlayerColor;
import Util.Position;
import java.util.HashSet;

public class AIHeuristics {

  public static float eval(Board board) {
    if (board.isCheckmate(PlayerColor.WHITE)) {
      return -1000;
    }
    if (board.isCheckmate(PlayerColor.BLACK)) {
      return 1000;
    }
    if (board.isStalemate(PlayerColor.WHITE) || board.isStalemate(PlayerColor.BLACK)) {
      return 0;
    }

    float materialWeight = AIConfig.MATERIAL_WEIGHT;
    float mobilityWeight = AIConfig.MOBILITY_WEIGHT;
    float pawnStructureWeight = AIConfig.PAWN_STRUCTURE_WEIGHT;
    float centerControlWeight = AIConfig.CENTER_CONTROL_WEIGHT;
    float developmentWeight = AIConfig.DEVELOPMENT_WEIGHT;
    float kingSafetyWeight = AIConfig.KING_SAFETY_WEIGHT;
    float batteriesWeight = AIConfig.BATTERIES_WEIGHT;

    if(GameEngine.getPhase() == GamePhase.OPENING) {
      batteriesWeight = 0;
    }
    else if (GameEngine.getPhase() == GamePhase.MIDDLE_GAME) {
      developmentWeight = 0;
      centerControlWeight = 0;
      materialWeight = 2 * AIConfig.MATERIAL_WEIGHT;
      batteriesWeight = AIConfig.BATTERIES_WEIGHT;
    }
    else if (GameEngine.getPhase() == GamePhase.END_GAME) {
      kingSafetyWeight = 0;
      batteriesWeight = 0;
    }

    return materialWeight * scoreMaterial(board, materialWeight)
        + mobilityWeight * scoreMobility(board, mobilityWeight)
        + pawnStructureWeight * scorePawnStructure(board, pawnStructureWeight)
        + centerControlWeight * scoreCenterControl(board, centerControlWeight)
        + developmentWeight * scoreDevelopment(board, developmentWeight)
        + kingSafetyWeight * scoreKingSafety(board, kingSafetyWeight)
        + batteriesWeight * scoreBatteries(board, batteriesWeight);
  }

  private static int scoreMaterial(Board board, float weight) {
    if (weight == 0) return 0;
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

  private static int scoreMobility(Board board, float weight) {
    if (weight == 0) return 0;
    int score = 0;
    for (Piece p : board.getPieces()) {
      int mobility = board.calcPossibleMovesForPiece(p.getId()).size();
      score += (p.getColor() == PlayerColor.WHITE) ? mobility : -1 * mobility;
    }
    return score;
  }

  private static int scoreKingSafety(Board board, float weight) {
    if (weight == 0) return 0;
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
      score += 5;
    }
    return score;
  }

  private static int scorePawnStructure(Board board, float weight) {
    if (weight == 0) return 0;
    int score = 0;
    score += evalPawns(board, PlayerColor.WHITE);
    score -= evalPawns(board, PlayerColor.BLACK);
    return score;
  }

  private static int evalPawns(Board board, PlayerColor color) {
    HashSet<Integer> pawnCols = new HashSet<>();
    int score = 0;

    // eval double pawns
    for (Piece piece : board.getPieces(color)) {
      if (piece.isType(PieceType.PAWN)) {
        if (!pawnCols.add(board.getPositionOfPiece(piece.getId()).col)) {
          score--;
        }
      }
    }

    // eval isolated pawns
    for (Piece piece : board.getPieces(color)) {
      if (piece.isType(PieceType.PAWN)) {
        int pieceCol = board.getPositionOfPiece(piece.getId()).col;
        if (!(pawnCols.contains(pieceCol - 1) || pawnCols.contains(pieceCol + 1))) {
          score -= 2;
        }
      }
    }

    return score;
  }


  private static int scoreDevelopment(Board board, float weight) {
    if (weight == 0) return 0;
    boolean isPieceWhite;
    int score = 0;
    for (Piece p : board.getPieces()) {
      isPieceWhite = p.getColor() == PlayerColor.WHITE;
      if (p.getType() == PieceType.KNIGHT || p.getType() == PieceType.BISHOP) {
        int centerDistance = Position.calcCenterDistance(board.getPositionOfPiece(p.getId()));
        score += (isPieceWhite ? 1 : -1) * evaluateDevelopment(centerDistance);
      }
      if (p.hasMoved() && p.getType() != PieceType.PAWN) {
        score += isPieceWhite ? 1 : -1;
      }
    }
    return score;
  }

  private static int evaluateDevelopment(int centerDistance) {
    int res;
    switch (centerDistance) {
      case 0 -> res = 4;
      case 1 -> res = 3;
      case 2 -> res = 2;
      case 3 -> res = 1;
      default -> res = 0;
    }
    return res;
  }

  private static int scoreCenterControl(Board board, float weight) {
    if (weight == 0) return 0;
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

  private static int scoreBatteries(Board board, float weight) {
    if (weight == 0) return 0;
    int score = 0;
    score += evalBatteries(board, PlayerColor.WHITE);
    score -= evalBatteries(board, PlayerColor.BLACK);
    return score;
  }

  private static int evalBatteries(Board board, PlayerColor color) {
    int score = 0;
    int batteryCount;
    for (Piece opponentPiece : board.getPieces(color.getOppositeColor())) {
      batteryCount = 0;
      Position opponentPiecePosition = board.getPositionOfPiece(opponentPiece.getId());
      for (Piece piece : board.getMajorPieces(color)) {
        for (Move move : board.calcPossibleMovesForPiece(piece.getId())) {
          if (move.getNewPosition().equals(opponentPiecePosition)) {
            batteryCount++;
            break;
          }
        }
      }
      if (batteryCount >= 2) {
        score += batteryCount;
      }
    }
    return score;
  }
}
