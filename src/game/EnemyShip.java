package game;

import utilities.Vector2D;

import java.awt.*;

import static game.Constants.FRAME_HEIGHT;
import static game.Constants.FRAME_WIDTH;

public class EnemyShip extends Ship {

    //public static final Color SHIP_COLOUR =  new Color(255,64,128,32);

    public EnemyShip(EnemyPlayer ctrl, Game game){
        super(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),Vector2D.polar((Math.random() * Math.PI * 2) - Math.PI, 1),ctrl,game);
        objectColour = new Color(255,128,0,96);
        pointValue = 20;
        direction = new Vector2D(velocity).normalise();
        //direction.normalise();
        BULLET_DELAY = 500;
        STEER_RATE = 1.5*Math.PI;
    }

    public EnemyPlayer getPlayer(){
        return (EnemyPlayer) ctrl;
    }


    //@Override
    protected void addBulletToChildren() {
        childObjects.add(
                new EnemyBullet(
                        new Vector2D(
                                (position.x + ((2 * RADIUS) * (direction.x))),
                                (position.y + ((2 * RADIUS) * (direction.y)))
                        ),
                        direction
                )
        );
        //the new bullet is constructed in front of where the ship is, in the direction that it is pointing
        //and is put it in childObjects

        SoundManager.fire();
    }

    @Override
    public void draw(Graphics2D g){
        super.draw(g);
        intangible = false;
    }

    @Override
    protected void drawDetails(Graphics2D g) {
        if (thrusting) {
            g.setColor(Color.blue);
            g.fillPolygon(thrustPolygon);
        }
    }

    /*
    @Override
    protected void hitLogic(boolean hitByPlayer) {
        wasHit = hitByPlayer;
    }*/

    @Override
    protected void drawLineToPlayer(Graphics2D g){
        Vector2D playerPos = game.getShipPosition();
        if (playerPos != null) {
            g.setColor(Color.WHITE);
            /*
            double dist = position.dist(playerPos);
            double xAngle = playerPos.x - position.x;
            double yAngle = playerPos.y - position.y;
            Vector2D lineEnd = Vector2D.polar(Math.atan2(yAngle,xAngle),dist);
             */
            //Vector2D lineEnd = position.getVectorBetween(playerPos);

            /*
            if (lineEnd.x > FRAME_WIDTH/2){
                lineEnd.x =- FRAME_WIDTH/2;
            }
            if (lineEnd.y > FRAME_HEIGHT/2){
                lineEnd.y =- FRAME_HEIGHT/2;
            } */
            //Vector2D lineEnd = Vector2D.polar(position.getAngleBetween(playerPos,FRAME_WIDTH,FRAME_HEIGHT),300);
            Vector2D lineEnd = position.getVectorBetween(playerPos,FRAME_WIDTH,FRAME_HEIGHT);
            //lineEnd.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
            g.drawLine(0,0,(int)lineEnd.x,(int)lineEnd.y);
        }
    }
}
