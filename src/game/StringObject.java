package game;

import utilities.AttributeString;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game.Constants.DT;
import static game.Constants.sans;

public class StringObject extends GameObject {

    private String thisString;

    private int alignment;
    static final int LEFT_ALIGN = 0; static final int RIGHT_ALIGN = 1; static final int MIDDLE_ALIGN = 2;

    StringObject(Vector2D p, Vector2D v, String s, int a){
        this(p,v,a);
        setText(s);
    }
    StringObject(Vector2D p, Vector2D v, String s){
        this(p,v);
        setText(s);
    }

    StringObject(Vector2D p, Vector2D v, AttributeString s, int a){
        this(p,v,a);
        setText(s.toString());
    }

    StringObject(Vector2D p, Vector2D v, AttributeString s){
        this(p,v);
        setText(s.toString());
    }

    StringObject(Vector2D p, Vector2D v, int a){
        this(p,v);
        alignment = a;
    }

    StringObject(Vector2D p, Vector2D v){
        super(p,v);
        alignment = 0;
        thisString = "";
        objectColour = Color.WHITE;
    }

    @Override
    public void revive(Vector2D p, Vector2D v) {
        super.revive(p,v);
    }

    public void revive(){
        revive(new Vector2D(),new Vector2D());
    }

    public void revive(String s){
        revive(new Vector2D(),new Vector2D());
        thisString = s;
    }

    @Override
    public void draw(Graphics2D g) {
        if (!dead) {
            Font tempFont = g.getFont();
            g.setFont(sans);
            AffineTransform at = g.getTransform();
            g.translate(position.x, position.y);
            g.setColor(objectColour);
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int w = metrics.stringWidth(thisString);
            switch (alignment){
                case 0:
                    g.drawString(thisString,0,0);
                    break;
                case 1:
                    g.drawString(thisString, 0 - w, 0);
                    break;
                case 2:
                    g.drawString(thisString, 0 - (w / 2), 0);
                    break;
            }
            g.setTransform(at);
            g.setFont(tempFont);
        }
    }

    public void setText(String s){
        thisString = s;
    }

    public void setText(AttributeString as){
        thisString = as.toString();
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

    public void yeet(){
        this.dead = true;
    }
}
