# This is the README for InconvenientSpaceRocks

oh no you are in space and there are a bunch of rocks (in space)
that are being very inconvenient and are in everyone's way pls
get rid of them for us k thx bye

## If you're here to assess this for the CE218 assignment

[The assignment documentation has its own folder. Go there.](Assignment documentation stuff)

# The README

## What you will need

* Keyboard
* Mouse (optional)
* Computer that can run Java stuff
* Speakers (optional)
* Monitor (optional, but highly recommended)

## Controls

* Up key: engage thrusters on your ship
* Left key: rotate anticlockwise
* Right key: rotate clockwise
* Down key: teleport forwards
* Spacebar: shoot/start game
* B: deploy bomb (if you have a bomb)
* Any button: the Any button/skip intro/skip full leaderboard
* Escape: pause game, show quit prompt
* Mouse: select main menu options

## Running the game

* Run the main method.
    * In case you can't find it:
        * [src/ThePackageThatContainsTheMainMethod/MainClass.java](src/ThePackageThatContainsTheMainMethod/MainClass.java)
* Folders that are needed for the game to work
    * pretty much all of /src
    * /textAssets
    * /images
    * /SaveData
    * /sounds
* yes it might take a moment for everything to get loaded.

## How to play

* get an high score
    * Destroy asteroids to earn points
        * Destroy all asteroids to proceed to the next level
        * You get a bomb when you complete a level (up to 3 bombs)
    * Destroy enemy ships to earn points
* how to destroy stuff
    * Shoot it
    * Drop a bomb near it, and let it get caught in the explosion
    * Yeet yourself into it (not recommended)
* Super Helpful Informative Tips!
    * Don't die
        * Don't get shot at by an enemy ship
        * Don't get caught by an exploding bomb
        * Don't crash into an enemy ship or an asteroid
    * You have 3 lives
        * You earn an extra life after scoring 100 points
    * The big/medium asteroids will break into smaller asteroids
        * If they got hit
            * They break into 2 smaller asteroids
            * You get points if you hit it
        * If they didn't get hit
            * 5 smaller asteroids
            * No points for you.
    * Bombs
        * You get 1 bomb after finishing a level
        * You may only have up to 3 bombs in reserve
        * Only 1 bomb can be active at a time
        * Bombs have 2 stages
            * Countdown
                * Yellow circle, fading to orange, getting smaller
                * If it gets hit, it's destroyed. No refunds.
            * Exploding
                * Orange circle, getting bigger
                * Anything it hits is destroyed
                    * Even you
        * Destroying something via bomb only gives you half the normal points
    * Invincibility
        * If your ship has a magenta overlay, congrats, you've earned some invincibility
        * Getting invincibility
            * You are invincible for 1 second at the start of every life
            * You gain a quarter of a second of invincibility after:
                * You destroy an asteroid
                * You finish a level
        * Losing invincibility
            * Wait for it to expire naturally
            * Shoot
            * Deploy a bomb
    * The game area
        * It wraps around at the edges basically
    * Collision stuff
        * basically the hitboxes match up exactly with what you see onscreen.
            * even if the object in question is wrapped around the screen.
        * 'but muh pass-through problem' (I see you there, examiner)
            * If a tree falls in a forest but there's no-one around to see it,
            does it do a very epic 69000 degree backflip on its way down?
    
## CREDITS

* Code:
    * Me
        * Some stuff was written entirely for use in this
        * Other stuff was reverse-engineered from some of my previous projects
    * Some sample/template code provided by Dr. Dimitri Ognibene
* Art:
    * Me
        * Made with MS Paint because thats all the university computers had lol
* Audio:
    * Me
        * With some additional sounds provided by a bag of Tesco roast chicken crisps
        * Recorded using my phone's sound recorder thing, exported into .wav with Audacity
* Story stuff:
    * Me
    