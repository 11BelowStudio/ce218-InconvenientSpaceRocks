package game2;

import utilities.AttributeString;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class View extends JComponent {
    // background colour
    public static final Color BG_COLOR = Color.black;

    private Game game;

    private InfoPanel gameInfo;

    AttributeString<Integer> scoreString;
    AttributeString<Integer> levelString;
    AttributeString<Integer> livesString;

    public View(Game game) {

        this.game = game;
        //gameInfo = new InfoPanel(game);
        //gameInfo = new InfoPanel();
        //this.add(gameInfo,BorderLayout.NORTH);


        scoreString = new AttributeString<>("Score: ",0);
        levelString = new AttributeString<>("Level: ",0);
        livesString = new AttributeString<>("Lives: ",0);
    }


    void updateScore(int newScore){
        scoreString.showValue(newScore);
    }

    void updateLevel(int newLevel){
        levelString.showValue(newLevel);
    }

    void updateLives(int newLives){
        livesString.showValue(newLives);
    }

    void updateAll(int newScore, int newLevel, int newLives){
        updateScore(newScore);
        updateLevel(newLevel);
        updateLives(newLives);
    }


    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        // paint the background
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        //Now, in order to actually see the asteroids you'll need to add a for-each loop to the paintComponent method of BasicView that draws all the asteroids on the screen.

        synchronized (Game.class) {
            for (GameObject o : game.gameObjects) {
                o.draw(g);
                //basically calls the draw method of each gameObject
            }
        }
        AffineTransform backup = g.getTransform();
        //game.ship.draw(g);
        g.setColor(Color.white);
        /*
        gameInfo.updateAll(game.score,game.currentLevel,game.lives);
        gameInfo.paint(g);
        g.drawString(gameInfo.scoreLabel.getText(),(getWidth()/8),10);
        g.drawString(gameInfo.levelLabel.getText(),3*(getWidth()/8),10);
        g.drawString(gameInfo.livesLabel.getText(), 6*(getWidth()/8),10);
         */
        int eighthWidth = getWidth()/8;;
        //g.scale(2,2);
        updateAll(game.score,game.currentLevel,game.lives);
        g.drawString(scoreString.toString(),eighthWidth,10);
        g.drawString(levelString.toString(),(int)(3.5*eighthWidth),10);
        g.drawString(livesString.toString(), 6*eighthWidth,10);

        if(game.gameOver){
            g.drawString("GAME OVER YEAH",(int)(3.5*eighthWidth),getHeight()/2);
        } else if (game.waitingToRespawn){
            g.drawString("press any key to respawn",(int)(3*eighthWidth),getHeight()/2);
        }
        g.setTransform(backup);
        revalidate();
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}