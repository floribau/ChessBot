package Game;

public enum PlayerColor {
  WHITE, BLACK;

  public PlayerColor getOppositeColor() {
    return this == WHITE ? BLACK : WHITE;
  }
}
