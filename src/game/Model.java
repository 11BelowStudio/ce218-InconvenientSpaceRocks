package game;

import utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class Model {


    public List<GameObject> gameObjects;
    public List<GameObject> hudObjects;


    protected List<GameObject> alive;
    protected List<GameObject> dead;

    protected Stack<Asteroid> asteroidStack;
    protected Stack<MediumAsteroid> mediumAsteroidStack;
    protected Stack<BigAsteroid> bigAsteroidStack;

    protected Stack<EnemyShip> enemyShips;
    protected Stack<EnemyBullet> enemyBullets;

    boolean endGame;
    boolean gameOver;

    PlayerController ctrl;

    Model(PlayerController ctrl){
        this.ctrl = ctrl;
        ctrl.stopAll();
        gameObjects = new ArrayList<>();
        hudObjects = new ArrayList<>();

        asteroidStack = new Stack<>();
        mediumAsteroidStack = new Stack<>();
        bigAsteroidStack = new Stack<>();

        enemyShips = new Stack<>();

        enemyBullets = new Stack<>();




        alive = new ArrayList<>();
        //list for objects that are still alive

        dead = new ArrayList<>();
        //list for objects that are now dead
    }


    public int getScore(){
        return -1;
    }

    public abstract void update();

    public boolean clicked(Point p){return false;}


    abstract Vector2D getShipPosition();

    protected static boolean isPlayer(GameObject o){
        return (o instanceof PlayerShip || o instanceof PlayerBullet || o instanceof Bomb);
    }

    protected static boolean isEnemy(GameObject o){
        return (o instanceof EnemyShip || o instanceof  EnemyBullet);
    }

    protected static boolean isAsteroid(GameObject o){
        return (o instanceof GenericAsteroid);
    }

    protected static boolean isGenericLargerAsteroid(GameObject o){ return (o instanceof GenericLargerAsteroid);}



    protected void spawnChildren(GenericLargerAsteroid g){
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
