package game;

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


        alive = new ArrayList<>();
        //list for objects that are still alive

        dead = new ArrayList<>();
        //list for objects that are now dead
    }


    public int getScore(){
        return -1;
    }

    public abstract void update();
}
