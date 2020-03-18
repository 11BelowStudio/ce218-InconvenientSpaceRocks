package GamePackage;

import java.awt.*;

//basically contains some commonly used values and such

public class Constants {
    public static final int FRAME_HEIGHT = 600;
    public static final int FRAME_WIDTH = 800;
    static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    public static final int HALF_HEIGHT = (FRAME_HEIGHT/2);
    public static final int HALF_WIDTH =  (FRAME_WIDTH/2);

    // sleep time between two frames
    static final int DELAY = 20;  // time between frames in milliseconds
    public static final double DT = DELAY / 1000.0;  // DELAY in seconds

}