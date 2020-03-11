package game;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Bullet extends GameObject {

    int timeToLive;

    public Bullet(){
        this(new Vector2D(1,1),new Vector2D(300,1));
    }

    public Bullet(Vector2D p, Vector2D v){
        super(p,v);
        timeToLive = 50;

        RADIUS = 5;
        hitboxX = new int[]{0,1,-1};
        hitboxY = new int[]{-1,1,1};

        objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,0.5);

    }

    public Bullet revive(Vector2D p, Vector2D d){
        super.revive(p,d);
        timeToLive = 50;
        updateColour();
        return this;
    }

    @Override
    public void update() {
        super.update();
        timeToLive--;
        if (timeToLive == 0){
            dead = true;
        }
        updateColour();
    }



    @Override
    public void draw(Graphics2D g) {
        this.notIntangible();
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = velocity.angle() + Math.PI / 2;
        g.rotate(rot);
        g.setColor(objectColour);
        Shape transformedShape = g.getTransform().createTransformedShape(objectPolygon);
        g.setTransform(at); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        paintTheArea(g);
    }

    protected void paintTheArea(Graphics2D g){
        //g.setPaint(new TexturePaint(texture, areaRectangle));
        //g.fill(transformedArea); //filling the sprite with the texture
        g.setColor(objectColour);
        g.fill(transformedArea); //now filling it with the overlay
    }

    @Override
    public void bounceOff(GameObject other){
        this.dead = true;
    }

    protected abstract void updateColour();

}
