package GamePackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import utilities.ImageManager;
import utilities.Vector2D;

//basically contains some commonly used values and such

//should avoid using any sort of constants thing for any big projects

public class Constants {

    /*
    public static final int WORLD_HEIGHT = 1200;
    public static final int WORLD_WIDTH = 1200;
    public static final int HALF_WORLD_HEIGHT = WORLD_HEIGHT/2;
    public static final int HALF_WORLD_WIDTH = WORLD_WIDTH/2;*/


    public static final int FRAME_HEIGHT = 600;
    public static final int FRAME_WIDTH = 800;
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    public static final int HALF_HEIGHT = (FRAME_HEIGHT/2);
    public static final int HALF_WIDTH =  (FRAME_WIDTH/2);

    public static final Vector2D MIDDLE_VECTOR = new Vector2D(HALF_WIDTH,HALF_HEIGHT);

    private static final int boundingRect = 400; //300

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


    public static Image SPEHSS, TITLE, LOADING;
    static {
        try {
            SPEHSS = ImageManager.loadImage("spehss2");
            TITLE = ImageManager.loadImage("titleBG");
            LOADING = ImageManager.loadImage("loading");
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static BufferedImage SHIP, SPACE_ROCK, ENEMY_SHIP;
    static {
        try {
            SHIP = ImageManager.loadBufferedImage("shipV2");
            SPACE_ROCK = ImageManager.loadBufferedImage("inconvenientSpaceRockTM");
            ENEMY_SHIP = ImageManager.loadBufferedImage("enemyShip");
        } catch (IOException e) { e.printStackTrace(); }
    }
}