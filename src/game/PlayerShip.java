package game;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static game.Constants.*;

public class PlayerShip extends Ship {
    //public static final int RADIUS = 8;

    // rotation velocity in radians per second
    public static final double STEER_RATE = 2*Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 5;

    //maximum speed
    private double MAX_SPEED = 250;

    // constant speed loss factor
    public static final double DRAG = 0.015;

    public static final Color SHIP_COLOUR =  new Color(0,255,255,32);
    public static final Color GODMODE_COLOUR = new Color(255,0,255,32);





    //private static final int RESPAWN_GRACE_PERIOD = 1000;
    private static final int RESPAWN_GRACE_PERIOD = 1000;
    //player has a grace period of 1000ms (1s) of invulnerability when respawning
    private static final int REWARD_GRACE_PERIOD = 250;
    //0.25s of invulnerability after destroying an asteroid

    private long gracePeriodExpiresAt;
    //records when the player's grace period will expire





    public PlayerShip(Controller ctrl, Game game) {
        //super(new Vector2D(0,0),Vector2D.polar(Math.toRadians(270),0), ctrl, game);

        super(new Vector2D(HALF_WIDTH,HALF_HEIGHT),Vector2D.polar(Math.toRadians(270),0), ctrl, game);

        //super(new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2),Vector2D.polar(Math.toRadians(270),0), ctrl, game);

        //position = new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2);
        direction = Vector2D.polar(Math.toRadians(270),1);
        //velocity = Vector2D.polar(direction.angle(),0);

        /*
        //declaring the ship shape
        hitboxX = new int[] {0,2,0,-2};
        hitboxY = new int[] {1,2,-2,2};

        this.objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,1);

        //ensures that the ship's hitbox and texture is scaled correctly
        RADIUS = DRAWING_SCALE*2;

        definedRect = new Rectangle((int)(position.x - RADIUS),(int)(position.y - RADIUS),(int)RADIUS*2,(int)RADIUS*2);

         */

        //declaring the thrust triangle shape



        gracePeriodExpiresAt = System.currentTimeMillis() + RESPAWN_GRACE_PERIOD;



        //intangible = true;

        objectColour = SHIP_COLOUR;

        texture = (BufferedImage)Constants.SHIP;

        BULLET_DELAY = 250;

        warpDistance = 200;
        MAX_SPEED = 250;



    }

    @Override
    public void revive() {
        super.revive(new Vector2D(HALF_WIDTH,HALF_HEIGHT),Vector2D.polar(Math.toRadians(270),0),Vector2D.polar(Math.toRadians(270),1));
        gracePeriodExpiresAt = System.currentTimeMillis() + RESPAWN_GRACE_PERIOD;
        objectColour = SHIP_COLOUR;
        texture = (BufferedImage)Constants.SHIP;
        BULLET_DELAY = 250;
        warpDistance = 200;
    }

    public PlayerShip(Controller ctrl, Game game, Vector2D direction){
        this(ctrl,game);
        this.direction = direction;
    }


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
    }



    @Override
    protected void hitLogic(boolean hitByPlayer) {
        //currently doesn't need to do anything if hit, may change
        System.out.println("oof");
    }


    //@Override
    protected void addBulletToChildren() {
        childObjects.add(
                new PlayerBullet(
                        new Vector2D(
                                (position.x + ((2 * RADIUS) * (direction.x))),
                                (position.y + ((2 * RADIUS) * (direction.y)))
                        ),
                        direction
                )
        );
        //the new bullet is constructed in front of where the ship is, in the direction that it is pointing
        //and is put it in childObjects

        SoundManager.fire();
    }

    @Override
    protected void notIntangible(){
        super.notIntangible();
        System.out.println("no more godmode for u"); //debug message
        this.objectColour = SHIP_COLOUR; //goes back to normal colour
    }

    @Override
    protected void drawDetails(Graphics2D g) {
        if (thrusting) {
            g.setColor(Color.red);
            g.fillPolygon(thrustPolygon);
        }
    }

    public void giveImmunity(){
        //gives the ship some temporary immunity at the start of every level,
        //so it won't get defeated by a new asteroid as it spawns in
        gracePeriodExpiresAt = System.currentTimeMillis() + REWARD_GRACE_PERIOD;
        intangible = true;
    }




}