package game;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Bomb extends GameObject {

    private int timeToLive;

    private double scaling;

    public Bomb() {
        this(new Vector2D(0, 0));

    }

    public Bomb(Vector2D p){
        super(p, new Vector2D(0, 0));
        scaling = RADIUS*2;
        //this.objectPolygon = PolygonUtilities.prettyMuchACircle(hitboxX, hitboxY, scaling);
        timeToLive = 40;
        objectColour = new Color(255,255,0,128);
        //scaling = RADIUS;
    }

    @Override
    public void revive(Vector2D p, Vector2D v) {
        super.revive(p, v);
        scaling = RADIUS*2;
        //this.objectPolygon = PolygonUtilities.prettyMuchACircle(hitboxX, hitboxY, scaling);
        timeToLive = 40;
        objectColour = new Color(255,255,0,128);
    }

    @Override
    public void update() {
        //super.update();
        collided = false;
        timeToLive--;
        scaling += 5;
        if (timeToLive < 0) {
            this.dead = true;
        }
    }

    public boolean hit(boolean hitByPlayer, boolean hitByBomb) {
        collided = false;
        return false;
    }


    @Override
    public void draw(Graphics2D g) {
        intangible = false;
        AffineTransform backup = g.getTransform();
        g.translate(position.x,position.y);
        g.setColor(objectColour);
        Shape transformedShape = new Ellipse2D.Double(position.x-scaling,position.y-scaling,(scaling*2),(scaling*2));
        System.out.println(transformedShape.getBounds());
        g.setTransform(backup); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        //g.setPaint(new TexturePaint(texture,this.areaRectangle));
        //g.fill(transformedArea);
        g.setColor(objectColour);
        g.fill(transformedArea);
        //areaRectangle = transformedArea.getBounds();
        //g.setColor(new Color(255,128,0,128));
        //g.fill(areaRectangle);
    }

    protected void paintTheArea(Graphics2D g){
        //g.setPaint(new TexturePaint(texture,areaRectangle));
        //g.fill(transformedArea); //filling the sprite with the texture
        g.setColor(objectColour);
        g.fill(transformedArea); //now filling it with the overlay
    }
}
