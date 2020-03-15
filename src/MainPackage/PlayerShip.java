package MainPackage;

import utilities.Vector2D;

import java.awt.*;

import static MainPackage.Constants.*;

public class PlayerShip extends Ship {

    private static final Color SHIP_COLOUR =  new Color(0,255,255,32);
    private static final Color GODMODE_COLOUR = new Color(255,0,255,32);


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
        super(new Vector2D(HALF_WIDTH,HALF_HEIGHT),Vector2D.polar(UP_RADIANS,0),Vector2D.polar(UP_RADIANS,1), ctrl);

        gracePeriodExpiresAt = now + RESPAWN_GRACE_PERIOD;

        objectColour = SHIP_COLOUR;

        texture = Constants.SHIP;

        BULLET_DELAY = 250;

        warpDistance = 200;

        bombPosition = new Vector2D();

        objectType = PLAYER_OBJECT;
    }



    public PlayerShip revive() {
        super.revive(new Vector2D(HALF_WIDTH,HALF_HEIGHT),Vector2D.polar(GameObject.UP_RADIANS,0),Vector2D.polar(UP_RADIANS,1));
        gracePeriodExpiresAt = now + RESPAWN_GRACE_PERIOD;
        objectColour = SHIP_COLOUR;
        BULLET_DELAY = 250;
        warpDistance = 200;
        return this;
    }

    @Override
    public void update(){
        super.update();


        if (spawnBomb = currentAction.bomb){

            bombVelocity = Vector2D.flip(direction); //bomb goes in opposite direction to ship
            bombPosition = Vector2D.addScaled(position,bombVelocity,2.5*RADIUS); //2.5*RADIUS away from ship at first
            bombPosition.wrap(FRAME_WIDTH,FRAME_HEIGHT); //wraparound time
            bombVelocity.setMag(100); //100 speed for bomb
            notIntangible(); //no more intangibility
        }


        if (intangible){
            if (now >= gracePeriodExpiresAt){
                //will cause the player's godmode to expire after the grace period expires
                notIntangible();
            } else{
                this.objectColour = GODMODE_COLOUR;
                //intangible = true;
            }
        }
    }



    @Override
    protected void hitLogic(boolean hitByPlayer) {
        //currently doesn't need to do anything if hit, may change
        System.out.println("oof");
    }




    @Override
    protected void notIntangible(){
        super.notIntangible();
        this.objectColour = SHIP_COLOUR; //goes back to normal colour
    }



    public void giveImmunity(){
        //gives the ship some temporary immunity at the start of every level,
        //so it won't get defeated by a new asteroid as it spawns in
        gracePeriodExpiresAt = System.currentTimeMillis() + REWARD_GRACE_PERIOD;
        intangible = true;
        this.objectColour = GODMODE_COLOUR;
    }

    public boolean canSpawnBomb(){
        return spawnBomb;
    }

    public Bomb setBomb(Bomb b){
        spawnBomb = false;
        SoundManager.playBweb();
        return b.revive(bombPosition,bombVelocity);
    }




}