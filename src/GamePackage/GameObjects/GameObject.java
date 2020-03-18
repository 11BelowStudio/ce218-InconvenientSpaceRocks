package GamePackage.GameObjects;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;


import static GamePackage.GameObjects.GameObjectConstants.*;

public abstract class GameObject{

    //https://docs.oracle.com/javase/8/docs/api/java/awt/Polygon.html

    Vector2D position;
    Vector2D velocity;

    boolean dead;

    double RADIUS;

    int objectType;

    static final int BOMB = 0;
    static final int PLAYER_OBJECT = 1;
    static final int ENEMY_OBJECT = 2;
    static final int ASTEROID = 3;


    double rotationAngle;



    boolean intangible;
    //whether or not this game object can be interacted with

    boolean wasHit;
    //records if it was hit by something or not

    private boolean playerHit;
    //records if the player hit it or not

    private boolean bombHit;

    int pointValue;

    Polygon objectPolygon;

    Area transformedArea;

    private Rectangle areaRectangle;

    private Rectangle boundingRectangle;


    BufferedImage texture;

    Color objectColour;

    static final double DRAG = 0.985;

    static final double UP_RADIANS = Math.toRadians(270);

    int timeToLive;

    public GameObject(Vector2D p, Vector2D v){
        RADIUS = 10;
        position = p;
        velocity = v;
        dead = false;
        intangible = true; //everything intangible until drawn at earliest to avoid exceptions being thrown on frame 1 collisions
        wasHit = false;
        playerHit = false;
        bombHit = false;
        pointValue = 0;
        objectColour = new Color(255,255,255,32);
        objectType = BOMB;
        rotationAngle = 0;
    }

    public GameObject revive(Vector2D p, Vector2D v){
        this.position.set(p);
        this.velocity.set(v);
        dead = false;
        intangible = true; //everything intangible until drawn at earliest to avoid exceptions being thrown on frame 1 collisions
        wasHit = false;
        playerHit = false;
        bombHit = false;
        rotationAngle = 0;
        return this;
    }

    public void update(){
        if (!dead) {
            position.addScaled(velocity, DT);
            position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
        }
    }



    void hit(boolean hitByPlayer){
        dead = true;
        //intangible = true;
        wasHit = true;
        if (!playerHit) {
            playerHit = hitByPlayer; //will only be updated if this is not true;
        }
    }

    public void draw(Graphics2D g){
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        g.rotate(rotationAngle);
        Shape transformedShape = g.getTransform().createTransformedShape(objectPolygon);
        g.setTransform(at); //resets the Graphics2D transformation back to default
        wrapAround(g,transformedShape);
        paintTheArea(g);
        this.intangible = false;
    }

    private boolean overlap(GameObject other){// overlap detection based on areas
        try {//compares some bounding rectangles for the two objects first before attempting to properly collide them
            if (this.boundingRectangle.intersects(other.boundingRectangle)) {
                //If the actual areas of the two gameObjects involved in the collision overlap in any way, they've collided
                Area thisArea = new Area(this.transformedArea);
                thisArea.intersect(other.transformedArea);
                return !thisArea.isEmpty();
            }
        } catch(NullPointerException ignored){} //if the hitboxes/bounding rectangles haven't been set up, they haven't collided
        return false;
    }


    public void collisionHandling(GameObject other) {
        if (this.objectType != other.objectType && this.overlap(other)) {
            if (this.cantTouchThis() || other.cantTouchThis()) {
                this.bounceOff(other);
                //they bounce off each other if one of them is now dead/intangible.
                //shouldn't happen, but, just in case an object became dead/intangible mid-collision cycle
            } else if (!(this.bombCollision(other) || other.bombCollision(this))){
                //if the collision did not involve active bombs, the normal collision stuff happens
                this.hit(other.objectType == PLAYER_OBJECT);
                other.hit(objectType == PLAYER_OBJECT);
            }
        }
    }

    boolean bombCollision(GameObject other){ return false; } //bomb class overrides this with one that returns true.

    void hitByBomb(){
        hit(true);
        bombHit = true;
    }


