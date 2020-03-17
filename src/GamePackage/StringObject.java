package GamePackage;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static GamePackage.Constants.DT;
//import static game.Constants.sans;

public class StringObject extends GameObject {

    private String thisString;

    private int alignment;

    private Font theFont;

    static final int RIGHT_ALIGN = 0; static final int LEFT_ALIGN = 1; static final int MIDDLE_ALIGN = 2;


    private Rectangle areaRectangle;

    //le ebic font has arrived no bamboozle
    static final Font sans = new Font("Comic Sans MS",  Font.PLAIN , 20);
    static final Font medium_sans = new Font("Comic sans MS", Font.PLAIN,40);
    static final Font big_sans = new Font("Comic sans MS", Font.PLAIN,50);

    StringObject(Vector2D p, Vector2D v, String s, int a, Font f){
        this(p,v,s,a);
        theFont = f;
    }

    StringObject(Vector2D p, double speed, String s, int a, Font f){
        this(p,Vector2D.polar(UP_RADIANS,speed),s,a,f);
    }

    StringObject(Vector2D p, Vector2D v, String s, int a){
        this(p,v,a);
        setText(s);
    }

    StringObject(Vector2D p, double speed, String s, int a){
        this(p,Vector2D.polar(UP_RADIANS,speed),s,a);
    }

    StringObject(Vector2D p, Vector2D v, String s){
        this(p,v);
        setText(s);
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
        dead = false;
        theFont = sans;
        areaRectangle = new Rectangle();
    }

    @Override
    public StringObject revive(Vector2D p, Vector2D v) {
        super.revive(p,v);
        return this;
    }

    public StringObject revive(){ return revive(position,velocity); }

    public StringObject kill(){ this.dead = true; return this; }

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
                    g.drawString(thisString, 0 - w, 0);
                    break;
                case 2:
                    g.drawString(thisString, 0 - (w / 2), 0);
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
