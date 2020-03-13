package MainPackage;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static MainPackage.Constants.*;
import static MainPackage.Constants.FRAME_HEIGHT;

public abstract class Ship extends GameObject {

    // rotation velocity in radians per second
    protected double STEER_RATE = 2*Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 5;

    //maximum speed
    public static final double MAX_SPEED = 250;

    //public static final int RADIUS = 8;

    // constant speed loss factor

    //public static Color SHIP_COLOUR;

    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    public Vector2D direction;

    // controller which provides an Action object in each frame
    protected Controller ctrl;

    //recording if there is thrust
    protected boolean thrusting;

    protected Color thrustColour;

    protected long BULLET_DELAY;
    //delay between shooting bullets (in milliseconds) (250ms = 1/4s)

    protected long canFireNextBulletAt;
    //when the player can fire their next bullet


    public Bullet lastBullet;

    protected Rectangle definedRect;

    protected Polygon thrustPolygon;

    protected Action currentAction;

    protected long WARP_DELAY = 500;

    protected long canWarpAt;

    protected double warpDistance;

    public boolean fired;

    public Vector2D bulletLocation;

    public Vector2D bulletVelocity;




    public Ship(Vector2D p, Vector2D v, Vector2D d, Controller ctrl){
        super(p,v);
        direction = d;

        //the most recent bullet that has been fired
        lastBullet = null;
        canFireNextBulletAt = System.currentTimeMillis();
        //allows bullet to be fired instantly basically

        //declaring the ship shape
        hitboxX = new int[] {0,2,0,-2};
        hitboxY = new int[] {1,2,-2,2};

        this.objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,1);

        int[] XPTHRUST = new int[]{0,1,-1};
        int[] YPTHRUST = new int[]{2,0,0};

        this.thrustPolygon = PolygonUtilities.scaledPolygonConstructor(XPTHRUST,YPTHRUST,1);

        //objectColour = SHIP_COLOUR;

        //ensures that the ship's hitbox and texture is scaled correctly
        RADIUS = DRAWING_SCALE*2;
        definedRect = new Rectangle((int)(position.x - RADIUS),(int)(position.y - RADIUS),(int)RADIUS*2,(int)RADIUS*2);
        this.ctrl = ctrl;
        canWarpAt = System.currentTimeMillis();

        warpDistance = 100;

        thrustColour = Color.red;

    }

    public Ship revive(Vector2D p, Vector2D v, Vector2D d){
        super.revive(p,v);
        this.direction = d;
        lastBullet = null;
        definedRect = new Rectangle((int)(position.x - RADIUS),(int)(position.y - RADIUS),(int)RADIUS*2,(int)RADIUS*2);
        canWarpAt = System.currentTimeMillis();
        canFireNextBulletAt = System.currentTimeMillis();
        bulletLocation = null;
        bulletVelocity = null;
        fired = false;
        intangible = true;
        return this;
    }

    public Ship revive() {
        revive(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),
                Vector2D.polar((Math.random() * Math.PI * 2), Math.random() * MAX_SPEED),
                Vector2D.polar((Math.random() * Math.PI * 2),1));
        return this;

    }

    //basically like revive, but actually returns this object
    public Ship reviveAndReturn(){
        this.revive();
        return this;
    }

    public void update(){
        currentAction = ctrl.action();
        //Vector2D lastPos = new Vector2D(position);

        //Vector2D lastPos = this.position;

        if (currentAction.shoot){
            mkBullet();
            currentAction.shoot = false;
        } else {
            fired = false;
        }

        direction.rotate(Math.toRadians(currentAction.turn * STEER_RATE));
        //if the ship has a 1 or -1 for turn, it will turn in the appropriate direction
        direction.normalise();

        //adds the new direction to velocity, scaled by whether or not thrust is being applied, over the frame time
        velocity.addScaled(direction,(MAG_ACC/DT) * currentAction.thrust);

        thrusting = (currentAction.thrust != 0);

        if (thrusting){
            SoundManager.startThrust();
        } else{
            SoundManager.stopThrust();
        }

        if (velocity.mag() > MAX_SPEED){
            //ensures that velocity is capped at MAX_SPEED
            velocity.setMag(MAX_SPEED);
        }

        velocity.mult(1-DRAG);
        //reduces velocity by DRAG

        position.addScaled(velocity,DT);
        //updates the position by the velocity (weighted by the frame time)

        long now = System.currentTimeMillis();
        if (currentAction.warp && now >= canWarpAt) {
            SoundManager.playBwoab();
            position.addScaled(direction, warpDistance);
            canWarpAt = now + WARP_DELAY;
            velocity.setMag(0);
            currentAction.warp = false;
        }

        finishUpdate();
    }

    protected void finishUpdate(){
        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
        //wraps the position around if appropriate

        definedRect = new Rectangle((int)(position.x - RADIUS),(int)(position.y - RADIUS),(int)RADIUS*2,(int)RADIUS*2);
        //defines the definedRect
    }

    protected void mkBullet(){
        if (System.currentTimeMillis() > canFireNextBulletAt) {
            //if it's gone past the time when the next bullet can be fired,
            //a bullet can be fired
            notIntangible(); //attacking will cause a premature end to the player's intangibility
            canFireNextBulletAt = (System.currentTimeMillis() + BULLET_DELAY);
            //works out when the player is next allowed to fire a bullet
            fireBullet();
        }
    }

    protected void fireBullet(){
        //spawns the bullet 2*radius away from position
        bulletLocation = Vector2D.addScaled(position,direction,2*RADIUS);
        bulletLocation.wrap(FRAME_WIDTH,FRAME_HEIGHT);
        //obligatory wraparound


        //bullet will be going in the direction the ship is facing, but at 300 magnitude
        bulletVelocity = Vector2D.setMag(direction,300);
        fired = true;
    }


    public void draw(Graphics2D g){
        AffineTransform at = g.getTransform(); //gets a backup of the default transformation of the Graphics2D object
        g.translate(position.x, position.y);

        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);



        if (thrusting) {
            //colours in the thrust polygon
            g.setColor(thrustColour);
            g.fillPolygon(thrustPolygon);
        }
        Shape transformedShape = g.getTransform().createTransformedShape(objectPolygon);;
        g.setTransform(at); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);

        paintTheArea(g);
    }




    //casually using the definedRect, not the bounding box, to render the texturepaint psuedo-sprite correctly
    //(le lack of an image editor with transparency has arrived)
    @Override
    protected void paintTheArea(Graphics2D g){
        g.setPaint(new TexturePaint(texture,definedRect));
        g.fill(transformedArea);
        g.setColor(objectColour);
        g.fill(transformedArea);
    }

}
