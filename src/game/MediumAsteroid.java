package game;

import utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class MediumAsteroid extends GenericLargerAsteroid {



    public MediumAsteroid(){
        super();
    }

    public MediumAsteroid(Vector2D p){
        super(p);
        //random asteroid at a known position
    }

    @Override
    protected void setSpecifics(){
        super.setSpecifics();
        RADIUS = 25;
        //timeToLive = (int)(Math.random() * 512) + 512;
        pointValue = 3;
        asteroidScale = 0.75;
        //objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,1.5);
    }

    @Override
    public void spawnChildren() {
        this.childObjects = new ArrayList<>();
        int count = howManyChildren();
        double x = this.position.x;
        double y = this.position.y;
        for (int i = 0; i < count ; i++) {
            childObjects.add(new Asteroid(new Vector2D(x,y)));
        }
        //return children;
    }

    @Override
    public void updateColour(){
        this.objectColour = new Color(255-redScale,redScale,0,128);
    }

    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}
