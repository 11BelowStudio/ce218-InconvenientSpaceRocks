package game;

import utilities.Vector2D;

public class AimController implements Controller {

    Action action;

    Game game;
    AimController(Game game){
        this.game = game;
        action = new Action();
    }

    public Action action() {
        EnemyShip controlled = game.enemy;
        if (controlled != null){
            Vector2D targetPosition = game.getShipPosition();
            if (targetPosition != null) {
                Vector2D currentPosition = controlled.position;
                Vector2D currentDirection = controlled.direction;
                double distanceBetween = currentPosition.dist(targetPosition);
                double targetAngle = Math.acos((targetPosition.y - currentPosition.y) / distanceBetween);
                double angleDifference = targetAngle - Math.toDegrees(currentDirection.angle());
                System.out.println("enemy ship info");
                System.out.println(distanceBetween);
                System.out.println(targetAngle);
                System.out.println(angleDifference);
                if (distanceBetween < 200) {
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
                    action.turn = 1;
                } else if (angleDifference < 0) {
                    action.turn = -1;
                } else {
                    action.turn = 0;
                }
            } else {
                action.turn = (int) (Math.random() * 3) - 1;
                action.thrust = 1;
                action.shoot = false;
            }
        }
        return action;
    }
}
