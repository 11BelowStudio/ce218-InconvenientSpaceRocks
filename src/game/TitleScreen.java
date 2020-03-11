package game;

import utilities.HighScoreHandler;
import utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;

import static game.Constants.*;
import static game.GameObject.UP_RADIANS;

public class TitleScreen extends Model {





    private boolean readyToSpawnAsteroid;

    private boolean readyToSpawnShip;


    private ArrayList<GameObject> scrollingTextToAdd;


    //private boolean showingIntro;
    private boolean menuOnscreen;
    //private boolean showingScores;

    private boolean showScrollingText;

    private boolean setupScrollingText;

    private StringObject titleText;
    private StringObject subtitleText;

    private StringObject play;

    private StringObject showScores;

    private boolean yeetHUD; //whether or not the HUD should be yote

    TitleScreen(PlayerController ctrl, HighScoreHandler hs){
        super(ctrl, hs);

        //aliveHUD = new ArrayList<>();

        scrollingTextToAdd = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            bigAsteroidStack.push(new BigAsteroid());
        }
        for (int i = 0; i < 45; i++) {
            mediumAsteroidStack.push(new MediumAsteroid());
        }
        for (int i = 0; i < 135; i++){
            asteroidStack.push(new Asteroid());
        }

        for (int i = 0; i < 25; i++) {
            enemyBullets.push(new EnemyBullet());
        }

        for (int i = 0; i < 5; i++) {
            EnemyShip e = new EnemyShip(this);
            enemyShips.push(e);
        }

