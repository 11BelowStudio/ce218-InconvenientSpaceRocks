package game;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.List;

import static game.Constants.*;

public abstract class GameObject{

    //https://docs.oracle.com/javase/8/docs/api/java/awt/Polygon.html

    public Vector2D position, velocity;

    public boolean dead;

    public double RADIUS; //kept for collision stuff

    public List<GameObject> childObjects; //will hold stuff that is spawned by this object

    protected boolean intangible;
    //whether or not this game object can be interacted with
    protected boolean finalIntangible;
    //records whether or not this is the final intangibility update; used to extend intangibility until the thing can be used
    protected boolean stillIntangible;

    protected boolean wasHit;
    //records if it was hit by something or not

    protected boolean playerHit;
    //records if the player hit it or not

    public int pointValue;

    public Polygon objectPolygon;

    //public Shape transformedShape;

    public Area transformedArea;

    public Rectangle areaRectangle;


    public BufferedImage texture;

    protected Color objectColour;

    public static final double DRAG = 0.015;

    int[] hitboxX, hitboxY;

    public static final double MAX_SPEED = 100;

    boolean collided;

    public GameObject(Vector2D p, Vector2D v){
        position = p;
        velocity = v;
        dead = false;
        intangible = true; //everything intangible until drawn at earliest to avoid exceptions being thrown on frame 1 collisions
        wasHit = false;
        playerHit = false;
        childObjects = null;
        pointValue = 0;
        finalIntangible = false;
        texture = (BufferedImage)AN_TEXTURE;
        objectColour = new Color(255,255,255,32);
        collided = false;
    }

    public void update(){
        collided = false;
        if (!dead) {
            position.addScaled(velocity, DT);
            position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
            /*
            if (position.x > HALF_WIDTH){
                position.x -= FRAME_WIDTH;
            }
            if (position.y > HALF_HEIGHT){
                position.y -= FRAME_HEIGHT;
            }*/
        }
    }


    public boolean hit(boolean hitByPlayer){
        if ((!finalIntangible || !stillIntangible) && !intangible){
            //if (!intangible) {
                //it's dead if it got hit whilst not intangible
                dead = true;
                intangible = true;
                hitLogic(hitByPlayer);
                return true;
            //}
        } return false;

        /* else {
            stillIntangible = true;
        }*/
    }

    protected void hitLogic(boolean hitByPlayer){
        wasHit = true;
        if (!playerHit) {
            playerHit = hitByPlayer; //will only be updated if this is not true;
        }
    }
    //will contain the stuff that will happen if this object was actually hit (and not intangible)

    public abstract void draw(Graphics2D g);

    public boolean overlap(GameObject other){
        // overlap detection based on areas
        try {
            /*if (this.intangible || other.intangible) {
                return false;
            } else */ //if (this.position.dist(other.position) <= 100) { //if (!(this instanceof GenericAsteroid  && other instanceof  GenericAsteroid)) {
                //else if (this instanceof GenericAsteroid  && (other instanceof Ship || other instanceof Bullet)) {

                if (this.areaRectangle.intersects(other.areaRectangle)) { //compares some bounding rectangles for the two objects
                //if (this.position.dist(other.position) <= 100){

                    //If the areas of the two gameObjects involved in the collision overlap in any way, they've collided
                    Area thisArea = new Area(this.transformedArea);
                    thisArea.intersect(other.transformedArea);
                    return !thisArea.isEmpty();
                //}
            }
        } catch(NullPointerException e){
            System.out.println(e);
            System.out.println("This: " + this.toString());
            System.out.println("Other: " + other.toString());
        }
        return false;
    }
    void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlap(other)) {
            if (this.intangible || other.intangible) {
                this.bounceOff(other);
            } else{
                this.hit(other instanceof PlayerShip || other instanceof PlayerBullet);
                other.hit(this instanceof PlayerShip || this instanceof PlayerBullet);
            }
        }
    }


    void capSpeed(){
        if (velocity.mag() > MAX_SPEED){
            velocity.setMag(MAX_SPEED);
        }
    }

    /*
    public Shape transformObjectPolygon(AffineTransform at){
        return at.createTransformedShape(objectPolygon);
        //return transformedObjectPolygon;
    }*/

    void wrapAround(Graphics2D g, Shape transformedShape){
        transformedArea = new Area(transformedShape);
        //the transformedArea is the transformedShape parameter but as an Area instead

        this.areaRectangle = transformedArea.getBounds();
        //a simple bounding rectangle for this area

        if (transformedShape.intersects(aboveScreen)){
            //moving stuff above the screen to the bottom of it
            intersectHandler(g,  aboveScreen, 0, FRAME_HEIGHT);
        } else if (transformedShape.intersects(underScreen)){
            //moving stuff under the screen to the top of it
            intersectHandler(g, underScreen, 0, -FRAME_HEIGHT);
        }
        if (transformedShape.intersects(leftScreen)){
            //moving stuff to the left of the screen to the right of it
            intersectHandler(g,  leftScreen, FRAME_WIDTH, 0);
        } else if (transformedShape.intersects(rightScreen)){
            //moving stuff on the right of the screen to the left of it
            intersectHandler(g, rightScreen, -FRAME_WIDTH, 0);
        }

    }

    private void intersectHandler(Graphics2D g, Rectangle intersectCheckRect, int xTranslate, int yTranslate) {
        AffineTransform backup = g.getTransform(); //gets copy of original affine transform
        Area tempArea = (Area)transformedArea.clone(); //copies the transformed area
        tempArea.intersect(new Area(intersectCheckRect)); //get the intersection of it with the intersection rectangle
        //transformedArea.subtract(tempArea);
        g.translate(xTranslate, yTranslate);
        transformedArea.add(tempArea);
        areaRectangle = transformedArea.getBounds();
        paintTheArea(g);
        g.setTransform(backup);
    }

    protected void paintTheArea(Graphics2D g){
        g.setPaint(new TexturePaint(texture,areaRectangle));
        g.fill(transformedArea); //filling the sprite with the texture
        g.setColor(objectColour);
        g.fill(transformedArea); //now filling it with the overlay
    }




    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }

    protected void notIntangible(){
        //basically supposed to make the thing not intangible,
        //however, also means that the thing won't die instantly due to losing intangibility whilst in contact with something
        if (intangible){
            this.intangible = false;
            this.finalIntangible = true;
        } else if (stillIntangible) {
            this.intangible = true;
            this.stillIntangible = false;
            this.finalIntangible = true;
        } else if (finalIntangible){
            finalIntangible = false;
        }
    }


    public void bounceOff(GameObject other){
        if (other instanceof Bullet){
            other.dead = true;
        } else if (!other.collided){
            collided = true;
            Vector2D coll = new Vector2D(other.position);
            coll.subtract(position).normalise();
            Vector2D tangent = new Vector2D(-coll.y, coll.x);
            tangent.mult(1.1);
            Vector2D thisV = new Vector2D(this.velocity);
            Vector2D otherV = new Vector2D(other.velocity);
            this.velocity = new Vector2D(thisV.proj(tangent)).add(otherV.proj(coll));
            position.addScaled(velocity, DT);

            other.velocity = new Vector2D(otherV.proj(tangent)).add(thisV.proj(coll));
            //other.position.addScaled(other.velocity, DT);

        }
    }




}
