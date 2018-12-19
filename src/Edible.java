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
public abstract class Edible extends GameObj implements Comparable {

  public static final double INIT_POS_X = 0;
  public static final double INIT_POS_Y = 0;
  public static final double INIT_VEL_X = 0;
  public static final double INIT_VEL_Y = 0;

  public Edible(int x, int y) {
    super(INIT_VEL_X, INIT_VEL_Y, x, y);
  }

  public static int createPosX() {
    return (int) ((Math.random() * (GameCourt.COURT_WIDTH)) / 20);
  }

  public static int createPosY() {
    return (int) ((Math.random() * (GameCourt.COURT_HEIGHT)) / 20);
  }

  public abstract Color getColor();

  // public abstract void setColor();

  @Override
  public int compareTo(Object o) {
    if (o instanceof Edible) {
      return (int) (((Edible) o).getPx() - this.getPx())
          + (int) (((Edible) o).getPy() - this.getPy());
    } else {
      throw new ClassCastException();
    }
  }
}
