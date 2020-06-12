package GamePackage.Models;

import GamePackage.Controllers.PlayerController;
import GamePackage.GameObjects.*;
import utilities.HighScoreHandler;
import utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static GamePackage.Models.ModelConstants.FRAME_HEIGHT;
import static GamePackage.Models.ModelConstants.FRAME_WIDTH;

//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene)

public abstract class Model {


    List<GameObject> gameObjects;

    List<GameObject> alive;
    List<GameObject> dead;


    List<GameObject> hudObjects;

    List<GameObject> aliveHUD;

    Stack<Asteroid> asteroidStack;
    Stack<MediumAsteroid> mediumAsteroidStack;
    Stack<BigAsteroid> bigAsteroidStack;

    Stack<EnemyShip> enemyShips;
    Stack<EnemyBullet> enemyBullets;

    Stack<PlayerBullet> playerBullets;

    boolean endGame;
    boolean gameOver;

    PlayerController ctrl;

    HighScoreHandler highScores;

    Model(PlayerController ctrl, HighScoreHandler hs){

        this.ctrl = ctrl;
        this.ctrl.noAction();

        this.highScores = hs;

        gameObjects = new ArrayList<>();
        hudObjects = new ArrayList<>();

        alive = new ArrayList<>();
        //list for objects that are still alive
        dead = new ArrayList<>();
        //list for objects that are now dead
        aliveHUD = new ArrayList<>();
        //list for HUD objects that are now dead

        asteroidStack = new Stack<>();
        mediumAsteroidStack = new Stack<>();
        bigAsteroidStack = new Stack<>();

        enemyShips = new Stack<>();
        enemyBullets = new Stack<>();

        playerBullets = new Stack<>();


        endGame = false;
        gameOver = false;
    }

    public Model revive(){
        ctrl.noAction();
        gameObjects.clear();
        hudObjects.clear();

        asteroidStack.clear();
        mediumAsteroidStack.clear();

        enemyShips.clear();
        enemyBullets.clear();

        playerBullets.clear();

        alive.clear();
        dead.clear();
        aliveHUD.clear();

        endGame = false;
        gameOver = false;
        return this;
    }


    public abstract void update();

    //le collision handling has arrived
    void collisionHandler(){
        int currentObjectType; //declares local variable
        for (int i = 0; i < alive.size(); i++) { //iterates through the alive list
            GameObject g = alive.get(i); //obtains the current alive object
            if (!g.cantTouchThis()) { //if it's not dead/intangible, it goes to the next step
                currentObjectType = g.getObjectType(); //records its object type
                for (int j = i + 1; j < alive.size(); j++) { //iterates through the remainder of alive
                    GameObject g2 = alive.get(j); //obtains next current alive object
                    if (!g2.cantTouchThis() && currentObjectType != g2.getObjectType()) { //if it's not intangible, and a different type to the other one
                        g.collisionHandling(g2); //collision handling happens
                    }
                }
            }
        }
    }

    void clicked(Point p){}


    public Vector2D getShipPosition(){
        //default version either returns a random vector, or the position of a random other gameObject
        if (!gameObjects.isEmpty()){
            int range = gameObjects.size();
            return (gameObjects.get((int)(Math.random() * range-2)+1).getPosition());
        }
        return Vector2D.getRandomVector(FRAME_WIDTH,FRAME_HEIGHT);
    }

    //static boolean isPlayerObject(GameObject o){ return (o instanceof PlayerShip || o instanceof PlayerBullet); }

    //static boolean isEnemyObject(GameObject o){ return (o instanceof EnemyShip || o instanceof  EnemyBullet); }

    static boolean isAsteroid(GameObject o){ return (o instanceof GenericAsteroid); }

    static boolean isGenericLargerAsteroid(GameObject o){ return (o instanceof GenericLargerAsteroid);}



    void spawnChildren(GenericLargerAsteroid g){
        int numToSpawn = g.getChildrenToSpawn();
        if (g instanceof MediumAsteroid) {
            for (int i = 0; i < numToSpawn; i++) {
                if (asteroidStack.isEmpty()) {
                    break;
                }
                alive.add(g.reviveChild(asteroidStack.pop()));
            }
        } else if (g instanceof BigAsteroid) {
            for (int i = 0; i < numToSpawn; i++) {
                if (mediumAsteroidStack.isEmpty()) {
                    break;
                }
                alive.add(g.reviveChild(mediumAsteroidStack.pop()));
            }
        }
    }

    void enemyFire(EnemyShip e){
        if (e.hasFired() && !enemyBullets.isEmpty()){
            alive.add(e.setBullet(enemyBullets.pop()));
        }
    }

    void endThis(){ endGame = true; }



    void cleanLists(){
        synchronized (Model.class){

            //yeets gameObjects, then adds all alive objects to it
            gameObjects.clear();
            gameObjects.addAll(alive);

            //yeets hudObjects, then adds all alive HUD objects to it
            hudObjects.clear();
            hudObjects.addAll(aliveHUD);

            //yeets the temporary alive/dead lists
            alive.clear();
            aliveHUD.clear();
            dead.clear();
        }
    }

    public boolean keepGoing(){ return !endGame; }


    public void draw(Graphics2D g){
        synchronized (Model.class) {
            for (GameObject o : gameObjects) {
                o.draw(g);
                //basically calls the draw method of each gameObject
            }
            for (GameObject o : hudObjects) {
                o.draw(g);
                //and then the HUD (so its displayed above the HUD)
            }
        }
    }
}
