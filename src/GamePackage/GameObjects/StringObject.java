package GamePackage.GameObjects;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static GamePackage.GameObjects.GameObjectConstants.DT;
//import static game.Constants.sans;

public class StringObject extends GameObject {

    private String thisString;

    private int alignment;

    private Font theFont;

    public static final int RIGHT_ALIGN = 0;
    public static final int LEFT_ALIGN = 1;
    public static final int MIDDLE_ALIGN = 2;


    private Rectangle areaRectangle;

    //le ebic font has arrived no bamboozle
    public static final Font SANS = new Font("Comic Sans MS",  Font.PLAIN , 20);
    public static final Font MEDIUM_SANS = new Font("Comic sans MS", Font.PLAIN,40);
    public static final Font BIG_SANS = new Font("Comic sans MS", Font.PLAIN,50);

    public StringObject(Vector2D p, Vector2D v, String s, int a, Font f){
        this(p,v,s,a);
        theFont = f;
    }

    public StringObject(Vector2D p, double speed, String s, int a, Font f){ this(p,Vector2D.polar(UP_RADIANS,speed),s,a,f); }

    public StringObject(Vector2D p, Vector2D v, String s, int a){
        this(p,v,a);
        setText(s);
    }

    public StringObject(Vector2D p, double speed, String s, int a){ this(p,Vector2D.polar(UP_RADIANS,speed),s,a); }

    public StringObject(Vector2D p, Vector2D v, String s){
        this(p,v);
        setText(s);
    }

    public StringObject(Vector2D p, Vector2D v, int a){
        this(p,v);
        alignment = a;
    }

    public StringObject(Vector2D p, Vector2D v){
        super(p,v);
        alignment = 0;
        thisString = "";
        objectColour = Color.WHITE;
        theFont = SANS;
        areaRectangle = new Rectangle();
    }

    @Override
    public StringObject revive(Vector2D p, Vector2D v) {
        super.revive(p,v);
        return this;
    }

    public StringObject revive(){ return revive(position,velocity); }

    public StringObject kill(){ dead = true; return this; }

    public String getString(){ return thisString; }

    public StringObject revive(String s){
        revive();
        return setText(s);
    }

    public boolean isClicked(Point p){ return ((!dead) && (areaRectangle.contains(p))); }


    @Override
    public void draw(Graphics2D g) {
        if (!dead) {
            Font tempFont = g.getFont();
            g.setFont(theFont);
            AffineTransform at = g.getTransform();
            g.translate(position.x, position.y);
            g.setColor(objectColour);
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int w = metrics.stringWidth(thisString);
            int h = metrics.getHeight();
            switch (alignment){
                case 0:
                    g.drawString(thisString,0,0);
                    break;
                case 1:
                    g.drawString(thisString, -w, 0);
                    break;
                case 2:
                    g.drawString(thisString, -(w / 2), 0);
                    break;
            }
            g.setTransform(at);
            g.setFont(tempFont);
            areaRectangle = new Rectangle((int)position.x - w/2, (int)position.y - h/2,w,h);
        }
    }

    public StringObject setText(String s){ thisString = s; return this;}


    @Override
    public void update(){
        //only really used for scrolling text tbh
        if (position.y < 0){
            dead = true;
        } else {
            position.addScaled(velocity, DT);
        }
    }


}
