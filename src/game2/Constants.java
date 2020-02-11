package game2;

import java.awt.*;
import java.awt.geom.Area;

//basically contains some commonly used values and such

//should avoid using any sort of constants thing for any big projects

public class Constants {
    public static final int FRAME_HEIGHT = 480;
    public static final int FRAME_WIDTH = 640;
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    public static final Rectangle leftScreen = new Rectangle(-100,-100,100,FRAME_HEIGHT+100);
    public static final Rectangle rightScreen = new Rectangle(FRAME_WIDTH,-100,100,FRAME_HEIGHT+100);
    public static final Rectangle aboveScreen = new Rectangle(-100,-100,FRAME_WIDTH+100,100);
    public static final Rectangle underScreen = new Rectangle(-100,FRAME_HEIGHT,FRAME_WIDTH+100,100);

    public static final double DRAWING_SCALE = 10;

    // sleep time between two frames
    public static final int DELAY = 20;  // time between frames in milliseconds
    //public static final int DELAY = 20; //delay of 20: 50fps
    public static final double DT = DELAY / 1000.0;  // DELAY in seconds
    //public static final double DT = DELAY / 1000.0;
}