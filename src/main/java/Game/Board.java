package Game;

import Util.Exception.IllegalMoveException;
import Util.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

  private final String[][] board;
  private Map<String, Piece> pieces;
  private List<Move> possibleMovesForWhite;
  private List<Move> possibleMovesForBlack;
  private boolean whiteCastled;
  private boolean blackCastled;

  public Board() {
    this.board = new String[8][8];
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 7; j++) {
        this.board[i][j] = "";
      }
    }
    whiteCastled = false;
    blackCastled = false;
  }

  private Board(Board board) {
    this.board = new String[8][8];
    for (int i = 0; i <= 7; i++) {
      System.arraycopy(board.getBoard()[i], 0, this.board[i], 0, 8);
    }
    this.whiteCastled = board.whiteCastled;
    this.blackCastled = board.blackCastled;

    this.pieces = new HashMap<>();
    for (Piece p : board.pieces.values()) {
      this.pieces.put(p.getId(), p.copyPiece());
    }
  }

  /**
   * Provides deep copy of the Board instance
   */
  public Board copyBoard() {
    return new Board(this);
  }

  public void initBoard() {
    pieces = new HashMap<>();
    initPieces(PlayerColor.WHITE);
    initPieces(PlayerColor.BLACK);
    calcPossibleMoves(PlayerColor.WHITE);
  }

  private void initPieces(PlayerColor color) {
    int pawnRow = color == PlayerColor.WHITE ? 6 : 1;
    int mainRow = color == PlayerColor.WHITE ? 7 : 0;

    addPiece(new Piece(PieceType.KING, color), mainRow, 4);
    addPiece(new Piece(PieceType.QUEEN, color), mainRow, 3);
    addPiece(new Piece(PieceType.ROOK, color), mainRow, 0);
    addPiece(new Piece(PieceType.ROOK, color), mainRow, 7);
    addPiece(new Piece(PieceType.BISHOP, color), mainRow, 2);
    addPiece(new Piece(PieceType.BISHOP, color), mainRow, 5);
    addPiece(new Piece(PieceType.KNIGHT, color), mainRow, 1);
    addPiece(new Piece(PieceType.KNIGHT, color), mainRow, 6);

    for (int i = 0; i < 8; i++) {
      addPiece(new Piece(PieceType.PAWN, color), pawnRow, i);
    }
  }

  public void initPuzzle() {
    pieces = new HashMap<>();
    // white pieces
    addPiece(new Piece(PieceType.KNIGHT, PlayerColor.WHITE), 2, 5);
    addPiece(new Piece(PieceType.PAWN, PlayerColor.WHITE), 4, 3);
    addPiece(new Piece(PieceType.PAWN, PlayerColor.WHITE), 5, 2);
    addPiece(new Piece(PieceType.PAWN, PlayerColor.WHITE), 6, 1);
    addPiece(new Piece(PieceType.PAWN, PlayerColor.WHITE), 6, 7);
    addPiece(new Piece(PieceType.BISHOP, PlayerColor.WHITE), 5, 3);
    addPiece(new Piece(PieceType.QUEEN, PlayerColor.WHITE), 5, 4);
    addPiece(new Piece(PieceType.KING, PlayerColor.WHITE), 6, 3);
    addPiece(new Piece(PieceType.ROOK, PlayerColor.WHITE), 7, 6);
    // black pieces
    addPiece(new Piece(PieceType.KING, PlayerColor.BLACK), 0, 5);
    addPiece(new Piece(PieceType.ROOK, PlayerColor.BLACK), 0, 7);
    addPiece(new Piece(PieceType.PAWN, PlayerColor.BLACK), 1, 0);
    addPiece(new Piece(PieceType.PAWN, PlayerColor.BLACK), 1, 1);
    addPiece(new Piece(PieceType.PAWN, PlayerColor.BLACK), 1, 2);
    addPiece(new Piece(PieceType.PAWN, PlayerColor.BLACK), 1, 7);
    addPiece(new Piece(PieceType.BISHOP, PlayerColor.BLACK), 2, 1);
    addPiece(new Piece(PieceType.PAWN, PlayerColor.BLACK), 2, 6);
    addPiece(new Piece(PieceType.QUEEN, PlayerColor.BLACK), 6, 0);
  }

  private void addPiece(Piece piece, int row, int col) {
    pieces.put(piece.getId(), piece);
    setPieceAt(new Position(row, col), piece.getId());
  }


  public boolean move(Move move) {
    String destinationPieceId = getPieceAt(move.getNewPosition());
    Piece movedPiece = getPieceById(move.getMovedPiece().getId());

    //capture piece
    if (isSquareOccupied(move.getNewPosition())) {
      Piece capturedPiece = getPieceById(destinationPieceId);
      if (capturedPiece.getColor().equals(movedPiece.getColor())) {
        new IllegalMoveException(move).printStackTrace();
        return false;
      }
      pieces.remove(destinationPieceId);
    }

    // move piece
    Position oldPos = move.getOldPosition();
    Position newPos = move.getNewPosition();
    this.setPieceAt(oldPos, "");
    this.setPieceAt(newPos, movedPiece.getId());
    movedPiece.setHasMoved();

    // en passant is possible for opponent
    if (move.isPawnMove() && move.isMultiSquareMove()) {
      movedPiece.setEnPassantPossible();
    }

    // en passant
    if (move.isEnPassant()) {
      handleEnPassant(move, movedPiece, newPos);
    }

    if (move.getMoveString().equals("O-O")) {
      //king-side castles
      handleCastle(newPos, 7);
    } else if (move.getMoveString().equals("O-O-O")) {
      //queen-side castles
      handleCastle(newPos, 0);
    }

    // pawn promotion
    if (move.isPromotion()) {
      handlePromotion(move, movedPiece, newPos);
    }

    return true;
  }

  private void handleEnPassant(Move move, Piece movedPiece, Position newPos) {
    int newRow =
        newPos.row + (movedPiece.getColor().equals(PlayerColor.WHITE) ? 1 : -1);
    Position capturedPos = new Position(newRow, newPos.col);
    if (isSquareOccupied(capturedPos)) {
      pieces.remove(getPieceAt(capturedPos));
      setPieceAt(capturedPos, "");
    } else {
      new IllegalMoveException(move).printStackTrace();
    }
  }

  private void handleCastle(Position newPos, int rookCol) {
    int newCol = newPos.col + (rookCol == 7 ? -1 : 1);
    Position oldPosRook = new Position(newPos.row, rookCol);
    Position newPosRook = new Position(newPos.row, newCol);
    String rookId = getPieceAt(oldPosRook);
    Piece rookPiece = getPieceById(rookId);
    if (rookPiece != null) {
      this.setPieceAt(oldPosRook, "");
      this.setPieceAt(newPosRook, rookId);
      rookPiece.setHasMoved();
      setHasCastled(rookPiece.getColor());
    }
  }

  private void handlePromotion(Move move, Piece movedPiece, Position newPos) {
    String promotion = move.getPromotionPiece();
    if (promotion != null) {
      PieceType type;
      switch (promotion) {
        case "Q" -> type = PieceType.QUEEN;
        case "R" -> type = PieceType.ROOK;
        case "B" -> type = PieceType.BISHOP;
        case "N" -> type = PieceType.KNIGHT;
        default -> {
          new IllegalMoveException(move).printStackTrace();
          type = null;
        }
      }
      Piece p = new Piece(type, movedPiece.getColor());
      p.setHasMoved();
      pieces.put(p.getId(), p);
      this.setPieceAt(newPos, p.getId());
      pieces.remove(movedPiece.getId());
    } else {
      new IllegalMoveException(move).printStackTrace();
    }
  }

  public String[][] getBoard() {
    return this.board;
  }

  public String getPieceAt(Position pos) {
    return board[pos.row][pos.col];
  }

  public Piece getPieceById(String pieceId) {
    return pieces.get(pieceId);
  }

  public void setPieceAt(Position pos, String pieceId) {
    board[pos.row][pos.col] = pieceId;
  }

  public Position getPositionOfPiece(String pieceId) {
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 7; j++) {
        if (board[i][j].equals(pieceId)) {
          return new Position(i, j);
        }
      }
    }
    return null;
  }

  public Position getPositionOfPiece(Piece p) {
    return getPositionOfPiece(p.getId());
  }

  public List<Piece> getPieces() {
    return new ArrayList<>(pieces.values());
  }

  public List<Piece> getPieces(PlayerColor color) {
    List<Piece> colorPieces = new ArrayList<>();
    for (Piece p : pieces.values()) {
      if (p.getColor().equals(color)) {
        colorPieces.add(p);
      }
    }
    return colorPieces;
  }

  public Position getKingPosition(PlayerColor color) {
    return getPositionOfPiece(PieceType.KING + "_" + color + "0");
  }

  public synchronized List<Move> calcPossibleMoves(PlayerColor color) {
    List<Move> moves = new ArrayList<>();
    for (Move m : calcLegalMovesOnBoard(color)) {
      if (m.isCastle()) {
        int neighborCol = m.getOldPosition().col + (m.getNewPosition().col - m.getOldPosition().col) / 2;
        Move neighborSquareMove = new Move(m.getOldPosition(), new Position(m.getNewPosition().row, neighborCol), m.getMovedPiece());
        if (calcMoveToBoard(neighborSquareMove).isKingInCheck(color)) {
          continue;
        }
      }
      if (!calcMoveToBoard(m).isKingInCheck(color)) {
        moves.add(m);
      }
    }
    switch (color) {
      case WHITE -> possibleMovesForWhite = moves;
      case BLACK -> possibleMovesForBlack = moves;
    }
    return moves;
  }

  public synchronized List<Move> calcPossibleMovesForPiece(String pieceId) {
    List<Move> possibleMoves =
        getPieceById(pieceId).getColor().equals(PlayerColor.WHITE) ? possibleMovesForWhite
            : possibleMovesForBlack;
    List<Move> moves = new ArrayList<>();
    for (Move m : possibleMoves) {
      if (m.getMovedPiece().getId().equals(pieceId)) {
        moves.add(m);
      }
    }
    return moves;
  }

  public synchronized Board calcMoveToBoard(Move move) {
    Board newBoard = copyBoard();
    newBoard.move(move);
    return newBoard;
  }

  public synchronized List<Move> calcLegalMovesOnBoard(PlayerColor color) {
    List<Move> moves = new ArrayList<>();
    for (Piece p : getPieces(color)) {
      for (Move m : calcMovesOnBoardForPiece(p.getId())) {
        if (isLegalMove(m)) {
          moves.add(m);
        }
      }
    }
    return moves;
  }

  /***
   * Calculates all moves on board obeying the move patterns of the piece. Pieces in the way and checks are disregarded.
   * @param pieceId the piece to be moved
   * @return the list of moves on board
   */
  private synchronized List<Move> calcMovesOnBoardForPiece(String pieceId) {
    return MoveCalculator.calcMovesOnBoardForPiece(this, pieceId);
  }

  /**
   * Checks whether a given move obeys to the rules of chess, or whether it is, e.g., blocked by a
   * piece. This method ignores whether the result of the move is a check on the own king
   *
   * @param move
   * @return
   */
  private synchronized boolean isLegalMove(Move move) {
    Piece movedPiece = move.getMovedPiece();

    // check if move tries to capture own piece
    if (isSquareOccupied(move.getNewPosition())) {
      Piece destinationPiece = getPieceById(getPieceAt(move.getNewPosition()));
      if (destinationPiece.getColor().equals(movedPiece.getColor())) {
        return false;
      }
    }

    // check if any piece blocks the way for multi-square move
    if (move.isMultiSquareMove()) {
      int oldRow = move.getOldPosition().row;
      int newRow = move.getNewPosition().row;
      int oldCol = move.getOldPosition().col;
      int newCol = move.getNewPosition().col;
      if (move.isParallelMove()) {
        if (oldRow < newRow) {
          for (int i = oldRow + 1; i < newRow; i++) {
            if (isSquareOccupied(new Position(i, oldCol))) {
              return false;
            }
          }
        } else if (oldRow > newRow) {
          for (int i = oldRow - 1; i > newRow; i--) {
            if (isSquareOccupied(new Position(i, oldCol))) {
              return false;
            }
          }
        } else if (oldCol < newCol) {
          for (int i = oldCol + 1; i < newCol; i++) {
            if (isSquareOccupied(new Position(oldRow, i))) {
              return false;
            }
          }
        } else if (oldCol > newCol) {
          for (int i = oldCol - 1; i > newCol; i--) {
            if (isSquareOccupied(new Position(oldRow, i))) {
              return false;
            }
          }
        } else {
          new IllegalMoveException(move).printStackTrace();
        }
      } else if (move.isDiagonalMove()) {
        int diff = Math.abs(oldRow - newRow);
        if (oldRow < newRow && oldCol < newCol) {
          for (int i = 1; i < diff; i++) {
            if (isSquareOccupied(new Position(oldRow + i, oldCol + i))) {
              return false;
            }
          }
        } else if (oldRow < newRow && oldCol > newCol) {
          for (int i = 1; i < diff; i++) {
            if (isSquareOccupied(new Position(oldRow + i, oldCol - i))) {
              return false;
            }
          }
        } else if (oldRow > newRow && oldCol < newCol) {
          for (int i = 1; i < diff; i++) {
            if (isSquareOccupied(new Position(oldRow - i, oldCol + i))) {
              return false;
            }
          }
        } else if (oldRow > newRow && oldCol > newCol) {
          for (int i = 1; i < diff; i++) {
            if (isSquareOccupied(new Position(oldRow - i, oldCol - i))) {
              return false;
            }
          }
        } else {
          new IllegalMoveException(move).printStackTrace();
        }
      }
    }

    if (move.isPawnMove()) {
      // check if move tries to push pawn to a blocked square
      return !move.isParallelMove() || !isSquareOccupied(move.getNewPosition());
    }

    return true;
  }

  public synchronized List<Piece> calcMovablePieces(PlayerColor playerColor) {
    List<Piece> movablePieces = new ArrayList<>();
    for (Piece p : getPieces(playerColor)) {
      if (calcPossibleMovesForPiece(p.getId()).size() > 0) {
        movablePieces.add(p);
      }
    }
    return movablePieces;
  }

  /**
   * Checks if the player with the specified color is checkmated on this board
   *
   * @param color the color of the player
   * @return true if it is a checkmate
   */
  public synchronized boolean isCheckmate(PlayerColor color) {
    List<Move> possibleMoves =
        color.equals(PlayerColor.WHITE) ? possibleMovesForWhite : possibleMovesForBlack;
    return isKingInCheck(color) && possibleMoves.size() == 0;
  }

  /**
   * Checks if it is a stalemate, i.e., if the player with the specified color does not have any
   * move left but is not checked.
   *
   * @param color the color of the player
   * @return true if it is a stalemate
   */
  public synchronized boolean isStalemate(PlayerColor color) {
    return !isKingInCheck(color) && calcPossibleMoves(color).size() == 0;
  }

  public synchronized boolean isKingInCheck(PlayerColor color) {
    for (Move m : calcLegalMovesOnBoard(color.getOppositeColor())) {
      if (m.getNewPosition().equals(getKingPosition(color))) {
        return true;
      }
    }
    return false;
  }

  public synchronized void resetEnPassantPossible(PlayerColor color) {
    for (Piece piece : getPieces(color)) {
      if (piece.getType().equals(PieceType.PAWN)) {
        piece.resetEnPassantPossible();
      }
    }
  }

  public synchronized boolean isSquareOccupied(Position pos) {
    return !getPieceAt(pos).equals("");
  }

  public synchronized List<Move> getPossibleMoves(PlayerColor player) {
    return player.equals(PlayerColor.WHITE) ? possibleMovesForWhite : possibleMovesForBlack;
  }

  public synchronized boolean isCapture(Move m) {
    return isSquareOccupied(m.getNewPosition());
  }

  public synchronized boolean isMoveCheck(Move m) {
    PlayerColor opponent = m.getMovedPiece().getColor().getOppositeColor();
    return calcMoveToBoard(m).isKingInCheck(opponent);
  }

  public synchronized List<Piece> getMajorPieces() {
    return pieces.values().stream()
        .filter(piece -> (piece.getType() != PieceType.PAWN) && piece.getType() != PieceType.KING)
        .collect(Collectors.toList());
  }

  public synchronized List<Piece> getMajorPieces(PlayerColor color) {
    return getMajorPieces().stream().filter((piece -> piece.getColor() == color))
        .collect(Collectors.toList());
  }

  public synchronized int countMajorPieces() {
    return getMajorPieces().size();
  }

  public synchronized void setHasCastled(PlayerColor color) {
    switch (color) {
      case WHITE -> whiteCastled = true;
      case BLACK -> blackCastled = true;
    }
  }

  public synchronized boolean hasCastled(PlayerColor color) {
    return color == PlayerColor.WHITE ? whiteCastled : blackCastled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Board b = (Board) o;
    return Arrays.deepEquals(board, b.board);
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 7; j++) {
        res.append("[").append(board[i][j]).append("]");
      }
      res.append("\n");
    }
    return res.toString();
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(board);
  }

}
