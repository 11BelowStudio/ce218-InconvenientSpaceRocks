package game;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Bullet extends GameObject {

    int timeToLive;

    double distanceToGo;

    double scaledDistance;

    int frameCount;

    public Bullet(Vector2D position, Vector2D direction){
        super(position,direction);
        timeToLive = 50;
        distanceToGo = 30;
        RADIUS = 5;
        hitboxX = new int[]{0,1,-1};
        hitboxY = new int[]{-1,1,1};

        frameCount = 0;

        objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,0.5);

    }

    @Override
    public void update() {
        super.update();
        timeToLive--;
        if (timeToLive == 0){
            dead = true;
        }
    }

    public void hitLogic(){
        //just here to not break the abstract method basically
    }

    @Override
    public void draw(Graphics2D g) {
        this.notIntangible();
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = velocity.angle() + Math.PI / 2;
        g.rotate(rot);
        g.setColor(objectColour);
        Shape transformedShape = g.getTransform().createTransformedShape(objectPolygon);;
        g.setTransform(at); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        paintTheArea(g);
    }
}
