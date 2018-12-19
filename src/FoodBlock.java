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
public class FoodBlock extends Edible {

  private int value;
  private static final int maxValue = 3;

  public FoodBlock(int val, int x, int y) {
    super(x, y);
    this.value = val;
  }

  public int getValue() {
    return value;
  }

  public static int createValue() {
    return ((int) (Math.random() * maxValue)) + 1;
  }

  // public void setColor() {}

  public Color getColor() {
    switch (value) {
      case 1:
        return new Color(164, 223, 194);
      case 2:
        return new Color(180, 155, 248);
      case 3:
        return new Color(173, 223, 230);
      default:
        return Color.ORANGE;
    }
  }
}
