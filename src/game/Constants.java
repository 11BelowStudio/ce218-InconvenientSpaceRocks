package game;

import java.awt.*;
import java.io.IOException;

import utilities.ImageManager;

//basically contains some commonly used values and such

//should avoid using any sort of constants thing for any big projects

public class Constants {
    public static final int FRAME_HEIGHT = 600;
    public static final int FRAME_WIDTH = 800;
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    public static final int boundingRect = 200;
    public static final Rectangle leftScreen = new Rectangle(-boundingRect,-boundingRect,boundingRect,FRAME_HEIGHT+boundingRect);
    public static final Rectangle rightScreen = new Rectangle(FRAME_WIDTH,-boundingRect,boundingRect,FRAME_HEIGHT+boundingRect);
    public static final Rectangle aboveScreen = new Rectangle(-boundingRect,-boundingRect,FRAME_WIDTH+boundingRect,boundingRect);
    public static final Rectangle underScreen = new Rectangle(-boundingRect,FRAME_HEIGHT,FRAME_WIDTH+boundingRect,boundingRect);

    public static final double DRAWING_SCALE = 10;

    // sleep time between two frames
    public static final int DELAY = 20;  // time between frames in milliseconds
    //public static final int DELAY = 20; //delay of 20: 50fps
    public static final double DT = DELAY / 1000.0;  // DELAY in seconds
    //public static final double DT = DELAY / 1000.0;

    public static final int MODEL_SPEED = 20;
    public static final long MODEL_DURATION = 20;

    public static Image AN_TEXTURE, SPEHSS, SHIP;//ASTEROID1, MILKYWAY1;
    static {
        try {
            //ASTEROID1 = ImageManager.loadImage("asteroid1");
            //MILKYWAY1 = ImageManager.loadImage("milkyway1");
            AN_TEXTURE = ImageManager.loadImage("anTexture");
            SPEHSS = ImageManager.loadImage("spehss");
            SHIP = ImageManager.loadImage("shipV1");
        } catch (IOException e) { e.printStackTrace(); }
    }
}