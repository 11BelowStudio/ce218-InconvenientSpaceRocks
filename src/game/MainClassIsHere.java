package game;

import javax.swing.*;

public class MainClassIsHere {

    JFrame titleFrame;

    //GameFrame theGame;

    public static void main(String[] args) throws Throwable{

        try {
            GameFrame theGame = new GameFrame();
        } catch (Exception e){
            e.printStackTrace();
        }

        /*
        Game game = new Game();
        View view = new View(game);
        JEasyFrame gameFrame = new JEasyFrame(view, "Basic Game");
        gameFrame.addKeyListener(game.ctrl);
        InfoPanel gameInfo = new InfoPanel();
        gameFrame.add(gameInfo, BorderLayout.NORTH);

        // run the game
        while (!game.gameOver) {
            game.update();
            gameInfo.updateAll(game.score,game.currentLevel,game.lives);
            view.repaint();
            Thread.sleep(DELAY);
        }
        game.update();
        view.repaint();*/
    }


}
