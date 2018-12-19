/**
 * CIS 120 Game HW (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.*;

/** Game Main class that specifies the frame and widgets of the GUI */
public class Game implements Runnable {

  java.io.Reader r;

  Map<Integer, String> highScores;
  Set<Integer> keys;
  ArrayList<Integer> scores;
  private String usernamestr;

  public Game() {

    highScores = new TreeMap<Integer, String>();
    usernamestr = "no name";
    try {
      r = new BufferedReader(new FileReader("/Users/joelaflop/Documents/code/game/highscores.txt"));
    } catch (FileNotFoundException e) {
      System.out.println("didnt find highscores file for reader");
    }
  }

  public void run() {

    // NOTE : recall that the 'final' keyword notes immutability even for local variables.

    // Top-level frame in which game components live
    // Be sure to change "TOP LEVEL FRAME" to the name of your game
    final JFrame frame = new JFrame("The Snake");
    frame.setLocation(20, 20);

    // Status panel
    final JPanel status_panel = new JPanel();
    frame.add(status_panel, BorderLayout.SOUTH);
    final JLabel status = new JLabel("        Ready to begin");
    status.setPreferredSize(new Dimension(400, 35));
    status_panel.add(status);

    final JFrame usernameWindow = new JFrame("Enter Username: Only letters and no spaces allowed!");
    usernameWindow.setLocation(300, 300);

    // Main playing area
    final GameCourt court = new GameCourt(status, 600, 600);

    court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    JLabel i1 = new JLabel("Hello! ");
    i1.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
    i1.setForeground(Color.GREEN);
    court.add(i1);
    JLabel i2 = new JLabel("Press 'Instructions' above for a");
    i2.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
    i2.setForeground(Color.GREEN);
    court.add(i2);
    JLabel i2b = new JLabel("startup guide to playing  Snake");
    i2b.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
    i2b.setForeground(Color.GREEN);
    court.add(i2b);
    JLabel s1 = new JLabel("  ");
    court.add(s1);
    JLabel i3a = new JLabel("Press 'Enter User' to enter a");
    i3a.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
    i3a.setForeground(Color.GREEN);
    court.add(i3a);
    JLabel i3aa = new JLabel("name for the high scores!");
    i3aa.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
    i3aa.setForeground(Color.GREEN);
    court.add(i3aa);
    JLabel s2 = new JLabel("  ");
    court.add(s2);
    JLabel i3 = new JLabel("Press 'Reset' to begin playing!");
    i3.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
    i3.setForeground(Color.GREEN);
    court.add(i3);

    frame.add(court, BorderLayout.CENTER);

    // Reset button
    final JPanel control_panelTP = new JPanel();
    frame.add(control_panelTP, BorderLayout.NORTH);

    final JLabel highScores1 = new JLabel("no scores");
    final JLabel highScores2 = new JLabel("");
    final JLabel highScores3 = new JLabel("");

    // Note here that when we add an action listener to the reset button, we define it as an
    // anonymous inner class that is an instance of ActionListener with its actionPerformed()
    // method overridden. When the button is pressed, actionPerformed() will be called.
    final JFrame instrucFrame = new JFrame("Instructions");
    final JButton instruc = new JButton("Instructions");
    instruc.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            instrucFrame.setLocation(50, 100);
            final JLabel sp1 = new JLabel(" ");
            final JLabel instructions = new JLabel("Snake Instructions:");
            final JTextArea i1 =
                new JTextArea(
                    "Your goal is to eat as much food as possible, getting as long as possible. You can toggle the walls on/off, allowing your snake to travel through ");
            final JTextArea i2 =
                new JTextArea(
                    "the edges of the board. Additionally, there are sliders on each side of the board that allow you to adjust the snake's speed and the number of FoodBlocks ");
            final JTextArea i3 =
                new JTextArea(
                    "on the board during the game, each time you eat a FoodBlock, your snake will be elongated accordingly and your score incremented. ");
            final JTextArea i4 =
                new JTextArea(
                    "Lastly, you can enter you Username by clicking the 'Enter User' buttton and typing in your single-word name and then clicking enter! ");
            final JLabel foodblocks = new JLabel("FoodBlocks:");
            final JTextArea f1 =
                new JTextArea(
                    "The FoodBlocks are always the light pastel-colored blocks on the board. Green blocks elongate your snake and increase your score by 1, Purple elongate ");
            final JTextArea f2 =
                new JTextArea(
                    "your snake and increase your score by 2, and Blue blocks by 3. All you have to do is eat a FoodBlock by traveling over it to up your score");
            final JLabel speedblocks = new JLabel("SpeedBlocks:");
            final JTextArea s1 =
                new JTextArea(
                    "SpeedBlocks are toggled with their respective checkbox on top of the board, speed blocks either speed up or slow down your snake. You can judge their ");
            final JTextArea s2 =
                new JTextArea(
                    "effect by looking at their shade, darker blocks will speed you up and lighter ones will slow you down, SpeedBlocks are always some shade of gray/black");
            final JLabel deathblocks = new JLabel("DeathBlocks:");
            final JTextArea d1 =
                new JTextArea(
                    "DeathBlocks can also be toggled via their checkbox. Deathblocks offers a challenge game mode in which skull-and-crossbone blocks appear often that will ");
            final JTextArea d2 = new JTextArea("kill your snake if you make contact with them");
            final JLabel sp2 = new JLabel(" ");
            final JPanel panel = new JPanel();

            panel.setLayout(new GridLayout(0, 1));
            instrucFrame.add(panel);
            panel.add(sp1);
            panel.add(instructions);
            panel.add(i1);
            panel.add(i2);
            panel.add(i3);
            panel.add(i4);
            panel.add(foodblocks);
            panel.add(f1);
            panel.add(f2);
            panel.add(speedblocks);
            panel.add(s1);
            panel.add(s2);
            panel.add(deathblocks);
            panel.add(d1);
            panel.add(d2);
            panel.add(sp2);

            instrucFrame.pack();
            instrucFrame.setVisible(true);
          }
        });
    control_panelTP.add(instruc);

    final JButton userButton = new JButton("Enter User");
    userButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {

            // final JFrame usernameWindow = new JFrame();
            final JPanel panelU = new JPanel();
            panelU.setLayout(new GridLayout(0, 1));
            usernameWindow.add(panelU);

            // final JLabel enter = new JLabel("and click enter");
            final JLabel warning =
                new JLabel(
                    "hit enter to submit, we wont save your score unless you enter a username");

            final JTextField username = new JTextField("");
            username.setColumns(20);

            panelU.add(username);
            // panel.add(enter);
            panelU.add(warning);

            username.setText("");
            usernameWindow.pack();
            usernameWindow.setVisible(true);
            username.addActionListener(
                new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                    usernamestr = username.getText();
                    usernameWindow.setVisible(false);
                    if (usernamestr.equals(null)
                        || usernamestr.equals("")
                        || usernamestr.equals(" ")) {
                      usernamestr = "no name";
                    }
                    court.setUsername(usernamestr);
                  }
                });
          }
        });
    control_panelTP.add(userButton);

    final JButton reset = new JButton("Reset");
    reset.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            court.reset();
            int ch = -1;
            String score = "";
            String name = "";
            try {
              while ((ch = r.read()) != -1) {

                if (Character.isDigit(ch)) {
                  while (Character.isDigit(ch) && ch != -1) {
                    score = score + (char) ch;
                    ch = r.read();
                  }
                  ch = r.read();
                  if (Character.isLetter(ch)) {
                    while (Character.isLetter(ch) && ch != -1) {
                      name = name + (char) ch;
                      ch = r.read();
                    }
                  }
                  if (highScores.containsKey(Integer.valueOf(score))) {
                    if (highScores.get(Integer.valueOf(score)).contains(name)) {
                      return;
                    } else {
                      highScores.put(
                          Integer.valueOf(score),
                          highScores.get(Integer.valueOf(score)) + ", " + name);
                    }
                  } else {
                    highScores.put(Integer.valueOf(score), name);
                  }
                }
              }
            } catch (IOException ee) {
              System.out.println("exception when reading from highscores file");
            }
            String scoreString1 = "";
            String scoreString2 = "";
            String scoreString3 = "";
            keys = highScores.keySet();
            scores = new ArrayList<Integer>(keys);

            if (scores.size() == 1) {
              scoreString1 =
                  scores.get(scores.size() - 1).toString()
                      + ": "
                      + highScores.get(scores.get(scores.size() - 1));
              highScores1.setText(scoreString1);
            } else if (scores.size() == 2) {
              scoreString1 =
                  scores.get(scores.size() - 1).toString()
                      + ": "
                      + highScores.get(scores.get(scores.size() - 1));
              highScores1.setText(scoreString1);
              scoreString2 =
                  scores.get(scores.size() - 2).toString()
                      + ": "
                      + highScores.get(scores.get(scores.size() - 2));
              highScores2.setText(scoreString2);
            } else if (scores.size() >= 3) {
              scoreString1 =
                  scores.get(scores.size() - 1).toString()
                      + ": "
                      + highScores.get(scores.get(scores.size() - 1));
              highScores1.setText(scoreString1);
              scoreString2 =
                  scores.get(scores.size() - 2).toString()
                      + ": "
                      + highScores.get(scores.get(scores.size() - 2));
              highScores2.setText(scoreString2);
              scoreString3 =
                  scores.get(scores.size() - 3).toString()
                      + ": "
                      + highScores.get(scores.get(scores.size() - 3));
              highScores3.setText(scoreString3);
            }

            instrucFrame.setVisible(false);
            i1.setVisible(false);
            i2.setVisible(false);
            i2b.setVisible(false);
            i3.setVisible(false);
            i3a.setVisible(false);
            i3aa.setVisible(false);
          }
        });

    control_panelTP.add(reset);

    final JCheckBox walls = new JCheckBox("Toggle Walls");
    walls.setSelected(true);
    walls.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (walls.isSelected()) {
              court.setWalls(true);

            } else {
              court.setWalls(false);
            }
            court.reset();
          }
        });
    control_panelTP.add(new JLabel("       "));
    control_panelTP.add(walls);

    final JCheckBox deathblocks = new JCheckBox("Toggle DeathBlocks");
    deathblocks.setSelected(false);
    deathblocks.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (deathblocks.isSelected()) {
              court.setDeath(true);

            } else {
              court.setDeath(false);
            }
            court.reset();
          }
        });
    control_panelTP.add(new JLabel("    "));
    control_panelTP.add(deathblocks);

    final JCheckBox speedblocks = new JCheckBox("Toggle SpeedBlocks");
    speedblocks.setSelected(false);
    speedblocks.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (speedblocks.isSelected()) {
              court.setSlow(true);

            } else {
              court.setSlow(false);
            }
            court.reset();
          }
        });
    control_panelTP.add(new JLabel("    "));
    control_panelTP.add(speedblocks);

    final JPanel control_panelRT = new JPanel();
    frame.add(control_panelRT, BorderLayout.EAST);
    control_panelRT.setLayout(new BoxLayout(control_panelRT, BoxLayout.PAGE_AXIS));

    final JLabel foodL = new JLabel("Food Count:");
    control_panelRT.add(foodL);

    final JSlider numFoods = new JSlider(JSlider.VERTICAL, 0, 40, 1);
    numFoods.setLabelTable(numFoods.createStandardLabels(2));
    reset.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            court.setNumFoods(numFoods.getValue());
          }
        });
    numFoods.setMinorTickSpacing(1);
    numFoods.setMajorTickSpacing(5);
    numFoods.setPaintLabels(true);
    numFoods.setPaintTicks(true);

    control_panelRT.add(numFoods);

    JLabel HSL = new JLabel("HIGH SCORES: ");
    HSL.setFont(HSL.getFont().deriveFont(20.0f));
    highScores1.setFont(highScores1.getFont().deriveFont(16.0f));
    highScores2.setFont(highScores2.getFont().deriveFont(16.0f));
    highScores3.setFont(highScores3.getFont().deriveFont(16.0f));
    control_panelRT.add(HSL);
    control_panelRT.add(highScores1);
    control_panelRT.add(highScores2);
    control_panelRT.add(highScores3);

    final JPanel control_panelLF = new JPanel();
    frame.add(control_panelLF, BorderLayout.WEST);

    final JLabel speedL = new JLabel("Speed:");
    control_panelLF.add(speedL);
    control_panelLF.setLayout(new BoxLayout(control_panelLF, BoxLayout.PAGE_AXIS));

    final JSlider speed = new JSlider(JSlider.VERTICAL, 40, 300, 200);
    speed.setLabelTable(speed.createStandardLabels(40));
    reset.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            court.setInterval(340 - speed.getValue());
          }
        });
    speed.setMajorTickSpacing(25);
    speed.setPaintLabels(true);
    speed.setPaintTicks(true);
    control_panelLF.add(speed);

    // Put the frame on the screen
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  /**
   * Main method run to start and run the game. Initializes the GUI elements specified in Game and
   * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Game());
  }
}
