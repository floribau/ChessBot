package Util;

import Game.Move;

public record ScoringEntry(Move move, float score) implements Comparable<ScoringEntry> {
  @Override
  public int compareTo(ScoringEntry o) {
    int compareScore = -1 * Float.compare(this.score, o.score);
    if (compareScore == 0) {
      return Math.random() >= 0.5 ? 1 : -1;
    }
    return compareScore;
  }

  public String toString() {
    return "Move: " + move + ", Score: " + score;
  }
}
