package GamePackage.GameObjects;

import GamePackage.Controllers.Action;
import GamePackage.Controllers.Controller;
import utilities.SoundManager;
import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static GamePackage.GameObjects.GameObjectConstants.*;

public abstract class Ship extends GameObject {

    // rotation velocity in radians per second
    double STEER_RATE = 2*Math.PI;

    // acceleration when thrust is applied
    private static final double MAG_ACC = 5;

    //maximum speed
    static final double MAX_SPEED = 250;

    //public static final int RADIUS = 8;

    // constant speed loss factor

    //public static Color SHIP_COLOUR;

    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    Vector2D direction;

    // controller which provides an Action object in each frame
    Controller ctrl;

    //recording if there is thrust
    private boolean thrusting;

    Color thrustColour;

    long BULLET_DELAY = 250;
    //delay between shooting bullets (in milliseconds) (250ms = 1/4s)

    private long canFireNextBulletAt;
    //when the player can fire their next bullet


    private Rectangle definedRect;

    private Polygon thrustPolygon;

    private final static long WARP_DELAY = 500;

    private long canWarpAt;

    double warpDistance;

    private boolean fired;

    private Vector2D bulletLocation;

    private Vector2D bulletVelocity;

    Action currentAction;

    private int definedWidthHeight;

    long now;


    Ship(Vector2D p, Vector2D v, Vector2D d, Controller ctrl){
        super(p,v);
        this.ctrl = ctrl; //has a controller

        direction = d;

        now = System.currentTimeMillis();

        canFireNextBulletAt = now;
        //allows bullet to be fired instantly basically
        warpDistance = 100;
        canWarpAt = now;
        //can warp instantly

        //declaring the ship shape
        this.objectPolygon = PolygonUtilities.scaledPolygonConstructor(new int[] {0,2,0,-2},new int[] {1,2,-2,2},DRAWING_SCALE);

        //the thrust polygon
        this.thrustPolygon = PolygonUtilities.scaledPolygonConstructor(new int[]{0,1,-1},new int[]{2,0,0},DRAWING_SCALE);


        //ensures that the ship's hitbox and texture is scaled correctly
        RADIUS = DRAWING_SCALE*2;

        definedWidthHeight = (int)RADIUS*2;

        definedRect = new Rectangle((int)(position.x - RADIUS),(int)(position.y - RADIUS),definedWidthHeight,definedWidthHeight);

        thrustColour = Color.red; //red thrust polygon by default

    }

    public Ship revive(Vector2D p, Vector2D v, Vector2D d){
        super.revive(p,v);
        this.direction = d;
        definedRect = new Rectangle((int)(position.x - RADIUS),(int)(position.y - RADIUS),definedWidthHeight,definedWidthHeight);
        now = System.currentTimeMillis();
        canWarpAt = now;
        canFireNextBulletAt = now;
        bulletLocation = null;
        bulletVelocity = null;
        fired = false;
        intangible = true;
        return this;
    }

    public void update(){
        currentAction = ctrl.action(); //obtains 'action' from ctrl

        now = System.currentTimeMillis(); //current time

        //defines 'fired' as whatever currentAction.shoot is (and calls 'shoot()' if it's true)
        if (fired = currentAction.shoot){ shoot(); }

        direction.rotate(Math.toRadians(currentAction.turn * STEER_RATE));
        //if the ship has a 1 or -1 for turn, it will turn in the appropriate direction
        direction.normalise();

        if (thrusting = (currentAction.thrust != 0)){
            SoundManager.startThrust(); //thrust noises
            //adds the new direction to velocity, scaled by whether or not thrust is being applied, over the frame time
            velocity.addScaled(direction,(MAG_ACC/DT));
        } else{
            stopThrust(); //no thrust noises
        }

        velocity.mult(DRAG);
        //reduces velocity by DRAG

        if (velocity.mag() > MAX_SPEED){ velocity.setMag(MAX_SPEED); } //velocity is capped at MAX_SPEED

        position.addScaled(velocity,DT);
        //updates the position by the velocity (weighted by the frame time)


        if (currentAction.warp){ warp(); } //calls warp() if currentAction.warp is true

        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
        //wraps the position around if appropriate

        definedRect = new Rectangle((int)(position.x - RADIUS),(int)(position.y - RADIUS),definedWidthHeight,definedWidthHeight);
        //defines the definedRect (used for drawing the texture

        rotationAngle = direction.angle() + Math.PI / 2;
    }


    private void shoot(){
        if (now > canFireNextBulletAt) {
            //if it's gone past the time when the next bullet can be fired,a bullet can be fired
            canFireNextBulletAt = (now + BULLET_DELAY);
            //works out when the player is next allowed to fire a bullet
            //spawns the bullet 2*radius away from position
            bulletLocation = Vector2D.addScaled(position,direction,2*RADIUS);
            bulletLocation.wrap(FRAME_WIDTH,FRAME_HEIGHT);
            //obligatory wraparound
            //bullet will be going in the direction the ship is facing, but at 300 magnitude
            bulletVelocity = Vector2D.setMag(direction,300);
            notIntangible(); //attacking will cause a premature end to the player's intangibility
        } else{
            fired = false; //actually hasn't fired if it can't shoot
        }
    }

    private void warp(){
        if (now >= canWarpAt) { //if it's gone past the time when the ship can warp, it will warp
            SoundManager.playBwoab(); //b w o a b
            position.addScaled(direction, warpDistance); //moves warpDistance away in current direction
            canWarpAt = now + WARP_DELAY; //next warp will be after WARP_DELAY
            velocity.setMag(0); //no velocity
        }
    }


    public void draw(Graphics2D g){
        AffineTransform at = g.getTransform(); //gets a backup of the default transformation of the Graphics2D object
        g.translate(position.x, position.y);
        g.rotate(rotationAngle);
        if (thrusting) {
            //colours in the thrust polygon
            g.setColor(thrustColour);
            g.fillPolygon(thrustPolygon);
        }
        Shape transformedShape = g.getTransform().createTransformedShape(objectPolygon);
        g.setTransform(at); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        paintTheArea(g);
    }




    //casually using the definedRect, not the bounding box, to render the texturepaint psuedo-sprite correctly
    //(le lack of an image editor with transparency has arrived)
    @Override
    protected void paintTexture(Graphics2D g){
        g.setPaint(new TexturePaint(texture,definedRect));
        g.fill(transformedArea);
    }

    public boolean hasFired(){ return fired; }

    public Bullet setBullet(Bullet b){
        fired = false;
        return b.revive(bulletLocation,bulletVelocity);
    }

    public Vector2D getDirection(){ return direction; }

    private void stopThrust(){
        SoundManager.stopThrust();
    }

    @Override
    void hit(boolean hitByPlayer){
        super.hit(hitByPlayer);
        stopThrust();
    }

    @Override
    void hitByBomb() {
        super.hitByBomb();
        stopThrust();
    }

    @Override
    public GameObject kill() {
        stopThrust();
        return super.kill();
    }
}
