package game;

import utilities.Vector2D;

import static game.Constants.*;

public class EnemyPlayer implements Controller{

    private Game game;
    private EnemyShip enemyShip;
    private Boolean alive;
    private int playerType;
    private Action action;
    private final int positionUpdateRange = 15;
    private int framesUntilUpdatingEnemyPosition;
    private final int maxActionDelay = 10;
    private int actionDelay;
    private boolean canAct;
    private Vector2D targetPosition;

    public EnemyPlayer(Game g){
        game = g;
        alive = false;
        enemyShip = null;
        action = new Action();
        nextUpdateIn();
        nextActionIn();
    }


    public void newEnemy(EnemyShip s){
    //public void newEnemy(){
        enemyShip = s;
        alive = true;
        playerType = (int)(Math.random() * 5);
        System.out.println("Enemy "+ playerType);
        getTargetPosition();
        nextUpdateIn();
        nextActionIn();
    }

    public void ded(){
        //enemyShip = null;
        alive = false;
    }

    private void nextUpdateIn(){
        framesUntilUpdatingEnemyPosition = (int)(Math.random() * positionUpdateRange);
    }

    private void nextActionIn(){
        actionDelay = (int)(Math.random()* maxActionDelay);
    }

    private void canAct(){
        if (actionDelay != 0){
            actionDelay--;
            canAct = false;
        } else{
            canAct = true;
        }
    }

    private void getTargetPosition(){
        if (framesUntilUpdatingEnemyPosition != 0){
            framesUntilUpdatingEnemyPosition--;
        } else{
            targetPosition = game.getShipPosition();
        }
    }

    @Override
    public Action action() {
        getTargetPosition();
        canAct();
        if (alive) {
            switch (playerType) {
                case 0:
                    rotateShootAction();
                    break;
                case 1:
                    chaseShootAction();
                    break;
                case 2:
                    aimShootAction();
                    break;
                case 3:
                    aimWarpAction();
                    break;
                case 4:
                default:
                    randomAction();
                    break;
            }
        } else{
            action.shoot = true;
            action.thrust = 0;
            action.turn = 0;
        }
        return action;
    }

    private void rotateShootAction(){
        action.shoot = (Math.random() > 0.8);
        action.turn = 1;
        thrustIfSlowerThan(10);
        //return action;
    }

    private void chaseShootAction (){
        if (targetPosition != null) {
            Vector2D vectorBetween = enemyShip.position.getVectorBetween(targetPosition, FRAME_WIDTH,FRAME_HEIGHT);
            double distanceBetween = vectorBetween.mag();
            double angleDifference = vectorBetween.angle(enemyShip.direction);
            //double targetAngle = vectorBetween.angle();
            //System.out.println("enemy ship info");
            if (distanceBetween < 300) {
                if (distanceBetween < 150) {

                    if (angleDifference >= 0 - (Math.PI /16) && angleDifference <= (Math.PI / 16) && canAct) {
                        action.shoot = true;
                        nextActionIn();
                    } else {
                        action.shoot = false;
                    }
                    //if (distanceBetween < 100){
                    action.thrust = 0;
                } else{
                    action.thrust = 1;
                }
            } else {
                action.warp = true;
                action.thrust = 1;
                action.shoot = false;
            }
            turnToPlayer(angleDifference);
        } else {
            action.turn = (int) (Math.random() * 3) - 1;
            action.thrust = 1;
            action.shoot = false;
        }
        //return action;
    }

    private void thrustIfSlowerThan(double minSpeed){
        if (enemyShip.velocity.mag() < minSpeed){
            action.thrust = 1;
        } else{
            action.thrust = 0;
        }
    }

    private void randomAction(){
        action.turn = (int)(Math.random() * 3) - 1;
        action.thrust = (int) (Math.random() * 2);
        action.shoot = (Math.random() > 0.95);
        action.warp = (Math.abs(Math.random()) == 0);
    }

    private void aimShootAction(){
        getTargetPosition();
        if (targetPosition != null) {
            Vector2D vectorBetween = enemyShip.position.getVectorBetween(targetPosition, FRAME_WIDTH, FRAME_HEIGHT);
            double angleDifference = vectorBetween.angle(enemyShip.direction);
            if (angleDifference >= 0 - (Math.PI /4) && angleDifference <= (Math.PI / 4) && canAct) {
                action.shoot = true;
                nextActionIn();
            } else {
                action.shoot = false;
            }
            turnToPlayer(angleDifference);
        }
        thrustIfSlowerThan(20);
    }

    private void aimWarpAction(){
        getTargetPosition();
        if (targetPosition != null) {
            Vector2D vectorBetween = enemyShip.position.getVectorBetween(targetPosition, FRAME_WIDTH, FRAME_HEIGHT);
            double angleDifference = vectorBetween.angle(enemyShip.direction);
            if (angleDifference >= 0 - (Math.PI / 4) && angleDifference <= (Math.PI / 4) && canAct) {
                action.warp = true;
                nextActionIn();
            } else {
                action.warp = false;
                turnToPlayer(angleDifference);
            }
        }
    }

    private void turnToPlayer(double angleDifference){
        if (angleDifference > 0 ) {
            action.turn = -1;
        } else if (angleDifference < 0) {
            action.turn = 1;
        } else{
            action.turn = 0;
        }
    }

}
