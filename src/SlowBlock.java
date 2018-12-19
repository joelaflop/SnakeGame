/**
 * CIS 120 Game HW (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class SlowBlock extends Edible {

  private int speed;

  public SlowBlock(int x, int y) {
    super(x, y);
    speed = (int) (((Math.random() * 100)) + -60);
  }

  public SlowBlock(int s, int x, int y) {
    super(x, y);
    speed = -1 * s;
  }

  public int getSpeed() {
    return speed;
  }

  // public void setColor() {}

  public Color getColor() {
    int greyness = (int) (((double) (speed + 60)) * 2.4);
    return new Color(greyness, greyness, greyness);
  }
}
