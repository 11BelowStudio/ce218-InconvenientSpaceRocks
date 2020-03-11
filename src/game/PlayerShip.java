package game;

import utilities.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

import static game.Constants.*;

public class PlayerShip extends Ship {

    public static final Color SHIP_COLOUR =  new Color(0,255,255,32);
    public static final Color GODMODE_COLOUR = new Color(255,0,255,32);


    //private static final int RESPAWN_GRACE_PERIOD = 1000;
    private static final int RESPAWN_GRACE_PERIOD = 1000;
    //player has a grace period of 1000ms (1s) of invulnerability when respawning
    private static final int REWARD_GRACE_PERIOD = 250;
    //0.25s of invulnerability after destroying an asteroid

    private long gracePeriodExpiresAt;
    //records when the player's grace period will expire

    boolean spawnBomb;
    Vector2D bombPosition;
    Vector2D bombVelocity;





    public PlayerShip(Controller ctrl) {
        super(new Vector2D(HALF_WIDTH,HALF_HEIGHT),Vector2D.polar(UP_RADIANS,0),Vector2D.polar(UP_RADIANS,1), ctrl);

        gracePeriodExpiresAt = System.currentTimeMillis() + RESPAWN_GRACE_PERIOD;

        objectColour = SHIP_COLOUR;

        texture = (BufferedImage)Constants.SHIP;

        BULLET_DELAY = 250;

        warpDistance = 200;

        bombPosition = new Vector2D();

        objectType = PLAYER_OBJECT;
    }


    @Override
    public PlayerShip revive() {
        super.revive(new Vector2D(HALF_WIDTH,HALF_HEIGHT),Vector2D.polar(GameObject.UP_RADIANS,0),Vector2D.polar(UP_RADIANS,1));
        gracePeriodExpiresAt = System.currentTimeMillis() + RESPAWN_GRACE_PERIOD;
        objectColour = SHIP_COLOUR;
        texture = (BufferedImage)Constants.SHIP;
        BULLET_DELAY = 250;
        warpDistance = 200;
        return this;
    }

    /*
    public PlayerShip(Controller ctrl, Game game, Vector2D direction){
        this(ctrl,game);
        this.direction = direction;
    }*/


    @Override
    public void update(){
        super.update();
        if (intangible){
            if (System.currentTimeMillis() >= gracePeriodExpiresAt || fired){
                //will cause the player's godmode to expire after the grace period expires
                notIntangible();
            } else{
                this.objectColour = GODMODE_COLOUR;
                //intangible = true;
            }
        }
        if (ctrl.action().bomb){
            Vector2D reverseDirection = Vector2D.flip(direction);
            bombPosition.set(Vector2D.addScaled(position,reverseDirection,RADIUS*1.5));
            bombPosition.wrap(FRAME_WIDTH,FRAME_HEIGHT);
            //System.out.println(position.x + ", " + position.y);
            bombVelocity = Vector2D.flip(velocity).mult(0.5);
            ctrl.action().bomb = false;
            spawnBomb = true;
        } else{
            spawnBomb = false;
            //bombLocation = null;
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
        //System.out.println("no more godmode for u"); //debug message
        this.objectColour = SHIP_COLOUR; //goes back to normal colour
    }



    public void giveImmunity(){
        //gives the ship some temporary immunity at the start of every level,
        //so it won't get defeated by a new asteroid as it spawns in
        gracePeriodExpiresAt = System.currentTimeMillis() + REWARD_GRACE_PERIOD;
        intangible = true;
    }




}