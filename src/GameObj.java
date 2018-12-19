/**
 * CIS 120 Game HW (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * An object in the game.
 *
 * <p>Game objects exist in the game court. They have a position, velocity, size and bounds. Their
 * velocity controls how they move; their position should always be within their bounds.
 */
public abstract class GameObj {
  /*
   * Current position of the object (in terms of graphics coordinates)
   *
   * Coordinates are given by the upper-left hand corner of the object. This position should
   * always be within bounds.
   *  0 <= px <= maxX
   *  0 <= py <= maxY
   */

  private double px;
  private double py;

  /* Size of object, in pixels. */

  /* Velocity: number of pixels to move every time move() is called. */
  private double vx;
  private double vy;

  /** Constructor */
  public GameObj(double vx, double vy, double px, double py) {
    this.vx = vx;
    this.vy = vy;
    this.px = px;
    this.py = py;
  }

  /** * GETTERS ********************************************************************************* */
  public double getPx() {
    return this.px;
  }

  public double getPy() {
    return this.py;
  }

  public double getVx() {
    return this.vx;
  }

  public double getVy() {
    return this.vy;
  }

  public abstract Color getColor(); // {
  // return new Color(color.getRed(), color.getGreen(), color.getBlue());
  // }

  /** * SETTERS ********************************************************************************* */
  public void setPx(double px) {
    this.px = px;
    // clip();
  }

  public void setPy(double py) {
    this.py = py;
    // clip();
  }

  public void setVx(double vx) {
    this.vx = vx;
  }

  public void setVy(double vy) {
    this.vy = vy;
  }

  /** * UPDATES AND OTHER METHODS *************************************************************** */
  public void move() {
    this.px += this.vx;
    this.py += this.vy;
  }
}
