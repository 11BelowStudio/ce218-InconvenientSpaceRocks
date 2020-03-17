package GamePackage;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static GamePackage.Constants.*;
import static GamePackage.SoundManager.playExplosion;

public class Bomb extends GameObject {


    private boolean exploding;


    private int timeToLive;

    private double scaling;

    private Shape bombCircle;

    public Bomb() {
        super(new Vector2D(), new Vector2D());
        objectColour = new Color(255,255,0,128);
        scaling = RADIUS;
        exploding = false;
        timeToLive = 40;
        objectType = BOMB;
    }

    @Override
    public Bomb revive(Vector2D p, Vector2D v) {
        super.revive(p, v);
        exploding = false;
        scaling = RADIUS;
        timeToLive = 40;
        objectColour = new Color(255,255,0,128);
        double diameter = scaling*2;
        bombCircle = new Ellipse2D.Double(position.x-scaling,position.y-scaling,(diameter),(diameter));
        SoundManager.playBweb();
        return this;
    }

    @Override
    public void update() {
        if (!dead) {
            timeToLive--;
            if (exploding) {
                //collided = false;
                scaling += 5;
                if (timeToLive <= 0) {
                    this.dead = true;
                }
            } else {
                velocity.mult(0.9);
                scaling -= 0.1;
                position.addScaled(velocity, DT);
                position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
                if (timeToLive <= 0) {
                    playExplosion();
                    objectColour = new Color(255,128,0,192);
                    this.exploding = true;
                    this.velocity.set(0,0);
                    timeToLive = 40;
                } else{
                    objectColour = new Color(255,128+(3*timeToLive),0);
                }
            }
        }
        double diameter = scaling*2;
        bombCircle = new Ellipse2D.Double(position.x-scaling,position.y-scaling,(diameter),(diameter));
        //updates the bombCircle so it's in the correct position/correct size
    }


    @Override
    public void draw(Graphics2D g) {
        wrapAround(g,bombCircle); //attempts dealing with making the bombCircle wrap around  (and sets up hitboxes)
        paintTheArea(g); //renders the bombCircle
        notIntangible(); //this will no longer be intangible
    }


    protected void paintTheArea(Graphics2D g){
        g.setColor(objectColour);
        g.fill(transformedArea); //no texture, just colour
    }

    @Override
    boolean bombCollision(GameObject other){
        if (exploding){
            //other object is destroyed if this is exploding, this is unaffected
            other.hitByBomb();
        } else {
            //if this had not yet exploded, this is destroyed, other object isn't
            dead = true;
            if (other instanceof Bullet){
                other.hit(true); //but if it was a bullet, it gets destroyed as usual
            }
        }
        return true;
    }
}
