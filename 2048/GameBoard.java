import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.awt.Color;

/**
 * This is a simple game where you join together boxes of the same value 
 * and try to get to 2048
 * 
 * @author Logan Batson
 * @version 2.0 - Final version
 */
public class GameBoard extends World
{

    //Instance Constants
    //Define some directions to make it easier to reference which way the blocks are moving
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    Color trans = new Color(0, 0, 0, 0); //transparent for reset btn
    int sqrCount = 0;
    int winVal = 2048;
    
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public GameBoard()
    {    
        // Create a new world with 600x400 cells with a cell size of 50x50 pixels.
        super(4, 4, 50);
        
        //populate gameboard with x randomly placed objects
        while(sqrCount<2) {
            if(placeRandomBlock()){
                sqrCount++;
            } else {
                continue;
            }
        }
    }

    /**
     * Place a block on a random location on the board
     * 
     * @return Returns true if successful, false if not successful
     */
    private boolean placeRandomBlock()
    {
        //Generate Random Location (Hint: Is there anything to do with random values in the apis?)
        GameSquare gs = new GameSquare(); //make new game square
        int x = (Greenfoot.getRandomNumber(4)); //Get random num from 0-3
        int y = (Greenfoot.getRandomNumber(4));
        
        //Check to ensure random location is not yet taken, if the spot is free add it to the world
        if (getObjectsAt(x, y, GameSquare.class).isEmpty()) {
            addObject(gs,x,y);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Act - Check for key presses and tell each block to move itsself.
     */
    public void act() 
    {
        MouseInfo mse = Greenfoot.getMouseInfo();
        
        if (mse != null) {
            Actor cAct = mse.getActor();
            if (cAct != null) {
                if (cAct.getClass() == button.class) {
                    button x = (button)cAct;
                    int mCC = mse.getClickCount();
                    if (mCC == 1) {
                        x.toggle();
                        Greenfoot.delay(60);
                        removeObjects(getObjects(null));
                        setBackground("resetbg.png");
                        Greenfoot.setWorld(new GameBoard());
                    }
                }
            }
        }
                        
        //Add key press actions here
        String key = Greenfoot.getKey();
        //If a key was pressed...do something
        if (key != null) {
            switch(key) {
                case "up": 
                    //Tell the blocks to move up
                    //Start checking from the top, then move downwards

                    for (int i=0; i<getWidth(); i++) {
                        for (int j=0; j<getHeight(); j++) {
                            //Get a list containing all of the GameSquare objects at position (i,j)
                            List blockList = getObjectsAt(i,j,GameSquare.class);
            
                            //Tell the other block object we wish to merge with it.  If successful, delete this block from the game
                            if (blockList.size() == 1) { //Error checking
                                Object tempObject;
                                tempObject = blockList.get(0);
                                GameSquare tempSquare;
                                tempSquare = (GameSquare)tempObject;
                                tempSquare.move(UP);
                            }
                        }
                    }
                    break;
                //NOTE: The remaining cases are similar to the one above, but not exactly the same
                case "right":
                    //Tell the blocks to move right
                    //Start checking from the right most col, then move left                    
                    for (int i=3; i>=0; i--) {
                        for (int j=0; j<getHeight(); j++) {
                            //Get a list containing all of the GameSquare objects at position (i,j)
                            List blockList = getObjectsAt(i,j,GameSquare.class);
            
                            //Tell the other block object we wish to merge with it.  If successful, delete this block from the game
                            if (blockList.size() == 1) { //Error checking
                                Object tempObject;
                                tempObject = blockList.get(0);
                                GameSquare tempSquare;
                                tempSquare = (GameSquare)tempObject;
                                tempSquare.move(RIGHT);
                            }
                        }
                    }
                    break;

                case "down":
                    //Tell the blocks to move down
                    //Start checking from the bottom, then move up
                    for (int i=3; i>=0; i--) {
                        for (int j=3; j>=0; j--) {
                            //Get a list containing all of the GameSquare objects at position (i,j)
                            List blockList = getObjectsAt(j,i,GameSquare.class);
            
                            //Tell the other block object we wish to merge with it.  If successful, delete this block from the game
                            if (blockList.size() == 1) { //Error checking
                                Object tempObject;
                                tempObject = blockList.get(0);
                                GameSquare tempSquare;
                                tempSquare = (GameSquare)tempObject;
                                tempSquare.move(DOWN);
                            }
                        }
                    }
                    break;

                case "left":
                    //Tell the blocks to move left
                    //Start checking from the left, then move right
                    for (int i=0; i<getWidth(); i++) {
                        for (int j=0; j<getHeight(); j++) {
                            //Get a list containing all of the GameSquare objects at position (i,j)
                            List blockList = getObjectsAt(j,i,GameSquare.class);
            
                            //Tell the other block object we wish to merge with it.  If successful, delete this block from the game
                            if (blockList.size() == 1) { //Error checking
                                Object tempObject;
                                tempObject = blockList.get(0);
                                GameSquare tempSquare;
                                tempSquare = (GameSquare)tempObject;
                                tempSquare.move(LEFT);
                            }
                        }
                    }
                    break;

                }
                

            //Since placeRandomBlock is not guaranteed to work the first time, repeat the process until it does (Video)
            //This is NOT a great way to do this.  (Hint: Making this part better could be a level 4 feature)
            int count = 0;
            if(numberOfObjects() < 16) {
                while (count < 1) {
                    if (placeRandomBlock()) {
                        count++;
                    }
                }
            }
        }
        
        for (int i=0; i<getWidth(); i++) {
            for (int j=0; j<getHeight(); j++) {
                if (!getObjectsAt(i,j,GameSquare.class).isEmpty()) {
                    GameSquare sqr = (GameSquare)getObjectsAt(i,j,GameSquare.class).get(0);
                    if(sqr.getValue() == winVal) {
                        Greenfoot.setSpeed(50);
                        Greenfoot.delay(60);
                        removeObjects(getObjects(null));
                        setBackground("youwin.png");
                        addObject(new button(),2,3);
                    }
                }
            }
        }
    }
}