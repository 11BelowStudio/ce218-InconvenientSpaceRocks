package MainPackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import utilities.ImageManager;

//basically contains some commonly used values and such

//should avoid using any sort of constants thing for any big projects

public class Constants {

    public static final int WORLD_HEIGHT = 1200;
    public static final int WORLD_WIDTH = 1200;

    public static final int HALF_WORLD_HEIGHT = WORLD_HEIGHT/2;
    public static final int HALF_WORLD_WIDTH = WORLD_WIDTH/2;


    public static final int FRAME_HEIGHT = 600;
    public static final int FRAME_WIDTH = 800;
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);


    public static final int HALF_HEIGHT = 300; //(FRAME_HEIGHT/2);
    public static final int HALF_WIDTH =  400; //(FRAME_WIDTH/2);

    public static final int boundingRect = 400; //300

    public static final Rectangle leftScreen = new Rectangle(-boundingRect,-boundingRect,boundingRect,FRAME_HEIGHT+boundingRect);
    //public static final Rectangle leftScreen = new Rectangle(-HALF_WIDTH-boundingRect,-HALF_HEIGHT-boundingRect, boundingRect,FRAME_HEIGHT+boundingRect);

    public static final Rectangle rightScreen = new Rectangle(FRAME_WIDTH,-boundingRect,boundingRect,FRAME_HEIGHT+boundingRect);
    //public static final Rectangle rightScreen = new Rectangle(HALF_WIDTH,-HALF_HEIGHT,boundingRect,FRAME_HEIGHT+boundingRect);

    public static final Rectangle aboveScreen = new Rectangle(-boundingRect,-boundingRect,FRAME_WIDTH+boundingRect,boundingRect);
    //public static final Rectangle aboveScreen = new Rectangle(-HALF_WIDTH,-HALF_HEIGHT-boundingRect,FRAME_WIDTH+boundingRect,boundingRect);

    public static final Rectangle underScreen = new Rectangle(-boundingRect,FRAME_HEIGHT,FRAME_WIDTH+boundingRect,boundingRect);
    //public static final Rectangle underScreen = new Rectangle(-HALF_WIDTH-boundingRect,HALF_HEIGHT,FRAME_WIDTH+boundingRect,boundingRect);

    public static final double DRAWING_SCALE = 10;

    // sleep time between two frames
    public static final int DELAY = 20;  // time between frames in milliseconds
    public static final double DT = DELAY / 1000.0;  // DELAY in seconds


    public static Image SPEHSS, DEFAULT_VIEW;
    static {
        try {
            SPEHSS = ImageManager.loadImage("spehss2");
            DEFAULT_VIEW = ImageManager.loadImage("titleBG");
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static BufferedImage AN_TEXTURE, SHIP, SPACE_ROCK, ENEMY_SHIP;//ASTEROID1, MILKYWAY1;
    static {
        try {
            AN_TEXTURE = ImageManager.loadBufferedImage("anTexture");
            SHIP = ImageManager.loadBufferedImage("shipV2");
            SPACE_ROCK = ImageManager.loadBufferedImage("inconvenientSpaceRockTM");
            ENEMY_SHIP = ImageManager.loadBufferedImage("enemyShip");
        } catch (IOException e) { e.printStackTrace(); }
    }
}