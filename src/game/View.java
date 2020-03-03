package game;

import utilities.AttributeString;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static game.Constants.SPEHSS;

public class View extends JComponent {
    // background colour
    public static final Color BG_COLOR = Color.black;

    private Game game;

    private InfoPanel gameInfo;

    private Image bg;

    AffineTransform bgTransform;

    AttributeString<Integer> scoreString;
    AttributeString<Integer> levelString;
    AttributeString<Integer> livesString;

    //Rectangle background;
    //int tempx,tempy;

    public View(Game game) {

        this.game = game;
        //gameInfo = new InfoPanel(game);
        //gameInfo = new InfoPanel();
        //this.add(gameInfo,BorderLayout.NORTH);

        bg = SPEHSS;

        double imWidth = bg.getWidth(null);
        double imHeight = bg.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 :
                Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 :
                Constants.FRAME_HEIGHT/imHeight);
        bgTransform = new AffineTransform();
        bgTransform.scale(stretchx, stretchy);


        scoreString = new AttributeString<>("Score: ",0);
        levelString = new AttributeString<>("Level: ",0);
        livesString = new AttributeString<>("Lives: ",0);


        //tempx = 0;
        //tempy = 0;
        //background = new Rectangle(tempx, tempy, (int)stretchx, (int)stretchy);


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

        //AffineTransform backup1 = g.getTransform();

        //g.translate(tempx--,tempy--);


        // paint the background
        g.setColor(BG_COLOR);
        g.fill(this.getBounds());
        //Rectangle background = new Rectangle(0, 0, getWidth(), getHeight());
        //g.setPaint(new TexturePaint(spehss,background));
        //g.fill(background);
        //bgTransform.translate(-1,-1);
        g.drawImage(bg, bgTransform,null);
        //g.setTransform(backup1);
        //g.setTransform(bgTransform);
        g.setColor(Color.white);
        int eighthWidth = getWidth()/8;
        /*
        g.drawString(scoreString.toString(),eighthWidth,10);
        g.drawString(levelString.toString(),(int)(3.5*eighthWidth),10);
        g.drawString(livesString.toString(), 6*eighthWidth,10);
        */
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
        g.setColor(Color.white);
        g.drawString(gameInfo.scoreLabel.getText(),(getWidth()/8),10);
        g.drawString(gameInfo.levelLabel.getText(),3*(getWidth()/8),10);
        g.drawString(gameInfo.livesLabel.getText(), 6*(getWidth()/8),10);
         */

        //g.scale(2,2);
        updateAll(game.score,game.currentLevel,game.lives);
        //int eighthWidth = getWidth()/8;
        g.drawString(scoreString.toString(),eighthWidth,10);
        g.drawString(levelString.toString(),(int)(3.5*eighthWidth),10);
        g.drawString(livesString.toString(), 6*eighthWidth,10);

        //gameInfo.updateAll(game.score,game.currentLevel,game.lives);

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