package game2;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;

public abstract class GenericLargerAsteroid extends GenericAsteroid {

    //public static final double MAX_SPEED = 100;

    //how long it can persist for
    protected int timeToLive;
    //how many children it spawns if hit
    protected int hitChildren;
    //how many children it spawns if allowed to expire naturally
    protected int decayChildren;

    int redScale;


    public GenericLargerAsteroid(){
        super();
        /*constructs an asteroid with randomly initialised variables
        number between -100 and 100, but random returns (0.0 to 1.0)
	        get random between 0 and 200, subtract 100
	            0 to 200: overall range
	            -100: moving the range to the appropriate area or something
        */
    }

    public GenericLargerAsteroid(Vector2D p){
        super(p);
        //random asteroid at a known position
    }

    @Override
    protected void setSpecifics(){
        hitChildren = 2;
        decayChildren = 5;
        timeToLive = (int)(Math.random() * 512) + 512;
    }

    public void update(){
        super.update();
        timeToLive--;
        redScale = timeToLive/8;
        if (timeToLive == 0){
            dead = true;
            wasHit = false;
            spawnChildren();
        }
    }

    //calls spawnChildren() when this is hit
    @Override
    protected void hitLogic(){
        super.hitLogic();
        spawnChildren();
    }

    protected int howManyChildren(){
        //will only attempt to spawn the children if this is dead
        if (wasHit) {
            return hitChildren;
        } else {
            return decayChildren;
        }
    }

    public abstract void spawnChildren();
    //will be used to generate the child asteroids

    public Color getColour(){
        return new Color(128-redScale,128+redScale,0);
    }


    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        spaceRockGoSpinny(g);
        //g.setColor(Color.red);
        g.setColor(getColour());
        //g.fillOval((int) (position.x - RADIUS), (int) (position.y - RADIUS), (int) (2 * RADIUS), (int)(2 * RADIUS));
        //g.fillPolygon(this.objectPolygon);
        Shape transformedShape = g.getTransform().createTransformedShape(objectPolygon);;
        g.setTransform(at); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        g.fill(transformedArea);
    }

    @Override
    public String toString(){
        return ("x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}
