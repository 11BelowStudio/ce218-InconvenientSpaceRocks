package game;

import utilities.HighScoreHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.rmi.server.ExportException;

import static basicGame.Constants.DELAY;

public class GameFrame extends JFrame {

    public Game game;

    public View view;

    private HighScoreHandler highScores;

    Timer repaintTimer;

    //private InfoPanel gameInfo;

    public GameFrame() throws InterruptedException {
        this.setTitle("blideo bame");

        highScores = new HighScoreHandler("SaveData/scores.txt",this, false);

        game = new Game();
        view = new View(game);
        //gameInfo = new InfoPanel(game);
        //this.addKeyListener(game.ctrl);
        //this.add(gameInfo,BorderLayout.NORTH);

        this.add(view,BorderLayout.CENTER);
        //this.add(title,BorderLayout.CENTER);

        //getContentPane().add(BorderLayout.CENTER,view);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
        //titleScreen();
        /*
        repaintTimer = new Timer(DELAY,
                ev -> view.repaint());
        */
        mainLoop();

    }


    private void mainLoop() throws InterruptedException{
        boolean keepGoing = true;

        while (keepGoing) {

            /* TITLE SCREEN WILL GO HERE  */
            JOptionPane.showMessageDialog(
                    this,
                    "press ok to start the blideo bame"
            );
            /* */

            game = new Game();
            //game = new Game();
            this.addKeyListener(game.ctrl);
            view.showGame(game);
            //view.setVisible(true);
            pack();
            System.out.println("* setup done *");

            repaintTimer = new Timer(DELAY,
                    ev -> view.repaint());
            repaintTimer.start();

            System.out.println("* game started *");

            runGame();

            Thread.sleep(DELAY);

            System.out.println("* game stopped *");

            highScores.recordHighScore(game.score);
            for (KeyListener k: this.getKeyListeners()) {
                this.removeKeyListener(k);
            }
            repaintTimer.stop();
            repaintTimer = null;
            //view.setVisible(false);
            view.hideGame();
            pack();

            System.out.println("* Removed stuff *");
            //repaintTimer.restart();
            //repaint();
        }
    }

    public void gameStart() throws Throwable{
        try{
            runGame();
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    private void runGame() throws InterruptedException{

        /*
        while (true){//(!game.gameOver) {
            game.update();
            //gameInfo.updateAll(game.score,game.currentLevel,game.lives);
            view.repaint();
            Thread.sleep(DELAY);
        }*/

        SoundManager.stopMenu();
        SoundManager.startGame();

        /*
        Timer repaintTimer = new Timer(DELAY,
                ev -> view.repaint());

         */
        //repaintTimer.start();
        int missedFrames = 0;
        while (!game.gameOver){
            long startTime = System.currentTimeMillis();
            game.update();
            //gameInfo.update();
            long endTime = System.currentTimeMillis();
            long timeout = DELAY - (endTime - startTime);
            if (timeout > 0){
                Thread.sleep(timeout);
            } /*else{
                missedFrames++;
            }
            System.out.println(missedFrames);*/
        }

        SoundManager.stopGame();
        SoundManager.stopThrust();
        SoundManager.startMenu();
        while (!game.reset){
            long startTime = System.currentTimeMillis();
            game.update();
            long endTime = System.currentTimeMillis();
            long timeout = DELAY - (endTime - startTime);
            if (timeout > 0){
                Thread.sleep(timeout);
            }
        }

        Thread.sleep(DELAY);

        //repaintTimer.stop();

        //game.update();
        //view.repaint();
    }
}
