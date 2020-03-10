package game;

import utilities.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

import static game.Constants.FRAME_HEIGHT;
import static game.Constants.FRAME_WIDTH;

public class EnemyShip extends Ship {

    //public static final Color SHIP_COLOUR =  new Color(255,64,128,32);

    public EnemyShip(Game game){
        super(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),Vector2D.polar((Math.random() * Math.PI * 2) - Math.PI, 0),Vector2D.polar((Math.random() * Math.PI * 2) - Math.PI, 1),new EnemyPlayer(game),game);
        objectColour = new Color(255,128,0,96);
        pointValue = 20;
        direction = new Vector2D(velocity).normalise();
        BULLET_DELAY = 500;
        STEER_RATE = 1.5*Math.PI;
        texture = (BufferedImage) Constants.ENEMY_SHIP;
        ((EnemyPlayer) ctrl).setEnemyShip(this);
        thrustColour = Color.CYAN;
    }

    public void revive(){
        super.revive();
        ((EnemyPlayer) ctrl).revive();
    }


    @Override
    public void draw(Graphics2D g){
        super.draw(g);
        intangible = false;
    }



    @Override
    protected void drawLineToPlayer(Graphics2D g){
        Vector2D playerPos = game.getShipPosition();
        if (playerPos != null) {
            g.setColor(Color.WHITE);
            Vector2D lineEnd = position.getVectorTo(playerPos,FRAME_WIDTH,FRAME_HEIGHT);
            g.drawLine(0,0,(int)lineEnd.x,(int)lineEnd.y);
        }
    }
}
