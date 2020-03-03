package game;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import utilities.Vector2D;

public class EnemyPlayer implements Controller{

    Game game;
    EnemyShip enemyShip;
    Boolean alive;
    int playerType;
    Action action;
    public EnemyPlayer(Game g){
        game = g;
        alive = false;
        enemyShip = null;
        action = new Action();
    }

    public void newEnemy(EnemyShip s){
        enemyShip = s;
        alive = true;
        playerType = (int)(Math.random() * 2);
        System.out.println("Enemy "+ playerType);
    }

    public void ded(){
        enemyShip = null;
        alive = false;
    }

    @Override
    public Action action() {
        if (alive) {
            switch (playerType) {
                case 0:
                    rotateShootAction();
                    break;
                case 1:
                    aimShootAction();
                    break;
            }
        } else{
            action.shoot = false;
            action.thrust = 0;
            action.turn = 0;
        }
        return action;
    }

    private Action rotateShootAction(){
        action.shoot = true;
        action.turn = 1;
        thrustIfSlowerThan(10);
        return action;
    }

    private Action aimShootAction () {
        Vector2D targetPosition = game.getShipPosition();
        if (targetPosition != null) {
            Vector2D vectorBetween = enemyShip.position.getVectorBetween(targetPosition);
            //vectorBetween.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
            Vector2D currentPosition = enemyShip.position;
            Vector2D currentDirection = enemyShip.direction;
            double distanceBetween = vectorBetween.mag();
            double targetAngle = vectorBetween.angle();
            double angleDifference = currentDirection.angle() - targetAngle;
            System.out.println("enemy ship info");
            System.out.println(distanceBetween);
            System.out.println(targetAngle);
            System.out.println(angleDifference);
            if (distanceBetween < 100) {
                if (angleDifference >= 0 - (Math.PI / 16) && angleDifference <= (Math.PI / 16)) {
                    action.shoot = true;
                } else {
                    action.shoot = false;
                }
                //if (distanceBetween < 100){
                action.thrust = 0;
                //} else{
                //action.thrust = 1;
                //}
            } else {
                action.thrust = 1;
                action.shoot = false;
            }
            if (angleDifference > 0) {
                action.turn = -1;
            } else if (angleDifference < 0) {
                action.turn = 1;
            } else {
                action.turn = 0;
            }
        } else {
            action.turn = (int) (Math.random() * 3) - 1;
            action.thrust = 1;
            action.shoot = false;
        }
        return action;
    }

    private void thrustIfSlowerThan(double minSpeed){
        if (enemyShip.velocity.mag() < minSpeed){
            action.thrust = 1;
        } else{
            action.thrust = 0;
        }
    }
}
