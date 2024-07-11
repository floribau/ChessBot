package Game;

import Util.Exception.IllegalMoveException;
import Util.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {

  String[][] board;
  private List<Piece> pieces;

  public Board() {
    this.board = new String[8][8];
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 7; j++) {
        this.board[i][j] = "";
      }
    }
  }

  public Board(String[][] board) {

  }

  private Board(Board board) {
    String[][] boardString = board.getBoard();
    this.board = new String[8][8];
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 7; j++) {
        this.board[i][j] = boardString[i][j];
      }
    }
    this.pieces = new ArrayList<>();
    for (Piece p : board.pieces) {
      this.pieces.add(p.copyPiece());
    }
  }

  /**
   * Provides deep copy of the Board instance
   */
  public Board copyBoard() {
    return new Board(this);
  }

  public void initBoard() {
    pieces = new ArrayList<>();

    Piece king_white = new Piece(PieceType.KING_WHITE, PlayerColor.WHITE);
    Piece queen_white = new Piece(PieceType.QUEEN_WHITE, PlayerColor.WHITE);
    Piece rook_white_0 = new Piece(PieceType.ROOK_WHITE, PlayerColor.WHITE);
    Piece rook_white_1 = new Piece(PieceType.ROOK_WHITE, PlayerColor.WHITE);
    Piece bishop_white_0 = new Piece(PieceType.BISHOP_WHITE, PlayerColor.WHITE);
    Piece bishop_white_1 = new Piece(PieceType.BISHOP_WHITE, PlayerColor.WHITE);
    Piece knight_white_0 = new Piece(PieceType.KNIGHT_WHITE, PlayerColor.WHITE);
    Piece knight_white_1 = new Piece(PieceType.KNIGHT_WHITE, PlayerColor.WHITE);
    Piece pawn_white_0 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_1 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_2 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_3 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_4 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_5 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_6 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);
    Piece pawn_white_7 = new Piece(PieceType.PAWN_WHITE, PlayerColor.WHITE);

    pieces.add(king_white);
    pieces.add(queen_white);
    pieces.add(rook_white_0);
    pieces.add(rook_white_1);
    pieces.add(bishop_white_0);
    pieces.add(bishop_white_1);
    pieces.add(knight_white_0);
    pieces.add(knight_white_1);
    pieces.add(pawn_white_0);
    pieces.add(pawn_white_1);
    pieces.add(pawn_white_2);
    pieces.add(pawn_white_3);
    pieces.add(pawn_white_4);
    pieces.add(pawn_white_5);
    pieces.add(pawn_white_6);
    pieces.add(pawn_white_7);

    Piece king_black = new Piece(PieceType.KING_BLACK, PlayerColor.BLACK);
    Piece queen_black = new Piece(PieceType.QUEEN_BLACK, PlayerColor.BLACK);
    Piece rook_black_0 = new Piece(PieceType.ROOK_BLACK, PlayerColor.BLACK);
    Piece rook_black_1 = new Piece(PieceType.ROOK_BLACK, PlayerColor.BLACK);
    Piece bishop_black_0 = new Piece(PieceType.BISHOP_BLACK, PlayerColor.BLACK);
    Piece bishop_black_1 = new Piece(PieceType.BISHOP_BLACK, PlayerColor.BLACK);
    Piece knight_black_0 = new Piece(PieceType.KNIGHT_BLACK, PlayerColor.BLACK);
    Piece knight_black_1 = new Piece(PieceType.KNIGHT_BLACK, PlayerColor.BLACK);
    Piece pawn_black_0 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_1 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_2 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_3 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_4 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_5 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_6 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);
    Piece pawn_black_7 = new Piece(PieceType.PAWN_BLACK, PlayerColor.BLACK);

    pieces.add(king_black);
    pieces.add(queen_black);
    pieces.add(rook_black_0);
    pieces.add(rook_black_1);
    pieces.add(bishop_black_0);
    pieces.add(bishop_black_1);
    pieces.add(knight_black_0);
    pieces.add(knight_black_1);
    pieces.add(pawn_black_0);
    pieces.add(pawn_black_1);
    pieces.add(pawn_black_2);
    pieces.add(pawn_black_3);
    pieces.add(pawn_black_4);
    pieces.add(pawn_black_5);
    pieces.add(pawn_black_6);
    pieces.add(pawn_black_7);

    this.setPieceAt(new Position(0, 0), rook_black_0.getId());
    this.setPieceAt(new Position(0, 1), knight_black_0.getId());
    this.setPieceAt(new Position(0, 2), bishop_black_0.getId());
    this.setPieceAt(new Position(0, 3), queen_black.getId());
    this.setPieceAt(new Position(0, 4), king_black.getId());
    this.setPieceAt(new Position(0, 5), bishop_black_1.getId());
    this.setPieceAt(new Position(0, 6), knight_black_1.getId());
    this.setPieceAt(new Position(0, 7), rook_black_1.getId());

    this.setPieceAt(new Position(1, 0), pawn_black_0.getId());
    this.setPieceAt(new Position(1, 1), pawn_black_1.getId());
    this.setPieceAt(new Position(1, 2), pawn_black_2.getId());
    this.setPieceAt(new Position(1, 3), pawn_black_3.getId());
    this.setPieceAt(new Position(1, 4), pawn_black_4.getId());
    this.setPieceAt(new Position(1, 5), pawn_black_5.getId());
    this.setPieceAt(new Position(1, 6), pawn_black_6.getId());
    this.setPieceAt(new Position(1, 7), pawn_black_7.getId());

    this.setPieceAt(new Position(7, 0), rook_white_0.getId());
    this.setPieceAt(new Position(7, 1), knight_white_0.getId());
    this.setPieceAt(new Position(7, 2), bishop_white_0.getId());
    this.setPieceAt(new Position(7, 3), queen_white.getId());
    this.setPieceAt(new Position(7, 4), king_white.getId());
    this.setPieceAt(new Position(7, 5), bishop_white_1.getId());
    this.setPieceAt(new Position(7, 6), knight_white_1.getId());
    this.setPieceAt(new Position(7, 7), rook_white_1.getId());

    this.setPieceAt(new Position(6, 0), pawn_white_0.getId());
    this.setPieceAt(new Position(6, 1), pawn_white_1.getId());
    this.setPieceAt(new Position(6, 2), pawn_white_2.getId());
    this.setPieceAt(new Position(6, 3), pawn_white_3.getId());
    this.setPieceAt(new Position(6, 4), pawn_white_4.getId());
    this.setPieceAt(new Position(6, 5), pawn_white_5.getId());
    this.setPieceAt(new Position(6, 6), pawn_white_6.getId());
    this.setPieceAt(new Position(6, 7), pawn_white_7.getId());
  }


  public boolean move(Move move) {
    String destinationPieceId = getPieceAt(move.getNewPosition());
    Piece movedPiece = getPieceById(move.getMovedPiece().getId());

    //capture piece
    if (destinationPieceId != "") {
      Piece capturedPiece = getPieceById(destinationPieceId);
      if (capturedPiece.getColor().equals(movedPiece.getColor())) {
        new IllegalMoveException(move).printStackTrace();
        return false;
      }
      pieces.remove(getPieceById(destinationPieceId));
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
      if (movedPiece.getColor().equals(PlayerColor.WHITE)) {
        Position capturedPos = new Position(newPos.row + 1, newPos.col);
        String capturedPieceId = getPieceAt(capturedPos);
        if (capturedPieceId != "") {
          Piece capturedPiece = getPieceById(capturedPieceId);
          setPieceAt(capturedPos, "");
          pieces.remove(capturedPiece);
        } else {
          new IllegalMoveException(move).printStackTrace();
        }
      } else {
        // PlayerColor.BLACK
        Position capturedPos = new Position(newPos.row - 1, newPos.col);
        String capturedPieceId = getPieceAt(capturedPos);
        if (capturedPieceId != "") {
          Piece capturedPiece = getPieceById(capturedPieceId);
          setPieceAt(capturedPos, "");
          pieces.remove(capturedPiece);
        } else {
          new IllegalMoveException(move).printStackTrace();
        }
      }
    }

    //king-side castles
    if (move.getMoveString().equals("O-O")) {
      Position oldPosRook =
          movedPiece.getColor() == PlayerColor.WHITE ? new Position(7, 7) : new Position(0, 7);
      Position newPosRook = new Position(oldPosRook.row, newPos.col - 1);
      String rookId = getPieceAt(oldPosRook);
      Piece rookPiece = getPieceById(rookId);
      this.setPieceAt(oldPosRook, "");
      this.setPieceAt(newPosRook, rookId);
      rookPiece.setHasMoved();
      //queen-side castles
    } else if (move.getMovedPiece().equals("O-O-O")) {
      Position oldPosRook =
          movedPiece.getColor() == PlayerColor.WHITE ? new Position(7, 0) : new Position(0, 0);
      Position newPosRook = new Position(oldPosRook.row, newPos.col + 1);
      String rookId = getPieceAt(oldPosRook);
      Piece rookPiece = getPieceById(rookId);
      this.setPieceAt(oldPosRook, "");
      this.setPieceAt(newPosRook, rookId);
      rookPiece.setHasMoved();
    }

    // pawn promotion
    if(move.isPromotion()){
      String symbolPattern = "=([^=])";
      Pattern pattern = Pattern.compile(symbolPattern);
      Matcher matcher = pattern.matcher(move.getMoveString());
      String promotion;
      if (matcher.find()) {
        promotion = matcher.group(1);
      } else {
        new IllegalMoveException(move).printStackTrace();
        promotion = null;
      }
      PieceType type = null;
      Piece p;
      if (move.getMovedPiece().getColor().equals(PlayerColor.WHITE)) {
        switch (promotion) {
          case "Q" -> type = PieceType.QUEEN_WHITE;
          case "R" -> type = PieceType.ROOK_WHITE;
          case "B" -> type = PieceType.BISHOP_WHITE;
          case "N" -> type = PieceType.KNIGHT_WHITE;
          default -> new IllegalMoveException(move).printStackTrace();
        }
        p = new Piece(type, PlayerColor.WHITE);
      } else {
        switch (promotion) {
          case "Q" -> type = PieceType.QUEEN_BLACK;
          case "R" -> type = PieceType.ROOK_BLACK;
          case "B" -> type = PieceType.BISHOP_BLACK;
          case "N" -> type = PieceType.KNIGHT_BLACK;
          default -> new IllegalMoveException(move).printStackTrace();
        }
        p = new Piece(type, PlayerColor.BLACK);
      }
      p.setHasMoved();
      pieces.add(p);
      this.setPieceAt(newPos, p.getId());
      pieces.remove(movedPiece);
    }
    return true;
  }

  public String[][] getBoard() {
    return this.board;
  }

  public String getPieceAt(Position pos) {
    return board[pos.row][pos.col];
  }

  public Piece getPieceById(String pieceId) {
    for (Piece piece : pieces) {
      if (piece.getId().equals(pieceId)) {
        return piece;
      }
    }
    return null;
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

  public String toString() {
    String res = "";
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 7; j++) {
        res += "[" + board[i][j] + "]";
      }
      res += "\n";
    }
    return res;
  }

  public List<Piece> getPieces() {
    return pieces;
  }

  public List<Piece> getPieces(PlayerColor color) {
    List<Piece> colorPieces = new ArrayList<>();
    for (Piece p : pieces) {
      if (p.getColor().equals(color)) {
        colorPieces.add(p);
      }
    }
    return colorPieces;
  }

  public Position getKingPosition(PlayerColor color) {
    switch (color) {
      case WHITE -> {
        return getPositionOfPiece(PieceType.KING_WHITE + "0");
      }
      case BLACK -> {
        return getPositionOfPiece(PieceType.KING_BLACK + "0");
      }
      default -> {
        return null;
      }
    }
  }

  public List<Move> getCastleMoves(PlayerColor color) {
    List<Move> moves = new ArrayList<>();
    Piece kingPiece = getPieceById("KING_" + color + "0");
    Piece rookPiece0 = getPieceById("ROOK_" + color + "0");
    Piece rookPiece1 = getPieceById("ROOK_" + color + "1");

    if (!kingPiece.hasMoved()) {
      Position oldKingPosition = getPositionOfPiece(kingPiece.getId());
      if (!(rookPiece0 == null) && kingPiece.getColor().equals(rookPiece0.getColor()) && !rookPiece0.hasMoved()) {
        Position newKingPosition = new Position(oldKingPosition.row, oldKingPosition.col - 2);
        Move bigCastle = new Move(oldKingPosition, newKingPosition, kingPiece);
        moves.add(bigCastle);
      }
      if (!(rookPiece1 == null) && kingPiece.getColor().equals(rookPiece1.getColor()) && !rookPiece1.hasMoved()) {
        Position newKingPosition = new Position(oldKingPosition.row, oldKingPosition.col + 2);
        Move smallCastle = new Move(oldKingPosition, newKingPosition, kingPiece);
        moves.add(smallCastle);
      }
    }

    return moves;
  }

  public synchronized List<Board> calcPossibleBoards(PlayerColor playerColor) {
    List<Board> boards = new ArrayList<>();
    for (Move move : calcPossibleMoves(playerColor)) {
      boards.add(calcMoveToBoard(move));
    }
    return boards;
  }

  public synchronized List<Move> calcPossibleMoves(PlayerColor playerColor) {
    List<Move> moves = new ArrayList<>();
    for (Piece piece : getPieces(playerColor)) {
      for (Move move : calcMovesForPiece(piece.getId())) {
        moves.add(move);
      }
    }
    return moves;
  }

  public synchronized Board calcMoveToBoard(Move move) {
    Board newBoard = copyBoard();
    newBoard.move(move);
    return newBoard;
  }

  public synchronized List<Move> calcMovesForPiece(String pieceId) {
    List<Move> moves = new ArrayList<>();
    for (Move m : calcMovesOnBoardForPiece(pieceId)) {
      if (isLegalMove(m)) {
        moves.add(m);
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
    List<Move> moves = new ArrayList<>();
    Position fromPos = getPositionOfPiece(pieceId);
    Piece piece = getPieceById(pieceId);
    if (piece.getType() == PieceType.KING_WHITE || piece.getType() == PieceType.KING_BLACK) {
      int newRow = fromPos.row + 1;
      int newCol = fromPos.col;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row - 1;
      newCol = fromPos.col;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row;
      newCol = fromPos.col + 1;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row;
      newCol = fromPos.col - 1;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row + 1;
      newCol = fromPos.col + 1;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row + 1;
      newCol = fromPos.col - 1;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row - 1;
      newCol = fromPos.col + 1;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row - 1;
      newCol = fromPos.col - 1;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      for (Move m : getCastleMoves(piece.getColor())) {
        moves.add(m);
      }

    } else if (piece.getType() == PieceType.QUEEN_WHITE
        || piece.getType() == PieceType.QUEEN_BLACK) {
      for (Move m : calcParallelMoves(fromPos)) {
        moves.add(m);
      }
      for (Move m : calcDiagonalMoves(fromPos)) {
        moves.add(m);
      }

    } else if (piece.getType() == PieceType.ROOK_WHITE || piece.getType() == PieceType.ROOK_BLACK) {
      for (Move m : calcParallelMoves(fromPos)) {
        moves.add(m);
      }

    } else if (piece.getType() == PieceType.BISHOP_WHITE
        || piece.getType() == PieceType.BISHOP_BLACK) {
      for (Move m : calcDiagonalMoves(fromPos)) {
        moves.add(m);
      }

    } else if (piece.getType() == PieceType.KNIGHT_WHITE
        || piece.getType() == PieceType.KNIGHT_BLACK) {
      int newRow = fromPos.row + 2;
      int newCol = fromPos.col + 1;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row + 2;
      newCol = fromPos.col - 1;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row - 2;
      newCol = fromPos.col + 1;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row - 2;
      newCol = fromPos.col - 1;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row + 1;
      newCol = fromPos.col + 2;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row - 1;
      newCol = fromPos.col + 2;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row + 1;
      newCol = fromPos.col - 2;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }
      newRow = fromPos.row - 1;
      newCol = fromPos.col - 2;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(fromPos, new Position(newRow, newCol), piece));
      }

    } else if (piece.getType() == PieceType.PAWN_WHITE) {
      if(fromPos.row - 1 >= 1) {
        moves.add(new Move(fromPos, new Position(fromPos.row - 1, fromPos.col), piece));
      } else {
        // pawn promotion on row 0 (= rank 8)
        String moveStr = Move.calcMoveString(fromPos, new Position(fromPos.row - 1, fromPos.col), piece);
        moves.add(new Move(moveStr + "=Q", piece));
        moves.add(new Move(moveStr + "=R", piece));
        moves.add(new Move(moveStr + "=B", piece));
        moves.add(new Move(moveStr + "=N", piece));
      }
      if (!piece.hasMoved()) {
        moves.add(new Move(fromPos, new Position(fromPos.row - 2, fromPos.col), piece));
      }
      //captures
      Position leftCapture = new Position(fromPos.row - 1, fromPos.col - 1);
      Position rightCapture = new Position(fromPos.row - 1, fromPos.col + 1);
      if (Position.isOnBoard(leftCapture.row, leftCapture.col) && getPieceAt(leftCapture) != "") {
        if (leftCapture.row >= 1) {
          moves.add(new Move(fromPos, leftCapture, piece));
        } else {
          // leftCapture on row 0 (= rank 8)
          String moveStr = Move.calcMoveString(fromPos, leftCapture, piece);
          moves.add(new Move(moveStr + "=Q", piece));
          moves.add(new Move(moveStr + "=R", piece));
          moves.add(new Move(moveStr + "=B", piece));
          moves.add(new Move(moveStr + "=N", piece));
        }
      }
      if (Position.isOnBoard(rightCapture.row, rightCapture.col)
          && getPieceAt(rightCapture) != "") {
        if (rightCapture.row >= 1) {
          moves.add(new Move(fromPos, rightCapture, piece));
        } else {
          String moveStr = Move.calcMoveString(fromPos, rightCapture, piece);
          moves.add(new Move(moveStr + "=Q", piece));
          moves.add(new Move(moveStr + "=R", piece));
          moves.add(new Move(moveStr + "=B", piece));
          moves.add(new Move(moveStr + "=N", piece));
        }
      }
      // en passant
      if (Position.isOnBoard(fromPos.row, fromPos.col -1)) {
        String leftPieceId = getPieceAt(new Position(fromPos.row, fromPos.col - 1));
        if(leftPieceId != "") {
          Piece leftPiece = getPieceById(leftPieceId);
          if (leftPiece.isEnPassantPossible()) {
            moves.add(new Move(fromPos, leftCapture, piece, true));
          }
        }
      }
      if (Position.isOnBoard(fromPos.row, fromPos.col + 1)) {
        String rightPieceId = getPieceAt(new Position(fromPos.row, fromPos.col + 1));
        if(rightPieceId != "") {
          Piece rightPiece = getPieceById(rightPieceId);
          if (rightPiece.isEnPassantPossible()) {
            moves.add(new Move(fromPos, rightCapture, piece, true));
          }
        }
      }

    } else if (piece.getType() == PieceType.PAWN_BLACK) {
      if(fromPos.row - 1 <= 6) {
        moves.add(new Move(fromPos, new Position(fromPos.row + 1, fromPos.col), piece));
      } else {
        // pawn promotion on row 7 (= rank 1)
        String moveStr = Move.calcMoveString(fromPos, new Position(fromPos.row + 1, fromPos.col), piece);
        moves.add(new Move(moveStr + "=Q", piece));
        moves.add(new Move(moveStr + "=R", piece));
        moves.add(new Move(moveStr + "=B", piece));
        moves.add(new Move(moveStr + "=N", piece));
      }
      if (!piece.hasMoved()) {
        moves.add(new Move(fromPos, new Position(fromPos.row + 2, fromPos.col), piece));
      }
      //captures
      Position leftCapture = new Position(fromPos.row + 1, fromPos.col - 1);
      Position rightCapture = new Position(fromPos.row + 1, fromPos.col + 1);
      if (Position.isOnBoard(leftCapture.row, leftCapture.col) && getPieceAt(leftCapture) != "") {
        if (leftCapture.row <= 6) {
          moves.add(new Move(fromPos, leftCapture, piece));
        } else {
          String moveStr = Move.calcMoveString(fromPos, leftCapture, piece);
          moves.add(new Move(moveStr + "=Q", piece));
          moves.add(new Move(moveStr + "=R", piece));
          moves.add(new Move(moveStr + "=B", piece));
          moves.add(new Move(moveStr + "=N", piece));
        }
      }
      if (Position.isOnBoard(rightCapture.row, rightCapture.col)
          && getPieceAt(rightCapture) != "") {
        if (rightCapture.row <= 6) {
          moves.add(new Move(fromPos, rightCapture, piece));
        } else {
          String moveStr = Move.calcMoveString(fromPos, rightCapture, piece);
          moves.add(new Move(moveStr + "=Q", piece));
          moves.add(new Move(moveStr + "=R", piece));
          moves.add(new Move(moveStr + "=B", piece));
          moves.add(new Move(moveStr + "=N", piece));
        }
      }
      // en passant
      if (Position.isOnBoard(fromPos.row, fromPos.col -1)) {
        String leftPieceId = getPieceAt(new Position(fromPos.row, fromPos.col - 1));
        if(leftPieceId != "") {
          Piece leftPiece = getPieceById(leftPieceId);
          if (leftPiece.isEnPassantPossible()) {
            moves.add(new Move(fromPos, leftCapture, piece, true));
          }
        }
      }
      if (Position.isOnBoard(fromPos.row, fromPos.col + 1)) {
        String rightPieceId = getPieceAt(new Position(fromPos.row, fromPos.col + 1));
        if(rightPieceId != "") {
          Piece rightPiece = getPieceById(rightPieceId);
          if (rightPiece.isEnPassantPossible()) {
            moves.add(new Move(fromPos, rightCapture, piece, true));
          }
        }
      }

    } else {
      // some error occurred
    }
    return moves;
  }

  /**
   * Calculates all parallel moves from the starting position that are still on the board
   *
   * @param pos the starting position
   * @return the list of all parallel moves that are still on the board
   */
  private synchronized List<Move> calcParallelMoves(Position pos) {
    int col = pos.col;
    int row = pos.row;
    Piece piece = getPieceById(getPieceAt(pos));
    List<Move> moves = new ArrayList<>();
    for (int i = 1; i < 8; i++) {
      int newCol = col + i;
      if (Position.isOnBoard(row, newCol)) {
        moves.add(new Move(pos, new Position(row, newCol), piece));
      }
      newCol = col - i;
      if (Position.isOnBoard(row, newCol)) {
        moves.add(new Move(pos, new Position(row, newCol), piece));
      }
      int newRow = row + i;
      if (Position.isOnBoard(newRow, col)) {
        moves.add(new Move(pos, new Position(newRow, col), piece));
      }
      newRow = row - i;
      if (Position.isOnBoard(newRow, col)) {
        moves.add(new Move(pos, new Position(newRow, col), piece));
      }
    }
    return moves;
  }

  /**
   * Calculates all diagonal moves from the starting position that are still on the board
   *
   * @param pos the starting position
   * @return the list of all diagonal moves that are still on the board
   */
  private synchronized List<Move> calcDiagonalMoves(Position pos) {
    int col = pos.col;
    int row = pos.row;
    Piece piece = getPieceById(getPieceAt(pos));
    List<Move> moves = new ArrayList<>();
    for (int i = 1; i <= 7; i++) {
      int newCol = col + i;
      int newRow = row + i;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(pos, new Position(newRow, newCol), piece));
      }
      newCol = col + i;
      newRow = row - i;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(pos, new Position(newRow, newCol), piece));
      }
      newCol = col - i;
      newRow = row + i;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(pos, new Position(newRow, newCol), piece));
      }
      newCol = col - i;
      newRow = row - i;
      if (Position.isOnBoard(newRow, newCol)) {
        moves.add(new Move(pos, new Position(newRow, newCol), piece));
      }
    }
    return moves;
  }

  private synchronized boolean isLegalMove(Move move) {
    Piece movedPiece = move.getMovedPiece();
    PlayerColor color = movedPiece.getColor();

    // check if move tries to capture own piece
    String destinationPieceId = getPieceAt(move.getNewPosition());
    if (destinationPieceId != "") {
      Piece destinationPiece = getPieceById(destinationPieceId);
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
          for(int i=oldRow+1; i<newRow; i++) {
            if(getPieceAt(new Position(i, oldCol)) != "") {
              return false;
            }
          }
        } else if (oldRow > newRow) {
          for(int i=oldRow-1; i>newRow; i--) {
            if (getPieceAt(new Position(i, oldCol)) != "") {
              return false;
            }
          }
        } else if (oldCol < newCol) {
          for(int i=oldCol+1; i<newCol; i++) {
            if(getPieceAt(new Position(oldRow, i)) != "") {
              return false;
            }
          }
        } else if (oldCol > newCol) {
          for(int i=oldCol-1; i>newCol; i--) {
            if (getPieceAt(new Position(oldRow, i)) != "") {
              return false;
            }
          }
        } else {
          new IllegalMoveException(move).printStackTrace();
        }
      } else if (move.isDiagonalMove()) {
        int diff = Math.abs(oldRow - newRow);
        if (oldRow < newRow && oldCol < newCol) {
          for(int i=1; i<diff; i++) {
            if(getPieceAt(new Position(oldRow + i, oldCol + i)) != "") {
              return false;
            }
          }
        } else if (oldRow < newRow && oldCol > newCol) {
          for(int i=1; i<diff; i++) {
            if (getPieceAt(new Position(oldRow + i, oldCol - i)) != "") {
              return false;
            }
          }
        } else if (oldRow > newRow && oldCol < newCol) {
          for(int i=1; i<diff; i++) {
            if(getPieceAt(new Position(oldRow - i, oldCol + i)) != "") {
              return false;
            }
          }
        } else if (oldRow > newRow && oldCol > newCol) {
          for(int i=1; i<diff; i++) {
            if (getPieceAt(new Position(oldRow - i, oldCol - i)) != "") {
              return false;
            }
          }
        } else {
          new IllegalMoveException(move).printStackTrace();
        }
      }
    }

    if(move.isPawnMove()) {
      // check if move tries to push pawn to a blocked square
      if (move.isParallelMove() && getPieceAt(move.getNewPosition()) != "") {
        return false;
      }
    }

    Board newBoard = calcMoveToBoard(move);
    // if(isKingChecked(color, newBoard)){
    // return false;
    // }

    // TODO implement check for checks, for castling, for en passant, and for pieces blocking the way

    return true;
  }

  public synchronized List<Piece> calcMovablePieces(PlayerColor playerColor) {
    List<Piece> movablePieces = new ArrayList<>();
    for (Piece piece : getPieces()) {
      if (piece.getColor() == playerColor && calcMovesForPiece(piece.getId()).size() > 0) {
        movablePieces.add(piece);
      }
    }
    return movablePieces;
  }

  /**
   * Checks if the player with the specified color is checkmated on this board
   *
   * @param color
   * @return true if it is a checkmate
   */
  public synchronized boolean isCheckmate(PlayerColor color) {
    return isKingChecked(color) && calcPossibleMoves(color).size() == 0;
  }

  /**
   * Checks if it is a stalemate, i.e., if the player with the specified color does not have any
   * move left but is not checked.
   *
   * @param color
   * @return true if it is a stalemate
   */
  public synchronized boolean isStalemate(PlayerColor color) {
    return !isKingChecked(color) && calcPossibleMoves(color).size() == 0;
  }

  //TODO check if this works properly
  public synchronized boolean isKingChecked(PlayerColor color) {
    PlayerColor opponentColor = (color == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
    Position kingPosition = getKingPosition(color);
    for (Move m : calcPossibleMoves(opponentColor)) {
      if (m.getNewPosition().equals(kingPosition)) {
        return true;
      }
    }
    return false;
  }

  public synchronized void resetEnPassantPossible(PlayerColor color) {
    for(Piece piece : pieces) {
      PieceType type = (color == PlayerColor.WHITE) ? PieceType.PAWN_WHITE : PieceType.PAWN_BLACK;
      if(piece.getType().equals(type)) {
        piece.resetEnPassantPossible();
      }
    }
  }

}
