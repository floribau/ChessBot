package Game;

public class Player {
  private PlayerColor color;
  private boolean humanPlayer;
  private GameEngine game;

  public Player(PlayerColor color, boolean humanPlayer) {
    this.color = color;
    this.humanPlayer = humanPlayer;
  }

  public PlayerColor getColor() {
    return color;
  }

  public boolean isHumanPlayer() {
    return humanPlayer;
  }

  public void makeMove(){
    GameEngine.activateColorButtons(this.color);
  }
}
