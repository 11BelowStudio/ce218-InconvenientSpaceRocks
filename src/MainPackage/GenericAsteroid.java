package MainPackage;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import static MainPackage.Constants.*;
import static java.lang.Math.PI;

public abstract class GenericAsteroid extends GameObject {

    private static final double MAX_SPEED = 150;

    protected double asteroidScale;

    private boolean needToSlowDown;

    private double originSpeed;

    private double spinAngle;

    public GenericAsteroid() {
        super(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),
                Vector2D.polar(Math.random() * 2 * PI, Math.random() * MAX_SPEED)
        );
        setShared();
    }

    public GenericAsteroid(Vector2D startPosition){
        super(startPosition,
                Vector2D.polar(Math.random() * 2 * PI,Math.random() * MAX_SPEED)
        );
        setShared();
    }

    public GenericAsteroid(Vector2D p, Vector2D v) {
        super(p, v);
        setShared();
    }

    public GenericAsteroid revive(){ //reviving original asteroids (not from parent; no given position)
        super.revive(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),Vector2D.polar(Math.random() * 2 * PI, 1+ Math.random() * MAX_SPEED));
        setShared();
        return this;
    }

    public GenericAsteroid revive(Vector2D p){ //this is for reviving child asteroids (spawned from a parent; at a given position)
        super.revive(p,Vector2D.polar(Math.random() * 2 * PI, 1+ Math.random() * MAX_SPEED));
        setShared();
        velocity.mult(1 + (Math.random()/2));
            //child asteroids have a random initial speed boost between 0 and 0.5
                // (1-1.5x initial speed)
        needToSlowDown = true;
        return this;
    }

    @Override
    public void update(){
        super.update();
        if (needToSlowDown) {
            velocity.mult(0.99);
            needToSlowDown = (velocity.mag() > originSpeed);
            //slows down the asteroid slightly if is above the original speed for it
        }
        rotationAngle += spinAngle;
    }

    private void setShared(){
        //these bits of the constructor are shared between all asteroids, before the per-size specifics
        originSpeed = velocity.mag();
        needToSlowDown = false;
        texture = SPACE_ROCK;
        boolean definedAsteroid = true;
        int corners = (int)((Math.random() * 6) + (Math.random() * 6)) + 3;
        int[] hitboxX;
        int[] hitboxY;
        switch ((int)(Math.random() * 15)){
            case 0:
                hitboxX = new int[]{0,3,4,3,0,-3,-4,-3};
                hitboxY = new int[]{4,3,0,-3,-4,-3,0,3};
                break;
            case 1:
                hitboxX = new int[]{-4,-3,-4,0,4,3,4,0};
                hitboxY = new int[]{-4,0,4,3,4,0,-4,-3};
                break;
            case 2:
                hitboxX = new int[]{ 4, 4, 2, 2,-2,-2,-4,-4,-2,-2, 2, 2};
                hitboxY = new int[]{-2, 2, 2, 4, 4, 2, 2,-2,-2,-4,-4,-2};
                break;
            case 3:
                hitboxX = new int[]{0,4,3,-3,-4};
                hitboxY = new int[]{-4,-1,4,4,-1};
                break;
            case 4:
                hitboxX = new int[]{4,-0,-4};
                hitboxY = new int[]{-4,4,-4};
                break;
            case 5:
                hitboxX = new int[]{0,-3,-2,0,2,3};
                hitboxY = new int[]{-4,1,3,4,3,1};
                break;
            default: //random asteroid if not a predefined shape, empty hitbox
                hitboxX = new int[corners];
                hitboxY = new int[corners];
                definedAsteroid = false;
        }
        rotationAngle = Math.toRadians((Math.random() * 360)-180); //initial angle for the asteroid
        spinAngle = (Math.toRadians(((Math.random() * 2)-1)/32))/DT; //rate at which space rock go spinny
        setSpecifics();
        //and now setting up the shape of it
        if (definedAsteroid) {
            objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX, hitboxY, asteroidScale);
        } else{
            objectPolygon = PolygonUtilities.randomScaledPolygonConstructor(hitboxX,hitboxY,asteroidScale,corners);
        }
        objectType = ASTEROID;
    }

    protected abstract void setSpecifics();
    //overridden to get per-asteroid specifics (such as asteroidScale, timeToLive, etc)

    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}
