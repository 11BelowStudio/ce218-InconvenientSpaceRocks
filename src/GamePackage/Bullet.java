package GamePackage;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;

public abstract class Bullet extends GameObject {

    //protected int timeToLive;

    public Bullet(){
        super(new Vector2D(),new Vector2D(300,0));
        timeToLive = 50;
        RADIUS = 5;
        objectPolygon = PolygonUtilities.scaledPolygonConstructor(new int[]{0,1,-1},new int[]{-1,1,1},0.5);
    }

    //this used to be the only constructor until I moved to using stacks and reviving
    public Bullet(Vector2D p, Vector2D v){
        super(p,v);
        timeToLive = 50;
        RADIUS = 5;
        objectPolygon = PolygonUtilities.scaledPolygonConstructor(new int[]{0,1,-1},new int[]{-1,1,1},0.5);
    }

    public Bullet revive(Vector2D p, Vector2D v){
        super.revive(p,v);
        timeToLive = 50;
        updateColour();
        rotationAngle = (velocity.angle() + Math.PI / 2);
        SoundManager.fire();
        return this;
    }

    @Override
    public void update() {
        super.update(); //updating position and such

        //updating timeToLive
        if (timeToLive > 0) {
            timeToLive--;
        } else{
            dead = true;
        }
        //updating the colour
        updateColour();
    }


    @Override
    protected void paintTheArea(Graphics2D g){ paintColour(g); }
    //no textures here, so only the colour is painted

    @Override
    public void bounceOff(GameObject other){ dead = true; }
    //this dies instead of bouncing off another object.

    protected abstract void updateColour();
    //PlayerBullet and EnemyBullet have their colours updated differently

    @Override
    public void hit(boolean hitByPlayer) { dead = true; }
    //dead if hit
}
