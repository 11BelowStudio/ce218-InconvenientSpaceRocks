package MainPackage;

import utilities.HighScoreHandler;

import javax.swing.*;

import java.awt.*;

import static MainPackage.Constants.DELAY;

public class GameRunner {

    private GameFrame frame;

    private EscapeListener esc;

    private Game game;
    private TitleScreen title;
    private View view;

    private Timer repaintTimer;

    private boolean paused;

    //private Dimension viewDimension;

    private boolean gameActive;

    private Model currentModel;

    GameRunner() {


        frame = new GameFrame();


        esc = new EscapeListener(this);
        frame.addKeyListener(esc);

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

        title = new TitleScreen(ctrl, highScores);
        game = new Game(ctrl, highScores);

        repaintTimer = new Timer(DELAY,
                ev -> view.repaint());
        paused = false;

        frame.pack();
    }

    public void pauseGame(){
        if (!paused){
            paused = true;
            repaintTimer.stop();
        }
    }
    public void unPause(){
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
            while (!currentModel.endGame){ //keeps updating the model until the endGame variable of it is true
                //basically updates the model once every 'DELAY' milliseconds (
                startTime = System.currentTimeMillis();
                if (!paused) {
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
        } else{
            SoundManager.stopMenu();
            SoundManager.startGame();
            return game.revive();
        }
    }


    public void quitPrompt(){
        pauseGame();
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
        } else {
            JOptionPane.showMessageDialog(frame, "then why did you press escape? smh my head", "resuming", JOptionPane.PLAIN_MESSAGE);
        }
        unPause();
    }

    public void run() {
        try {
            mainLoop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
