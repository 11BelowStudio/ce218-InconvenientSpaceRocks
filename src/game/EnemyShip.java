package game;

import utilities.Vector2D;

import java.awt.*;
import java.util.Random;

import static game.Constants.FRAME_HEIGHT;
import static game.Constants.FRAME_WIDTH;

public class EnemyShip extends Ship {

    public static final Color SHIP_COLOUR =  new Color(255,64,128,32);

    public EnemyShip(Controller ctrl, Game game){
        super(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),Vector2D.polar((Math.random() * Math.PI * 2) - Math.PI, (Math.random() * MAX_SPEED)),ctrl,game);
        objectColour = SHIP_COLOUR;
        pointValue = 20;
        direction = new Vector2D(velocity);
        direction.normalise();
    }

    public EnemyShip(Game game){
        super(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),Vector2D.polar((Math.random() * Math.PI * 2) - Math.PI, (Math.random() * MAX_SPEED)),game);
        objectColour = SHIP_COLOUR;
        pointValue = 20;
        direction = new Vector2D(velocity);
        direction.normalise();
    }

    public void giveController(Controller ctrl){
        this.ctrl = ctrl;
    }

    @Override
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
    public void update() {
        super.update();
        //if (velocity.mag() < 10){
        //   thrusting = true;
        //    velocity.addScaled(direction,Math.random()*MAX_SPEED);
        //}
    }

    @Override
    public void draw(Graphics2D g){
        super.draw(g);
        intangible = false;
    }

    @Override
    protected void drawDetails(Graphics g) {
        if (thrusting) {
            g.setColor(Color.blue);
            g.fillPolygon(thrustPolygon);
        }

    }

    @Override
    protected void hitLogic() {

    }
}
