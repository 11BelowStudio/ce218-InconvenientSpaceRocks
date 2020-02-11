package game1;

import java.awt.*;

//basically contains some commonly used values and such

//should avoid using any sort of constants thing for any big projects

public class Constants {
    public static final int FRAME_HEIGHT = 480;
    public static final int FRAME_WIDTH = 640;
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    // sleep time between two frames
    public static final int DELAY = 20;  // time between frames in milliseconds
    //public static final int DELAY = 20; //delay of 20: 50fps
    public static final double DT = DELAY / 1000.0;  // DELAY in seconds
    //public static final double DT = DELAY / 1000.0;
}