package GamePackage;

import utilities.HighScoreHandler;
import utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;

import static GamePackage.Constants.*;

public class TitleScreen extends Model {





    private boolean readyToSpawnAsteroid;


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


        menuOnscreen = false;

        setupScrollingText = true;

        showScrollingText = false;

        yeetHUD = false;

        createScrollingText(LiterallyJustTheOpeningCreditsThing.openingArrayList,30,25);


        /*
        mediumAsteroidsToYeet = 0;
        smallAsteroidsToYeet = 0;*/
    }


    public TitleScreen revive(){
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

        menuOnscreen = false;
        setupScrollingText = true;
        showScrollingText = false;
        yeetHUD = false;

        createScrollingText(LiterallyJustTheOpeningCreditsThing.openingArrayList,30,25);

        yeetHUD = false;

        return this;
    }

    @Override
    public void update() {

        if (ctrl.shot()){
            endThis(); //ends the title screen instantly if the player presses the space bar
        }

        for (GameObject g: gameObjects) {

            if (g.isDead()){
                //dead stuff added to dead list
                dead.add(g);
                //dead stuff put into dead stack
                if (isAsteroid(g)){
                    if (g instanceof BigAsteroid){
                        bigAsteroidStack.push((BigAsteroid) g);
                    } else if(g instanceof MediumAsteroid){
                        mediumAsteroidStack.push((MediumAsteroid) g);
                    } else if (g instanceof Asteroid){
                        asteroidStack.push((Asteroid) g);
                    }
                }else if (g instanceof EnemyShip){
                    enemyShips.push((EnemyShip) g);
                } else if (g instanceof EnemyBullet){
                    enemyBullets.push((EnemyBullet) g);
                }
            } else{

                g.update(); //only alive stuff is updated


                if (g instanceof EnemyShip){
                    enemyFire((EnemyShip) g);
                    //sees if it's an enemy ship that can shoot
                }

                //this object is added to alive list
                alive.add(g);

            }
        }


        collisionHandler();


        //boolean yeetEverything = (  ((showingIntro || showingScores) && ctrl.theAnyButton()));

        //yeetHUD = ((showScrollingText && ctrl.theAnyButton()) || (setupScrollingText ));




        if (yeetHUD) {
            //deletes all the HUD objects if they need to be yote
            for (GameObject h : hudObjects) {
                h.kill();
            }
            yeetHUD = false;
        } else{
            //updates them all if they don't need to be yote
            for (GameObject h : hudObjects) {
                h.update();
                if (!h.isDead()) {
                    aliveHUD.add(h);
                }
            }
        }


        if (aliveHUD.isEmpty()){
            if (showScrollingText) {
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


        if (!enemyShips.isEmpty() && Math.random() < 0.005){
            alive.add(enemyShips.pop().revive());
        }

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
                createScrollingText(highScores.StringArrayListLeaderboard(),60,100);
                scrollingTextToAdd.add(new StringObject(new Vector2D(HALF_WIDTH,FRAME_HEIGHT),100,"LEADERBOARD",StringObject.MIDDLE_ALIGN,StringObject.big_sans));
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
            scrollingTextToAdd.add(new StringObject(new Vector2D(HALF_WIDTH,FRAME_HEIGHT+distFromBottom),scrollSpeed,s,StringObject.MIDDLE_ALIGN));
            //distFromBottom += distBetweenLines;
            distFromBottom += 22;
        }
        setupScrollingText =true;
    }
}
