package GamePackage;

import GamePackage.Controllers.PlayerController;
import GamePackage.Frames.GameFrame;
import GamePackage.Models.Game;
import GamePackage.Models.Model;
import GamePackage.Models.TitleScreen;
import utilities.HighScoreHandler;
import utilities.SoundManager;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static GamePackage.Constants.DELAY;

public class GameRunner {

    private GameFrame frame;

    private Game game;
    private TitleScreen title;
    private View view;

    private Timer repaintTimer;

    private boolean paused;

    //private Dimension viewDimension; //(le unused fullscreen support has arrived)

    public GameRunner() {


        frame = new GameFrame();

        frame.addKeyListener(new EscapeListener(this));
        //EscapeListener used to allow the pause button stuff to work.

        HighScoreHandler highScores = new HighScoreHandler("SaveData/scores.txt", frame);
        PlayerController ctrl = new PlayerController();

        frame.addKeyListener(ctrl);
        frame.addMouseListener(ctrl);

        view = new View();

        frame.addView(view);
        //viewDimension = view.getPreferredSize();

        /*
         Basically, I intended to have something that would allow the game to be run in fullscreen,
         editing the dimensions of View, and then, these dimensions would be compared to the default
         dimensions (800 by 600), and would then cause the drawing stuff in view to be scaled in some way,
         maybe changing the X and Y dimensions of the actual GameObjects within Model itself,
         so everything would functionally still be the same, but I didn't get around to performing
         the necessary refactoring in time before the deadline. anywho, ramble over, back to the code.
         */


        //creating the two models
        title = new TitleScreen(ctrl, highScores);
        game = new Game(ctrl, highScores);

        //repaintTimer, used to update view every 'DELAY' milliseconds (50fps)
        repaintTimer = new Timer(DELAY,
                ev -> view.repaint());

        paused = false; //not paused

        frame.pack(); //packs the frame

    }

    private void pauseGame(){
        //sets pause to true and stops the repaintTimer if not paused already
        if (!paused){
            paused = true;
            repaintTimer.stop();
        }
    }
    private void resumeGame(){
        //unpauses the model and restarts the repaintTimer if currently paused
        if (paused){
            paused = false;
            repaintTimer.start();
        }
    }


    private void mainLoop() throws InterruptedException {
        Model currentModel; //the model which currently is active
        boolean gameActive = true;//whether or not the game is the active model.
                // true by default so it swaps to the title screen on startup

        long startTime; //when it started the current update() call
        long endTime; //when it finished the current update() call
        long timeout; //time it has to wait until it can next perform an update() call

        while (true) { //the loop for swapping and replacing the game stuff

            currentModel = modelSwapper(gameActive); //obtains the model which is to be displayed by view
            gameActive = !gameActive; //if the game was active, it now isn't (and vice versa)
            view.showModel(currentModel, gameActive); //gets the view to display the appropriate model
            frame.pack(); //repacks the frame
            repaintTimer.start(); //starts the repaintTimer


            //AND NOW THE MODEL UPDATE LOOP
            while (currentModel.keepGoing()){ //keeps updating the model until the endGame variable of it is true
                //basically updates the model once every 'DELAY' milliseconds (
                startTime = System.currentTimeMillis();
                if (!paused) {
                    //WILL ONLY UPDATE THE MODEL IF NOT PAUSED!
                    currentModel.update();
                }
                endTime = System.currentTimeMillis();
                timeout = DELAY - (endTime - startTime);
                if (timeout > 0){
                    Thread.sleep(timeout);
                }
            }
            SoundManager.stopThrust();

            repaintTimer.stop();
        }
    }

    private Model modelSwapper(boolean swapToMenu){
        if (swapToMenu){
            SoundManager.stopGame();
            SoundManager.startMenu();
            return title.revive();
            //returns a revived title screen/menu if it must swap to the menu
        } else{
            SoundManager.stopMenu();
            SoundManager.startGame();
            return game.revive();
            //returns a revived game if it must swap to the game
        }
    }


    void quitPrompt(){
        pauseGame(); //pauses the game
        if ((
                JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to quit?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION
                )
        ) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(frame, "aight bye", "closing", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
            //closes everything if the player selects 'YES'
        } else {
            //expresses disappointment at the player for pressing escape if they didn't want to quit
            JOptionPane.showMessageDialog(frame, "then why did you press escape? smh my head", "resuming", JOptionPane.PLAIN_MESSAGE);
        }
        resumeGame(); //resumes the game
    }

    //THE THING WHAT RUNS THE STUFF
    public void run() {
        try {
            mainLoop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private static class EscapeListener extends KeyAdapter {
        private GameRunner runner;
        EscapeListener(GameRunner r) {
            runner = r;
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                runner.quitPrompt();
            }
        }
    }


}
