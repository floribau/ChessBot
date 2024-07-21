package Game;

import java.util.Objects;

public record PieceTuple(PieceType type, PlayerColor color) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PieceTuple tuple = (PieceTuple) o;
    return type.equals(tuple.type) && color.equals(tuple.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, color);
  }

}
