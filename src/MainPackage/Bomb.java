package MainPackage;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static MainPackage.Constants.*;
import static MainPackage.SoundManager.playExplosion;

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
        bombCircle = new Ellipse2D.Double(position.x-scaling,position.y-scaling,(scaling*2),(scaling*2));
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
                    this.velocity = new Vector2D();
                    timeToLive = 40;
                } else{
                    objectColour = new Color(255,128+(3*timeToLive),0);
                }
            }
        }
        bombCircle = new Ellipse2D.Double(position.x-scaling,position.y-scaling,(scaling*2),(scaling*2));
        //updates the bombCircle so it's in the correct position/correct size
    }

    public void hit(boolean hitByPlayer) { this.kill(); }


    @Override
    public void draw(Graphics2D g) {
        notIntangible();
        wrapAround(g,bombCircle);
        paintTheArea(g);
    }


    protected void paintTheArea(Graphics2D g){
        g.setColor(objectColour);
        g.fill(transformedArea); //no texture, just colour
    }

    @Override
    boolean bombCollision(GameObject other){
        if (exploding){
            //other object is destroyed if this is exploding, this is unaffected
            other.hit(true);
            other.bombHit = true;
        } else {
            //if this had not yet exploded, this is destroyed, other object isn't
            //collided = true;
            dead = true;
            if (other instanceof Bullet){
                other.hit(true); //but if it was a bullet, it gets destroyed as usual
            }
        }
        return true;
    }
}
