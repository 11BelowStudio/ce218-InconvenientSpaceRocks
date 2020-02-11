#Lab 1 quiz

**1: What is the effect on the frame rate of increasing or decreasing DELAY?**

* Higher DELAY = slower framerate
* Lower DELAY = higher framerate

**2: Approximately what would the frame rate be if the DELAY is set at 20?
(You may need to look up some Java documentation to answer this.)**

* 50 frames per second
    * 1000 ms per second, DELAY 20 = 20ms delay
    * 1000/20 = 50

**3: Why is your answer to the previous question just an approximate answer?
Does the approximation become poorer as the frame rate increases, and if so why?**

* DELAY is only the minimum length of time between frames (effectively dictates maximum framerate)
* If the processing time needed for a frame is longer than the DELAY, the extra time needed will delay that frame further,
therefore reducing the framerate
* if DELAY is shorter, there's less leeway for processing the frame within the DELAY, therefore, it'll be easier for the
frame to overrun, delaying the next frames, and therefore reducing the framerate for that second 

**4: Try to write a simple equation to explain this phenomenon.**

here's an if statement instead
```
/*
frameDelay is the minimum length between frames
frameProcessingTime is how long it took for this frame to be processed
actualFrameDelay is how long it took for this frame to be shown
*/
if (frameDelay >= frameProcessingTime){
    actualFrameDelay = frameDelay;
} else{
    actualFrameDelay = frameProcessingTime;
}
```

**5: What is the purpose of the DT factor in the position update equations?**

* some quick physics first
    * speed = distance/time
        * magnitude of velocity
        * distance travelled = speed * time
    * velocity = speed in a given direction
* purpose of DT
    * used to calculate distance that a BasicAsteroid should travel between each frame
        * x and y (location) of BasicAsteroid updated each frame
            * x = vx * DT, y = vy * DT
            * vx and vy are speed (in pixels/second), DT is time (seconds)
                * (pixels/second) * seconds = pixels travelled in those seconds

**6: What is the unit of velocity in the above code?
For example, if you have an asteroid with velocity vx = 20, vy= 50,
how many pixels does the asteroid move per second in the x- and y- direction?
And why is this only approximate?**

* Unit of velocity
    * Pixels per second
* Asteroid with velocity vx = 20, vy = 50
    * moves at 20 px/s in X, 50 px/s in Y
    * Will be slower if the framerate is reduced due to a frame taking longer than DELAY to render/be displayed.
        * movement calculations are tied to framerate
        * 0.4 px/frame in x, 1 px/frame in y

**7: Design and run a test to check whether your estimate for the velocity is roughly correct.**

* designing test
    * Changes to BasicAsteroid
        * Created a 'toString' method, to output the x, y, vx, and vy details of it
    * Changes to BasicGame
        * Created a new 'testAsteroid' BasicAsteroid object, with defined vx 20 and vy 50 (x and y 0)
            * is in the 'asteroids' list wth all the others
        * Changed the actual framerate calculation stuff I made earlier on in the 'main' method slightly
            * Will output the details of testAsteroid every second (via its toString method)
* Results of the test (DELAY of 20; ~50fps)
    * Movement in x direction
        * Moved 0.4 x per frame
            * 20 in x when 50 frames (intended speed at intended frames)
            * 19.6 in x when 49 frames
    * Movement in y direction
        * Moved 1 y per frame
            * 50 in y when 50 frames (intended speed at intended frames)
            * 49 in y when 49 frames 
* prediction was correct
            

**8: In the example code above, BasicGame was responsible for specifying the initial state of each randomly created asteroid.
Simplify the BasicGame class by introducing and using a no-args Asteroid constructor
(hence the Asteroid is responsible for its own initialisation).**

```
public BasicAsteroid(){
    //creates a random asteroid
    this.x = Math.random() * FRAME_WIDTH;
    this.y = Math.random() * FRAME_HEIGHT;
    this.vx = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
    this.vy = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
}
```

**9: The above code for BasicAsteroid stores the horizontal and vertical components of position in separate variables (x and y).
Can you think of a better way to store position (and velocity)?**

* Velocity
    * Basic thinking
        * vx and vy are like the adjacent/opposite of a triangle where the hypotenuse is the actual distance the asteroid travels
            * vx: adjacent, vy: opposite, travel: hypotenuse
                * define hypotenuse and hyp/adj angle, can find other lengths from there
                    * https://www.khanacademy.org/math/geometry/hs-geo-trig/hs-geo-solve-for-a-side/a/unknown-side-in-right-triangle-w-trig
    * Solution
        * Define overall distance to travel per second/per frame, as well as angle of travel (angle of hyp from current position)
            * travelDistance: double
            * travelRadian: double
                * trigonometry stuff in Java Math requires radians, so the angle must be stored as a radian
        * can use these to work out vx and vy
            * vx: cos(angle) = vx/hyp
                * ```x += (Math.cos(travelRadian)/travelDistance);```
            * vy: sin(angle) = vy/hyp
                * ```y += (Math.sin(travelRadian)/travelDistance);```
        * pros/cons
            * pros
                * easier to express what the speed/angle are supposed to be, more consistent
            * cons
                * might take slightly longer to render each frame             
* Position
    * thoughts
        * current implementation
            * x and y used directly when they are being updated
                * updated with new movement
                * updated to make sure they are in the frame
            * x and y minus radius (cast to int) used when they are being drawn
    * solution
        * store x and y as an int
            * use local doubles for the update stuff
                * subtract radius from the local doubles after the double stuff is done,
            downcast back to int to be stored properly
            * might cause lack of precision
        * keep x and y as double, just subtract radius whilst updating them, not whilst drawing them
            