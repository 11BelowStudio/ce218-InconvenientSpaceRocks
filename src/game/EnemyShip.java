package game;

import utilities.Vector2D;

import java.awt.*;
import static game.Constants.*;

public class EnemyShip extends Ship {

    //public static final Color SHIP_COLOUR =  new Color(255,64,128,32);

    public EnemyShip(Model m){
        super(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),Vector2D.polar((Math.random() * Math.PI * 2) - Math.PI, 0),Vector2D.polar((Math.random() * Math.PI * 2) - Math.PI, 1),new EnemyController(m));
        objectColour = new Color(255,128,0,96);
        pointValue = 20;
        direction = new Vector2D(velocity).normalise();
        BULLET_DELAY = 500;
        STEER_RATE = 1.5*Math.PI;
        texture = ENEMY_SHIP;
        ctrl.setEnemyShip(this);
        thrustColour = Color.CYAN;
        objectType = ENEMY_OBJECT;
    }

    public EnemyShip revive(){
        super.revive();
        ctrl.revive();
        return this;
    }


    @Override
    public void draw(Graphics2D g){
        super.draw(g);
        intangible = false;
    }

}
