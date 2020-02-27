package game;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;

import static game.Constants.FRAME_HEIGHT;
import static game.Constants.FRAME_WIDTH;
import static game.Constants.DT;

public abstract class GenericAsteroid extends GameObject {

    public static final double MAX_SPEED = 100;

    protected double asteroidScale;

    double rotationAngle, spinSpeed;

    public GenericAsteroid() {

        super(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),
                new Vector2D((Math.random() * MAX_SPEED * 2) - MAX_SPEED, (Math.random() * MAX_SPEED * 2) - MAX_SPEED)
        );
        setShared();
    }

    public GenericAsteroid(Vector2D startPosition){
        super(startPosition,
                new Vector2D((Math.random() * MAX_SPEED * 2) - MAX_SPEED,(Math.random() * MAX_SPEED * 2) - MAX_SPEED)
        );
        setShared();
    }

    public GenericAsteroid(Vector2D p, Vector2D v) {
        super(p, v);
        setShared();
    }

    private void setShared(){
        switch ((int)(Math.random() * 6)){
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
        }
        rotationAngle = Math.toRadians((Math.random() * 360)-180); //initial angle for the asteroid
        spinSpeed = Math.toRadians(((Math.random() * 2)-1)/32); //rate at which space rock go spinny
        setSpecifics();
        objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,asteroidScale);
        //texture = (BufferedImage)AN_TEXTURE;
    }

    protected abstract void setSpecifics();

    protected void spaceRockGoSpinny(Graphics2D g){
        g.rotate(rotationAngle);
        rotationAngle+=(spinSpeed/DT);
        //space rock go spinny at speed of nyoom (or at least spinSpeed/DT so it's not eye-hurting
    }

    @Override
    protected void hitLogic() {
        wasHit = true;
    }

    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }
}
