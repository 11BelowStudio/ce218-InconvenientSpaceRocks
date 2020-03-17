package utilities;

import java.awt.*;


import static GamePackage.Constants.DRAWING_SCALE;

public class PolygonUtilities {

    public static Polygon scaledPolygonConstructor(int[] xCorners, int[] yCorners, double scale){
        if (xCorners.length != yCorners.length){
            return null;
        }
        for (int i = 0; i < xCorners.length; i++) {
            xCorners[i] = (int)(xCorners[i] * (DRAWING_SCALE * scale));
            yCorners[i] = (int)(yCorners[i] * (DRAWING_SCALE * scale));
        }
        return new Polygon(xCorners,yCorners,xCorners.length);
    }

    public static Polygon randomScaledPolygonConstructor(int[] xCorners, int[] yCorners, double scale, int nPoints){
        if (xCorners.length != yCorners.length){
            return null;
        }
        double standardRadius = scale * DRAWING_SCALE * 4;
        double maxRadius = standardRadius * 1.05;
        double minRadius = standardRadius * 0.75;

        for (int i = 0; i < nPoints; i++) {
            Vector2D temp = Vector2D.polar(
                    Math.PI * 2 * (i + Math.random())/ nPoints,
                    standardRadius + Math.random() * (maxRadius - minRadius)
            );
            xCorners[i] = (int)temp.x;
            yCorners[i] = (int)temp.y;
        }
        return new Polygon(xCorners,yCorners,nPoints);
    }

    public static Polygon prettyMuchACircle(int[] xCorners, int[] yCorners, double scale){
        xCorners = new int[12];
        yCorners = new int[12];

        double radius = scale;
        double angle = 0;

        for (int i = 0; i < 12; i++) {
            Vector2D temp = Vector2D.polar(
                    angle,
                    radius
            );
            angle += Math.PI/6; //(Math.PI * 2) / 12
            xCorners[i] = (int)temp.x;
            yCorners[i] = (int)temp.y;
        }
        return new Polygon(xCorners,yCorners,12);

    }

}
