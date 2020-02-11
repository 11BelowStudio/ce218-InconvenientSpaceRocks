package game2;


import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game1.Constants.*;

public class Asteroid extends GenericAsteroid {

    //public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;


    protected int[] xCorners, yCorners;


    public Asteroid(){

        super(new Vector2D(Math.random() * FRAME_WIDTH,Math.random() * FRAME_HEIGHT),
                new Vector2D((Math.random() * MAX_SPEED * 2) - MAX_SPEED,(Math.random() * MAX_SPEED * 2) - MAX_SPEED)
        );
        /*
        RADIUS = 10;
        pointValue = 1;
        objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,1);*/
        /*constructs an asteroid with randomly initialised variables
        number between -100 and 100, but random returns (0.0 to 1.0)
	        get random between 0 and 200, subtract 100
	            0 to 200: overall range
	            -100: moving the range to the appropriate area or something
        */
        /*
        this.position = new Vector2D(Math.random() * FRAME_WIDTH,Math.random() * FRAME_HEIGHT);
        this.velocity = new Vector2D((Math.random() * MAX_SPEED * 2) - MAX_SPEED,(Math.random() * MAX_SPEED * 2) - MAX_SPEED);

         */
    }

    public Asteroid(Vector2D startPosition){
        super(startPosition,
                new Vector2D((Math.random() * MAX_SPEED * 2) - MAX_SPEED,(Math.random() * MAX_SPEED * 2) - MAX_SPEED)
        );

    }

    public Asteroid(Vector2D p, Vector2D v) {
        super(p,v);
        //objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,1);
    }


    @Override
    protected void setSpecifics(){
        RADIUS = 15;
        pointValue = 1;
        asteroidScale = 0.5;
        //objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,1);
    }

    /*public static Asteroid makeRandomAsteroid() {
	    return new Asteroid();
    }*/

    /*public Asteroid spawnChild(){
        return new Asteroid(position);
    }*/


    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        spaceRockGoSpinny(g);
        g.setColor(Color.red);
        //g.fillPolygon(objectPolygon = new Polygon(xCorners,yCorners,xCorners.length));
        //g.fillOval((int) (position.x - RADIUS), (int) (position.y - RADIUS), (int) (2 * RADIUS), (int)(2 * RADIUS));
        //g.fillPolygon(objectPolygon);
        Shape transformedShape = g.getTransform().createTransformedShape(objectPolygon);
        g.setTransform(at); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        g.fill(transformedArea);
    }

    @Override
    public String toString(){
	    return ("x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}