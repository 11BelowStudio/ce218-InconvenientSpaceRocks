package game;

import utilities.Vector2D;

import java.util.ArrayList;

public class BigAsteroid extends GenericLargerAsteroid {


    public BigAsteroid(){
        super();
    }

    @Override
    protected void setSpecifics(){
        super.setSpecifics();
        RADIUS = 50;
        pointValue = 24; //worth 4 potential medium asteroids (extra 12 potential from the 2 spawned mediums = potential 36) if shot
            //only worth up to 30 points from the mediums if this is allowed to expire (25 if mediums allowed to expire)
        asteroidScale = 1.25;

    }




    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}
