package game;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

import static game.Constants.*;
import static java.lang.Math.PI;

public abstract class GenericAsteroid extends GameObject {

    public static final double MAX_SPEED = 150;

    //how long it can persist for
    //protected int timeToLive;

    protected double asteroidScale;

    private double originSpeed;

    double rotationAngle, spinSpeed;

    public GenericAsteroid() {

        /* */
        super(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),
                Vector2D.polar(Math.random() * 2 * PI, Math.random() * MAX_SPEED)
        );
        /*
        super(new Vector2D(Math.random() * HALF_WIDTH, Math.random() * HALF_HEIGHT),
                Vector2D.polar(Math.random() * 2 * PI, Math.random() * MAX_SPEED)
        );
        */
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

    public GenericAsteroid revive(){
        revive(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT));
        return this;
    }

    public GenericAsteroid revive(Vector2D p){
        super.revive(p,Vector2D.polar(Math.random() * 2 * PI, Math.random() * MAX_SPEED));
        setShared();
        return this;
    }

    @Override
    public void update(){
        super.update();
        if(velocity.mag() >= originSpeed){
            velocity.mult(1-DRAG);
        }
    }

    private void setShared(){
        originSpeed = velocity.mag();
        texture = (BufferedImage)SPACE_ROCK;
        boolean definedAsteroid = true;
        int corners = (int)((Math.random() * 6) + (Math.random() * 6)) + 3;
        switch ((int)(Math.random() * 15)){
            case 0:
                this.hitboxX = new int[]{0,3,4,3,0,-3,-4,-3}; //{0,3,4,3,0,-3,-4,-3}
                this.hitboxY = new int[]{4,3,0,-3,-4,-3,0,3}; //{4,3,0,-3,-4,-3,0,3}
                break;
            case 1:
                this.hitboxX = new int[]{-4,-3,-4,0,4,3,4,0};
                this.hitboxY = new int[]{-4,0,4,3,4,0,-4,-3};
                break;
            case 2:
                this.hitboxX = new int[]{ 4, 4, 2, 2,-2,-2,-4,-4,-2,-2, 2, 2};
                this.hitboxY = new int[]{-2, 2, 2, 4, 4, 2, 2,-2,-2,-4,-4,-2};
                break;
            case 3:
                this.hitboxX = new int[]{0,4,3,-3,-4};
                this.hitboxY = new int[]{-4,-1,4,4,-1};
                break;
            case 4:
                this.hitboxX = new int[]{4,-0,-4};
                this.hitboxY = new int[]{-4,4,-4};
                break;
            case 5:
                this.hitboxX = new int[]{0,-3,-2,0,2,3};
                this.hitboxY = new int[]{-4,1,3,4,3,1};
                break;
            default:
                this.hitboxX = new int[corners];
                this.hitboxY = new int[corners];
                definedAsteroid = false;
        }
        rotationAngle = Math.toRadians((Math.random() * 360)-180); //initial angle for the asteroid
        spinSpeed = Math.toRadians(((Math.random() * 2)-1)/32); //rate at which space rock go spinny
        setSpecifics();
        if (definedAsteroid) {
            objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX, hitboxY, asteroidScale);
        } else{
            objectPolygon = PolygonUtilities.randomScaledPolygonConstructor(hitboxX,hitboxY,asteroidScale,corners);
        }
        objectType = ASTEROID;
        //texture = (BufferedImage)AN_TEXTURE;
    }

    protected abstract void setSpecifics();

    void spaceRockGoSpinny(Graphics2D g){
        g.rotate(rotationAngle);
        rotationAngle+=(spinSpeed/DT);
        //space rock go spinny at speed of nyoom (or at least spinSpeed/DT so it's not eye-hurting)
    }

    /*
    @Override
    protected void hitLogic(boolean hitByPlayer) {
        wasHit = true;
        playerHit = hitByPlayer;
    }*/

    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}
