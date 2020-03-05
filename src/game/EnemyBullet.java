package game;

import utilities.Vector2D;

import java.awt.*;

public class EnemyBullet extends Bullet {


    public EnemyBullet(Vector2D position, Vector2D direction) {
        super(position,  Vector2D.polar(direction.angle(),300));
        super.update();
    }

    EnemyBullet(){
        super();
    }

    public void update(){
        super.update();
        objectColour = new Color(255 - (timeToLive/2),255 - (3*timeToLive),255 - (5*timeToLive),128);
    }
}