    void wrapAround(Graphics2D g, Shape transformedShape){
        transformedArea = new Area(transformedShape);
        //the transformedArea is the transformedShape parameter but as an Area instead
        areaRectangle = transformedArea.getBounds();
        //a simple bounding rectangle for this area, before wrapping, used for the texturePaint
        boolean wrapped = false;
        if (transformedShape.intersects(aboveScreen)){
            //moving stuff above the screen to the bottom of it
            wraparoundHandler(g,  aboveScreen, 0, FRAME_HEIGHT);
            wrapped = true;
        } else if (transformedShape.intersects(underScreen)){
            //moving stuff under the screen to the top of it
            wraparoundHandler(g, underScreen, 0, -FRAME_HEIGHT);
            wrapped = true;
        }
        if (transformedShape.intersects(leftScreen)){
            //moving stuff to the left of the screen to the right of it
            wraparoundHandler(g,  leftScreen, FRAME_WIDTH, 0);
            wrapped = true;
        } else if (transformedShape.intersects(rightScreen)){
            //moving stuff on the right of the screen to the left of it
            wraparoundHandler(g, rightScreen, -FRAME_WIDTH, 0);
            wrapped = true;
        }

        if (wrapped){
            boundingRectangle = transformedArea.getBounds();
            //bounding box will have changed if wrapped around, should update this as a result
        } else{
            boundingRectangle=areaRectangle;
            //boundingRectangle same as it was beforehand if not wrapped
        }

    }

    private void wraparoundHandler(Graphics2D g, Rectangle wraparoundCheckRect, int xTranslate, int yTranslate) {
        AffineTransform backup = g.getTransform(); //gets copy of original affine transform
        Area tempArea = (Area)transformedArea.clone(); //copies the transformed area
        tempArea.intersect(new Area(wraparoundCheckRect)); //get the intersection of it with the wraparound rectangle
        g.translate(xTranslate, yTranslate); //moves it to the opposite edge of the playable area
        tempArea.transform(g.getTransform()); //and then applies the transformation that was applied to the main object
        transformedArea.add(tempArea); //adds it to the object's area
        g.setTransform(backup); //reverts to backup
    }

    protected void paintTheArea(Graphics2D g){
        paintTexture(g);
        paintColour(g);
    }

    protected void paintTexture(Graphics2D g){
        g.setPaint(new TexturePaint(texture, areaRectangle));
        g.fill(transformedArea); //filling the sprite with the texture
    }

    void paintColour(Graphics2D g){
        g.setColor(objectColour);
        g.fill(transformedArea); //now filling it with the overlay
    }

    @Override
    public String toString(){
        return (this.getClass() + " x: " + String.format("%.2f",position.x) + ", y: " + String.format("%.2f",position.y) + ", vx: " + velocity.x + ", vy: " + velocity.y);
    }

    protected void notIntangible(){ this.intangible = false; }//basically supposed to make the thing not intangible,



    protected void bounceOff(GameObject other){
        if (other instanceof Bullet){
            other.kill(); //the bullet just dies if it's a bullet
        }  else if (other.objectType == BOMB) {
            //this is moved, not the bomb, if the other object is a bomb
            velocity.getCollisionVelocity(position,other.position,other.velocity);
        } else{
            //both moved if there are no bombs or bullets
            Vector2D coll = position.getCollisionVector(other.position);
            Vector2D tangent = coll.getTangent();
            tangent.mult(1.1);
            Vector2D thisV = new Vector2D(this.velocity);
            Vector2D otherV = new Vector2D(other.velocity);
            this.velocity.set(Vector2D.getCollisionVelocity(thisV,otherV,coll,tangent));
            //position.addScaled(velocity, DT);
            other.velocity.set(Vector2D.getCollisionVelocity(otherV,thisV,coll,tangent));
        }
    }

    public GameObject kill(){
        dead = true;
        return this;
    }


    public boolean cantTouchThis(){ return (dead || intangible); } //true if dead or intangible


    public Vector2D getPosition(){ return position; }

    public Vector2D getVelocity(){ return velocity; }

    public double getSpeed(){ return velocity.mag(); }

    public void setPosition(Vector2D p){ position.set(p); }

    public int getPoints(){
        if (bombHit){
            return (int) Math.ceil((double)pointValue/ 2); //points halved if hit by bomb
        } else if (playerHit){
            return pointValue; //full points if hit by player without using a bomb
        } else{
            return 0; //no points if not hit by player
        }
    }

    public boolean isDead(){ return dead; }

    public boolean isPlayerObject(){ return objectType == PLAYER_OBJECT;}

    public int getObjectType(){ return objectType; }



}
