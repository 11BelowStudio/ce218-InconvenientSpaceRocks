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

    private TitleScreen title;

    private Image bg;

    private final Image gameBG = SPEHSS;

    private final Image defaultBG = DEFAULT_VIEW;

    //private Image urDed;

    //private AffineTransform urDedTransform;

    //private final Image defaultBG = TITLE;

    private AffineTransform bgTransform;

    public boolean displayingGame;

    //public boolean fullscreen;

    int viewHeight;
    int viewWidth;

    public Dimension defaultDimension;
    public Dimension preferredDimension;

    //Rectangle background;
    //int tempx,tempy;

    public static final int FRAME_HEIGHT = 600;
    public static final int FRAME_WIDTH = 800;
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    double xScale;
    double yScale;

    public View() {

        //fullscreen = false;
        viewHeight = 600;
        viewWidth = 800;

        xScale = 1;
        yScale = 1;

        defaultDimension = FRAME_SIZE;

        preferredDimension = new Dimension(800, 600);

        //this.game = game;
        //gameInfo = new InfoPanel(game);
        //gameInfo = new InfoPanel();
        //this.add(gameInfo,BorderLayout.NORTH);

        //urDed = YOU_ARE_DED;

        //urDedTransform = new AffineTransform(1,0,0,1,HALF_WIDTH-128,HALF_HEIGHT-32);



        //hideGame();
        replaceBackground(false);



        //tempx = 0;
        //tempy = 0;
        //background = new Rectangle(tempx, tempy, (int)stretchx, (int)stretchy);


    }

    public void showGame(Game game){
        this.game = game;
        replaceBackground(true);
        displayingGame = true;
    }

    public void showTitle(TitleScreen t){
        this.title = t;
        displayingGame = false;
    }

    public void setPreferredDimension(Dimension d){
        preferredDimension = d;
        xScale = (preferredDimension.width == FRAME_WIDTH? 1 :
                (double)FRAME_WIDTH/preferredDimension.width);
        yScale = (preferredDimension.height == FRAME_HEIGHT? 1 :
                (double)FRAME_HEIGHT/preferredDimension.height);
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
        double stretchX = (imWidth > Constants.FRAME_WIDTH? 1 :
                Constants.FRAME_WIDTH/imWidth);
        double stretchY = (imHeight > Constants.FRAME_HEIGHT? 1 :
                Constants.FRAME_HEIGHT/imHeight);
        bgTransform = new AffineTransform();
        bgTransform.scale(stretchX, stretchY);

    }

    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        AffineTransform initialTransform = g.getTransform();

        g.setColor(BG_COLOR);
        g.fill(this.getBounds());
        g.drawImage(bg, bgTransform,null);

        g.scale(xScale,yScale);
        AffineTransform backup = g.getTransform();
        if (displayingGame) {
            synchronized (Game.class) {
                for (GameObject o : game.gameObjects) {
                    o.draw(g);
                    //basically calls the draw method of each gameObject
                }
                for (GameObject o: game.hudObjects){
                    o.draw(g);
                    //and also the HUD
                }
            }
        } else{
            synchronized (TitleScreen.class){
                for (GameObject o : title.gameObjects) {
                    o.draw(g);
                    //basically calls the draw method of each gameObject
                }
                for (GameObject o: title.hudObjects){
                    o.draw(g);
                    //and also the HUD
                }
            }
        }
        g.setTransform(backup);
        g.setTransform(initialTransform);
        revalidate();
    }

    @Override
    public Dimension getPreferredSize() {
        return preferredDimension;
    }

}