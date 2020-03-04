package game;

import utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static game.Constants.FRAME_HEIGHT;
import static game.Constants.FRAME_WIDTH;

public class Game {
    public List<GameObject> gameObjects;

    private GameLevels levelConfigs;

    Keys ctrl;
    PlayerShip ship;

    EnemyShip enemy;

    int score;

    int lives;

    private final int LIFE_COST = 100;

    int pointsToEarnLife;

    int currentLevel;

    boolean waitingToRespawn;

    boolean gameOver;

    private EnemyPlayer enemyPlayer;

    int timeForEnemyToRespawn;

    int enemyRespawnChance;

    Stack<GameObject> newObjects;

    int objectWait;

    int lastAsteroidCount;

    int timeSinceLastAsteroidChange;


    boolean reset;

    public Game() {
        //levelConfigs = new GameLevels();
        //ctrl = new Keys();
        //resetGame();

        gameObjects = new ArrayList<>();
        newObjects = new Stack<>();
        levelConfigs = new GameLevels();
        ctrl = new Keys();
        ship = new PlayerShip(ctrl,this);
        gameObjects.add(ship);

        score = 0;

        lives = 3;

        pointsToEarnLife = LIFE_COST;

        currentLevel = 0;
        //currentLevel = 7;

        waitingToRespawn = false;
        gameOver = false;

        enemy = null;

        enemyPlayer = new EnemyPlayer(this);

        lastAsteroidCount = 0;

        timeSinceLastAsteroidChange = 0;

        resetEnemySpawnTimer();

        reset = false;
        /**/


        //setupLevel();

        //gameObjects.add(new Bullet(new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2),Vector2D.polar(Math.toRadians(270),0)));

        //testAsteroid = new BasicAsteroid(0,0,20,50);
        //asteroids.add(testAsteroid);
    }


    private void resetGame(){
        reset = true;
        if (gameObjects != null) {
            gameObjects = new ArrayList<>();
        }
        if (newObjects != null) {
            newObjects = new Stack<>();
        }
        if (ship != null) {
            ship = new PlayerShip(ctrl, this);
            gameObjects.add(ship);
        }

        score = 0;

        lives = 3;

        pointsToEarnLife = LIFE_COST;

        currentLevel = 0;
        //currentLevel = 7;

        waitingToRespawn = false;
        gameOver = false;

        if (enemy != null) {
            enemy = null;
        }
        if (enemyPlayer != null) {
            enemyPlayer = new EnemyPlayer(this);
        }

        lastAsteroidCount = 0;

        timeSinceLastAsteroidChange = 0;

        resetEnemySpawnTimer();
        //ready = true;
        //gf.gameStart();
    }



    public ArrayList<GameObject> setupLevel(){

        SoundManager.play(SoundManager.intimidating);

        ArrayList<GameObject> newObjects = new ArrayList<>();

        ArrayList<Integer> thisLevel = levelConfigs.getLevelConfig(currentLevel);

        for (int i = 0; i < thisLevel.get(0); i++) {
            //asteroids.add(BasicAsteroid.makeRandomAsteroid());
            //gameObjects.add(new Asteroid());
            newObjects.add(new Asteroid());
        }

        for (int i = 0; i < thisLevel.get(1); i++) {
            //asteroids.add(BasicAsteroid.makeRandomAsteroid());
            //gameObjects.add(new Asteroid());
            newObjects.add(new MediumAsteroid());
        }

        for (int i = 0; i < thisLevel.get(2); i++) {
            //asteroids.add(BasicAsteroid.makeRandomAsteroid());
            //gameObjects.add(new Asteroid());
            newObjects.add(new BigAsteroid());
        }

        return newObjects;

    }

