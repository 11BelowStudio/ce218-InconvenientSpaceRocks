package game2;

import javax.swing.*;
import java.awt.*;

import static game1.Constants.DELAY;

public class GameFrame extends JFrame {

    public Game game;

    public View view;

    public GameFrame() throws InterruptedException {
        super("blideo bame");
        game = new Game();
        view = new View(game);
        this.addKeyListener(game.ctrl);

        getContentPane().add(BorderLayout.CENTER,view);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();

        runGame();

    }

    private void runGame() throws InterruptedException {
        while (true){//(!game.gameOver) {
            game.update();
            //gameInfo.updateAll(game.score,game.currentLevel,game.lives);
            view.repaint();
            Thread.sleep(DELAY);
        }
        //game.update();
        //view.repaint();
    }
}
