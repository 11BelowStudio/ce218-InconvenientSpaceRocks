package game1;

import utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;
import static game1.Constants.DELAY;

public class BasicGame {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public List<BasicAsteroid> asteroids;
    public BasicAsteroid testAsteroid;

    BasicKeys ctrl;
    BasicShip ship;

    public BasicGame() {
        asteroids = new ArrayList<BasicAsteroid>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            //asteroids.add(BasicAsteroid.makeRandomAsteroid());
            asteroids.add(new BasicAsteroid());
        }
        ctrl = new BasicKeys();
        ship = new BasicShip(ctrl);

        //testAsteroid = new BasicAsteroid(0,0,20,50);
        //asteroids.add(testAsteroid);
    }

    public static void main(String[] args) throws Exception {
        BasicGame game = new BasicGame();
        BasicView view = new BasicView(game);
        new JEasyFrame(view, "Basic Game").addKeyListener(game.ctrl);
        // run the game
        while (true) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    public void update() {
        for (BasicAsteroid a : asteroids) {
            a.update();
        }
        ship.update();
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