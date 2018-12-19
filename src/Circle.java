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
public class Circle extends GameObj {
  public static final int SIZE = 20;
  public static final int WIDTH = 1;
  public static final int HEIGHT = 1;
  public static final double INIT_POS_X = 1;
  public static final double INIT_POS_Y = 1;
  public static final double VX = 2;
  public static final double VY = 3;

  private Color color;

  public Circle(double Vx, double Vy, Color color) {
    super(Vx, Vy, INIT_POS_X, INIT_POS_Y);

    this.color = color;
  }

  public Color getColor() {
    return new Color(color.getRed(), color.getGreen(), color.getBlue());
  }
  /*
      @Override
      public void draw(Graphics g) {
          g.setColor(this.color);
          g.fillOval(20*this.getPx(), 20*this.getPy(), 20*this.getWidth(), 20*this.getHeight());
      }
  */
}
