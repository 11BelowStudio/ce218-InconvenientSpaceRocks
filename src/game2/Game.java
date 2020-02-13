package game2;

import utilities.JEasyFrame;
import utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static game1.Constants.DELAY;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public List<GameObject> gameObjects;

    private GameLevels levelConfigs;

    Keys ctrl;
    Ship ship;

    int score;

    int lives;

    private final int LIFE_COST = 50;

    int pointsToEarnLife;

    int currentLevel;

    boolean waitingToRespawn;

    boolean gameOver;

    public Game() {
        gameObjects = new ArrayList<>();
        levelConfigs = new GameLevels();
        ctrl = new Keys();
        ship = new Ship(ctrl,this);
        gameObjects.add(ship);

        score = 0;

        lives = 3;

        pointsToEarnLife = LIFE_COST;

        currentLevel = 0;

        waitingToRespawn = false;
        gameOver = false;


        //setupLevel();

        //gameObjects.add(new Bullet(new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2),Vector2D.polar(Math.toRadians(270),0)));

        //testAsteroid = new BasicAsteroid(0,0,20,50);
        //asteroids.add(testAsteroid);
    }

    public ArrayList<GameObject> setupLevel(){

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

        for (GameObject g: newObjects){
            if (g.position.dist(ship.position) <= 100){
                g.position.add(200,200);
                //nothing will spawn too close to the ship now
            }
        }

        return newObjects;

    }

    public void update() {
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
            if (g instanceof GenericAsteroid){
                asteroidsRemaining = true;
            }
            g.update();
        }

        //doing collision handling and dealing with the results of the collision handling
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject temp = gameObjects.get(i);
            if (!temp.intangible || !temp.dead) { //only need to bother checking the collision if this isn't intangible/dead
                boolean isAsteroid = (temp instanceof GenericAsteroid);
                boolean isBullet = (temp instanceof Bullet);
                boolean isShip = (temp instanceof Ship);
                //working out what type of object temp is
                for (int j = i; j < gameObjects.size(); j++) {
                    GameObject temp2 = gameObjects.get(j);
                    if (!temp2.intangible || !temp2.dead){ //again, only need to bother if this also isn't intangible/dead
                        if ((isAsteroid ^ temp2 instanceof GenericAsteroid) ||
                                (isBullet ^ (temp2 instanceof Bullet || temp2 instanceof Ship)) ||
                                (isShip ^ (temp2 instanceof Ship || temp2 instanceof Bullet))
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

        boolean playerHit = false;

        for (GameObject g: dead){
            //working out if something is dead because the player hit it or not
            if (g.wasHit){
                //player earns points if they hit the thing which is now dead
                score += g.pointValue;
                pointsToEarnLife -= g.pointValue;
                if (pointsToEarnLife <= 0){
                    lives++;
                    pointsToEarnLife += LIFE_COST;
                }
                ship.giveImmunity();
                //also given some temporary immunity for free,
                // so the player isn't immediately going to die if they shot an asteroid right in front of them
            }
            if (g instanceof Ship){
                //if the ship is here, the player is mcfricking dead
                System.out.println("ur ded lol");
                lives--;
                playerHit = true;

            }
        }
        if (playerHit){
            if (lives > 0) {
                waitingToRespawn = true;
            } else{
                System.out.println("GAME OVER YEAH!");
                gameOver = true;
            }
        }

        if (waitingToRespawn && ctrl.action.theAnyButton()) {
            waitingToRespawn = false;
            alive.add(ship = new Ship(ctrl, this, ship.direction));
            //creates a new ship if the player can respawn and the player presses any button
        }

        if (!asteroidsRemaining){
            //if no asteroids remain alive, the level is done
            currentLevel++;
            //moves forward a level
            alive.addAll(setupLevel());
            //adds the asteroids for the next level
            ship.giveImmunity();
            //gives the player some temporary immunity,
            //so they have some time to react to the new obstacles
        }

        synchronized (Game.class) {
            gameObjects.clear();
            gameObjects.addAll(alive);
        }
        //ship.update();

        //System.out.println("Lives: " + lives + "| Score: " + score);
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