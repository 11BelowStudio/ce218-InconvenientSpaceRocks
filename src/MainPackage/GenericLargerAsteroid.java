package MainPackage;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class GenericLargerAsteroid extends GenericAsteroid {

    //how long it can persist for
    private int timeToLive;

    public int childrenToSpawn;

    int redScale;


    public GenericLargerAsteroid(){ super();} //random asteroid at an unknown position

    public GenericLargerAsteroid(Vector2D p){ super(p); }//random asteroid at a known position

    @Override
    protected void setSpecifics(){ timeToLive = (int)(Math.random() * 512) + 512; }

    public void update() {
        super.update();
        timeToLive--;
        if (timeToLive > 0){
            redScale = timeToLive / 8;
            updateColour();
        } else {
            dead = true;
            wasHit = false; //died from not being hit if it died as a result of time to live expiring
            spawnChildren();
        }
    }

    @Override
    //calls spawnChildren() when this is hit, as it died from not natural causes.
    public void hit(boolean hitByPlayer){
        super.hit(hitByPlayer);
        spawnChildren();
    }


    private void spawnChildren() {
        if (wasHit) {
            SoundManager.playCrunch();
            //spawns 2 children if killed, and plays the shorter crunch noise
            childrenToSpawn = 2;
        } else {
            SoundManager.playMedCrunch();
            //spawns 5 children if allowed to expire naturally, longer crunch noise
            childrenToSpawn = 5;
        }
    }

    protected abstract void updateColour();

    public int getChildrenToSpawn(){
        return childrenToSpawn;
    }

    public GenericAsteroid reviveChild(GenericAsteroid a){
        return a.revive(position);
    }


}
