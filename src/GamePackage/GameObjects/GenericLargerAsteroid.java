package GamePackage.GameObjects;

import utilities.SoundManager;

public abstract class GenericLargerAsteroid extends GenericAsteroid {


    private int childrenToSpawn;

    //used in updateColour, basically timeToLive/8 but saved so it only needs to be calculated once per update
    int redScale;

    GenericLargerAsteroid(){ super(); } //random GenericLargerAsteroid

    //public GenericLargerAsteroid(Vector2D p){ super(p); } //random GenericLargerAsteroid with known position



    @Override
    protected void setSpecifics(){ timeToLive = (int)(Math.random() * 512) + 512; }
    //timeToLive will be somewhere between 512 and 1024 update calls

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

    //Big and Medium asteroids spawn differently
    protected abstract void updateColour();

    //obtaining the number of children that need to be spawned
    public int getChildrenToSpawn(){ return childrenToSpawn; }

    //reviving the child asteroid passed to this (freshly popped from the appropriate stack) to have the appropriate position
    public GenericAsteroid reviveChild(GenericAsteroid a){ return a.revive(position); }
}
