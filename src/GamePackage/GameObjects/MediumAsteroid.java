package GamePackage.GameObjects;

//import utilities.Vector2D;

import java.awt.*;

public class MediumAsteroid extends GenericLargerAsteroid {

    public MediumAsteroid(){ super(); }

    //public MediumAsteroid(Vector2D p){ super(p); }
    //random asteroid at a known position
    //initially used for spawning in child medium asteroids, redundant ever since I moved to stacks and reviving

    @Override
    void setSpecifics(){
        super.setSpecifics();
        RADIUS = 25;
        pointValue = 4; //worth 4 small asteroids if destroyed by the player
            //2 small asteroids if shot = 6 potential points if shot, but only 5 if allowed to decay
        asteroidScale = 0.75;
    }


    @Override
    void updateColour(){
        if (redScale < 0){
            redScale = 0;
        }
        this.objectColour = new Color(255-redScale,redScale,0,128); //colour is somewhere between red and yellow
    }

    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}
