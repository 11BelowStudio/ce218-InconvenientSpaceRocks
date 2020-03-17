package GamePackage.GameObjects;

import GamePackage.Constants;
import utilities.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class GameObjectConstants {


    static final int FRAME_HEIGHT = Constants.FRAME_HEIGHT;
    static final int FRAME_WIDTH = Constants.FRAME_WIDTH;
    static final int HALF_HEIGHT = Constants.HALF_HEIGHT;
    static final int HALF_WIDTH = Constants.HALF_WIDTH;

    private static final int boundingRect = 400;
    static final Rectangle leftScreen = new Rectangle(-boundingRect,-boundingRect,boundingRect,FRAME_HEIGHT+boundingRect);
    static final Rectangle rightScreen = new Rectangle(FRAME_WIDTH,-boundingRect,boundingRect,FRAME_HEIGHT+boundingRect);
    static final Rectangle aboveScreen = new Rectangle(-boundingRect,-boundingRect,FRAME_WIDTH+boundingRect,boundingRect);
    static final Rectangle underScreen = new Rectangle(-boundingRect,FRAME_HEIGHT,FRAME_WIDTH+boundingRect,boundingRect);

    static final double DRAWING_SCALE = 10;

    static BufferedImage SHIP, SPACE_ROCK, ENEMY_SHIP;
    static {
        try {
            SHIP = ImageManager.loadBufferedImage("shipV2");
            SPACE_ROCK = ImageManager.loadBufferedImage("inconvenientSpaceRockTM");
            ENEMY_SHIP = ImageManager.loadBufferedImage("enemyShip");
        } catch (IOException e) { e.printStackTrace(); }
    }
    static final double DT = Constants.DT;







}
