package Game;

import Util.Position;
import java.util.ArrayList;
import java.util.List;

public class MoveCalculator {

  /**
   * Calculates all moves on board obeying the move patterns of the piece. Pieces in the way and
   * checks are disregarded.
   *
   * @param pieceId the piece to be moved
   * @return the list of moves on board
   */
  static synchronized List<Move> calcMovesOnBoardForPiece(Board board, String pieceId) {
    List<Move> moves = new ArrayList<>();
    Position fromPos = board.getPositionOfPiece(pieceId);
    Piece piece = board.getPieceById(pieceId);

    switch (piece.getType()) {
      case KING -> moves.addAll(calcKingMoves(board, fromPos, piece));
      case QUEEN -> moves.addAll(calcQueenMoves(fromPos, piece));
      case ROOK -> moves.addAll(calcParallelMoves(fromPos, piece));
      case BISHOP -> moves.addAll(calcDiagonalMoves(fromPos, piece));
      case KNIGHT -> moves.addAll(calcKnightMoves(fromPos, piece));
      case PAWN -> moves.addAll(calcPawnMoves(board, fromPos, piece));
    }

    return moves;
  }

  private static List<Move> calcKingMoves(Board board, Position fromPos, Piece piece) {
    List<Move> moves = new ArrayList<>();
    int[][] directions = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };
    moves.addAll(calcDirectionMoves(fromPos, piece, directions, 1));
    moves.addAll(calcCastleMoves(board, piece.getColor()));
    return moves;
  }

  private static List<Move> calcCastleMoves(Board board, PlayerColor color) {
    List<Move> moves = new ArrayList<>();
    Piece kingPiece = board.getPieceById(PieceType.KING.getDisplayName() + "_" + color + "0");
    Piece rookPiece0 = board.getPieceById(PieceType.ROOK.getDisplayName() + "_" + color + "0");
    Piece rookPiece1 = board.getPieceById(PieceType.ROOK.getDisplayName() + "_" + color + "1");

    if (!kingPiece.hasMoved()) {
      Position oldKingPosition = board.getPositionOfPiece(kingPiece.getId());
      if (rookPiece0 != null && !rookPiece0.hasMoved()) {
        Position newKingPosition = new Position(oldKingPosition.row, oldKingPosition.col - 2);
        Move bigCastle = new Move(oldKingPosition, newKingPosition, kingPiece);
        moves.add(bigCastle);
      }
      if (rookPiece1 != null && !rookPiece1.hasMoved()) {
        Position newKingPosition = new Position(oldKingPosition.row, oldKingPosition.col + 2);
        Move smallCastle = new Move(oldKingPosition, newKingPosition, kingPiece);
        moves.add(smallCastle);
      }
    }
    return moves;
  }

  private static List<Move> calcQueenMoves(Position fromPos, Piece piece) {
    List<Move> moves = new ArrayList<>();
    moves.addAll(calcParallelMoves(fromPos, piece));
    moves.addAll(calcDiagonalMoves(fromPos, piece));
    return moves;
  }

  /**
   * Calculates all parallel moves from the starting position that are still on the board
   *
   * @param pos the starting position
   * @return the list of all parallel moves that are still on the board
   */
  private static List<Move> calcParallelMoves(Position pos, Piece piece) {
    int[][] directions = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };
    return new ArrayList<>(calcDirectionMoves(pos, piece, directions, 7));
  }

  /**
   * Calculates all diagonal moves from the starting position that are still on the board
   *
   * @param pos the starting position
   * @return the list of all diagonal moves that are still on the board
   */
  private static List<Move> calcDiagonalMoves(Position pos, Piece piece) {
    int[][] directions = {
        {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };
    return new ArrayList<>(calcDirectionMoves(pos, piece, directions, 7));
  }

  private static List<Move> calcKnightMoves(Position fromPos, Piece piece) {
    int[][] directions = {
        {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}
    };
    return new ArrayList<>(calcDirectionMoves(fromPos, piece, directions, 1));
  }

  private static List<Move> calcDirectionMoves(Position pos, Piece piece, int[][] directions, int range) {
    List<Move> moves = new ArrayList<>();
    for (int[] direction : directions) {
      int dRow = direction[0];
      int dCol = direction[1];

      for (int i=1; i<= range; i++) {
        int newRow = pos.row + i * dRow;
        int newCol = pos.col + i* dCol;
        if (Position.isOnBoard(newRow, newCol)) {
          moves.add(new Move(pos, new Position(newRow, newCol), piece));
        }
      }
    }
    return moves;
  }

  private static List<Move> calcPawnMoves(Board board, Position fromPos, Piece piece) {
    List<Move> moves = new ArrayList<>();
    int direction = piece.getColor().equals(PlayerColor.WHITE) ? -1 : 1;
    boolean isPromotion = piece.getColor().equals(PlayerColor.WHITE) ? (fromPos.row - 1 <= 0)
        : (fromPos.row - 1 >= 7);

    if (!isPromotion) {
      moves.add(new Move(fromPos, new Position(fromPos.row + direction, fromPos.col), piece));
    } else {
      // pawn promotion
      moves.addAll(calcPromotionMoves(
          Move.calcMoveString(fromPos, new Position(fromPos.row + direction, fromPos.col), piece),
          piece));
    }

    if (!piece.hasMoved()) {
      // 2 steps possible
      moves.add(new Move(fromPos, new Position(fromPos.row + 2 * direction, fromPos.col), piece));
    }

    //captures
    Position leftCapture = new Position(fromPos.row + direction, fromPos.col - 1);
    Position rightCapture = new Position(fromPos.row + direction, fromPos.col + 1);
    moves.addAll(calcPawnCaptures(board, fromPos, leftCapture, piece));
    moves.addAll(calcPawnCaptures(board, fromPos, rightCapture, piece));

    // en passant
    if (Position.isOnBoard(fromPos.row, fromPos.col - 1)) {
      Position leftPos = new Position(fromPos.row, fromPos.col - 1);
      if (board.isSquareOccupied(leftPos)) {
        Piece leftPiece = board.getPieceById(board.getPieceAt(leftPos));
        if (leftPiece.isEnPassantPossible()) {
          moves.add(new Move(fromPos, leftCapture, piece, true));
        }
      }
    }
    if (Position.isOnBoard(fromPos.row, fromPos.col + 1)) {
      Position rightPos = new Position(fromPos.row, fromPos.col + 1);
      if (board.isSquareOccupied(rightPos)) {
        Piece rightPiece = board.getPieceById(board.getPieceAt(rightPos));
        if (rightPiece.isEnPassantPossible()) {
          moves.add(new Move(fromPos, rightCapture, piece, true));
        }
      }
    }

    return moves;
  }

  private static List<Move> calcPawnCaptures(Board board, Position fromPos, Position capturePos,
      Piece piece) {
    List<Move> moves = new ArrayList<>();
    boolean isPromotion = piece.getColor().equals(PlayerColor.WHITE) ? (fromPos.row - 1 <= 0)
        : (fromPos.row - 1 >= 7);

    if (Position.isOnBoard(capturePos.row, capturePos.col) && board.isSquareOccupied(capturePos)) {
      if (isPromotion) {
        moves.addAll(calcPromotionMoves(Move.calcMoveString(fromPos, capturePos, piece), piece));
      } else {
        moves.add(new Move(fromPos, capturePos, piece));
      }
    }
    return moves;
  }

  private static List<Move> calcPromotionMoves(String moveStr, Piece piece) {
    List<Move> moves = new ArrayList<>();
    moves.add(new Move(moveStr + "=Q", piece));
    moves.add(new Move(moveStr + "=R", piece));
    moves.add(new Move(moveStr + "=B", piece));
    moves.add(new Move(moveStr + "=N", piece));
    return moves;
  }
}
