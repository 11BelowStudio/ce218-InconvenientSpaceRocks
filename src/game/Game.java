package game;


import utilities.AttributeString;
import utilities.Vector2D;

import java.awt.*;
import java.util.*;
import java.util.List;

import static game.Constants.*;

public class Game extends Model  {
    public List<GameObject> gameObjects;

    public List<GameObject> hudObjects;

    private GameLevels levelConfigs;

    //PlayerController ctrl;
    PlayerShip ship;

    EnemyShip enemy;

    int score;

    int lives;

    private final int LIFE_COST = 100;

    int pointsToEarnLife;

    int currentLevel;

    boolean waitingToRespawn;


    //private EnemyPlayer enemyPlayer;

    int timeForEnemyToRespawn;

    int enemyRespawnChance;

    //Stack<GameObject> newObjects;
    public Stack<GameObject> newObjects;

    int objectWait;

    int lastAsteroidCount;

    int timeSinceLastAsteroidChange;

    private Stack<Asteroid> asteroidStack;

    private Stack<MediumAsteroid> mediumAsteroidStack;

    private Stack<BigAsteroid> bigAsteroidStack;

    private Stack<PlayerBullet> playerBullets;

    private Stack<EnemyBullet> enemyBullets;

    private Stack<EnemyShip> enemyShips;

    //private List<EnemyShip> enemyList;

    private StringObject middleTextObject;


    private AttributeString<Integer> scoreText;
    private StringObject scoreTextObject;

    private AttributeString<Integer> livesText;
    private StringObject livesTextObject;

    private AttributeString<Integer> levelText;
    private StringObject levelTextObject;


    private int maxEnemies;

    private int currentEnemies;

    private boolean levelStarting;

    private StringObject newAsteroidCount;

    boolean spawnIt;

    //boolean endGame;

