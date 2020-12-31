import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;


public class GameSquare extends Actor
{
    //Instance Constants
    //Define some directions to make it easier to reference which way the blocks are moving
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    
    //Define a debugging variable
    private final boolean debug = false;
    
    //Instance Variables    
    private int value;
        
    //Constructor
    public GameSquare()
    {
        this(2);
    }
    
    public GameSquare(int valueIn)
    {
        int bias = (Greenfoot.getRandomNumber(50));
        if (bias < 35) {
            int num = 1;
            valueIn *= num;
            setValue(valueIn);
            displayValue();
        } else {
            int num = 2;
            valueIn *= num;
            setValue(valueIn);
            displayValue();
        }
    }
    
    /**
     * Tell the block to move in the given direction until it hits an obstacle
     * 
     * @param The direction in which the block is to move (UP = 0; RIGHT = 1; DOWN = 2; LEFT = 3;
     */
    public void move(int direction) 
    {
        //check if can move
        int movable = canMove(direction);
        
        //if moveable, start a loop
        while (movable > 0)
        {
            //Get current coordinates
            int xval = getX();
            int yval = getY();
            //Change x and y values to the "new" location based on direction
            if (direction == 0) {
                yval-=1;
            } else if (direction == 1) {
                xval+=1;
            } else if (direction == 2) {
                yval+=1;
            } else if (direction == 3) {
                xval-=1;
            }

            //If Nothing in the way - move the block
            if (movable == 1) {
                setLocation(xval,yval);
                movable = canMove(direction);
                continue;
                //return;
            }
            //Merge the blocks
            else {
                if (!getWorld().getObjectsAt(xval,yval,GameSquare.class).isEmpty()) {
                    GameSquare sqr = (GameSquare)getWorld().getObjectsAt(xval,yval,GameSquare.class).get(0);
                    if(merge(movable)) {
                       getWorld().removeObject(sqr);
                       setLocation(xval,yval);
                       displayValue();
                       movable = canMove(direction);
                       continue;
                    } else {
                        movable = 0;
                        continue;
                    }
                } else {
                    movable = canMove(direction);
                    continue;
                }
            }
        }
        
        
        //can't move, so don't move.
        return;
    }

    /**
     * Sets the value of the game square to the value of the input parameter.
     * Will only work if the value is a factor of 2
     * 
     * @param The number to use as the new value
     * @return If the number was set correctly return true, otherwise return false
     */
    
    boolean setValue(int valueIn) {
        if(valueIn % 2 != 0) {
            return false;
        } else {
            value = valueIn;
            return true;
        }
    }
    
    /**
     * Merge with another block and combine values.
     * Will only work if the two blocks are of the same value
     * 
     * @param The value of the block to be added
     * 
     * @return Return true if the merge is successful.
     */

    public boolean merge(int valueIn) {
        int sqrVal = getValue();
        if (sqrVal == valueIn) {
            setValue((sqrVal*2));
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Returns the current value of the gameSquare
     * 
     * @return The current value (int) of the game square
     */
    
    public int getValue() {
        return this.value;
    }

    /**
     * Checks to see if the block can move
     * 
     * @return int value representing what is in the space to be moved to.  0 -> Path Blocked, 1 -> Empty Space, int>1 value of block in the space to be moved to.
     */
    private int canMove(int direction)
    {
        //Get World
        World world = getWorld();
        
        //Get x and y values of current object
        int xval = getX();
        int yval = getY();
        
        //Change x and y values to the "new" location based on direction
        if (direction == 0) {
            yval-=1;
        } else if (direction == 1) {
            xval+=1;
        } else if (direction == 2) {
            yval+=1;
        } else if (direction == 3) {
            xval-=1;
        }
        //Test for outside border
        if (xval<0 || xval>3 || yval<0 || yval>3) {
            return 0;
        }
        
        //Check to see if there is a block in the way
        if(!world.getObjectsAt(xval, yval, GameSquare.class).isEmpty()) {
            GameSquare sqr = (GameSquare)world.getObjectsAt(xval,yval,GameSquare.class).get(0);
            return sqr.getValue();
        }
        
        return 1;
    }
    
    /**
     * displayValue - Displays the current value of a block in an image, then sets that image to the block display image
     */    
    private void displayValue() 
    {
        //Create an image consisting of the display value using built in greenfoot commands
        GreenfootImage displayImage;
        displayImage = new GreenfootImage(getValue() + ".png");
        setImage(displayImage);
    }
}