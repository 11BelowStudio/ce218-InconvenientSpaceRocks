package GamePackage;

import utilities.Vector2D;

import java.awt.*;

import static GamePackage.Constants.*;

public class PlayerShip extends Ship {

    private static final Color SHIP_COLOUR =  new Color(0,255,255,32);
    private static final Color INVINCIBLE_COLOUR = new Color(255,0,255,32);


    //private static final int RESPAWN_GRACE_PERIOD = 1000;
    private static final int RESPAWN_GRACE_PERIOD = 1000;
    //player has a grace period of 1000ms (1s) of invulnerability when respawning
    private static final int REWARD_GRACE_PERIOD = 250;
    //0.25s of invulnerability after destroying an asteroid

    private long gracePeriodExpiresAt;
    //records when the player's grace period will expire

    private boolean spawnBomb;
    private Vector2D bombPosition;
    private Vector2D bombVelocity;


    public PlayerShip(Controller ctrl) {
        super(new Vector2D(HALF_WIDTH,HALF_HEIGHT), Vector2D.polar(UP_RADIANS,0), Vector2D.polar(UP_RADIANS,1), ctrl);
        gracePeriodExpiresAt = now + RESPAWN_GRACE_PERIOD; //grace period expires after the RESPAWN_GRACE_PERIOD
        objectColour = INVINCIBLE_COLOUR; //invincible colour by default (because respawn invulnerability)
        texture = Constants.SHIP; //has the 'SHIP' texture
        BULLET_DELAY = 250; //250ms between shots
        warpDistance = 200; //200 unit warp distance
        objectType = PLAYER_OBJECT; //player object
    }



    public PlayerShip revive() {
        super.revive(new Vector2D(HALF_WIDTH,HALF_HEIGHT), Vector2D.polar(UP_RADIANS,0), Vector2D.polar(UP_RADIANS,1));
        gracePeriodExpiresAt = now + RESPAWN_GRACE_PERIOD; //given respawn invulnerability
        objectColour = INVINCIBLE_COLOUR; //invincible at first
        return this;
    }

    @Override
    public void update(){
        super.update();
        if (spawnBomb = currentAction.bomb){  //defines spawnBomb to be the same as currentAction.bomb
            //if the player is trying to spawn a bomb
            bombVelocity = Vector2D.flip(direction); //bomb goes in opposite direction to ship
            bombPosition = Vector2D.addScaled(position,bombVelocity,2.5*RADIUS); //2.5*RADIUS away from ship at first
            bombPosition.wrap(FRAME_WIDTH,FRAME_HEIGHT); //wraparound time
            bombVelocity.setMag(100); //100 speed for bomb
            notIntangible(); //no more intangibility
        }


        if (intangible && now >= gracePeriodExpiresAt){ notIntangible();}
        //will cause the player's godmode to expire after the grace period expires
    }


    @Override
    protected void notIntangible(){ intangible = false; this.objectColour = SHIP_COLOUR; }
    //no more intangibility, ship goes back to normal



    public void giveImmunity(){
        //gives the ship some temporary immunity at the start of every level (or after an enemy is destroyed),
        //so it won't get defeated by a new asteroid as it spawns in
        gracePeriodExpiresAt = System.currentTimeMillis() + REWARD_GRACE_PERIOD;
        intangible = true;
        this.objectColour = INVINCIBLE_COLOUR;
    }

    public boolean canSpawnBomb(){ return spawnBomb; }
    //returns value of spawnBomb

    //given a bomb object, revives it with bombPosition and bombVelocity
    public Bomb setBomb(Bomb b){
        spawnBomb = false;
        return b.revive(bombPosition,bombVelocity);
    }




}