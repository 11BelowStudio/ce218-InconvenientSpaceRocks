package game;


import utilities.Vector2D;

import java.util.*;

import static game.Constants.*;

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

    private List<EnemyShip> enemyList;


    private int maxEnemies;


    boolean reset;

    public Game() {
        //levelConfigs = new GameLevels();
        //ctrl = new Keys();
        //resetGame();

        gameObjects = new ArrayList<>();
        newObjects = new Stack<>();
        //newObjects = new ArrayList<>();

        levelConfigs = new GameLevels();

        asteroidStack = new Stack<>();
        for (int i = 0; i < 120; i++) {
            asteroidStack.push(new Asteroid());
        }
        mediumAsteroidStack = new Stack<>();
        for (int i = 0; i < 40; i++) {
            mediumAsteroidStack.push(new MediumAsteroid());
        }
        bigAsteroidStack = new Stack<>();
        for (int i = 0; i < 10; i++) {
            bigAsteroidStack.push(new BigAsteroid());
        }
        playerBullets = new Stack<>();
        for (int i = 0; i < 5; i++) {
            playerBullets.push(new PlayerBullet());
        }
        enemyBullets = new Stack<>();
        for (int i = 0; i < 15; i++) {
            enemyBullets.push(new EnemyBullet());
        }

        /*
        enemyShips = new Stack<>();
        for (int i = 0; i < 3; i++) {
            //EnemyPlayer ep = new EnemyPlayer(this);

            EnemyShip es = new EnemyShip(ep,this);
            ep.newEnemy(es);
            enemyShips.push(es);
        }

        enemyList = new ArrayList<>();
        */
        ctrl = new Keys();
        ship = new PlayerShip(ctrl,this);
        gameObjects.add(ship);

        score = 0;

        lives = 3;

        pointsToEarnLife = LIFE_COST;

        currentLevel = 0;
        //currentLevel = 7;
        //currentLevel = 100;

        waitingToRespawn = false;
        gameOver = false;

        enemy = null;

        enemyPlayer = new EnemyPlayer(this);

        //enemy = new EnemyShip(enemyPlayer,this);
        //enemyPlayer.newEnemy(enemy);

        lastAsteroidCount = 0;

        timeSinceLastAsteroidChange = 0;

        resetEnemySpawnTimer();

        reset = false;
        /**/

        maxEnemies = 3;


        //setupLevel();

        //gameObjects.add(new Bullet(new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2),Vector2D.polar(Math.toRadians(270),0)));

        //testAsteroid = new BasicAsteroid(0,0,20,50);
        //asteroids.add(testAsteroid);
    }


    private void resetGame(){
        reset = true;
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



    private ArrayList<GameObject> setupLevel(){

        SoundManager.play(SoundManager.intimidating);

        ArrayList<GameObject> newObjects = new ArrayList<>();

        ArrayList<Integer> thisLevel = levelConfigs.getLevelConfig(currentLevel);

        for (int i = 0; i < thisLevel.get(0); i++) {
            //asteroids.add(BasicAsteroid.makeRandomAsteroid());
            //gameObjects.add(new Asteroid());
            if (asteroidStack.isEmpty()) {
                break;
            } else{
                Asteroid newAsteroid = asteroidStack.pop();
                newAsteroid.revive();
                newObjects.add(newAsteroid);
            }
        }

        for (int i = 0; i < thisLevel.get(1); i++) {
            //asteroids.add(BasicAsteroid.makeRandomAsteroid());
            //gameObjects.add(new Asteroid());
            if (mediumAsteroidStack.isEmpty()){
                break;
            } else {
                MediumAsteroid newAsteroid = mediumAsteroidStack.pop();
                newAsteroid.revive();
                newObjects.add(newAsteroid);
            }
        }

        for (int i = 0; i < thisLevel.get(2); i++) {
            //asteroids.add(BasicAsteroid.makeRandomAsteroid());
            //gameObjects.add(new Asteroid());
            if (bigAsteroidStack.isEmpty()) {
                break;
            } else{
                BigAsteroid newAsteroid = bigAsteroidStack.pop();
                newAsteroid.revive();
                newObjects.add(newAsteroid);
            }
        }

        maxEnemies += (currentLevel % 3);

        Collections.shuffle(newObjects);

        /*
        enemy = new EnemyShip(enemyPlayer,this);
        enemyPlayer.newEnemy(enemy);

        newObjects.add(enemy);
        */
        return newObjects;

    }

    public void update(){
        List<GameObject> alive = new ArrayList<>();
        //list for objects that are still alive

        List<GameObject> dead = new ArrayList<>();
        //list for objects that are now dead

        boolean shipJustAdded = false;

        boolean enemyExists = (enemy != null);

        if (waitingToRespawn){
            //if waiting for the player to respawn
            if (ctrl.action.theAnyButton()) {
                waitingToRespawn = false;
                alive.add(ship = new PlayerShip(ctrl, this, ship.direction));
                shipJustAdded = true;
                //creates a new ship if the player can respawn and the player presses any button
            }
        }  else if (!enemyExists) {
            //if (Math.random() < 0.125) {
                if (timeForEnemyToRespawn <= 0) {
                    enemy = new EnemyShip(enemyPlayer, this);
                    moveObjectAwayFromShip(enemy);
                    enemyPlayer.newEnemy(enemy);
                    alive.add(enemy);
                    //newObjects.add(enemy);
                    resetEnemySpawnTimer();
                    //enemyExists = true;
                } else{
                    timeForEnemyToRespawn--;
                    System.out.println(timeForEnemyToRespawn);
                }
            //}
        }

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
            boolean isPlayer = (temp instanceof PlayerShip || temp instanceof PlayerBullet);
            boolean isEnemy = (temp instanceof EnemyShip || temp instanceof  EnemyBullet);
            boolean isAsteroid = (temp instanceof GenericAsteroid);

            if(shipJustAdded && (isAsteroid || isEnemy)){
                moveObjectAwayFromShip(temp); //will move objects away from the ship if it's just spawned in
            }

            if (!temp.intangible || !temp.dead) { //only need to bother checking the collision if this isn't intangible/dead
                //working out what type of object temp is
                for (int j = i+1; j < gameObjects.size(); j++) {
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

                //dead asteroids put into the asteroid stacks
                //and dead bullets put back into the bullet stacks

                //this is done here, and not through the check for the dead objects,
                //as this allows child asteroids to be prepared for when they are needed
                if (isAsteroid){
                    if (temp instanceof Asteroid){
                        asteroidStack.push((Asteroid) temp);
                    } else if (temp instanceof MediumAsteroid){
                        mediumAsteroidStack.push((MediumAsteroid) temp);
                    } else{
                        bigAsteroidStack.push((BigAsteroid) temp);
                    }
                } else if (temp instanceof Bullet){
                    if (temp instanceof PlayerBullet){
                        playerBullets.push((PlayerBullet)temp);
                    } else if (temp instanceof EnemyBullet){
                        //assert temp instanceof EnemyBullet;
                        enemyBullets.push((EnemyBullet)temp);
                    }
                } else if (isEnemy){
                    EnemyShip e = (EnemyShip) temp;
                    e.getPlayer().ded();
                    enemy = null;
                    /*
                    enemyShips.push(e);
                    enemyList.remove(e);
                     */
                }

            } else{
                //alive objects added to the alive list
                alive.add(temp);
                if (!asteroidsRemaining && isAsteroid){
                    asteroidsRemaining = true;
                }
            }


            if (temp.childObjects != null && !temp.childObjects.isEmpty()){
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

            }  else if (g instanceof EnemyShip){
                enemy = null;
                //((EnemyShip) g).getPlayer().ded();
                enemyExists = false;
                enemyPlayer.ded();
                //no enemy if the enemy got hit
            } else if (g instanceof MediumAsteroid){
                //int i = 0;
                int numToSpawn = ((MediumAsteroid) g).childrenToSpawn;
                for (int i = 0; i < numToSpawn; i++) {
                    if (asteroidStack.isEmpty()){
                        break;
                    }
                    Asteroid newAsteroid = asteroidStack.pop();
                    newAsteroid.revive(g.position);
                    alive.add(newAsteroid);
                }
            } else if (g instanceof BigAsteroid){
                int numToSpawn = ((BigAsteroid) g).childrenToSpawn;
                for (int i = 0; i < numToSpawn; i++) {
                    if (mediumAsteroidStack.isEmpty()){
                        break;
                    }
                    MediumAsteroid newAsteroid = mediumAsteroidStack.pop();
                    newAsteroid.revive(g.position);
                    alive.add(newAsteroid);
                }
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



        if (!gameOver) {
            reset = false;

            /*
            if (enemyList.size() <= maxEnemies && !enemyShips.isEmpty()) {
                //if (Math.random() < 0.125) {
                    //if (timeForEnemyToRespawn <= 0) {
                        EnemyShip e = enemyShips.pop();
                        //moveObjectAwayFromShip(e);
                        e.getPlayer().newEnemy(e);
                        alive.add(e);
                        //newObjects.add(enemy);
                        //resetEnemySpawnTimer();
                        //enemyExists = true;
                    //} else {
                    //    timeForEnemyToRespawn--;
                    //}
                //}
            }*/
            if (enemyExists){
            //if (!enemyList.isEmpty()){
            //for (EnemyShip e: enemyList) {
                //if (e.fired && !enemyBullets.isEmpty()) {
                if (enemy.fired && !enemyBullets.isEmpty()){
                    EnemyBullet temp = enemyBullets.pop();
                    temp.revive(enemy.bulletLocation, enemy.bulletVelocity);
                    alive.add(temp);
                    SoundManager.fire();
                    enemy.fired = false;
                }
            }
            //}
            //}

            if (ship != null){
                if (ship.fired && !playerBullets.isEmpty()){
                    PlayerBullet temp = playerBullets.pop();
                    temp.revive(ship.bulletLocation,ship.bulletVelocity);
                    alive.add(temp);
                    SoundManager.fire();
                    ship.fired = false;
                }
            }


            //here is a failsafe, just in case an asteroid gets lost.
            //basically yeets all the asteroids if the count of asteroids hasn't changed in the past 3000 frames
            //int asteroidCount = 0;

            /*
            for (GameObject g : alive) {
                //checking to see if there are any alive asteroids left
                if (g instanceof GenericAsteroid){
                    //asteroidsRemaining = true;
                    //asteroidCount++;
                }
            }*/
            /*
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
            }*/
        } else{
            if (ctrl.action.theAnyButton){
                resetGame();
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
                /*
                if (newObjects.peek().dead){
                    newObjects.pop();
                }*/

                GameObject newObject = newObjects.pop();
                //for (GameObject newObject : newObjects) {
                moveObjectAwayFromShip(newObject);
                alive.add(newObject);
                objectWait = 10;
                //}
                //newObjects.clear();

            }


        //}

        synchronized (Game.class) {
            gameObjects.clear();
            gameObjects.addAll(alive);
        }
        //ship.update();

        //System.out.println("Lives: " + lives + "| Score: " + score);
    }

    Vector2D getShipPosition(){
        if (ship == null){
            return new Vector2D(Math.random() * FRAME_WIDTH,Math.random() * FRAME_HEIGHT);
        } else{
            return ship.position;
        }
    }

    private void moveObjectAwayFromShip(GameObject o){
        Vector2D vecFromShip;
        if (!ship.dead) { //obtains ship location if the ship isn't dead
            vecFromShip = getShipPosition().getVectorBetween(o.position, FRAME_WIDTH, FRAME_HEIGHT);
        } else { //obtains distance from ship respawn point if ship is dead
            vecFromShip = new Vector2D(HALF_WIDTH,HALF_HEIGHT).getVectorBetween(o.position, FRAME_WIDTH, FRAME_HEIGHT);
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