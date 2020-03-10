package game;

import java.awt.*;
import java.io.IOException;

import utilities.ImageManager;

import javax.swing.*;

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
    //public static final int DELAY = 20; //delay of 20: 50fps
    public static final double DT = DELAY / 1000.0;  // DELAY in seconds
    //public static final double DT = DELAY / 1000.0;

    static final Font sans = new Font("Comic Sans MS",  Font.PLAIN , 20);

    public static final int MODEL_SPEED = 20;
    public static final long MODEL_DURATION = 20;

    public static Image AN_TEXTURE, SPEHSS, SHIP, SPACE_ROCK, TITLE, DEFAULT_VIEW, YOU_ARE_DED, ENEMY_SHIP;//ASTEROID1, MILKYWAY1;
    static {
        try {
            //ASTEROID1 = ImageManager.loadImage("asteroid1");
            //MILKYWAY1 = ImageManager.loadImage("milkyway1");
            AN_TEXTURE = ImageManager.loadImage("anTexture");
            SPEHSS = ImageManager.loadImage("spehss2");
            SHIP = ImageManager.loadImage("shipV2");
            SPACE_ROCK = ImageManager.loadImage("inconvenientSpaceRockTM");
            TITLE = ImageManager.loadImage("titleImage");
            DEFAULT_VIEW = ImageManager.loadImage("default");
            YOU_ARE_DED = ImageManager.loadImage("urdedlmao");
            ENEMY_SHIP = ImageManager.loadImage("enemyShip");
        } catch (IOException e) { e.printStackTrace(); }
    }
}