package game;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;


public class PlayerBullet extends Bullet {

    //int timeToLive;

    //double distanceToGo;

    //double scaledDistance;

    //int frameCount;



    //private int[] xCorners, yCorners;

    public PlayerBullet(Vector2D position, Vector2D direction){
        super(position, Vector2D.polar(direction.angle(),300));
        super.update();
    }

    PlayerBullet(){
        super();
    }

    public void update(){
        super.update();
        objectColour = new Color(255 - (5*timeToLive),255 - (3*timeToLive),255 - (timeToLive/2),128);
    }

    /*
    public void hit(){
        dead = true;
    }*/

    /*
    public void hitLogic(){
        //just here to not break the abstract method basically
    }*/



}
