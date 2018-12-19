/**
 * CIS 120 Game HW (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.*;

/**
 * GameCourt
 *
 * <p>This class holds the primary game logic for how different objects interact with one another.
 * Take time to understand how the timer interacts with the different methods and how it repaints
 * the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

  private java.io.Writer w;
  java.io.Reader r;

  private int tickcount;
  private int deathcount;

  private Snake snake;
  private Set<FoodBlock> foodBlocks;
  private Set<DeathBlock> deathBlocks;
  private Set<SlowBlock> slowBlocks;
  private LinkedList<GameObj> snakeBod;
  private int numFoods = 1;
  private int numSlows = 5;

  private boolean playing = false; // whether the game is running
  private boolean walls = true;
  private boolean death = false;
  private boolean slow = false;
  private JLabel status; // Current status text, i.e. "Running..."
  private String username;

  // Game constants
  public static int COURT_WIDTH;
  public static int COURT_HEIGHT;
  public static double SNAKE_VELOCITY = 1;
  Color[][] court;

  Timer timer;
  // Update interval for timer, in milliseconds
  private int INTERVAL = 140;

  public GameCourt(JLabel status, int courtW, int courtH) {
    COURT_WIDTH = courtW;
    COURT_HEIGHT = courtH;
    int AWIDTH = COURT_WIDTH / 20;
    int AHEIGHT = COURT_HEIGHT / 20;
    court = new Color[(COURT_WIDTH / 20)][(COURT_HEIGHT / 20)];

    // The timer is an object which triggers an action periodically with the given INTERVAL. We
    // register an ActionListener with this timer, whose actionPerformed() method is called each
    // time the timer triggers. We define a helper method called tick() that actually does
    // everything that should be done in a single timestep.
    try {
      w = new BufferedWriter(new FileWriter("/Users/joelaflop/Documents/game/highscores.txt"));
    } catch (FileNotFoundException e) {
      System.out.println("excegrjibenfergni4ernvjinvi");
    } catch (IOException e) {
      System.out.println("excegrjibenfergni4ernvjinvi");
    }

    timer =
        new Timer(
            INTERVAL,
            new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                tick();
              }
            });
    timer.start(); // MAKE SURE TO START THE TIMER!

    // Enable keyboard focus on the court area.
    // When this component has the keyboard focus, key events are handled by its key listener.
    setFocusable(true);

    // This key listener allows the square to move as long as an arrow key is pressed, by
    // changing the square's velocity accordingly. (The tick method below actually moves the
    // square.)
    addKeyListener(
        new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
              if (snake.getVx() == 0) {
                snake.setVx(-SNAKE_VELOCITY);
                snake.setVy(0);
              }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
              if (snake.getVx() == 0) {
                snake.setVx(SNAKE_VELOCITY);
                snake.setVy(0);
              }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
              if (snake.getVy() == 0) {
                snake.setVy(SNAKE_VELOCITY);
                snake.setVx(0);
              }
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
              if (snake.getVy() == 0) {
                snake.setVy(-SNAKE_VELOCITY);
                snake.setVx(0);
              }
            }
          }
        });

    this.status = status;
    this.username = "noname";
  }

  /** (Re-)set the game to its initial state. */
  public void reset() {
    timer.setDelay(INTERVAL);

    tickcount = 0;
    deathcount = 0;

    for (int r = 0; r < court.length; r++) {
      for (int c = 0; c < court[0].length; c++) {
        court[r][c] = Color.WHITE;
      }
    }
    snake = new Snake(SNAKE_VELOCITY, 0, Color.GREEN);
    snakeBod = new LinkedList<GameObj>();
    snakeBod.add(snake);

    for (int i = 1; i < snake.getLength(); i++) {
      Color c =
          new Color(
              (int) (Math.random() * 255),
              (int) (Math.random() * 255),
              (int) (Math.random() * 255));
      Square curr = new Square(SNAKE_VELOCITY, 0, c);
      curr.setPx((snakeBod.get(i - 1)).getPx() - 1);
      curr.setPy(6);
      snakeBod.add(curr);
    }

    foodBlocks = new TreeSet<FoodBlock>();
    for (int i = 0; i < numFoods; i++) {
      int value = FoodBlock.createValue();
      int x = Edible.createPosX();
      int y = Edible.createPosY();
      FoodBlock curr = new FoodBlock(value, x, y);
      foodBlocks.add(curr);
    }
    if (death) {
      deathBlocks = new TreeSet<DeathBlock>();
    }

    if (slow) {
      slowBlocks = new TreeSet<SlowBlock>();
      for (int i = 0; i < numSlows; i++) {
        int x = Edible.createPosX();
        int y = Edible.createPosY();
        SlowBlock curr = new SlowBlock(x, y);
        slowBlocks.add(curr);
      }
    }

    playing = true;
    status.setFont(status.getFont().deriveFont(18.0f));
    status.setText("Current Length:  " + (snake.getLength() - 1));
    // usernameWindow.setVisible(false);

    // Make sure that this component has the keyboard focus
    requestFocusInWindow();
  }

  /** This method is called every time the timer defined in the constructor triggers. */
  void tick() {

    if (playing) {
      tickcount++;

      System.out.println(" " + tickcount);

      if (willEatSelf() || (walls && willExit(snake)) || (death && willEatDeath())) {
        playing = false;
        status.setFont(status.getFont().deriveFont(20.0f));
        status.setText("GAME OVER!  Final Score: " + (snake.getLength() - 1));
        try {
          w.write((snake.getLength() - 1) + " " + username + "\n");
          w.flush();
          // username.setText("");

        } catch (IOException ee) {
          System.out.println("Exception when writing to the high scores file");
        }

        return;
      } else {
        noTurnAround();
        snake.move();
        if (!walls) {
            throughWalls(snake);
          }
        status.setText("Length:  " + (snake.getLength() - 1));
        for (int i = snakeBod.size() - 1; i > 0; i--) {
          (snakeBod.get(i)).move();
          (snakeBod.get(i)).setVx(snakeBod.get(i - 1).getVx());
          (snakeBod.get(i)).setVy(snakeBod.get(i - 1).getVy());
          if (!walls) {
            throughWalls(snakeBod.get(i));
          }
        }
      }

      Iterator<FoodBlock> iterF = foodBlocks.iterator();
      while (iterF.hasNext()) {
        FoodBlock curr = iterF.next();
        if (snake.getPx() == curr.getPx() && snake.getPy() == curr.getPy()) {
          snake.increaseLengthBy(curr.getValue()); // should be by(curr.getValue())

          court[(int) (curr.getPx())][(int) (curr.getPy())] = Color.WHITE;
          iterF.remove();
          while (snakeBod.size() < snake.getLength()) {
            Color c =
                new Color(
                    (int) (Math.random() * 255),
                    (int) (Math.random() * 255),
                    (int) (Math.random() * 255));
            Square newBod = new Square(0, 0, c);
            newBod.setPx(snakeBod.get(snakeBod.size() - 1).getPx());
            newBod.setPy(snakeBod.get(snakeBod.size() - 1).getPy());
            snakeBod.add(newBod);
          }
        }
      }

      while (foodBlocks.size() < numFoods) {
        int value = FoodBlock.createValue();
        int x, y;
        do {
          x = Edible.createPosX();
          y = Edible.createPosY();
        } while (isOnSnake(x, y) && court[x][y] != Color.WHITE);
        FoodBlock curr = new FoodBlock(value, x, y);
        foodBlocks.add(curr);
      }

      if (slow) {
        Iterator<SlowBlock> iterS = slowBlocks.iterator();
        while (iterS.hasNext()) {
          SlowBlock curr = iterS.next();
          if (snake.getPx() == curr.getPx() && snake.getPy() == curr.getPy()) {
            int delay;
            if (timer.getDelay() + curr.getSpeed() < 10) {
              delay = 10;
            } else {
              delay = timer.getDelay() + curr.getSpeed();
            }
            timer.setDelay(delay);
            court[(int) (curr.getPx())][(int) (curr.getPy())] = Color.WHITE;
            iterS.remove();
          }
        }

        while (slowBlocks.size() < numSlows) {
          int x, y;
          do {
            x = Edible.createPosX();
            y = Edible.createPosY();
          } while (isOnSnake(x, y) && court[x][y] != Color.WHITE);
          SlowBlock curr = new SlowBlock(x, y);
          slowBlocks.add(curr);
        }
      }

      if (deathcount > 5 && death) {
        int x, y;
        do {
          x = Edible.createPosX();
          y = Edible.createPosY();
        } while (x == snake.getPx() || y == snake.getPy() && court[x][y] != Color.WHITE);
        deathBlocks.add(new DeathBlock(x, y));
        deathcount = 0;
      }

      for (FoodBlock fb : foodBlocks) {
        court[(int) (fb.getPx())][(int) (fb.getPy())] = fb.getColor();
      }

      if (slow) {
        for (SlowBlock sb : slowBlocks) {
          court[(int) (sb.getPx())][(int) (sb.getPy())] = sb.getColor();
        }
      }

      for (GameObj g : snakeBod) {
        if (!walls) {
          throughWalls(g);
        }
        court[(int) (g.getPx())][(int) (g.getPy())] = g.getColor();
      }

      court[(int) (((snakeBod.get(snakeBod.size() - 1))).getPx())][
              (int) (((snakeBod.get(snakeBod.size() - 1)).getPy()))] =
          Color.WHITE;

      court[(int) snake.getPx()][(int) snake.getPy()] = Color.GREEN;

      if (death) {
        for (DeathBlock db : deathBlocks) {
          court[(int) (db.getPx())][(int) (db.getPy())] = db.getColor();
        }
        deathcount++;
      }

      // update the display
      repaint();
    }
  }

  public void throughWalls(GameObj g) {
    if (g.getPx() == court.length) {
      g.setPx(0);
    } else if (g.getPy() == court[0].length) {
      g.setPy(0);
    } else if (g.getPx() == -1) {
      g.setPx(court.length - 1);
    } else if (g.getPy() == -1) {
      g.setPy(court[0].length - 1);
    }
  }

  public Color[][] getCourt() {
    Color[][] arr = new Color[court.length][court[0].length];
    for (int r = 0; r < court.length; r++) {
      for (int c = 0; c < court[0].length; c++) {
        arr[r][c] = new Color(court[r][c].getRed(), court[r][c].getGreen(), court[r][c].getBlue());
      }
    }
    return arr;
  }

  public void setUsername(String n) {
    username = n;
  }

  public void setInterval(int inter) {
    INTERVAL = inter;
  }

  public int getSpeed() {
    return 340 - timer.getDelay();
  }

  public void setWalls(boolean w) {
    walls = w;
  }

  public void setDeath(boolean d) {
    death = d;
  }

  public void setSlow(boolean s) {
    slow = s;
  }

  public void setNumFoods(int num) {
    numFoods = num;
  }

  public int getNumFoods() {
    return numFoods;
  }

  public void setSnakeLength(int len) {
    snake.setLength(len);
  }

  public int getSnakeLength() {
    return snake.getLength() - 1;
  }

  public void noTurnAround() {
    if (snake.getVx() == -1 * snakeBod.get(1).getVx()) {
      snake.setVx(-1 * snake.getVx());
    }
    if (snake.getVy() == -1 * snakeBod.get(1).getVy()) {
      snake.setVy(-1 * snake.getVy());
    }
  }

  public boolean willExit(GameObj g) {
    return ((g.getPx() + g.getVx()) < 0
        || (g.getPx() + g.getVx()) > court.length - 1
        || (g.getPy() + g.getVy()) < 0
        || (g.getPy() + g.getVy()) > court[0].length - 1);
  }

  public boolean isOnSnake(int x, int y) {
    for (int i = 1; i < snakeBod.size(); i++) {
      if (snakeBod.get(i).getPx() == x && snakeBod.get(i).getPy() == y) {
        return true;
      }
    }
    return false;
  }

  public boolean willEatSelf() {
    for (int i = 1; i < snakeBod.size() - 1; i++) {
      if (snakeBod.get(i).getPx() == snake.getPx() && snakeBod.get(i).getPy() == snake.getPy()) {
        return true;
      }
    }
    return false;
  }

  public boolean willEatDeath() {
    for (DeathBlock d : deathBlocks) {
      if (d.getPx() == snake.getPx() + snake.getVx()
          && d.getPy() == snake.getPy() + snake.getVy()) {
        return true;
      }
    }
    return false;
  }

  public boolean willEatSlow() {
    for (SlowBlock s : slowBlocks) {
      if (s.getPx() == snake.getPx() && s.getPy() == snake.getPy()) {
        return true;
      }
    }
    return false;
  }

  public void stopTimer() {
    timer.stop();
  }

  public void startTimer() {
    timer.start();
  }

  public void movingL() {
    if (snake.getVx() == 0) {
      snake.setVx(-SNAKE_VELOCITY);
      snake.setVy(0);
    }
  }

  public void movingR() {
    if (snake.getVx() == 0) {
      snake.setVx(SNAKE_VELOCITY);
      snake.setVy(0);
    }
  }

  public void movingU() {
    if (snake.getVy() == 0) {
      snake.setVy(-SNAKE_VELOCITY);
      snake.setVx(0);
    }
  }

  public void movingD() {
    if (snake.getVy() == 0) {
      snake.setVy(SNAKE_VELOCITY);
      snake.setVx(0);
    }
  }

  public void putSnake(int x, int y) {
    snake.setPx(x);
    snake.setPy(y);
  }

  public boolean getGameStatus() {
    return playing;
  }

  public void addFoodBlock(int val, int x, int y) {
    foodBlocks.add(new FoodBlock(val, x, y)); // .getColor();
  }

  public void addSlowBlock(int speed, int x, int y) {
    slowBlocks.add(new SlowBlock(speed, x, y)); // .getColor();
  }

  public void addDeathBlock(int x, int y) {
    deathBlocks.add(new DeathBlock(x, y)); // .getColor();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int r = 0; r < court.length; r++) {
      for (int c = 0; c < court[0].length; c++) {
        if (court[r][c] == Color.RED) {
          g.drawImage(DeathBlock.getIMG(), 20 * r, 20 * c, 20, 20, null);
        } else {
          g.setColor(court[r][c]);
          g.fillRect(20 * r, 20 * c, 20, 20);
        }
      }
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(COURT_WIDTH, COURT_HEIGHT);
  }
}
