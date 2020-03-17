package GamePackage;


import utilities.Vector2D;

import java.awt.*;


public class Asteroid extends GenericAsteroid {

    //asteroid in random location with random velocity
    public Asteroid(){ super(); }

    //asteroid in set location with random velocity. pretty much unused now, was initially used to spawn child asteroids.
    public Asteroid(Vector2D p){ super(p); }

    //asteroid with set position and velocity. intended for testing, pretty much obsolete now
    public Asteroid(Vector2D p, Vector2D v) { super(p,v); }

    @Override
    //specific stuff for a normal Asteroid
    protected void setSpecifics(){
        RADIUS = 15;
        pointValue = 1;
        asteroidScale = 0.5;
        objectColour = new Color(255,0,0,128);
    }

    @Override
    //like hit but plays solidHit too
    public void hit(boolean hitByPlayer){
        super.hit(hitByPlayer);
        SoundManager.playSolidHit();
    }

    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}