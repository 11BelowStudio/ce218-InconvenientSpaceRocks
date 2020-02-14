package utilities;

import java.awt.*;


import static game.Constants.DRAWING_SCALE;

public class PolygonUtilities {

    public static Polygon scaledPolygonConstructor(int[] xCorners, int[] yCorners, double scale){
        if (xCorners.length != yCorners.length){
            return null;
        }
        for (int i = 0; i < xCorners.length; i++) {
            xCorners[i] = (int)(xCorners[i] * (DRAWING_SCALE * scale));
            yCorners[i] = (int)(yCorners[i] * (DRAWING_SCALE * scale));
        }
        Polygon scaledPolygon = new Polygon(xCorners,yCorners,xCorners.length);
        return scaledPolygon;
    }

}
