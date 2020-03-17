package GamePackage;

import GamePackage.Constants;
import GamePackage.GameObjects.GameObject;
import GamePackage.Models.Model;
import utilities.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene) (enhanced a bit by me)
import static GamePackage.Constants.*;

public class View extends JComponent {
    // background colour
    private static final Color BG_COLOR = Color.black;

    private Model model;

    private static Image gameBG, titleBG, bg;
    static {
        try {
            gameBG = ImageManager.loadImage("spehss2");
            titleBG = ImageManager.loadImage("titleBG");
            bg = ImageManager.loadImage("loading");
        } catch (IOException e) { e.printStackTrace(); }
    }

    private AffineTransform bgTransform;

    private boolean displayingModel;

    private Dimension preferredDimension;

    /* VARIABLES FOR THE UNIMPLEMENTED FULLSCREEN STUFF
    public boolean fullscreen;
    private int viewHeight;
    private int viewWidth;

    private Dimension defaultDimension;
    public static final int FRAME_HEIGHT = 600;
    public static final int FRAME_WIDTH = 800;
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);*/

    private double xScale;
    private double yScale;

    View(){

        xScale = 1;
        yScale = 1;

        setPreferredDimension(FRAME_SIZE);

        displayingModel = false;

        setupBackgroundTransformation();


    }

    void showModel(Model m, boolean isGame){
        this.model = m;
        displayingModel = true;
        swapBackground(isGame);
    }

    //would have got more use with the fullscreen support stuff
    public void setPreferredDimension(Dimension d){
        preferredDimension = d;
        xScale = (preferredDimension.width == FRAME_WIDTH? 1 :
                (double)FRAME_WIDTH/preferredDimension.width);
        yScale = (preferredDimension.height == FRAME_HEIGHT? 1 :
                (double)FRAME_HEIGHT/preferredDimension.height);
    }

    private void swapBackground(boolean gameActive){
        if (gameActive){
            bg = gameBG;
        } else{
            bg = titleBG;
        }
        setupBackgroundTransformation();
    }

    private void setupBackgroundTransformation(){
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

        g.drawImage(bg, bgTransform,null); //renders the background

        g.scale(xScale,yScale);
        AffineTransform backup = g.getTransform();
        if (displayingModel) {
            synchronized (Model.class) {
                for (GameObject o : model.gameObjects) {
                    o.draw(g);
                    //basically calls the draw method of each gameObject
                }
                for (GameObject o : model.hudObjects) {
                    o.draw(g);
                    //and then the HUD (so its displayed above the HUD)
                }
            }
        }
        g.setTransform(backup);
        g.setTransform(initialTransform);
        revalidate();
    }

    @Override
    public Dimension getPreferredSize() { return preferredDimension; }

}