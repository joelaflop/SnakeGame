/**
 * CIS 120 Game HW (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * circle of a specified color.
 */
public class Snake extends GameObj {
  public static final int SIZE = 20;
  public static final int WIDTH = 1;
  public static final int HEIGHT = 1;
  public static final double INIT_POS_X = 6;
  public static final double INIT_POS_Y = 6;
  public static final double VX = 1;
  public static final double VY = 0;

  private Color color;
  private int length = 2;

  public Snake(double Vx, double Vy, Color color) {
    super(Vx, Vy, INIT_POS_X, INIT_POS_Y);

    this.color = color;
  }

  public Color getColor() {
    return new Color(color.getRed(), color.getGreen(), color.getBlue());
  }

  public void increaseLengthBy(int incr) {
    length += incr;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int len) {
    length = len;
  }

}
