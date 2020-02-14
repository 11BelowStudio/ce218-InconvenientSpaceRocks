package basicGame;


import utilities.Vector2D;

import static basicGame.Constants.DT;
import static basicGame.Constants.FRAME_HEIGHT;
import static basicGame.Constants.FRAME_WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;

public class BasicAsteroid {

    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;

    private double x, y; //location in x and y co-ordinates
    private double vx, vy; //speed in x and y directions

    private Vector2D locationVector, velocityVector;

    public BasicAsteroid(double x, double y, double vx, double vy) {
        /*
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
         */

        this.locationVector = new Vector2D(x,y);
        this.velocityVector = new Vector2D(vx,vy);
    }

    public BasicAsteroid(){
        /*constructs an asteroid with randomly initialised variables
        number between -100 and 100, but random returns (0.0 to 1.0)
	        get random between 0 and 200, subtract 100
	            0 to 200: overall range
	            -100: moving the range to the appropriate area or something
        */
        this.locationVector = new Vector2D(Math.random() * FRAME_WIDTH,Math.random() * FRAME_HEIGHT);
        this.velocityVector = new Vector2D((Math.random() * MAX_SPEED * 2) - MAX_SPEED,(Math.random() * MAX_SPEED * 2) - MAX_SPEED);
    }


	public static BasicAsteroid makeRandomAsteroid() {
	    return new BasicAsteroid();
    }

    public void update() {
	    /*
        x += vx * DT;
        y += vy * DT;
        x = (x + FRAME_WIDTH) % FRAME_WIDTH;
        y = (y + FRAME_HEIGHT) % FRAME_HEIGHT;

	     */
        locationVector.addScaled(velocityVector,DT);
        locationVector.wrap(FRAME_WIDTH,FRAME_HEIGHT);

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.fillOval((int) locationVector.x - RADIUS, (int) locationVector.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }

    @Override
    public String toString(){
	    return ("x: " + String.format("%.2f",locationVector.x) + ", y: " + String.format("%.2f",locationVector.y) + ", vx: " + velocityVector.x + ", vy: " + velocityVector.y);
    }
}