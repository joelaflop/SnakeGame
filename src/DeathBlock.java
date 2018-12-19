/**
 * CIS 120 Game HW (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class DeathBlock extends Edible {

  public static final String IMG_FILE = "/Users/joelaflop/Documents/game/skull.png";
  private static BufferedImage img;

  public DeathBlock(int x, int y) {
    super(x, y);
    try {
      if (img == null) {
        img = ImageIO.read(new File(IMG_FILE));
      }
    } catch (IOException e) {
      System.out.println("Internal Error:" + e.getMessage());
    }
  }

  public static BufferedImage getIMG() {
    return img;
  }
  // public void setColor() {}

  public Color getColor() {
    return Color.RED;
  }
}
