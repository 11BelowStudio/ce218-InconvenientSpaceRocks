package game;

import javax.swing.*;
import java.awt.*;

import static basicGame.Constants.DELAY;

public class GameFrame extends JFrame {

    public Game game;

    public View view;

    private InfoPanel gameInfo;

    public GameFrame() throws InterruptedException {
        super("blideo bame");
        game = new Game();
        view = new View(game);
        gameInfo = new InfoPanel(game);
        this.addKeyListener(game.ctrl);
        this.add(gameInfo,BorderLayout.NORTH);

        getContentPane().add(BorderLayout.CENTER,view);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();

        runGame();

    }

    private void runGame() throws InterruptedException {

        /*
        while (true){//(!game.gameOver) {
            game.update();
            //gameInfo.updateAll(game.score,game.currentLevel,game.lives);
            view.repaint();
            Thread.sleep(DELAY);
        }*/

        Timer repaintTimer = new Timer(DELAY,
                ev -> view.repaint());
        repaintTimer.start();
        int missedFrames = 0;
        while (!game.gameOver){
            long startTime = System.currentTimeMillis();
            game.update();
            gameInfo.update();
            long endTime = System.currentTimeMillis();
            long timeout = DELAY - (endTime - startTime);
            if (timeout > 0){
                Thread.sleep(timeout);
            } else{
                missedFrames++;
            }
            System.out.println(missedFrames);
        }
        //game.update();
        //view.repaint();
    }
}
