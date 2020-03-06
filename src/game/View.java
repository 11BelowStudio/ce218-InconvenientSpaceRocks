package game;

import utilities.AttributeString;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;


import static game.Constants.*;

public class View extends JComponent {
    // background colour
    public static final Color BG_COLOR = Color.black;

    private Game game;

    private Image bg;

    private final Image gameBG = SPEHSS;

    private final Image defaultBG = DEFAULT_VIEW;

    private Image urDed;

    private AffineTransform urDedTransform;

    //private final Image defaultBG = TITLE;

    private AffineTransform bgTransform;

    private AttributeString<Integer> scoreString;
    private AttributeString<Integer> levelString;
    private AttributeString<Integer> livesString;

    public boolean displayingGame;

    //Rectangle background;
    //int tempx,tempy;

    public View(Game game) {

        this.game = game;
        //gameInfo = new InfoPanel(game);
        //gameInfo = new InfoPanel();
        //this.add(gameInfo,BorderLayout.NORTH);

        urDed = YOU_ARE_DED;

        urDedTransform = new AffineTransform(1,0,0,2,HALF_WIDTH,HALF_HEIGHT);



        hideGame();
        /*

        bg = defaultBG;

        displayingGame = false;


        double imWidth = bg.getWidth(null);
        double imHeight = bg.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 :
                Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 :
                Constants.FRAME_HEIGHT/imHeight);
        bgTransform = new AffineTransform();
        bgTransform.scale(stretchx, stretchy);
        */


        scoreString = new AttributeString<>("Score: ",0);
        levelString = new AttributeString<>("Level: ",0);
        livesString = new AttributeString<>("Lives: ",0);


        //tempx = 0;
        //tempy = 0;
        //background = new Rectangle(tempx, tempy, (int)stretchx, (int)stretchy);


    }

    public void showGame(Game game){
        replaceBackground(true);
        this.game = game;
        scoreString.showValue(0);
        levelString.showValue(0);
        livesString.showValue(0);
    }

    public void hideGame(){
        replaceBackground(false);
    }

    private void replaceBackground(boolean gameActive){
        displayingGame = gameActive;
        if (displayingGame){
            bg = gameBG;
        } else{
            bg = defaultBG;
        }
        double imWidth = bg.getWidth(null);
        double imHeight = bg.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 :
                Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 :
                Constants.FRAME_HEIGHT/imHeight);
        bgTransform = new AffineTransform();
        bgTransform.scale(stretchx, stretchy);






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
        if (displayingGame) {
            g.setColor(Color.white);
            int eighthWidth = getWidth() / 8;

            //g.fillRect(-1000,-1000,2000,2000);

            AffineTransform backup = g.getTransform();


            synchronized (Game.class) {


                for (GameObject o : game.gameObjects) {
                    o.draw(g);
                    //o.drawBoundingRect(g);
                    //basically calls the draw method of each gameObject
                }
                //}
            }

            g.setTransform(backup);
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
            updateAll(game.score, game.currentLevel, game.lives);
            //int eighthWidth = getWidth()/8;
            g.drawString(scoreString.toString(), eighthWidth, 10);
            g.drawString(levelString.toString(), (int) (3.5 * eighthWidth), 10);
            g.drawString(livesString.toString(), 6 * eighthWidth, 10);

            //gameInfo.updateAll(game.score,game.currentLevel,game.lives);

            if (game.gameOver) {
                g.drawString("GAME OVER YEAH", (int) (3.5 * eighthWidth), getHeight() / 2);
                g.drawImage(urDed,urDedTransform,null);
            } else if (game.waitingToRespawn) {
                g.drawString("press any key to respawn", (int) (3 * eighthWidth), getHeight() / 2);
            }
            g.setTransform(backup);
        }
        revalidate();
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}