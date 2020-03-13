package MainPackage;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class GenericLargerAsteroid extends GenericAsteroid {

    //public static final double MAX_SPEED = 100;

    //how long it can persist for
    protected int timeToLive;
    //how many children it spawns if hit
    protected final int hitChildren = 2;
    //how many children it spawns if allowed to expire naturally
    protected final int decayChildren =  5;

    int childrenToSpawn;

    int redScale;


    public GenericLargerAsteroid(){ super();} //random asteroid at an unknown position

    public GenericLargerAsteroid(Vector2D p){ super(p); }//random asteroid at a known position

    @Override
    protected void setSpecifics(){ timeToLive = (int)(Math.random() * 512) + 512; }

    public void update() {
        super.update();
        timeToLive--;
        if (timeToLive > 0){
            redScale = timeToLive / 8;
            updateColour();
        } else {
            dead = true;
            wasHit = false;
            spawnChildren();
        }
    }

    //calls spawnChildren() when this is hit
    @Override
    //public void hit(boolean hitByPlayer){
    public void hit(boolean hitByPlayer){
        super.hit(hitByPlayer);
        spawnChildren();
    }


    private void spawnChildren() {
        if (wasHit) {
            SoundManager.playCrunch();
            childrenToSpawn = hitChildren;
        } else {
            SoundManager.playMedCrunch();
            childrenToSpawn = decayChildren;
        }
    }
    //will be used to generate the child asteroids

    protected abstract void updateColour();


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
