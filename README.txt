=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Array to model the game state. The snake board is a 2d array of color 
     objects, white blocks represent empty space and the various types of blocks
     are represented by the colors given to them in their respective classes,
     except I have death blocks some image IO for fun. The 2d array works very
     well for holding all this data and makes it easy to model movements and
     changes in the game state.

  2. File IO for high scores. In the GameCourt class, at the end of the game,
     I write both the score and the user's name to a file titled highscores
     and then every game reset, I read from this file in the Game class and
     create a high scores table on the game screen for the user to see.

  3. Inheritance/Subtyping: I modeled the various types of Blocks: FoodBlocks,
     SlowBlocks, and DeathBlocks as their own classes that are all child classes
     of an abstract class titled Edible that extends from GameObj and implements
     comparable to allow me to make Collections of my blocks and to compare 
     them to one another on the board.

  4. JUnit testing: My GameCourt class models the state of the game almost 
     completely independently from any Swing stuff, allowing me to test the 
     various methods of the class and the interaction of the different parts via
     unit testing.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Game: handles all the Swing stuff from menus to instruction to settings
  
  GameCourt: Models the Game state and maintains instances of the following
  classes
  
  Snake: Is really only the head of the snake, but maintains the snakes length
  and all the methods for incrementing and setting it. Used to also hold the 
  color of the whole snake but currently only holds the color of the head.
  
  Square: Provided class that I used for the each unit in the body of the snake
  
  Edible: An abstract class that each of the edible block classes on the board
  implements. Has some functions for generating positions and implements the 
  Comparable interface so that any child classes can be put into Collections
  
  FoodBlock: The base edible block that increases the snake's length and the 
  players score
  
  DeathBlock: Another "edible" block that kills the snake immediately upon
  eating it. Is represented by an image of a skull and crossbones on the board
  
  SlowBlock: Another edible block that either speeds up or slows down the snake
  by some random amount that is indicated by the shade of the block on a gray
  scale, darker will speed you up and lighter will slow you down.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  Figuring out how to make the snake move and turn properly took a long time, 
  forcing all of the individual square blocks to link properly and follow each
  other around the screen was difficult.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
 
  I think its a pretty strong design, the classes all work well together and share
  information pretty well and are held together well in the GameCourt class which
  is instantiate and displayed in the Game class


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  just JavaDocs for the various classes I imported, nothing out of the ordinary: 
  Timer, Color, TreeMap, ArrayList, TreeSet, Swing stuff, etc.