package basicGame;

import utilities.Vector2D;

import java.awt.*;

import static basicGame.Constants.*;

public class BasicShip {
    public static final int RADIUS = 8;

    // rotation velocity in radians per second
    public static final double STEER_RATE = 2*Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 5;

    //maximum speed
    public static final double MAX_SPEED = 250;

    // constant speed loss factor
    public static final double DRAG = 0.01;

    public static final Color COLOUR = Color.cyan;

    public Vector2D position; // on frame
    public Vector2D velocity; // per second
    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    public Vector2D direction;

    // controller which provides an Action object in each frame
    private BasicController ctrl;

    public BasicShip(BasicController ctrl) {
        this.ctrl = ctrl;

        position = new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2);
        direction = Vector2D.polar(Math.toRadians(270),1);
        velocity = Vector2D.polar(direction.angle(),0);
    }

    public void update(){
        Action currentAction = ctrl.action();


        direction.rotate(Math.toRadians(currentAction.turn * STEER_RATE));
        //if the ship has a 1 or -1 for turn, it will turn in the appropriate direction

        //velocity.rotate(direction.angle(velocity));

        direction.normalise();


        //velocity.addScaled(direction,currentAction.thrust);
        //adds the new direction to velocity, scaled by whether or not thrust is being applied, over the frame time

        velocity.addScaled(direction,(MAG_ACC/DT) * currentAction.thrust);

        //if (currentAction.thrust != 0){
            //velocity.rotate(direction.angle()/DT);
            //velocity.add(direction);
            //velocity.addScaled(direction,(MAG_ACC/DT) * currentAction.thrust);
            //velocity.addScaled(direction,((1 - DRAG) + MAG_ACC)/DT);
            //velocity.setMag(200);
        //}


        if (velocity.mag() > MAX_SPEED){
            //velocity.setMag(MAX_SPEED);
            velocity.set(Vector2D.polar(velocity.angle(),MAX_SPEED));
        }

        velocity.mult(1-DRAG);
        //reduces velocity by DRAG

        position.addScaled(velocity,DT);
        //updates the position by the velocity (weighted by the frame time)

        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
        //wraps the position around if appropriate

        System.out.println("\n");
        //System.out.println(direction);
        //System.out.println(velocity);
        //System.out.println(position);

        /*
        double angleChange = velocity.angle(direction);
        //obtains the angle between the current velocity angle and the direction angle
        velocity.rotate(angleChange);
        //rotates it by that amount

        //double newVelocity = velocity.mag() * (1 - (DRAG/DT)) + (currentAction.thrust * MAG_ACC/DT);

        velocity.mult((1 - (DRAG/DT)) + (currentAction.thrust * MAG_ACC/DT));
        //decreases the velocity by 'DRAG', but adds the acceleration ('MAG_ACC') to it if applicable for this frame (/DT)
         */
        /*
        if (currentAction.thrust == 1){
            newVelocity = MAG_ACC;
            //acceleration by 200
        } else {
            newVelocity = velocity.mag() * (1 - DRAG) + (currentAction.thrust * MAG_ACC);
            //decreasing the magnitude of velocity by the constant speed loss factor
        }*/

    }

    public void draw(Graphics2D g){
        g.setColor(COLOUR);
        g.fillOval((int) position.x - RADIUS,
                (int) position.y - RADIUS,
                2 * RADIUS,
                2 * RADIUS
        );
        g.setColor(Color.GREEN);
        g.drawLine(
                (int) position.x,
                (int) position.y,
                (int) (position.x + 3*RADIUS*(direction.x)),
                (int) (position.y + 3*RADIUS*(direction.y))
        );
    }


}