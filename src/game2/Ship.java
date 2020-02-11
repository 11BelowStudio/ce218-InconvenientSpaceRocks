package game2;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.util.ArrayList;

import static game2.Constants.*;

public class Ship extends GameObject {
    //public static final int RADIUS = 8;

    // rotation velocity in radians per second
    public static final double STEER_RATE = 2*Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 5;

    //maximum speed
    public static final double MAX_SPEED = 250;

    // constant speed loss factor
    public static final double DRAG = 0.015;

    public static final Color SHIP_COLOUR = Color.cyan;
    public static final Color GODMODE_COLOUR = Color.MAGENTA;

    public static final long BULLET_DELAY = 250;
    //delay between shooting bullets (in milliseconds) (250ms = 1/4s)

    private long canFireNextBulletAt;
    //when the player can fire their next bullet

    private static final int GRACE_PERIOD = 1000;
    //player has a grace period of 1000ms (1s) of invulnerability when respawning

    private long gracePeriodExpiresAt;
    //records when the player's grace period when respawning will expire


    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    public Vector2D direction;

    // controller which provides an Action object in each frame
    private Controller ctrl;

    //recording if there is thrust
    private boolean thrusting;


    public Bullet lastBullet;

    public Game game;

    private Polygon thrustPolygon;

    public Ship(Controller ctrl, Game game) {

        super(new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2),Vector2D.polar(Math.toRadians(270),0));

        this.ctrl = ctrl;

        this.game = game;

        //position = new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2);
        direction = Vector2D.polar(Math.toRadians(270),1);
        //velocity = Vector2D.polar(direction.angle(),0);

        //declaring the ship shape
        hitboxX = new int[] {0,2,0,-2};
        hitboxY = new int[] {1,2,-2,2};

        this.objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,1);

        //declaring the thrust triangle shape
        int[] XPTHRUST = new int[]{0,1,-1};
        int[] YPTHRUST = new int[]{2,0,0};

        this.thrustPolygon = PolygonUtilities.scaledPolygonConstructor(XPTHRUST,YPTHRUST,1);


        //the most recent bullet that has been fired
        lastBullet = null;

        //ensures that the ship's hitbox is scaled correctly
        RADIUS = DRAWING_SCALE*2;

        canFireNextBulletAt = System.currentTimeMillis();
        //allows bullet to be fired instantly basically

        gracePeriodExpiresAt = System.currentTimeMillis() + GRACE_PERIOD;

        intangible = true;

    }

    public Ship(Controller ctrl, Game game, Vector2D direction){
        this(ctrl,game);
        this.direction = direction;
    }


    @Override
    public void update(){
        Action currentAction = ctrl.action();
        //Vector2D lastPos = new Vector2D(position);

        if (currentAction.shoot){
            mkBullet();
            currentAction.shoot = false;
        }

        if (intangible && System.currentTimeMillis() >= gracePeriodExpiresAt){
            //will cause the player's godmode to expire after the grace period expires
            intangible = false;
            System.out.println("no more godmode for u");
        }

        direction.rotate(Math.toRadians(currentAction.turn * STEER_RATE));
        //if the ship has a 1 or -1 for turn, it will turn in the appropriate direction
        direction.normalise();

        //adds the new direction to velocity, scaled by whether or not thrust is being applied, over the frame time
        velocity.addScaled(direction,(MAG_ACC/DT) * currentAction.thrust);

        thrusting = (currentAction.thrust != 0);

        if (velocity.mag() > MAX_SPEED){
            //ensures that velocity is capped at MAX_SPEED
            velocity.set(Vector2D.polar(velocity.angle(),MAX_SPEED));
        }

        velocity.mult(1-DRAG);
        //reduces velocity by DRAG

        position.addScaled(velocity,DT);
        //updates the position by the velocity (weighted by the frame time)

        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
        //wraps the position around if appropriate

        //System.out.println("\n");
        //System.out.println(direction);
        //System.out.println(velocity);
        //System.out.println(position);

        /*
        for (Bullet b: bulletList) {
            b.update();
        }*/
        //updateHitbox(lastPos);
        //Vector2D moveDist = getMoveDist(lastPos);
        //thrustPolygon.translate((int)moveDist.x, (int)moveDist.y);

    }

    @Override
    protected void hitLogic() {
        //currently doesn't need to do anything if hit, may change
        System.out.println("oof");
    }

    public void mkBullet(){
        if (System.currentTimeMillis() >= canFireNextBulletAt) {
            //if it's gone past the time when the next bullet can be fired,
            //a bullet can be fired

            intangible = false; //attacking will cause a premature end to the player's intangibility

            canFireNextBulletAt = System.currentTimeMillis() + BULLET_DELAY;
            //works out when the player is next allowed to fire a bullet
            childObjects = new ArrayList<>();
            childObjects.add(
                    new Bullet(
                        new Vector2D(
                                (position.x + ((2 * RADIUS) * (direction.x))),
                                (position.y + ((2 * RADIUS) * (direction.y)))
                        ),
                        direction
                    )
            );
            //the new bullet is constructed in front of where the ship is, in the direction that it is pointing
            //and is put it in childObjects
        }
    }

    public void draw(Graphics2D g){
        AffineTransform at = g.getTransform(); //gets a backup of the default transformation of the Graphics2D object
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        //AffineTransform translatedRotated = g.getTransform(); //gets backup of the scale before drawing scale was done
        //g.scale(DRAWING_SCALE, DRAWING_SCALE);
        //g.scale(RADIUS, RADIUS);
        if (thrusting) {
            g.setColor(Color.red);
            g.fillPolygon(thrustPolygon);
        }
        if (intangible){
            g.setColor(GODMODE_COLOUR);
        } else {
            g.setColor(SHIP_COLOUR);
        }
        //g.fillPolygon(objectPolygon);
        //g.fillPolygon(objectPolygon = new Polygon(hitboxX, hitboxY, hitboxX.length));
        //g.setTransform(translatedRotated);
        //g.fillPolygon(objectPolygon);
        //g.fillPolygon(objectPolygon);
        //objectPolygon.translate((int)position.x,(int)position.y);
        Shape transformedShape = g.getTransform().createTransformedShape(objectPolygon);;
        g.setTransform(at); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        g.fill(transformedArea);

        /*
        for (Bullet b: bulletList){
            if (b.dead){
                bulletList.remove(b);
                //causes java.util.ConcurrentModificationException
            } else {
                b.draw(g);
            }
        }*/
    }

    public void giveImmunity(){
        //gives the ship some temporary immunity at the start of every level,
        //so it won't get defeated by a new asteroid as it spawns in
        gracePeriodExpiresAt = System.currentTimeMillis() + GRACE_PERIOD;
        intangible = true;
    }


}