    public void update() throws Throwable {
        List<GameObject> alive = new ArrayList<>();
        //list for objects that are still alive

        List<GameObject> dead = new ArrayList<>();
        //list for objects that are now dead

        boolean asteroidsRemaining = false;
        //
        // 1. update all game objects and add non-dead game objects to alive list
        // 2. check if ship bullet is not null. If so, add it to alive list
        //    and set ship bullet field to null
        // 3. clear list of game objects
        // 4. add all elements from the alive list to the list of game objects
        ///
        //List<MediumAsteroid> genericLargerAsteroids = new ArrayList<>();

        for (GameObject g : gameObjects) {
            //updating everything basically

            g.update();
        }

        //doing collision handling and dealing with the results of the collision handling
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject temp = gameObjects.get(i);
            boolean isAsteroid = (temp instanceof GenericAsteroid);
            if (!temp.intangible || !temp.dead) { //only need to bother checking the collision if this isn't intangible/dead
                boolean isPlayer = (temp instanceof PlayerShip || temp instanceof PlayerBullet);
                boolean isEnemy = (temp instanceof EnemyShip || temp instanceof  EnemyBullet);
                //working out what type of object temp is
                for (int j = i; j < gameObjects.size(); j++) {
                    GameObject temp2 = gameObjects.get(j);
                    if (!temp2.intangible || !temp2.dead){ //again, only need to bother if this also isn't intangible/dead
                        if ((isAsteroid ^ temp2 instanceof GenericAsteroid) ||
                                (isPlayer ^ (temp2 instanceof PlayerShip || temp2 instanceof PlayerBullet)) ||
                                (isEnemy ^ (temp2 instanceof EnemyShip || temp2 instanceof EnemyBullet))
                        ) { //only need to bother handing collisions if both are different classes (^ operator is 'xor')
                            //can't really do 'if (.class != .class)', as the GenericAsteroid superclass kinda messes with it
                            temp.collisionHandling(temp2);
                        }
                    }
                }
            }
            if (temp.dead) {
                //dead objects added to the dead list
                dead.add(temp);
            } else{
                //alive objects added to the alive list
                alive.add(temp);
            }
            if (temp.childObjects != null){
                //if the current GameObject has childObjects,
                //they're all added to 'alive',
                //before the GameObject's childObjects list is wiped
                /*
                for (GameObject c: temp.childObjects){
                    c.update();
                    alive.add(c);
                }*/

                alive.addAll(temp.childObjects);

                temp.childObjects = null;
            }

        }



        boolean playerHit = false; //recording if the player was hit or not

        for (GameObject g: dead){
            //working out if something is dead because the player hit it or not
            if (g.playerHit){
                //player earns points if they hit the thing which is now dead
                score += g.pointValue;
                pointsToEarnLife -= g.pointValue;
                if (pointsToEarnLife <= 0){
                    //if player has earned enough points to get a new life
                    SoundManager.play(SoundManager.nice); //they are told that it's nice
                    lives++; //lives incremented
                    pointsToEarnLife += LIFE_COST; //need to earn another LIFE_COST points to earn a new life
                    //points above the threshold effectively roll over
                }
                ship.giveImmunity();
                //also given some temporary immunity for free,
                // so the player isn't immediately going to die if they shot an asteroid right in front of them
            }
            if (g instanceof PlayerShip){
                //if the ship is here, the player is mcfricking dead
                System.out.println("ur ded lol");
                lives--;
                playerHit = true;

            }
            if (g instanceof EnemyShip){
                enemy = null;
                enemyPlayer.ded();
                //no enemy if the enemy got hit
            }
        }
        if (playerHit){
            if (lives > 0) {
                waitingToRespawn = true;
                SoundManager.play(SoundManager.ohno);
            } else{
                System.out.println("GAME OVER YEAH!");
                SoundManager.play(SoundManager.andYouFailed);
                gameOver = true;
            }
        }


        //here is a failsafe, just in case an asteroid gets lost.
        //basically yeets all the asteroids if the count of asteroids hasn't changed in the past 3000 frames
        if (!gameOver) {
            reset = false;
            int asteroidCount = 0;

            for (GameObject g : alive) {
                //checking to see if there are any alive asteroids left
                if (g instanceof GenericAsteroid){
                    asteroidsRemaining = true;
                    asteroidCount++;
                }
            }
            if (asteroidCount == lastAsteroidCount) {
                timeSinceLastAsteroidChange++;
                if (timeSinceLastAsteroidChange == 3000) {
                    for (GameObject g : alive) {
                        if (g instanceof GenericAsteroid) {
                            g.dead = true;
                        }
                    }
                }
            } else {
                lastAsteroidCount = asteroidCount;
                timeSinceLastAsteroidChange = 0;
            }
        } else{
            if (ctrl.action.theAnyButton){
                resetGame();
                try {
                    this.finalize();
                } catch(Exception e){
                    e.printStackTrace();
                } catch(Throwable t){
                    t.printStackTrace();
                }
            }
        }



