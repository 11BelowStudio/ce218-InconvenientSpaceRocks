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

    private static final int RECT_SIZE = 400;
    static final Rectangle LEFT_SCREEN = new Rectangle(-RECT_SIZE,-RECT_SIZE, RECT_SIZE,FRAME_HEIGHT+ RECT_SIZE);
    static final Rectangle RIGHT_SCREEN = new Rectangle(FRAME_WIDTH,-RECT_SIZE, RECT_SIZE,FRAME_HEIGHT+ RECT_SIZE);
    static final Rectangle ABOVE_SCREEN = new Rectangle(-RECT_SIZE,-RECT_SIZE,FRAME_WIDTH+ RECT_SIZE, RECT_SIZE);
    static final Rectangle BELOW_SCREEN = new Rectangle(-RECT_SIZE,FRAME_HEIGHT,FRAME_WIDTH+ RECT_SIZE, RECT_SIZE);

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
