package game;

public class Action {
    public int thrust; // 0 = off, 1 = on
    public int turn; // -1 = left turn, 0 = no turn, 1 = right turn
    public boolean shoot; //whether or not the ship is shooting
    public boolean theAnyButton; //the 'any' button (true when any button is pressed, false when any button is released)
    public boolean warp;
    public boolean bomb;


    public boolean p;

    public boolean escape;

    public void stopAll(){
        thrust = 0;
        turn = 0;
        shoot = false;
        theAnyButton = false;
        warp = false;
        bomb = false;

        p = false;
    }

    public boolean theAnyButton(){ return theAnyButton; }

    public Action(){}

}