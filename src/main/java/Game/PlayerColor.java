package Game;

public enum PlayerColor {
  WHITE, BLACK;

  public PlayerColor getOppositeColor() {
    return this == WHITE ? BLACK : WHITE;
  }

  public boolean isWhite() {
    return this == WHITE;
  }
}
