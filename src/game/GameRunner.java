package game;

import utilities.HighScoreHandler;

import javax.swing.*;

import java.awt.*;

import static game.Constants.DELAY;

public class GameRunner {

    GameFrame frame;

    EscapeListener esc;

    Game game;
    View view;
    HighScoreHandler highScores;
    PlayerController ctrl;

    Timer repaintTimer;

    boolean paused;

    Dimension viewDimension;

    GameRunner(GameFrame f) {
        frame = f;
        esc = new EscapeListener(this);
        frame.addKeyListener(esc);
        frame.pack();

        highScores = new HighScoreHandler("SaveData/scores.txt",frame, false);
        ctrl = new PlayerController();
        frame.addKeyListener(ctrl);

        view = new View();
        //frame.add(view, BorderLayout.CENTER);
        frame.addView(view);

        viewDimension = view.getPreferredSize();

        game = new Game(ctrl, viewDimension);

        repaintTimer = new Timer(DELAY,
                ev -> view.repaint());
        paused = false;
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

        JOptionPane.showMessageDialog(
                frame,
                "press ok to start the blideo bame"
        );

        while (true) {

            /* TITLE SCREEN WILL GO HERE  */
            JOptionPane.showMessageDialog(
                    frame,
                    "press ok to start the blideo bame"
            );
            /* */

            game = new Game(ctrl, viewDimension);
            //game = new Game();
            //this.addKeyListener(game.ctrl);
            view.showGame(game);
            //view.setVisible(true);
            frame.pack();
            System.out.println("* setup done *");


            repaintTimer.start();

            System.out.println("* game started *");

            runGame();

            //Thread.sleep(DELAY);

            System.out.println("* game stopped *");

            highScores.recordHighScore(game.score);

            repaintTimer.stop();
            //repaintTimer = null;
            //view.setVisible(false);
            view.hideGame();
            frame.pack();

            System.out.println("* Removed stuff *");
            //repaintTimer.restart();
            //repaint();
        }
    }

    public void gameStart() {
        try{
            runGame();
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    private void runGame() throws InterruptedException {
        int missedFrames = 0;
        while (!game.endGame){
            long startTime = System.currentTimeMillis();
            if (!paused) {
                game.update();
            }
            long endTime = System.currentTimeMillis();
            long timeout = DELAY - (endTime - startTime);
            if (timeout > 0){
                Thread.sleep(timeout);
            }/* else{
                missedFrames++;
            }
            System.out.println(missedFrames);
            /* */
        }

        //Thread.sleep(DELAY);

        //repaintTimer.stop();

        //game.update();
        //view.repaint();
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