    public Game(PlayerController ctrl){
        super(ctrl);

        SoundManager.stopMenu();
        SoundManager.startGame();
        //levelConfigs = new GameLevels();
        //ctrl = new PlayerController();
        //resetGame();

        ctrl.stopAll();

        gameObjects = new ArrayList<>();
        newObjects = new Stack<>();
        //newObjects = new ArrayList<>();

        //levelConfigs = new GameLevels();

        asteroidStack = new Stack<>();
        for (int i = 0; i < 150; i++) {
            asteroidStack.push(new Asteroid());
        }
        mediumAsteroidStack = new Stack<>();
        for (int i = 0; i < 45; i++) {
            mediumAsteroidStack.push(new MediumAsteroid());
        }
        bigAsteroidStack = new Stack<>();
        for (int i = 0; i < 15; i++) {
            bigAsteroidStack.push(new BigAsteroid());
        }
        playerBullets = new Stack<>();
        for (int i = 0; i < 4; i++) {
            playerBullets.push(new PlayerBullet());
        }
        enemyBullets = new Stack<>();
        for (int i = 0; i < 15; i++) {
            enemyBullets.push(new EnemyBullet());
        }


        enemyShips = new Stack<>();
        for (int i = 0; i < 5; i++) {
            EnemyShip e = new EnemyShip(this);
            enemyShips.push(e);
        }

        this.ctrl = ctrl;
        ship = new PlayerShip(this.ctrl,this);
        //ship.revive();

        gameObjects.add(ship.reviveAndReturn());

        score = 0;

        lives = 3;

        pointsToEarnLife = LIFE_COST;

        currentLevel = 0;
        //currentLevel = 8;
        //currentLevel = 100;

        waitingToRespawn = false;
        gameOver = false;


        //enemyPlayer = new EnemyPlayer(this);

        //enemy = new EnemyShip(enemyPlayer,this);
        //enemyPlayer.newEnemy(enemy);

        lastAsteroidCount = 0;

        timeSinceLastAsteroidChange = 0;

        maxEnemies = 1;
        currentEnemies = 0;

        resetEnemySpawnTimer();

        endGame = false;
        /**/

        maxEnemies = 1;

        hudObjects = new ArrayList<>();

        hudObjects.add(middleTextObject = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT),new Vector2D(),StringObject.MIDDLE_ALIGN));

        hudObjects.add(newAsteroidCount = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT+20),new Vector2D(), StringObject.MIDDLE_ALIGN));

        levelText = new AttributeString<>("Level: ",currentLevel);
        scoreText = new AttributeString<>("Score: ",score);
        livesText = new AttributeString<>("Lives: ",lives);


        int eighthWidth = HALF_WIDTH/2;
        hudObjects.add(levelTextObject = new StringObject(new Vector2D(eighthWidth,20), new Vector2D(),levelText,StringObject.RIGHT_ALIGN));

        hudObjects.add(scoreTextObject = new StringObject(new Vector2D(HALF_WIDTH,20), new Vector2D(),scoreText,StringObject.MIDDLE_ALIGN));

        hudObjects.add(livesTextObject = new StringObject(new Vector2D(3*eighthWidth,20), new Vector2D(),livesText,StringObject.LEFT_ALIGN));


        spawnIt = false;




        //setupLevel();

        //gameObjects.add(new Bullet(new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2),Vector2D.polar(Math.toRadians(270),0)));

        //testAsteroid = new BasicAsteroid(0,0,20,50);
        //asteroids.add(testAsteroid);
    }


    private void endGame(){
        endGame = true;
        SoundManager.stopGame();
        SoundManager.stopThrust();
        //SoundManager.startMenu();
        /*
        ship = null;
        if (gameObjects == null) {
            gameObjects = new ArrayList<>();
        } else{
            gameObjects.clear();
        }
        if (newObjects == null) {
            //newObjects = new Stack<>();
            newObjects = new ArrayList<>();
        } else{
            newObjects.clear();
        }
        ship = new PlayerShip(ctrl, this);
        gameObjects.add(ship);

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

        enemyPlayer = new EnemyPlayer(this);



        lastAsteroidCount = 0;

        timeSinceLastAsteroidChange = 0;

        resetEnemySpawnTimer();
        */
        //ready = true;
        //gf.gameStart();
    }



    private ArrayList<GameObject> setupLevel(boolean ohNoes){

        SoundManager.play(SoundManager.intimidating);
        //intimidation noises

        levelTextObject.setText(levelText.showValue(currentLevel));
        //updates the level text shown to player

        ArrayList<GameObject> newAsteroids = new ArrayList<>();
        //list of new asteroids

        ArrayList<Integer> thisLevel;
        //will store the configuration for this level

        levelStarting = true;
        if (ohNoes){
        //if (true) {
            thisLevel = GameLevels.ohHecc;
            middleTextObject.setText("RIGHT THATS IT IMMA ASTEROID IN UR P");


            levelTextObject.setText("Level: oh noes");

            //maximum asteroids of all types.

            //have fun.
        } else{
            thisLevel = GameLevels.getLevelConfig(currentLevel);
            middleTextObject.setText("INCOMING");
            //works out how many of each to spawn
        }

        //le midget asteroids have arrived
        for (int i = 0; i < thisLevel.get(0); i++) {
            if (asteroidStack.isEmpty()) {
                break;
            } else{
                Asteroid newAsteroid = asteroidStack.pop();
                newAsteroid.revive();
                newAsteroids.add(newAsteroid);
            }
        }

        for (int i = 0; i < thisLevel.get(1); i++) {
            if (mediumAsteroidStack.isEmpty()){
                break;
            } else {
                MediumAsteroid newAsteroid = mediumAsteroidStack.pop();
                newAsteroid.revive();
                newAsteroids.add(newAsteroid);
            }
        }

        for (int i = 0; i < thisLevel.get(2); i++) {
            if (bigAsteroidStack.isEmpty()) {
                break;
            } else{
                BigAsteroid newAsteroid = bigAsteroidStack.pop();
                newAsteroid.revive();
                newAsteroids.add(newAsteroid);
            }
        }

        if (currentLevel % 3 == 0){
            maxEnemies++; //can have another enemy on screen at once every 3 levels
        }

        spawnIt = true;

        Collections.shuffle(newAsteroids);
        return newAsteroids;

    }

    public void update(){

        alive.clear();
        //list for objects that are still alive
        dead.clear();
        //list for objects that are now dead


        boolean shipJustAdded = false;

        if (waitingToRespawn && ctrl.action.theAnyButton()) {
            middleTextObject.setText("");
            waitingToRespawn = false;
            alive.add(ship.reviveAndReturn());
            ctrl.stopAll();
            shipJustAdded = true;
            //creates a new ship if the player can respawn and the player presses any button
        }

        //boolean enemyExists = false;



        boolean asteroidsRemaining = false;
        //
        // 1. update all game objects and add non-dead game objects to alive list
        // 2. check if ship bullet is not null. If so, add it to alive list
        //    and set ship bullet field to null
        // 3. clear list of game objects
        // 4. add all elements from the alive list to the list of game objects

        for (GameObject g : gameObjects) {
            //updating everything basically

            g.update();

            if (g.dead){
                dead.add(g);

                //if it's dead, it gets added back into the stack of stuff basically
                if (g instanceof GenericAsteroid){
                    if (g instanceof Asteroid){
                        asteroidStack.push((Asteroid) g);
                    } else if (g instanceof MediumAsteroid){
                        mediumAsteroidStack.push((MediumAsteroid) g);
                    } else{
                        assert g instanceof BigAsteroid;
                        bigAsteroidStack.push((BigAsteroid) g);
                    }
                } else if (g instanceof Bullet){
                    if (g instanceof PlayerBullet){
                        playerBullets.push((PlayerBullet)g);
                    } else if (g instanceof EnemyBullet){
                        enemyBullets.push((EnemyBullet)g);
                    }
                } else if (g instanceof EnemyShip) {
                    enemyShips.push(((EnemyShip) g));
                    currentEnemies--;
                }
            } else{
                alive.add(g);

                boolean isEnemy = isEnemy(g);
                boolean isAsteroid = isAsteroid(g);


                if(shipJustAdded && (isAsteroid || isEnemy)){
                    moveObjectAwayFromShip(g); //will move objects away from the ship if it's just spawned in
                }

                if (!asteroidsRemaining){
                    if (isAsteroid){
                        asteroidsRemaining = true;
                    }
                }

                if (g instanceof EnemyShip){
                    if (((EnemyShip) g).fired && !enemyBullets.isEmpty()){
                        EnemyBullet b = enemyBullets.pop();
                        b.revive(((EnemyShip) g).bulletLocation, ((EnemyShip) g).bulletVelocity);
                        alive.add(b);
                        SoundManager.fire();
                        ((EnemyShip) g).fired = false;
                    }
                }
            }
        }



        //doing collision handling
        for (int i = 0; i < alive.size(); i++) {
            GameObject g = alive.get(i);

            if (g.dead || g.intangible){
                continue; //skip it if it's now dead/is intangible
                //it'll be dealt with next update.
            }


                for (int j = i+1; j < alive.size(); j++) {
                GameObject g2 = alive.get(j);
                if (g2.dead || g2.intangible){
                    continue; //skip stuff that's dead/intangible
                }

                if ((isAsteroid(g) ^ isAsteroid(g2)) ||
                        (isPlayer(g) ^ isPlayer(g2)) ||
                        (isEnemy(g) ^ isEnemy(g2))
                ) {
                    //only need to bother handing collisions if both objects are both asteroid/player/enemy-related objects
                    // (^ operator is 'xor')
                    //can't really do 'if (.class != .class)', as the superclasses kinda mess around with it
                    g.collisionHandling(g2);
                }
            }

        }

        if (currentEnemies < maxEnemies && !enemyShips.isEmpty()) {
            //if (Math.random() < 0.125) {
            if (timeForEnemyToRespawn <= 0) {
                //enemy = new EnemyShip(enemyPlayer, this);
                //enemy = new EnemyShip(this);
                EnemyShip e = enemyShips.pop();
                //e.revive();
                moveObjectAwayFromShip(e.reviveAndReturn());
                //enemyPlayer.newEnemy(enemy);
                alive.add(e);
                //newObjects.add(enemy);
                resetEnemySpawnTimer();
                //enemyExists = true;
                currentEnemies++;
            } else {
                timeForEnemyToRespawn--;
                //System.out.println(timeForEnemyToRespawn);
            }
        }

        /*
        for (GameObject g : alive) {
            //updating everything basically

            g.update();

            if (g.dead){
                dead.add(g);

                //if it's dead, it gets added back into the stack of stuff basically
                if (g instanceof GenericAsteroid){
                    if (g instanceof Asteroid){
                        asteroidStack.push((Asteroid) g);
                    } else if (g instanceof MediumAsteroid){
                        mediumAsteroidStack.push((MediumAsteroid) g);
                    } else{
                        assert g instanceof BigAsteroid;
                        bigAsteroidStack.push((BigAsteroid) g);
                    }
                } else if (g instanceof Bullet){
                    if (g instanceof PlayerBullet){
                        playerBullets.push((PlayerBullet)g);
                    } else if (g instanceof EnemyBullet){
                        enemyBullets.push((EnemyBullet)g);
                    }
                } else if (g instanceof EnemyShip) {
                    enemyShips.push(((EnemyShip) g));
                    currentEnemies--;
                }
            } else{
                alive.add(g);

                boolean isEnemy = isEnemy(g);
                boolean isAsteroid = isAsteroid(g);


                if(shipJustAdded && (isAsteroid || isEnemy)){
                    moveObjectAwayFromShip(g); //will move objects away from the ship if it's just spawned in
                }

                if (!asteroidsRemaining){
                    if (isAsteroid){
                        asteroidsRemaining = true;
                    }
                }

                if (g instanceof EnemyShip){
                    if (((EnemyShip) g).fired && !enemyBullets.isEmpty()){
                        EnemyBullet b = enemyBullets.pop();
                        b.revive(((EnemyShip) g).bulletLocation, ((EnemyShip) g).bulletVelocity);
                        alive.add(b);
                        SoundManager.fire();
                        ((EnemyShip) g).fired = false;
                    }
                }
            }
        }

         */




        boolean playerHit = false; //recording if the player was hit or not

        boolean livesChanged = false;

        boolean scoreChanged = false;

        for (GameObject g: dead){
            //working out if something is dead because the player hit it or not, and how to react from there
            if (g.playerHit){
                scoreChanged = true;
                //player earns points if they hit the thing which is now dead
                int points = g.pointValue;
                if (g.bombHit){
                    points = (int) Math.ceil((double)points/ 2);
                }
                score += points;
                pointsToEarnLife -= g.pointValue;
                if (pointsToEarnLife <= 0){
                    //if player has earned enough points to get a new life
                    SoundManager.play(SoundManager.nice); //they are told that it's nice
                    lives++; //lives incremented
                    pointsToEarnLife += LIFE_COST; //need to earn another LIFE_COST points to earn a new life
                    //points above the threshold effectively roll over
                    livesChanged = true;
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
                livesChanged = true;

            } else if (isAsteroid(g)){
                //spawning in the children of the destroyed asteroid
                if (g instanceof MediumAsteroid) {
                    int numToSpawn = ((MediumAsteroid) g).childrenToSpawn;
                    for (int i = 0; i < numToSpawn; i++) {
                        if (asteroidStack.isEmpty()) {
                            break;
                        }
                        Asteroid newAsteroid = asteroidStack.pop();
                        newAsteroid.revive(g.position);
                        alive.add(newAsteroid);
                    }
                } else if (g instanceof BigAsteroid) {
                    int numToSpawn = ((BigAsteroid) g).childrenToSpawn;
                    for (int i = 0; i < numToSpawn; i++) {
                        if (mediumAsteroidStack.isEmpty()) {
                            break;
                        }
                        MediumAsteroid newAsteroid = mediumAsteroidStack.pop();
                        newAsteroid.revive(g.position);
                        alive.add(newAsteroid);
                    }
                }
            }

        }
        if (scoreChanged){
            scoreTextObject.setText(scoreText.showValue(score));
        }
        if (livesChanged){
            livesTextObject.setText(livesText.showValue(lives));
        }
        if (playerHit){
            levelStarting = false;
            ctrl.stopAll();
            if (lives > 0) {
                waitingToRespawn = true;
                SoundManager.play(SoundManager.ohno);
                middleTextObject.setText("PRESS THE ANY BUTTON TO RESPAWN");
            } else{
                System.out.println("GAME OVER YEAH!");
                middleTextObject.setText("GAME OVER!");
                SoundManager.play(SoundManager.andYouFailed);
                gameOver = true;
            }
        }



        if (!gameOver) {
            if (!ship.dead){
                if (ship.fired && !playerBullets.isEmpty()){
                    PlayerBullet temp = playerBullets.pop();
                    temp.revive(ship.bulletLocation,ship.bulletVelocity);
                    alive.add(temp);
                    SoundManager.fire();
                    ship.fired = false;
                }
                if (ship.spawnBomb){
                    Bomb b = new Bomb(ship.bombLocation);
                    alive.add(b);
                    ship.spawnBomb = false;
                }
            }


        } else{
            if (ctrl.action.theAnyButton){
                endGame();
            }
        }


            if (newObjects.isEmpty()) {
                //if there are no more new objects to spawn for this level
                if (levelStarting){
                    //clears the text that appears when the level is starting if this text was still being displayed
                    middleTextObject.setText("");
                    newAsteroidCount.setText("");
                    levelStarting = false;
                }
                if (!asteroidsRemaining && !gameOver) {
                    //if no asteroids remain, and the player isn't currently dead, the level is done
                    currentLevel++;
                    //moves forward a level
                    newObjects.addAll(setupLevel(ctrl.action.p));
                    //adds the asteroids for the next level
                    ship.giveImmunity();
                    objectWait = 0;
                    //gives the player some temporary immunity,
                    //so they have some time to react to the new obstacles
                }
            } else{
                //will be spawning the asteroids in
                //tbh the only reason for the 1 update delay between adding asteroids is so you can see them coming
                if (spawnIt) {
                    //adds the top GameObject from newObjects to 'alive'
                    GameObject newObject = newObjects.pop();
                    moveObjectAwayFromShip(newObject);
                    alive.add(newObject);
                    spawnIt = false;
                    newAsteroidCount.setText(String.valueOf(newObjects.size()));
                } else{
                    spawnIt = true;
                }

            }


        //}

        synchronized (Game.class) {
            gameObjects.clear();
            gameObjects.addAll(alive);
        }

        //System.out.println("Lives: " + lives + "| Score: " + score);
    }

    Vector2D getShipPosition(){
        if (ship.dead && !gameOver){
            return new Vector2D(Math.random() * FRAME_WIDTH,Math.random() * FRAME_HEIGHT);
        } else{
            return ship.position;
        }
    }

    private void moveObjectAwayFromShip(GameObject o){
        Vector2D vecFromShip;
        if (!ship.dead) { //obtains ship location if the ship isn't dead
            vecFromShip = getShipPosition().getVectorTo(o.position, FRAME_WIDTH, FRAME_HEIGHT);
        } else { //obtains distance from ship respawn point if ship is dead
            vecFromShip = new Vector2D(HALF_WIDTH,HALF_HEIGHT).getVectorTo(o.position, FRAME_WIDTH, FRAME_HEIGHT);
        }
        //if it's less than 200px away from the ship's location/spawn location, move it so it will be 200px away
        if (vecFromShip.mag() <= 200) {
            o.position = vecFromShip.setMag(200);
            //nothing will spawn too close to the ship now
        }
    }

    private void resetEnemySpawnTimer(){
        if (currentLevel < 100) {
            timeForEnemyToRespawn = 100 - currentLevel;
        }
        timeForEnemyToRespawn +=  (int)(Math.random() * 100) + (int)(Math.random() * 100);
    }

    private boolean isPlayer(GameObject o){
        return (o instanceof PlayerShip || o instanceof PlayerBullet || o instanceof Bomb);
    }

    private boolean isEnemy(GameObject o){
        return (o instanceof EnemyShip || o instanceof  EnemyBullet);
    }

    private boolean isAsteroid(GameObject o){
        return (o instanceof GenericAsteroid);
    }

    public int getScore(){
        return score;
    }
}