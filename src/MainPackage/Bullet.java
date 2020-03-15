package MainPackage;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Bullet extends GameObject {

    int timeToLive;

    public Bullet(){ this(new Vector2D(),new Vector2D(300,0)); }

    public Bullet(Vector2D p, Vector2D v){
        //this used to be the only constructor until I moved to using stacks and reviving
        super(p,v);
        timeToLive = 50;

        RADIUS = 5;

        objectPolygon = PolygonUtilities.scaledPolygonConstructor(new int[]{0,1,-1},new int[]{-1,1,1},0.5);

    }

    public Bullet revive(Vector2D p, Vector2D d){
        super.revive(p,d);
        timeToLive = 50;
        updateColour();
        rotationAngle = (velocity.angle() + Math.PI / 2);
        return this;
    }

    @Override
    public void update() {
        super.update();
        if (timeToLive > 0) {
            timeToLive--;
        } else{
            dead = true;
        }
        updateColour();
    }


    @Override
    protected void paintTheArea(Graphics2D g){
        paintColour(g);
    }

    @Override
    public void bounceOff(GameObject other){ this.dead = true; }
    //this dies instead of bouncing off another object.

    protected abstract void updateColour();

}
