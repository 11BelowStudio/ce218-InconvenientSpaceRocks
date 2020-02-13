package game2;

import utilities.PolygonUtilities;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;


public class Bullet extends GameObject {

    int timeToLive;

    double distanceToGo;

    double scaledDistance;

    int frameCount;



    private int[] xCorners, yCorners;

    public Bullet(Vector2D position, Vector2D direction){
        super(position, Vector2D.polar(direction.angle(),300));
        //this.direction = direction;
        timeToLive = 50;
        distanceToGo = 30;
        RADIUS = 5;
        hitboxX = new int[]{0,1,-1};
        hitboxY = new int[]{-1,1,1};

        frameCount = 0;

        objectPolygon = PolygonUtilities.scaledPolygonConstructor(hitboxX,hitboxY,0.5);

        /*
        this.xpoints = new int[]{0,1,-1};
        this.ypoints = new int[]{-1,1,1};
        this.npoints = 3;

         */
        super.update();
    }

    public void update(){
        super.update();
        timeToLive--;
        objectColour = new Color(255 - (5*timeToLive),255 - (3*timeToLive),255 - (timeToLive/2),128);

        if (timeToLive == 0){
            dead = true;
        }

    }

    /*
    public void hit(){
        dead = true;
    }*/

    public void hitLogic(){
        //just here to not break the abstract method basically
    }

    @Override
    public void draw(Graphics2D g) {
        this.notIntangible();
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = velocity.angle() + Math.PI / 2;
        g.rotate(rot);
        //g.scale(RADIUS, RADIUS);
        //g.setColor(Color.ORANGE);
        g.setColor(objectColour);
        //g.setColor(new Color(255 - (int)(0.5*scaledDistance),255 - (int)(0.15*scaledDistance),255 - (int)(0.05*scaledDistance)));
        //g.fillPolygon(objectPolygon = new Polygon(xCorners, yCorners, xCorners.length));
        //g.fillPolygon(transformedObjectPolygon = (Polygon)g.getTransform().createTransformedShape(objectPolygon));
        //g.fillPolygon(objectPolygon);
        Shape transformedShape = g.getTransform().createTransformedShape(objectPolygon);;
        g.setTransform(at); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        g.setPaint(new TexturePaint(texture,this.areaRectangle));
        g.fill(transformedArea);
        g.setColor(objectColour);
        g.fill(transformedArea);

    }

}
