import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class button extends Actor
{
    boolean state = false;
    String txt = "Click to reset";
    public void act() 
    {
        // Add your action code here.
    }
    public button() {
        bOff();
    }
    public button(boolean statIn, String txtIn) {
        txt = txtIn;
        if (statIn) { 
            bOn(); 
        } else {
            bOff();
        }
    }
    
    public void bOn() {
        GreenfootImage buttonImage = new GreenfootImage(txt, 20, Color.MAGENTA, Color.GRAY);
        setImage(buttonImage);
        state = true;
    }
    public void bOff() {
        GreenfootImage buttonImage = new GreenfootImage(txt, 20, Color.MAGENTA, Color.DARK_GRAY);
        setImage(buttonImage);
        state = false;
    }
    public void toggle() {
        if (state) { 
            bOff(); 
        } else {
            bOn();
        }
    }
    public boolean btnState() {
        if (state) {
            return true;
        } else {
            return false;
        }
    }
}
