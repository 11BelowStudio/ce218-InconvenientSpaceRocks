package utilities;

import java.awt.*;

// mutable 2D vectors
public final class Vector2D {
    public double x, y;

    // constructor for zero vector
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    // constructor for vector with given coordinates
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Point p){
        this.x = p.x;
        this.y = p.y;
    }

    // constructor that copies the argument vector
    public Vector2D(Vector2D v) {
        double tempX = v.x;
        double tempY = v.y;
        this.x = tempX;
        this.y = tempY;
    }

    // set coordinates
    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    // set coordinates based on argument vector
    public Vector2D set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    // compare for equality (note Object type argument)
    public boolean equals(Object o) {
        if (o instanceof Vector2D){
            //if the other object is a Vector2D, compares x and y of this Vector2D and that Vector2D
            Vector2D v =(Vector2D) o;
            return (this.x == v.x && this.y == v.y);
        }
        return false;

    }

    // String for displaying vector as text
    public String toString() {
        return(x + ", "  + y);

    }

    //  magnitude (= "length") of this vector
    public double mag() {
        return (Math.hypot(x,y));
        //hypotenuse of triangle basically
    }

    // angle between vector and horizontal axis in radians in range [-PI,PI]
// can be calculated using Math.atan2
    public double angle() {
        return Math.atan2(y,x);
    }

    // angle between this vector and another vector in range [-PI,PI]
    public double angle(Vector2D other) {
        //finding difference between the angles
        double result = other.angle() - this.angle();
        //wrapping the result around if it's outside range [-PI,PI] to keep it in range
        if (result < -Math.PI){
            result += 2*Math.PI;
            //2pi added if it's below -pi
        } else if (result > Math.PI){
            result -= 2* Math.PI;
            //2pi removed if it's above pi
        }
        return result;
    }

    // add argument vector
    public Vector2D add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }
    public static Vector2D add(Vector2D v1, Vector2D v2){
        Vector2D result = new Vector2D(v1);
        return result.add(v2);
    }

    // add values to coordinates
    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }
    public static Vector2D add(Vector2D v, double x, double y){
        Vector2D result = new Vector2D(v);
        return result.add(x,y);
    }

    // weighted add - surprisingly useful (adds v but multiplied by the factor)
    public Vector2D addScaled(Vector2D v, double fac) {
        this.x += (v.x*fac);
        this.y += (v.y*fac);
        return this;
    }
    public static Vector2D addScaled(Vector2D v1, Vector2D v2, double fac){
        Vector2D result = new Vector2D(v1);
        return result.addScaled(v2,fac);
    }

    // subtract argument vector (subtracts that vector from this)
    public Vector2D subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }
    public static Vector2D subtract(Vector2D v1, Vector2D v2){
        Vector2D result = new Vector2D(v1);
        return result.subtract(v2);
    }

    // subtract values from coordinates (subtracts stuff from the respective parameter)
    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    public static Vector2D subtract(Vector2D v, double x, double y){
        Vector2D result = new Vector2D(v);
        return result.subtract(x,y);
    }

    // multiply with factor (everything divided by factor)
    public Vector2D mult(double fac) {
        this.x = x*fac;
        this.y = y*fac;
        return this;
    }
    public static Vector2D mult(Vector2D v, double fac){
        Vector2D result = new Vector2D(v);
        return result.mult(fac);
    }

    // rotate by angle given in radians
    //basically scalar rotation
    public Vector2D rotate(double angle) {
        double tempX = x;
        double tempY = y;
        x = (tempX * Math.cos(angle)) - (tempY * Math.sin(angle));
        y = (tempX * Math.sin(angle)) + (tempY * Math.cos(angle));
        return this;
    }
    public static Vector2D rotate(Vector2D v, double angle){
        Vector2D result = new Vector2D(v);
        return result.rotate(angle);
    }

    // "dot product" ("scalar product") with argument vector
    public double dot(Vector2D v) {
        return ((this.x*v.x)+(this.y*v.y));
    }

    // distance to argument vector
    //Euclidean distance formula (which, for 2d planes, is pretty much pythagoras' theorem)
    public double dist(Vector2D v) {
        return (Math.hypot((x-v.x),(y-v.y)));
    }

    // normalise vector so that magnitude becomes 1
    //basically divides x and y by mag so mag effectively becomes 1
    //unless the magnitude is 0 at which case it can't really do anything
    public Vector2D normalise() {
        double currentMag = this.mag();
        if (currentMag != 0) {
            this.x = x / currentMag;
            this.y = y / currentMag;
        }
        return this;
    }
    public static Vector2D normalise(Vector2D v){
        Vector2D result = new Vector2D(v);
        return result.normalise();
    }

    public Vector2D setMag(double newMag) {
        this.set(polar(this.angle(),newMag));
        return this;
    }
    public static Vector2D setMag(Vector2D v, double newMag){
        return polar(v.angle(),newMag);
    }

    // wrap-around operation, assumes w> 0 and h>0
