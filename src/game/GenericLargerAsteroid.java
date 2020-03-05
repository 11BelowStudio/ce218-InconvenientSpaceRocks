package game;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public abstract class GenericLargerAsteroid extends GenericAsteroid {

    //public static final double MAX_SPEED = 100;

    //how long it can persist for
    protected int timeToLive;
    //how many children it spawns if hit
    protected final int hitChildren = 2;
    //how many children it spawns if allowed to expire naturally
    protected final int decayChildren =  5;

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
        //hitChildren = 2;
        //decayChildren = 5;
        childObjects = new ArrayList<>(decayChildren);
        timeToLive = (int)(Math.random() * 512) + 512;
    }

    public void update(){
        super.update();
        timeToLive--;
        redScale = timeToLive/8;
        updateColour();
        if (timeToLive == 0){
            dead = true;
            wasHit = false;
            spawnChildren();
        }
    }

    //calls spawnChildren() when this is hit
    @Override
    public void hit(boolean hitByPlayer){
        super.hit(hitByPlayer);
        spawnChildren();
    }

    protected int howManyChildren(){
        //will only attempt to spawn the children if this is dead
        if (wasHit) {
            SoundManager.play(SoundManager.crunch);
            return hitChildren;
        } else {
            SoundManager.play(SoundManager.mediumCrunch);
            return decayChildren;
        }
    }

    public abstract void spawnChildren();
    //will be used to generate the child asteroids

    protected void updateColour(){
        this.objectColour = new Color(128-redScale,128+redScale,0,128);
    }


    @Override
    public void draw(Graphics2D g) {
        this.notIntangible();
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        spaceRockGoSpinny(g);
        //g.setColor(Color.red);
        g.setColor(objectColour);
        //g.fillOval((int) (position.x - RADIUS), (int) (position.y - RADIUS), (int) (2 * RADIUS), (int)(2 * RADIUS));
        //g.fillPolygon(this.objectPolygon);
        Shape transformedShape = g.getTransform().createTransformedShape(objectPolygon);;
        g.setTransform(at); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        //g.fill(transformedArea);
        /*
        g.setPaint(new TexturePaint(texture,this.areaRectangle));
        g.fill(transformedArea);
        g.setColor(objectColour);
        g.fill(transformedArea);*/
        paintTheArea(g);
    }


}
