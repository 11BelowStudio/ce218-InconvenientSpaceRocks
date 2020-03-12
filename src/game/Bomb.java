package game;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import static game.Constants.*;

public class Bomb extends GameObject {


    boolean exploding;


    private int timeToLive;

    private double scaling;

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
        SoundManager.play(SoundManager.bweb);
        //objectType = BOMB;
        return this;
    }

    @Override
    public void update() {
        if (!dead) {
            timeToLive--;
            if (exploding) {
                //super.update();
                collided = false;
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
                    SoundManager.play(SoundManager.explosion);
                    objectColour = new Color(255,128,0,192);
                    this.exploding = true;
                    this.velocity = new Vector2D();
                    timeToLive = 40;
                } else{
                    objectColour = new Color(255,128+(3*timeToLive),0);
                }
            }
        }
    }

    public void hit(boolean hitByPlayer) { this.kill(); }


    @Override
    public void draw(Graphics2D g) {
        intangible = false;
        AffineTransform backup = g.getTransform();
        g.translate(position.x,position.y);
        g.setColor(objectColour);
        Shape transformedShape = new Ellipse2D.Double(position.x-scaling,position.y-scaling,(scaling*2),(scaling*2));
        g.setTransform(backup); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        g.setColor(objectColour);
        g.fill(transformedArea);
    }

    protected void paintTheArea(Graphics2D g){
        g.setColor(objectColour);
        g.fill(transformedArea); //now filling it with the overlay
    }

    @Override
    boolean bombCollision(GameObject other){
        if (exploding){
            //other object is destroyed if this is exploding, this is unaffected
            other.hit(true);
            other.bombHit = true;
        } else {
            //if this had not yet exploded, this is destroyed, other object isn't
            collided = true;
            dead = true;
            if (other instanceof Bullet){
                other.hit(true); //but if it was a bullet, it gets destroyed as usual
            }
        }
        return true;
    }
}