        if (waitingToRespawn){
            //if waiting for the player to respawn
            if (ctrl.action.theAnyButton()) {
                waitingToRespawn = false;
                alive.add(ship = new PlayerShip(ctrl, this, ship.direction));
                //creates a new ship if the player can respawn and the player presses any button
            }
        } else if (enemy == null) {
            if (Math.random() < 0.125) {
                if (timeForEnemyToRespawn <= 0) {
                    enemy = new EnemyShip(enemyPlayer, this);
                    enemyPlayer.newEnemy(enemy);
                    alive.add(enemy);
                    //newObjects.add(enemy);
                    resetEnemySpawnTimer();
                } else{
                    timeForEnemyToRespawn--;
                }
            }
        }

            /*
            if (!asteroidsRemaining) {
                //if no asteroids remain, and the player isn't currently dead, the level is done
                currentLevel++;
                //moves forward a level
                newObjects.addAll(setupLevel());
                //adds the asteroids for the next level
                ship.giveImmunity();
                objectWait = 10;
                //gives the player some temporary immunity,
                //so they have some time to react to the new obstacles
            }*/

            if (newObjects.isEmpty()) {
                if (!asteroidsRemaining && !gameOver) {
                    //if no asteroids remain, and the player isn't currently dead, the level is done
                    currentLevel++;
                    //moves forward a level
                    newObjects.addAll(setupLevel());
                    //adds the asteroids for the next level
                    ship.giveImmunity();
                    objectWait = 0;
                    //gives the player some temporary immunity,
                    //so they have some time to react to the new obstacles
                }
            } else{
                System.out.println(newObjects.size() + " new objects left");
                if (newObjects.peek().dead){
                    newObjects.pop();
                }
                if (objectWait <= 0) {
                    System.out.println("objects to spawn: " + newObjects.size());
                    GameObject newObject = newObjects.pop();
                    if (!ship.dead) {
                        Vector2D vecFromShip = getShipPosition().getVectorBetween(newObject.position, FRAME_WIDTH, FRAME_HEIGHT);
                        if (vecFromShip.mag() <= 100) {
                            newObject.position.add(vecFromShip.setMag(100));
                            newObject.update();
                            //nothing will spawn too close to the ship now
                        }
                    }
                    alive.add(newObject);
                    objectWait = 10;
                } else{
                    objectWait--;
                }
            }


        //}

        synchronized (Game.class) {
            gameObjects.clear();
            gameObjects.addAll(alive);
        }
        //ship.update();

        //System.out.println("Lives: " + lives + "| Score: " + score);
    }

    public Vector2D getShipPosition(){
        if (ship.dead){
            return null;
        } else{
            return ship.position;
        }
    }

    private void resetEnemySpawnTimer(){
        if (currentLevel < 100) {
            timeForEnemyToRespawn = 100 - currentLevel;
        }
        timeForEnemyToRespawn +=  (int)(Math.random() * 100);
    }


    /*
    public static void main(String[] args) throws Exception {
        BasicGame game = new BasicGame();
        BasicView view = new BasicView(game);
        new JEasyFrame(view, "Basic Game");
        /*
        for counting frames per second

        int frameCountPerSecond = 0;
        long startTime = System.nanoTime();

        while (true) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
            /*
            dont mind this, just counting how many frames there were per second

            frameCountPerSecond++;

            if (System.nanoTime() - startTime >= 1000000000){ //if there's been at least 1000000000 nanoseconds (1 second) since startTime
                System.out.println("There were " + frameCountPerSecond + " frames in that second");
                frameCountPerSecond = 0;
                startTime = System.nanoTime();
                System.out.println(game.testAsteroid); //getting the details of the testAsteroid asteroid
            }
        }
    }

     */
}