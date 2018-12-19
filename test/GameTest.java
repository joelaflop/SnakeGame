import static org.junit.Assert.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.junit.*;
import org.junit.Test;

public class GameTest {
  private GameCourt tcourt;
  private JLabel status;
  private JFrame instrucFrame;

  @Before
  public void setUp() {
    status = new JLabel(":(");
    tcourt = new GameCourt(status, 600, 600);
    tcourt.reset();
  }

  @Test
  public void trivialTest() {
    assertEquals(1, 1);
  }

  @Test
  public void initialState() {
    for (int r = 0; r < 30; r++) {
      for (int c = 0; c < 30; c++) {
        assertEquals(Color.WHITE, tcourt.getCourt()[r][c]);
      }
    }
  }

  @Test
  public void afterOneTick() {
    tcourt.tick();
    Color[][] g = tcourt.getCourt();
    int notEmpty = 0;
    assertEquals(Color.GREEN, g[7][6]);
    for (int r = 0; r < 30; r++) {
      for (int c = 0; c < 30; c++) {
        if (!g[r][c].equals(Color.WHITE)) {
          notEmpty++;
        }
      }
    }
    assertEquals(notEmpty, 2);
  }

  @Test
  public void afterTwoTick() {
    Color[][] g = tcourt.getCourt();
    tcourt.tick();
    tcourt.tick();
    assertEquals(Color.GREEN, tcourt.getCourt()[8][6]);
    tcourt.tick();
    assertEquals(Color.GREEN, tcourt.getCourt()[9][6]);
    assertEquals(1, tcourt.getSnakeLength());
  }

  @Test
  public void tickIntoWall() {
    tcourt.putSnake(29, 10);
    tcourt.tick();
    assertFalse(tcourt.getGameStatus());
    assertEquals(1, tcourt.getSnakeLength());
  }

  @Test
  public void afterTwoTickChangeDirection() {
    tcourt.tick();
    tcourt.tick();
    assertEquals(Color.GREEN, tcourt.getCourt()[8][6]);
    tcourt.movingD();
    tcourt.tick();
    assertEquals(Color.GREEN, tcourt.getCourt()[8][7]);
    assertEquals(1, tcourt.getSnakeLength());
  }

  @Test
  public void eatingFoodIncreaseSize() {
    tcourt.tick();
    assertEquals(Color.GREEN, tcourt.getCourt()[7][6]);
    assertEquals(1, tcourt.getSnakeLength());
    tcourt.addFoodBlock(2, 9, 6);
    tcourt.tick();
    tcourt.tick();
    assertEquals(3, tcourt.getSnakeLength());
    tcourt.tick();
    tcourt.tick();
    tcourt.tick();
    assertEquals(Color.WHITE, tcourt.getCourt()[9][6]);
  }

  @Test
  public void eatingDeathKillsYou() {
    tcourt.setDeath(true);
    tcourt.reset();
    tcourt.tick();
    assertEquals(Color.GREEN, tcourt.getCourt()[7][6]);
    assertEquals(1, tcourt.getSnakeLength());
    tcourt.addDeathBlock(9, 6);
    tcourt.tick();
    tcourt.tick();
    assertFalse(tcourt.getGameStatus());
    assertEquals(1, tcourt.getSnakeLength());
  }

  @Test
  public void eatingSpeedSpeedsYou() {
    tcourt.setSlow(true);
    tcourt.reset();
    tcourt.tick();
    assertEquals(200, tcourt.getSpeed());
    assertEquals(Color.GREEN, tcourt.getCourt()[7][6]);
    assertEquals(1, tcourt.getSnakeLength());
    tcourt.addSlowBlock(30, 9, 6);
    tcourt.tick();
    tcourt.tick();
    tcourt.tick();
    assertEquals(230, tcourt.getSpeed());
    assertEquals(1, tcourt.getSnakeLength());
  }

  @Test
  public void eatingFoodIncreaseSizeALotAndSnakeRunsInCirlceToEatSelfAndDie() {
    tcourt.tick();
    assertEquals(Color.GREEN, tcourt.getCourt()[7][6]);
    assertEquals(1, tcourt.getSnakeLength());
    tcourt.addFoodBlock(3, 9, 6);
    tcourt.addFoodBlock(3, 10, 6);
    tcourt.addFoodBlock(3, 11, 6);
    tcourt.addFoodBlock(3, 12, 6);
    tcourt.addFoodBlock(3, 13, 6);
    tcourt.tick();
    tcourt.tick();
    assertEquals(4, tcourt.getSnakeLength());
    tcourt.tick();
    assertEquals(7, tcourt.getSnakeLength());
    tcourt.tick();
    tcourt.tick();
    assertEquals(13, tcourt.getSnakeLength());
    tcourt.tick();
    assertEquals(16, tcourt.getSnakeLength());
    tcourt.movingD();
    tcourt.tick();
    tcourt.tick();
    tcourt.movingL();
    tcourt.tick();
    tcourt.tick();
    tcourt.tick();
    tcourt.movingU();
    tcourt.tick();
    tcourt.tick();
    tcourt.tick();
    assertFalse(tcourt.getGameStatus());
  }

  @Test
  public void eatingFoodIncreaseSizeAndRidingNextToWall() {
    tcourt.putSnake(7, 29);
    tcourt.addFoodBlock(3, 9, 29);
    tcourt.addFoodBlock(1, 10, 29);
    tcourt.addFoodBlock(2, 11, 29);
    tcourt.tick();
    tcourt.tick();
    tcourt.tick();
    tcourt.tick();
    tcourt.tick();
    tcourt.tick();
    assertTrue(tcourt.getGameStatus());
    assertEquals(7, tcourt.getSnakeLength());
  }
}