        titleText = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT/2),new Vector2D(),"INCONVENIENT SPACE ROCKS",StringObject.MIDDLE_ALIGN,StringObject.big_sans);
        titleText.kill();

        subtitleText = new StringObject(new Vector2D(HALF_WIDTH,5*(HALF_HEIGHT/8)), new Vector2D(),"(In Space!)",StringObject.MIDDLE_ALIGN,StringObject.medium_sans);
        subtitleText.kill();

        play = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT),new Vector2D(),"*Play*",StringObject.MIDDLE_ALIGN,StringObject.medium_sans);
        play.kill();

        showScores = new StringObject(new Vector2D(HALF_WIDTH,3*(HALF_HEIGHT/2)),new Vector2D(),"*Show Scores*",StringObject.MIDDLE_ALIGN,StringObject.medium_sans);
        showScores.kill();

        /*
        int distFromBottom = 30;
        for (String s: LiterallyJustTheOpeningCreditsThing.openingCreditsThing) {
            scrollingTextToAdd.add(new StringObject(new Vector2D(HALF_WIDTH,FRAME_HEIGHT+distFromBottom),Vector2D.polar(UP_RADIANS,25),s,StringObject.MIDDLE_ALIGN));
            distFromBottom += 22;
        }*/

        //showingIntro = false;
        //showingScores = false;

        readyToSpawnAsteroid = true;
        readyToSpawnShip = false;

        menuOnscreen = false;

        setupScrollingText = true;

        showScrollingText = false;

        yeetHUD = false;

        createScrollingText(LiterallyJustTheOpeningCreditsThing.openingArrayList,30,25);


        /*
        mediumAsteroidsToYeet = 0;
        smallAsteroidsToYeet = 0;*/
    }


    public void revive(){
        super.revive();

        //aliveHUD.clear();
        scrollingTextToAdd.clear();

        for (int i = 0; i < 15; i++) {
            bigAsteroidStack.push(new BigAsteroid());
        }
        for (int i = 0; i < 45; i++) {
            mediumAsteroidStack.push(new MediumAsteroid());
        }
        for (int i = 0; i < 135; i++){
            asteroidStack.push(new Asteroid());
        }
        for (int i = 0; i < 25; i++) {
            enemyBullets.push(new EnemyBullet());
        }
        for (int i = 0; i < 5; i++) {
            EnemyShip e = new EnemyShip(this);
            enemyShips.push(e);
        }

        titleText.kill();
        subtitleText.kill();
        play.kill();
        showScores.kill();

        readyToSpawnAsteroid = true;
        readyToSpawnShip = false;
        menuOnscreen = false;
        setupScrollingText = true;
        showScrollingText = false;
        yeetHUD = false;

        createScrollingText(LiterallyJustTheOpeningCreditsThing.openingArrayList,30,25);

        yeetHUD = false;
    }

    @Override
    public void update() {

        for (GameObject g: gameObjects) {
            g.update();
            if (g.dead){
                dead.add(g);
                if (g instanceof BigAsteroid){
                    bigAsteroidStack.push((BigAsteroid) g);
                    //mediumAsteroidsToYeet += 5;
                } else if(g instanceof MediumAsteroid){
                    mediumAsteroidStack.push((MediumAsteroid) g);
                    //mediumAsteroidsToYeet--;
                    //smallAsteroidsToYeet += 5;
                } else if (g instanceof Asteroid){
                    asteroidStack.push((Asteroid) g);
                    //smallAsteroidsToYeet--;
                } else if (g instanceof EnemyShip){
                    enemyShips.push((EnemyShip) g);
                } else if (g instanceof EnemyBullet){
                    enemyBullets.push((EnemyBullet) g);
                }
            } else{
                if (Math.random() < 0.00005 && (isAsteroid(g) || g instanceof EnemyShip)){
                    dead.add(g);
                } else {
                    alive.add(g);
                }
            }
        }


        for (int i = 0; i < alive.size(); i++) {
            GameObject g = alive.get(i);

            if (g.dead || g.intangible){
                continue; //skip it if it's now dead/is intangible
                //it'll be dealt with next update.
            }


            for (int j = i+1; j < alive.size(); j++) {
                GameObject g2 = alive.get(j);
                if (g2.dead || g2.intangible){
                    continue; //skip stuff that's dead/intangible
                }

                if ((isAsteroid(g) ^ isAsteroid(g2)) ||
                        (isEnemyObject(g) ^ isEnemyObject(g2))
                ) {
                    //only need to bother handing collisions if both objects are both asteroid/player/enemy-related objects
                    // (^ operator is 'xor')
                    //can't really do 'if (.class != .class)', as the superclasses kinda mess around with it
                    g.collisionHandling(g2);
                }
            }

            if (g instanceof EnemyShip){
                enemyFire((EnemyShip) g);
            }

        }


        //boolean yeetEverything = (  ((showingIntro || showingScores) && ctrl.theAnyButton()));

        //yeetHUD = ((showScrollingText && ctrl.theAnyButton()) || (setupScrollingText ));




        if (yeetHUD) {
            for (GameObject h : hudObjects) {
                h.kill();
            }
            yeetHUD = false;
        } else{
            for (GameObject h : hudObjects) {
                h.update();
                if (!h.dead) {
                    aliveHUD.add(h);
                }
            }
        }


        if (aliveHUD.isEmpty()){
            if (showScrollingText) {
                //showingScores = false;
                //showingIntro = false;
                showScrollingText = false;
                aliveHUD.add(titleText.revive());
                aliveHUD.add(subtitleText.revive());
                aliveHUD.add(play.revive());
                aliveHUD.add(showScores.revive());
                menuOnscreen = true;

            } else if (setupScrollingText){
                aliveHUD.addAll(scrollingTextToAdd);
                menuOnscreen = false;
                setupScrollingText = false;
                //scrollingTextToAdd.clear();
                showScrollingText = true;
                ctrl.noAction();
            }
        } else if (showScrollingText && ctrl.theAnyButton()){
            yeetHUD = true;
        }

        for(GameObject g: dead){
            if (isGenericLargerAsteroid(g)){
                spawnChildren((GenericLargerAsteroid)g);
            }
        }


        if (Math.random() < 0.001){
            readyToSpawnAsteroid = true;
        }
        if (readyToSpawnAsteroid && !bigAsteroidStack.isEmpty()){
            alive.add(bigAsteroidStack.pop().revive());
            readyToSpawnAsteroid = false;
        }

        if (Math.random() < 0.005){
            readyToSpawnShip = true;
        }

        if (readyToSpawnShip && !enemyShips.isEmpty()){
            alive.add(enemyShips.pop().revive());
            readyToSpawnShip = false;
        }

        //ArrayList<GameObject> aliveHUD = new ArrayList<>();


        /*
        for (GameObject h: hudObjects){
            h.update();
            if (!h.dead){
                aliveHUD.add(h);
            }
        }*/


        /*
        synchronized (TitleScreen.class) {
            gameObjects.clear();
            gameObjects.addAll(alive);

            hudObjects.clear();
            hudObjects.addAll(aliveHUD);

            alive.clear();
            aliveHUD.clear();
            dead.clear();
        } /* */

        cleanLists();

        if (ctrl.isClicked()){
            clicked(ctrl.clickLocation());
            //ctrl.noClick();
        }

    }

    protected void clicked(Point p){

        if (menuOnscreen) {
            if (play.isClicked(p)) {
                endThis();
            } else if (showScores.isClicked(p)){
                yeetHUD = true;
                showScrollingText = false;
                scrollingTextToAdd.clear();
                createScrollingText(highScores.longwindedLeaderboard(),60,100);
                scrollingTextToAdd.add(new StringObject(new Vector2D(HALF_WIDTH,FRAME_HEIGHT),Vector2D.polar(UP_RADIANS,100),"LEADERBOARD",StringObject.MIDDLE_ALIGN,StringObject.big_sans));
            }
        }
    }

    /*@Override
    Vector2D getShipPosition() {
        if (!gameObjects.isEmpty()){
            int range = gameObjects.size();
            return (gameObjects.get((int)(Math.random() * range-2)+1).position);
        }
        return new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT);
    }*/

    private void createScrollingText(ArrayList<String> theText, int distFromBottom, double scrollSpeed){
        scrollingTextToAdd.clear();
        yeetHUD = true;
        showScrollingText = false;
        for (String s: theText){
            scrollingTextToAdd.add(new StringObject(new Vector2D(HALF_WIDTH,FRAME_HEIGHT+distFromBottom),Vector2D.polar(UP_RADIANS,scrollSpeed),s,StringObject.MIDDLE_ALIGN));
            //distFromBottom += distBetweenLines;
            distFromBottom += 22;
        }
        setupScrollingText =true;
    }
}
