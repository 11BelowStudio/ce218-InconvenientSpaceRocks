package MainPackage;

import utilities.Vector2D;

import java.awt.*;
import static MainPackage.Constants.*;

public class EnemyShip extends Ship {


    public EnemyShip(Model m){
        super(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),Vector2D.polar((Math.random() * Math.PI * 2) - Math.PI, 0),Vector2D.polar((Math.random() * Math.PI * 2) - Math.PI, 1),new EnemyController(m));
        objectColour = new Color(255,128,0,96);
        pointValue = 20;
        direction = Vector2D.normalise(velocity);
        BULLET_DELAY = 500;
        STEER_RATE *= 1.5;
        texture = ENEMY_SHIP;
        ctrl.setEnemyShip(this);
        thrustColour = Color.CYAN;
        objectType = ENEMY_OBJECT;
    }

    public EnemyShip revive(){
        super.revive(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),
                Vector2D.polar((Math.random() * Math.PI * 2), Math.random() * MAX_SPEED),
                Vector2D.polar((Math.random() * Math.PI * 2),1));
        ctrl.revive();
        return this;
    }

    public void draw(Graphics2D g){
        super.draw(g);
        this.intangible = false;
    }

}
