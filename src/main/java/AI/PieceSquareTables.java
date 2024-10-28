package AI;

import Game.GameEngine;
import Game.GamePhase;
import Game.Piece;
import Util.Position;

public class PieceSquareTables {

  /**
   * This class uses the Piece-Square Tables proposed by Tomasz Michniewski
   */

  public static final int[][] PAWN_HEAT_MAP = {
      {0, 0, 0, 0, 0, 0, 0, 0},
      {10, 10, 10, 10, 10, 10, 10, 10},
      {2, 2, 4, 6, 6, 4, 2, 2},
      {1, 1, 2, 5, 5, 2, 1, 1},
      {0, 0, 0, 4, 4, 0, 0, 0},
      {1, -1, -2, 0, 0, -2, -1, 1},
      {1, 2, 2, -4, -4, 2, 2, 1},
      {0, 0, 0, 0, 0, 0, 0, 0}
  };

  public static final int[][] KNIGHT_HEAT_MAP = {
      {-10, -8, -6, -6, -6, -6, -8, -10},
      {-8, -4, 0, 0, 0, 0, -4, -8},
      {-6, 0, 2, 3, 3, 2, 0, -6},
      {-6, 1, 3, 4, 4, 3, 1, -6},
      {-6, 1, 3, 4, 4, 3, 1, -6},
      {-6, 0, 2, 3, 3, 2, 0, -6},
      {-8, -4, 0, 0, 0, 0, -4, -8},
      {-10, -8, -6, -6, -6, -6, -8, -10}
  };

  public static final int[][] BISHOP_HEAT_MAP = {
      {-4, -2, -2, -2, -2, -2, -2, -4},
      {-2, 0, 0, 0, 0, 0, 0, -2},
      {-2, 0, 1, 2, 2, 1, 0, -2},
      {-2, 1, 1, 2, 2, 1, 1, -2},
      {-2, 0, 2, 2, 2, 2, 0, -2},
      {-2, 2, 2, 2, 2, 2, 2, -2},
      {-2, 1, 0, 0, 0, 0, 1, -2},
      {-4, -2, -2, -2, -2, -2, -2, -4}
  };

  public static final int[][] ROOK_HEAT_MAP = {
      {0, 0, 0, 0, 0, 0, 0, 0},
      {1, 2, 2, 2, 2, 2, 2, 1},
      {-1, 0, 0, 0, 0, 0, 0, -1},
      {-1, 0, 0, 0, 0, 0, 0, -1},
      {-1, 0, 0, 0, 0, 0, 0, -1},
      {-1, 0, 0, 0, 0, 0, 0, -1},
      {-1, 0, 0, 0, 0, 0, 0, -1},
      {0, 0, 0, 1, 1, 0, 0, 0}
  };

  public static final int[][] QUEEN_HEAT_MAP = {
      {-4, -2, -2, -1, -1, -2, -2, -4},
      {-2, 0, 0, 0, 0, 0, 0, -2},
      {-2, 0, 1, 1, 1, 1, 0, -2},
      {-1, 0, 1, 1, 1, 1, 0, -1},
      {0, 0, 1, 1, 1, 1, 0, -1},
      {-2, 1, 1, 1, 1, 1, 0, -2},
      {-2, 0, 1, 0, 0, 0, 0, -2},
      {-4, -2, -2, -1, -1, -2, -2, -4}
  };

  public static final int[][] KING_HEAT_MAP_MID = {
      {-6, -8, -8, -10, -10, -8, -8, -6},
      {-6, -8, -8, -10, -10, -8, -8, -6},
      {-6, -8, -8, -10, -10, -8, -8, -6},
      {-6, -8, -8, -10, -10, -8, -8, -6},
      {-4, -6, -6, -8, -8, -6, -6, -4},
      {-2, -4, -4, -4, -4, -4, -4, -2},
      {4, 4, 0, 0, 0, 0, 4, 4},
      {4, 6, 2, 0, 0, 2, 6, 4}
  };

  public static final int[][] KING_HEAT_MAP_END = {
      {-10, -8, -6, -4, -4, -6, -8, -10},
      {-6, -4, -2, 0, 0, -2, -4, -6},
      {-6, -2, 4, 6, 6, 4, 2, -6},
      {-6, -2, 6, 8, 8, 6, -2, -6},
      {-6, -2, 6, 8, 8, 6, -2, -6},
      {-6, -2, 4, 6, 6, 4, 2, -6},
      {-6, -6, 0, 0, 0, 0, -6, -6},
      {-10, -6, -6, -6, -6, -6, -6, -10}
  };

  public static int scorePiecePos(Piece piece, Position pos) {
    int row = piece.getColor().isWhite() ? pos.row : 7 - pos.row;

    if (Position.isOnBoard(row, pos.col)) {
      switch (piece.getType()) {
        case PAWN -> {
          return PAWN_HEAT_MAP[row][pos.col];
        }
        case BISHOP -> {
          return BISHOP_HEAT_MAP[row][pos.col];
        }
        case KNIGHT -> {
          return KNIGHT_HEAT_MAP[row][pos.col];
        }
        case ROOK -> {
          return ROOK_HEAT_MAP[row][pos.col];
        }
        case QUEEN -> {
          return QUEEN_HEAT_MAP[row][pos.col];
        }
        case KING -> {
          if (GameEngine.getPhase() != GamePhase.END_GAME) {
            return KING_HEAT_MAP_MID[row][pos.col];
          } else {
            return KING_HEAT_MAP_END[row][pos.col];
          }
        }
      }
    }

    return -1000;
  }
}
