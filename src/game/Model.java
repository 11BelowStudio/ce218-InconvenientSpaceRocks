package game;

import utilities.HighScoreHandler;
import utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static game.Constants.FRAME_HEIGHT;
import static game.Constants.FRAME_WIDTH;

public abstract class Model {


    public List<GameObject> gameObjects;

    protected List<GameObject> alive;
    protected List<GameObject> dead;


    protected List<GameObject> hudObjects;
    protected List<GameObject> aliveHUD;

    protected Stack<Asteroid> asteroidStack;
    protected Stack<MediumAsteroid> mediumAsteroidStack;
    protected Stack<BigAsteroid> bigAsteroidStack;

    protected Stack<EnemyShip> enemyShips;
    protected Stack<EnemyBullet> enemyBullets;

    protected Stack<PlayerBullet> playerBullets;

    boolean endGame;
    boolean gameOver;

    PlayerController ctrl;

    HighScoreHandler highScores;

    Model(PlayerController ctrl, HighScoreHandler hs){

        this.ctrl = ctrl;
        ctrl.noAction();

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

    public void revive(){
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
    }


    public abstract void update();

    protected void clicked(Point p){}


    Vector2D getShipPosition(){
        if (!gameObjects.isEmpty()){
            int range = gameObjects.size();
            return (gameObjects.get((int)(Math.random() * range-2)+1).position);
        }
        return new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT);
    }

    protected static boolean isPlayerObject(GameObject o){ return (o instanceof PlayerShip || o instanceof PlayerBullet || o instanceof Bomb); }

    protected static boolean isEnemyObject(GameObject o){ return (o instanceof EnemyShip || o instanceof  EnemyBullet); }

    protected static boolean isAsteroid(GameObject o){ return (o instanceof GenericAsteroid); }

    protected static boolean isGenericLargerAsteroid(GameObject o){ return (o instanceof GenericLargerAsteroid);}



    protected void spawnChildren(GenericLargerAsteroid g){
        if (g instanceof MediumAsteroid) {
            int numToSpawn = ((MediumAsteroid) g).childrenToSpawn;
            for (int i = 0; i < numToSpawn; i++) {
                if (asteroidStack.isEmpty()) {
                    break;
                }
                /*
                Asteroid newAsteroid = asteroidStack.pop();
                newAsteroid.revive(g.position);
                alive.add(newAsteroid);*/
                alive.add(asteroidStack.pop().revive(g.position));
            }
        } else if (g instanceof BigAsteroid) {
            int numToSpawn = ((BigAsteroid) g).childrenToSpawn;
            for (int i = 0; i < numToSpawn; i++) {
                if (mediumAsteroidStack.isEmpty()) {
                    break;
                }/*
                MediumAsteroid newAsteroid = mediumAsteroidStack.pop();
                newAsteroid.revive(g.position);
                alive.add(newAsteroid);*/
                alive.add(mediumAsteroidStack.pop().revive(g.position));
            }
        }
    }

    protected void enemyFire(EnemyShip e){
        if (e.fired && !enemyBullets.isEmpty()){
            EnemyBullet b = enemyBullets.pop();
            b.revive(e.bulletLocation, e.bulletVelocity);
            alive.add(b);
            SoundManager.fire();
            e.fired = false;
        }
    }

    protected void endThis(){
        endGame = true;
    }

    protected void cleanLists(){
        synchronized (Model.class){
            gameObjects.clear();
            gameObjects.addAll(alive);

            gameObjects.clear();
            gameObjects.addAll(alive);

            hudObjects.clear();
            hudObjects.addAll(aliveHUD);

            alive.clear();
            aliveHUD.clear();
            dead.clear();
        }
    }
}
