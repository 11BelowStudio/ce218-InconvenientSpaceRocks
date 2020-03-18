package utilities;

import java.awt.*;

public class PolygonUtilities {

    public static Polygon scaledPolygonConstructor(int[] xCorners, int[] yCorners, double scale){
        if (xCorners.length != yCorners.length){
            return null;
        }
        for (int i = 0; i < xCorners.length; i++) {
            xCorners[i] = (int)(xCorners[i] * scale);
            yCorners[i] = (int)(yCorners[i] * scale);
        }
        return new Polygon(xCorners,yCorners,xCorners.length);
    }

    public static Polygon randomScaledPolygonConstructor(double scale, int nPoints){
        int[] xCorners = new int[nPoints];
        int[] yCorners = new int[nPoints];
        double maxRadius = scale * 1.05;
        double minRadius = scale * 0.75;

        for (int i = 0; i < nPoints; i++) {
            Vector2D temp = Vector2D.polar(
                    Math.PI * 2 * (i + Math.random())/ nPoints,
                    scale + Math.random() * (maxRadius - minRadius)
            );
            xCorners[i] = (int)temp.x;
            yCorners[i] = (int)temp.y;
        }
        return new Polygon(xCorners,yCorners,nPoints);
    }

    public static Polygon dodecahedron(double scale){
        int[] xCorners = new int[12];
        int[] yCorners = new int[12];

        double angle = 0;

        for (int i = 0; i < 12; i++) {
            Vector2D temp = Vector2D.polar(
                    angle,
                    scale
            );
            angle += Math.PI/6; //(Math.PI * 2) / 12
            xCorners[i] = (int)temp.x;
            yCorners[i] = (int)temp.y;
        }
        return new Polygon(xCorners,yCorners,12);

    }

}