// remember to manage negative values of the coordinates
    public Vector2D wrap(double w, double h) {
        this.x = (x + w) % w;
        //pretty much gets the remainder of x plus width over width (ensures x is under width)
        this.y = (y + h) % h;
        //ditto but for y and height instead
        return this;
    }
    public static Vector2D wrap(Vector2D v, double w, double h){
        Vector2D result = new Vector2D(v);
        return result.wrap(w,h);
    }

    // construct vector with given polar coordinates
    public static Vector2D polar(double angle, double mag) {
        return new Vector2D(mag*Math.cos(angle),mag*Math.sin(angle));
    }



    public double getAngleTo(Vector2D v){
        double xAngle = v.x - x;
        double yAngle = v.y - y;
        return Math.atan2(yAngle,xAngle);
    }

    public Vector2D getVectorTo(Vector2D v){
        return Vector2D.polar(getAngleTo(v),dist(v));
    }

    public double getAngleTo(Vector2D v, double w, double h){
        double xAngle = v.x - x;
        double yAngle = v.y - y;
        double w2 = w/2;
        double h2 = h/2;
        if (xAngle > w2){
            xAngle =- w2;
        } else if(xAngle < -w2){
            xAngle += w2;
        }

        if (yAngle > h2){
            yAngle =- h2;
        } else if(yAngle < -h2){
            yAngle += h2;
        }
        return Math.atan2(yAngle,xAngle);

    }

    public Vector2D getVectorTo(Vector2D v, double w, double h){
        return Vector2D.polar(getAngleTo(v,w,h),v.dist(this));
    }

    //projection of this vector in some direction
    public Vector2D proj(Vector2D d){
        Vector2D result = new Vector2D(d);
        result.mult(this.dot(d));
        return result;
    }

    public static Vector2D randomVectorFromOrigin(Vector2D v, double minDist, double maxDist){
        Vector2D v1 = Vector2D.polar(Math.toRadians(Math.random()*360),(Math.random()*maxDist+minDist)-minDist);
        v1.add(v);
        return v1;
    }

    public static Vector2D randomVectorPointingTo(Vector2D v, double mag){
        Vector2D v1 = Vector2D.polar(Math.toRadians(Math.random()*360),mag);
        v1.add(v);
        double xAngle = v.x - v1.x;
        double yAngle = v.y - v1.y;
        return Vector2D.polar(Math.atan2(yAngle,xAngle),mag);
    }

    public Vector2D flip(){
        x *= -1;
        y *= -1;
        return this;
    }
    public static Vector2D flip(Vector2D v){
        Vector2D result = new Vector2D(v);
        return result.flip();
    }

    public Point toPoint(){
        return new Point((int)x,(int)y);
    }

    public static Vector2D originToPoint(Vector2D v, Point p){
        return v.getVectorTo(new Vector2D(p));
    }


}