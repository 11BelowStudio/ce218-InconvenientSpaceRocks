package game;

import utilities.HighScoreHandler;
import utilities.Vector2D;

import java.util.*;

import static game.Constants.*;

public class Game extends Model  {

    private PlayerShip ship;

    private int score;

    private int lives;

    private static final int LIFE_COST = 100;

    private int pointsToEarnLife;

    private int currentLevel;

    private boolean waitingToRespawn;

    private int timeForEnemyToRespawn;

    private Stack<GameObject> newObjects;

    private Stack<Bomb> bombStack;

    private StringObject respawnPromptObject;
    private StringObject newLevelObject;
    private StringObject secretNewLevelObject;
    private StringObject gameOverText;

    private AttributeStringObject<Integer> newAsteroidCount;

    private AttributeStringObject<Integer> scoreDisplay;
    private AttributeStringObject<Integer> livesDisplay;
    private AttributeStringObject<Integer> levelDisplay;

    private AttributeStringObject<Integer> bombStatus;

    private int bombCooldown;

    private int maxEnemies;
    private int currentEnemies;

    private boolean levelStarting;

    private boolean spawnIt;


    public Game(PlayerController ctrl, HighScoreHandler hs){
        super(ctrl, hs);

        ///this.ctrl.noAction();

        newObjects = new Stack<>();

        //filling the stacks of predefined objects
        for (int i = 0; i < 150; i++) {
            asteroidStack.push(new Asteroid());
        }
        for (int i = 0; i < 45; i++) {
            mediumAsteroidStack.push(new MediumAsteroid());
        }
        for (int i = 0; i < 15; i++) {
            bigAsteroidStack.push(new BigAsteroid());
        }
        for (int i = 0; i < 15; i++) {
            enemyBullets.push(new EnemyBullet());
        }
        for (int i = 0; i < 5; i++) {
            EnemyShip e = new EnemyShip(this);
            enemyShips.push(e);
        }


        //player is not present in the intro, so player bullet stack and the player ship are only in here
        playerBullets = new Stack<>();
        for (int i = 0; i < 4; i++) {
            playerBullets.push(new PlayerBullet());
        }

        bombStack = new Stack<>();
        bombStack.push(new Bomb());


        ship = new PlayerShip(this.ctrl);
        gameObjects.add(ship.reviveAndReturn());

        score = 0;
        lives = 3;
        pointsToEarnLife = LIFE_COST;
        currentLevel = 0;
        waitingToRespawn = false;
        maxEnemies = 1;
        currentEnemies = 0;

        resetEnemySpawnTimer();
        maxEnemies = 1;


        hudObjects.add(respawnPromptObject = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT),new Vector2D(),"PRESS THE ANY BUTTON TO RESPAWN!",StringObject.MIDDLE_ALIGN).kill());

        hudObjects.add(newLevelObject = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT),new Vector2D(),"INCOMING!",StringObject.MIDDLE_ALIGN).kill());

        hudObjects.add(secretNewLevelObject = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT),new Vector2D(),"right thats it imma asteroid in ur p",StringObject.MIDDLE_ALIGN).kill());

        hudObjects.add(newAsteroidCount = new AttributeStringObject<>(new Vector2D(HALF_WIDTH,HALF_HEIGHT+20),new Vector2D(),"REMAINING: ",newObjects.size(),StringObject.MIDDLE_ALIGN).kill());
        hudObjects.add(gameOverText = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT), new Vector2D(), "GAME OVER!", StringObject.MIDDLE_ALIGN,StringObject.big_sans).kill());

        int eighthWidth = HALF_WIDTH/2;

        hudObjects.add(levelDisplay = new AttributeStringObject<>(new Vector2D(eighthWidth,20), new Vector2D(),"Level: ",currentLevel,StringObject.RIGHT_ALIGN));
        hudObjects.add(scoreDisplay = new AttributeStringObject<>(new Vector2D(HALF_WIDTH,20), new Vector2D(),"Score: ",score,StringObject.MIDDLE_ALIGN));
        hudObjects.add(livesDisplay = new AttributeStringObject<>(new Vector2D(3*eighthWidth,20), new Vector2D(),"Lives: ",lives,StringObject.LEFT_ALIGN));

        hudObjects.add(newAsteroidCount = new AttributeStringObject<>(new Vector2D(HALF_WIDTH,HALF_HEIGHT+20),new Vector2D(),"REMAINING: ",newObjects.size(),StringObject.MIDDLE_ALIGN).kill());


        hudObjects.add(gameOverText = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT), new Vector2D(), "GAME OVER!", StringObject.MIDDLE_ALIGN,StringObject.big_sans).kill());


        /*
        TODO:
            * Some sort of display to show bomb usability
            * Method of earning bombs (1 bomb per level, max of 3 in storage? (keeping limit of 1 active at one time ofc))
            * Exploding bombs lethal to player, bombs on timer do not interact with player
        */


    }

    public void revive(){
        super.revive();

        newObjects.clear();
        playerBullets.clear();
        bombStack.clear();


        for (int i = 0; i < 150; i++) {
            asteroidStack.push(new Asteroid());
        }
        for (int i = 0; i < 45; i++) {
            mediumAsteroidStack.push(new MediumAsteroid());
        }
        for (int i = 0; i < 15; i++) {
            bigAsteroidStack.push(new BigAsteroid());
        }
        for (int i = 0; i < 15; i++) {
            enemyBullets.push(new EnemyBullet());
        }
        for (int i = 0; i < 5; i++) {
            EnemyShip e = new EnemyShip(this);
            enemyShips.push(e);
        }
        for (int i = 0; i < 4; i++) {
            playerBullets.push(new PlayerBullet());
        }

        bombStack.push(new Bomb());

        gameObjects.add(ship.reviveAndReturn());

        score = 0;
        lives = 3;
        pointsToEarnLife = LIFE_COST;
        currentLevel = 0;
        waitingToRespawn = false;
        maxEnemies = 1;
        currentEnemies = 0;

        resetEnemySpawnTimer();
        maxEnemies = 1;

        respawnPromptObject.kill();
        newLevelObject.kill();
        secretNewLevelObject.kill();

        newAsteroidCount.kill();
        gameOverText.kill();

        hudObjects.add(levelDisplay.showValue(currentLevel));
        hudObjects.add(livesDisplay.showValue(lives));
        hudObjects.add(scoreDisplay.showValue(score));

        spawnIt = false;



    }


    private ArrayList<GameObject> setupLevel(boolean ohNoes){

        SoundManager.play(SoundManager.intimidating);
        //intimidation noises

        //levelTextObject.setText(levelText.showValue(currentLevel));
        levelDisplay.showValue(currentLevel);

        //levelTextObject.showValue(currentLevel);
        //updates the level text shown to player

        ArrayList<GameObject> newAsteroids = new ArrayList<>();
        //list of new asteroids

        ArrayList<Integer> thisLevel;
        //will store the configuration for this level

        levelStarting = true;
        if (ohNoes){
            thisLevel = GameLevels.ohHecc;
            //middleTextObject.setText("RIGHT THATS IT IMMA ASTEROID IN UR P");

            //levelTextObject.setText("Level: oh noes");
            //maximum asteroids of all types.
            //have fun.
        } else{
            thisLevel = GameLevels.getLevelConfig(currentLevel);
            //middleTextObject.setText("INCOMING");
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

        if (waitingToRespawn && ctrl.theAnyButton()) {
            //middleTextObject.setText("");
            respawnPromptObject.kill();
            waitingToRespawn = false;
            alive.add(ship.reviveAndReturn());
            ctrl.noAction();
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

            boolean isAsteroid = isAsteroid(g);

            if (!asteroidsRemaining){
                if (isAsteroid){
                    asteroidsRemaining = true;
                }
            }

            if (g.dead){
                dead.add(g);

                //if it's dead, it gets added back into the stack of stuff basically


                if (isAsteroid){
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
                } else if (g instanceof Bomb){
                    bombStack.push((Bomb)g);
                }
            } else{
                alive.add(g);

                boolean isEnemy = isEnemyObject(g);


                if(shipJustAdded && (isAsteroid || isEnemy)){
                    moveObjectAwayFromShip(g); //will move objects away from the ship if it's just spawned in
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
                        (isPlayerObject(g) ^ isPlayerObject(g2)) ||
                        (isEnemyObject(g) ^ isEnemyObject(g2))
                ) {
                    //only need to bother handing collisions if both objects are both asteroid/player/enemy-related objects
                    // (^ operator is 'xor')
                    //can't really do 'if (.class != .class)' as the superclasses kinda mess around with it,
                    //so yeah the Model class has some static methods for sorting that out
                    g.collisionHandling(g2);
                }
            }

            if (g instanceof EnemyShip){
                enemyFire((EnemyShip) g);
                //enemy ships attempt to shoot (if they're presently deadn't). bullets dealt with next update.
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
                //System.out.println("ur ded lol");
                lives--;
                playerHit = true;
                livesChanged = true;
                //will handle the respawning/not respawning once all the destruction is sorted
            } else if (isGenericLargerAsteroid(g)) {
                //spawning in the children of the destroyed asteroid
                spawnChildren((GenericLargerAsteroid) g);
            }

        }
        if (scoreChanged){

            //scoreTextObject.setText(scoreText.showValue(score));
            scoreDisplay.showValue(score);
            //scoreTextObject.showValue(score);
        }
        if (livesChanged){
            //livesTextObject.setText(livesText.showValue(lives));

            livesDisplay.showValue(lives);

            //livesTextObject.showValue(lives);
        }
        if (playerHit){
            //levelStarting = false;
            ctrl.noAction();
            //newAsteroidCount.kill();
            newLevelObject.kill();
            secretNewLevelObject.kill();
            if (lives > 0) {
                waitingToRespawn = true;
                SoundManager.play(SoundManager.ohno);
                //middleTextObject.setText("PRESS THE ANY BUTTON TO RESPAWN");
                aliveHUD.add(respawnPromptObject.revive());
            } else{
                System.out.println("GAME OVER YEAH!");
                newAsteroidCount.kill();
                //middleTextObject.setText("GAME OVER!");
                //middleTextObject.kill();
                aliveHUD.add(gameOverText.revive());
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
                if (ship.spawnBomb && !bombStack.isEmpty()){
                    //Bomb b = new Bomb(ship.bombLocation);
                    //Bomb b = bombStack.pop().revive(ship.bombLocation,ship.bombLocation);
                    alive.add(bombStack.pop().revive(ship.bombPosition,ship.bombVelocity));
                    ship.spawnBomb = false;
                }
            }
        } else{
            endThis();
        }


        if (newObjects.isEmpty()) {
            //if there are no more new objects to spawn for this level
            if (levelStarting){
                //clears the text that appears when the level is starting if this text was still being displayed
                //middleTextObject.setText("");
                //newAsteroidCount.setText("");
                newLevelObject.kill();
                secretNewLevelObject.kill();
                newAsteroidCount.kill();
                levelStarting = false;
            }
            if (!asteroidsRemaining && !gameOver) {
                //if no asteroids remain, and the player isn't currently dead, the level is done
                currentLevel++;
                //moves forward a level
                boolean secret = ctrl.action().p;
                newObjects.addAll(setupLevel(secret));
                //adds the asteroids for the next level

                if (secret){
                    aliveHUD.add(secretNewLevelObject.revive());
                } else{
                    aliveHUD.add(newLevelObject.revive());
                }
                newAsteroidCount.showValue(newObjects.size());
                aliveHUD.add(newAsteroidCount.revive());
                //revives and displays the appropriate new level text

                ship.giveImmunity();
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
                newAsteroidCount.showValue(newObjects.size());
            } else{
                spawnIt = true;
            }

        }


        for (GameObject h : hudObjects) {
            h.update();
            if (!h.dead) {
                aliveHUD.add(h);
            }
        }


        //}

        cleanLists();

        /*
        synchronized (Game.class) {
            gameObjects.clear();
            gameObjects.addAll(alive);

            gameObjects.clear();
            gameObjects.addAll(alive);

            hudObjects.clear();
            hudObjects.addAll(aliveHUD);

            alive.clear();
            aliveHUD.clear();
            dead.clear();
        } /* */

        //System.out.println("Lives: " + lives + "| Score: " + score);
    }

    @Override
    Vector2D getShipPosition(){
        if (ship.dead){
            return super.getShipPosition();
        } else{
            return ship.position;
        }
    }

    @Override
    protected void endThis() {
        if (ctrl.theAnyButton()) {
            highScores.recordHighScore(score);
            endGame = true;
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
        timeForEnemyToRespawn +=  (int)(Math.random() * 100) * (int)(Math.random() * 100);
    }

}