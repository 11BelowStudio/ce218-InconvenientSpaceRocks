package GamePackage.GameObjects;

import java.awt.*;

public class BigAsteroid extends GenericLargerAsteroid {

    //basically just uses the super constructor to make a random BigAsteroid
    public BigAsteroid(){ super(); }

    @Override
    //the specific values for a BigAsteroid
    void setSpecifics(){
        super.setSpecifics();
        RADIUS = 50;
        pointValue = 24; //worth 4 potential medium asteroids (extra 12 potential from the 2 spawned mediums = potential 36) if shot
            //only worth up to 30 points from the mediums if this is allowed to expire (25 if mediums allowed to expire)
        asteroidScale = 1.25;
    }


    void updateColour(){
        if (redScale < 0){
            redScale = 0;
        }
        objectColour = new Color(127-redScale,127+redScale,0,127);
    }

    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}
