package MainPackage;

import utilities.Vector2D;

import static MainPackage.Constants.*;

public class EnemyController extends ControllerAdapter{

    private static final int MAX_POS_UPDATE_DELAY = 20;

    private static final int MAX_ACTION_DELAY = 10;

    private Model model;
    private EnemyShip enemyShip;
    private int playerType;
    //private Action action;
    private int framesUntilUpdatingEnemyPosition;
    private int actionDelay;
    private boolean canAct;
    private boolean acted;
    private Vector2D targetPosition;

    private int anCountdown;

    private boolean itIsAMystery;

    private double distanceBetween;
    private double angleDifference;


    public EnemyController(Model m){
        model = m;
        action = new Action();
        acted = true;
        canAct();
        nextUpdateIn();
    }

    public void setEnemyShip(EnemyShip e){
        enemyShip = e;
    }


    public void revive(){
        playerType = (int)(Math.random() * 7);
        getTargetPosition();
        nextUpdateIn();
        acted = true;
        anCountdown = 0;
        itIsAMystery = (Math.random() < 0.5); //50% chance of true or false
        canAct();
    }

    private void nextUpdateIn(){
        framesUntilUpdatingEnemyPosition = (int)(Math.random() * MAX_POS_UPDATE_DELAY);
    }

    private void nextActionIn(){ actionDelay = (int)(Math.random()* MAX_ACTION_DELAY); }

    private void canAct(){
        if (acted){
            acted = false;
            nextActionIn(); //how long it will be until it can next change what it's doing
        } else if (canAct || actionDelay > 0){
            canAct = false;
            actionDelay--;
        } else{
            canAct = true;
        }
    }

    private void getTargetPosition(){
        if (framesUntilUpdatingEnemyPosition > 0){
            framesUntilUpdatingEnemyPosition--;
        } else{
            targetPosition = model.getShipPosition();
            nextUpdateIn();
        }
    }

    @Override
    public Action action() {
        canAct();
        switch (playerType) {
            case 0:
                rotateShootAction();
                //enemy go spinny
                break;
            case 1:
                chaseShootAction();
                //enemy goes aggro, chasing after player and shooting them
                break;
            case 2:
                turretWithMovement();
                //enemy just aims and shoots at the player, not much movement
                break;
            case 3:
                aimWarpAction();
                //*teleports inside you* nothing personell kid
                break;
            case 4:
                moveAndSpinAndAlsoShoot();
                //moves and spins and also shoots!
                    //stays still, moves spinning in one direction, stops spinning after a moment.
                    //moves again after it stops moving, and will proceed to spin in the other direction
                break;
            case 5:
                turretTime();
                //look at me look at me im a turret (essentially 'turret with movement' without the movement)
                break;
            case 6:
            default:
                randomAction();
                //the 'infinite monkeys' approach
                break;
        }
        return action;
    }


    private void rotateShootAction(){
        randomShoot(0.2);
        magicalMysteryTurn();
        thrustIfSlowerThan(10);
    }

    private void chaseShootAction (){
        if (analysePosition()) {
            if (distanceBetween < 300) {
                if (distanceBetween < 150) {
                    if (angleDifference >= 0 - (Math.PI /16) && angleDifference <= (Math.PI / 16) && canAct) {
                        action.shoot = true;
                        acted = true;
                    } else {
                        action.shoot = false;
                    }

                    action.thrust = 0;
                } else{
                    action.thrust = 1;
                }
            } else {
                randomAction();
            }
            turnToPlayer();
        } else {
            randomAction();
        }
        //return action;
    }

    private boolean thrustIfSlowerThan(double minSpeed){
        if (enemyShip.velocity.mag() < minSpeed){
            action.thrust = 1;
            return true;
        }
        action.thrust = 0;
        return false;

    }

    private void randomAction(){
        action.turn = (int)(Math.random() * 3) - 1;
        action.thrust = (int) (Math.random() * 2);
        randomShoot(0.5);
        action.warp = (Math.abs(Math.random()) == 0);
    }

    private void turretTime(){
        if (analysePosition()) {
            if (angleDifference >= 0 - (Math.PI /4) && angleDifference <= (Math.PI / 4) && canAct) {
                action.shoot = true;
                acted = true;
            } else {
                action.shoot = false;
            }
            turnToPlayer();
        } else{
            randomAction();
        }
    }

    private void turretWithMovement(){
        turretTime();
        thrustIfSlowerThan(20);
    }

    private void aimWarpAction(){
        if (analysePosition()) {
            if (angleDifference >= 0 - (Math.PI / 4) && angleDifference <= (Math.PI / 4) && canAct) {
                action.warp = true;
                acted = true;
            } else {
                action.warp = false;
                turnToPlayer();
            }
        } else{
            randomAction();
        }
    }

    private void turnToPlayer(){
        if (angleDifference > 0 ) {
            action.turn = -1;
        } else if (angleDifference < 0) {
            action.turn = 1;
        } else{
            action.turn = 0;
        }
    }

    private boolean analysePosition(){
        getTargetPosition();
        if (targetPosition == null){
            return false;
        }
        Vector2D vectorBetween = enemyShip.position.getVectorTo(targetPosition, FRAME_WIDTH, FRAME_HEIGHT);
        distanceBetween = vectorBetween.mag();
        angleDifference = vectorBetween.angle(enemyShip.direction);
        return true;
    }

    private void moveAndSpinAndAlsoShoot(){
        randomShoot(0.25);
        if (anCountdown > 0){
            magicalMysteryTurn();
            anCountdown--;
        } else{
            action.turn = 0;
        }
        if(thrustIfSlowerThan(10)){
            anCountdown = (int)(Math.random() * 360);
            itIsAMystery = !itIsAMystery;
        }
    }

    private void randomShoot(double shootChance){
        action.shoot = (Math.random() < shootChance);
    }

    private void magicalMysteryTurn(){
        action.turn = (int)(Math.random()) + 1;
        if (itIsAMystery){
            action.turn *= -1;
        }
    }

    public void noAction(){ action.noAction(); }

    public void noClick(){};

}
