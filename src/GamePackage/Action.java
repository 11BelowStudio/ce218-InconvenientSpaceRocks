package GamePackage;

import java.awt.*;

//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene) (enhanced by me)

public class Action {
    public int thrust; // 0 = off, 1 = on
    public int turn; // -1 = left turn, 0 = no turn, 1 = right turn
    public boolean shoot; //whether or not the ship is shooting
    private boolean theAnyButton; //the 'any' button (true when any button is pressed, false when any button is released)
    public boolean warp; //whether or not the warp button is pressed
    public boolean bomb; //whether or not the bomb button is pressed

    public boolean clicked; //whether or not the mouse is clicked

    public Point clickLocation; //where the mouse was last clicked

    public boolean p; //whether or not p.

    public void noAction(){
        thrust = 0;
        turn = 0;
        shoot = false;
        theAnyButton = false;
        warp = false;
        bomb = false;
        p = false;
        clicked = false;
    }

    public boolean theAnyButton(){ return theAnyButton; }

    public void pressedTheAnyButton(){theAnyButton = true;}

    public void releasedTheAnyButton(){theAnyButton = false;}

    public Action(){}

}