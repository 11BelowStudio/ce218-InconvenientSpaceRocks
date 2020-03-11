package game;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import static game.Constants.*;

public class Bomb extends GameObject {


    boolean exploding;

    boolean defused; //whether or not the bomb died due to it being shot out or not

    private int timeToLive;

    private double scaling;

    public Bomb() {
        super(new Vector2D(), new Vector2D());
        //this(new Vector2D());
        objectColour = new Color(255,255,0,128);
        scaling = RADIUS;
        //this.objectPolygon = PolygonUtilities.prettyMuchACircle(hitboxX, hitboxY, scaling);
        exploding = false;
        timeToLive = 40;
        //objectType = PLAYER_OBJECT;
    }

    @Override
    public Bomb revive(Vector2D p, Vector2D v) {
        super.revive(p, v);
        //velocity.setMag(100);
        exploding = false;
        defused = true;
        scaling = RADIUS;
        //this.objectPolygon = PolygonUtilities.prettyMuchACircle(hitboxX, hitboxY, scaling);
        timeToLive = 40;
        objectColour = new Color(255,255,0,128);
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
                    this.exploding = true;
                    defused = false;
                    this.velocity = new Vector2D();
                    timeToLive = 40;
                }
            }
        }
    }

    public void hit(boolean hitByPlayer) {
        defused = true;
    }


    @Override
    public void draw(Graphics2D g) {
        intangible = false;
        AffineTransform backup = g.getTransform();
        g.translate(position.x,position.y);
        g.setColor(objectColour);
        Shape transformedShape = new Ellipse2D.Double(position.x-scaling,position.y-scaling,(scaling*2),(scaling*2));
        System.out.println(scaling);
        System.out.println(transformedShape.getBounds());
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
    boolean bombCollision(GameObject other) {
        if (exploding){
            other.hit(true);
            other.bombHit = true;
        } else {
            collided = true;
            dead = true;
            defused = true;
        }
        return true;
    }
}
