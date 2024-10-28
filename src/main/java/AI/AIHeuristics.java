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
    float positionWeight = AIConfig.POSITION_WEIGHT;
    float pawnStructureWeight = AIConfig.PAWN_STRUCTURE_WEIGHT;
    float batteriesWeight = AIConfig.BATTERIES_WEIGHT;

    if(GameEngine.getPhase() == GamePhase.OPENING) {
      batteriesWeight = 0;
    }
    else if (GameEngine.getPhase() == GamePhase.MIDDLE_GAME) {
      materialWeight = 2 * AIConfig.MATERIAL_WEIGHT;
      batteriesWeight = AIConfig.BATTERIES_WEIGHT;
    }
    else if (GameEngine.getPhase() == GamePhase.END_GAME) {
      batteriesWeight = 0;
    }

    return (materialWeight != 0 ? materialWeight * scoreMaterial(board) : 0)
        + (positionWeight != 0 ? positionWeight * scorePiecePositions(board) : 0)
        + (pawnStructureWeight != 0 ? pawnStructureWeight * scorePawnStructure(board) : 0)
        + (batteriesWeight != 0 ? batteriesWeight * scoreBatteries(board) : 0);
  }

  private static int scoreMaterial(Board board) {
    int score = 0;
    for (Piece p : board.getPieces()) {
      if (p.getColor().isWhite()) {
        score += p.getValue();
      } else {
        score -= p.getValue();
      }
    }
    return score;
  }

  private static int scorePiecePositions(Board board) {
    int score = 0;
    for (Piece p : board.getPieces()) {
      int neg = p.getColor().isWhite() ? 1 : -1;
      score += neg * PieceSquareTables.scorePiecePos(p, board.getPositionOfPiece(p));
    }
    return score;
  }

  private static int scorePawnStructure(Board board) {
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
        if (!pawnCols.add(board.getPositionOfPiece(piece).col)) {
          score--;
        }
      }
    }

    // eval isolated pawns
    for (int i : pawnCols) {
      if (!pawnCols.contains(i - 1) && !pawnCols.contains(i + 1)) {
        score -= 2;
      }
    }

    return score;
  }

  private static int scoreBatteries(Board board) {
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
