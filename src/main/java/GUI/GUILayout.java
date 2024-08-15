package GUI;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public enum GUILayout {
  COLORFUL_LAYOUT(new Image(ClassLoader.getSystemResource("images/background1.png").toString()), Color.rgb(238, 212, 170), Color.rgb(74, 159, 170)),
  GREY_LAYOUT(new Image(ClassLoader.getSystemResource("images/background2.jpg").toString()), Color.rgb(203, 203, 203), Color.rgb(36, 36, 36)),
  BROWN_LAYOUT(new Image(ClassLoader.getSystemResource("images/background3.jpg").toString()), Color.rgb(197, 175, 138), Color.rgb(114, 73, 45));

  private final Image background;
  private final Color whiteColor;
  private final Color blackColor;

  GUILayout(Image background, Color whiteColor, Color blackColor) {
    this.background = background;
    this.whiteColor = whiteColor;
    this.blackColor = blackColor;
  }

  public Image getBackground() {
    return this.background;
  }

  public Color getWhiteColor() {
    return this.whiteColor;
  }

  public Color getBlackColor() {
    return this.blackColor;
  }
}
