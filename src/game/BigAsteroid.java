package game;

import utilities.Vector2D;

import java.util.ArrayList;

public class BigAsteroid extends GenericLargerAsteroid {


    public BigAsteroid(){
        super();
    }

    @Override
    protected void setSpecifics(){
        super.setSpecifics();
        RADIUS = 50;
        pointValue = 15;
        asteroidScale = 1.25;

    }


    @Override
    public void spawnChildren() {
        this.childObjects = new ArrayList<>();
        int count = howManyChildren();
        double x = this.position.x;
        double y = this.position.y;
        for (int i = 0; i < count ; i++) {
            childObjects.add(new MediumAsteroid(new Vector2D(x,y)));
        }
        //return children;
    }

    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}