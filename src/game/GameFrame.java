package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

import static basicGame.Constants.DELAY;

public class GameFrame extends JFrame {

    public Game game;

    public View view;

    //private InfoPanel gameInfo;

    public GameFrame() throws Throwable {
        this.setTitle("blideo bame");
        game = new Game();
        view = new View(game);
        //gameInfo = new InfoPanel(game);
        this.addKeyListener(game.ctrl);
        //this.add(gameInfo,BorderLayout.NORTH);

        this.add(view,BorderLayout.CENTER);
        //this.add(title,BorderLayout.CENTER);

        //getContentPane().add(BorderLayout.CENTER,view);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
        //titleScreen();

        while (true) {
            runGame();
            for (KeyListener k: this.getKeyListeners()) {
                this.removeKeyListener(k);
            }
            game = new Game();
            //game = new Game();
            this.addKeyListener(game.ctrl);
            view.replaceGame(game);
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


    private void runGame() throws Throwable {

        /*
        while (true){//(!game.gameOver) {
            game.update();
            //gameInfo.updateAll(game.score,game.currentLevel,game.lives);
            view.repaint();
            Thread.sleep(DELAY);
        }*/


        SoundManager.startGame();

        Timer repaintTimer = new Timer(DELAY,
                ev -> view.repaint());
        repaintTimer.start();
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
        while (!game.reset){
            long startTime = System.currentTimeMillis();
            game.update();
            long endTime = System.currentTimeMillis();
            long timeout = DELAY - (endTime - startTime);
            if (timeout > 0){
                Thread.sleep(timeout);
            }
        }
        repaintTimer.stop();

        //game.update();
        //view.repaint();
    }
}
