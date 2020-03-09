package game;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game.Constants.DT;

public class ScrollingStringObject extends GameObject {

    private String thisString;

    ScrollingStringObject(Vector2D p, Vector2D v, String s){
        super(p,v);
        thisString = s;
        objectColour = Color.WHITE;
    }


    @Override
    public void draw(Graphics2D g) {
        Font tempFont = g.getFont();
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        g.setColor(objectColour);
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int w = metrics.stringWidth(thisString);
        g.drawString(thisString,0-(w/2),0);
        g.setTransform(at);
        g.setFont(tempFont);
    }


    @Override
    public void update(){
        if (position.y < 0){
            dead = true;
        }
        if (!dead) {
            position.addScaled(velocity, DT);
        }
    }
}
