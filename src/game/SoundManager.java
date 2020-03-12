package game;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import java.io.File;

// SoundManager for Asteroids

public class SoundManager {

    static int nBullet = 0;
    static boolean thrusting = false;
    static boolean playingMenu = false;
    static boolean playingGameTheme = false;

    // this may need modifying
    final static String path = "sounds/";

    // note: having too many clips open may cause
    // "LineUnavailableException: No Free Voices"
    public final static Clip[] bullets = new Clip[15];

    /*
    public final static Clip bangLarge = getClip("thrust");
    public final static Clip bangMedium = getClip("bangMedium");
    public final static Clip bangSmall = getClip("bangSmall");
    public final static Clip beat1 = getClip("beat1");
    public final static Clip beat2 = getClip("beat2");
    public final static Clip extraShip = getClip("extraShip");
    public final static Clip fire = getClip("fire");
    public final static Clip saucerBig = getClip("saucerBig");
    public final static Clip saucerSmall = getClip("saucerSmall");
    public final static Clip thrust = getClip("thrust");
     */

    public final static Clip andYouFailed = getClip("andYouFailed");
    public final static Clip gameTheme = getClip("AnSoundtrack");
    public final static Clip bweb = getClip("bweb");
    public final static Clip bwoab = getClip("bwoab");
    public final static Clip clap = getClip("clap");
    public final static Clip crunch = getClip("crunch");
    public final static Clip intimidating = getClip("duDOOOO");
    public final static Clip hum = getClip("hum");
    public final static Clip longCrunch = getClip("longCrunch");
    public final static Clip menuTheme = getClip("MenuTheme");
    public final static Clip mediumCrunch = getClip("notAsLongCrunch");
    public final static Clip ohno = getClip("ohno");
    public final static Clip solidHit = getClip("solidHit");
    public final static Clip explosion = getClip("WeirdExplosion");
    public final static Clip yerDead = getClip("yerDead");
    public final static Clip nice = getClip("nice");

    /*
    public final static Clip[] clips = {bangLarge, bangMedium, bangSmall, beat1, beat2,
            extraShip, fire, saucerBig, saucerSmall, thrust};

     */

    public final static Clip[] clips = {andYouFailed,bweb,bwoab,nice,clap,crunch,intimidating,
    hum,longCrunch,menuTheme,mediumCrunch,ohno,solidHit,explosion,yerDead,gameTheme};

    static {
        for (int i = 0; i < bullets.length; i++)
            bullets[i] = getClip("clap");
    }

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 20; i++) {
            fire();
            Thread.sleep(100);
        }

        for (Clip clip : clips) {
            play(clip);
            Thread.sleep(1000);
        }
    }

    // methods which do not modify any fields

    public static void play(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }

    public static void playYouFailed(){
        play(andYouFailed);
    }

    public static void playBweb(){
        play(bweb);
    }

    private static Clip getClip(String filename) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
                    + filename + ".wav"));
            clip.open(sample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }

    // methods which modify (static) fields

    public static void fire() {
        // fire the n-th bullet and increment the index
        Clip clip = bullets[nBullet];
        clip.setFramePosition(0);
        clip.start();
        nBullet = (nBullet + 1) % bullets.length;
    }

    public static void startThrust() {
        if (!thrusting) {
            hum.loop(-1);
            thrusting = true;
        }
    }

    public static void stopThrust() {
        hum.loop(0);
        hum.stop();
        thrusting = false;
    }

    public static void startMenu(){
        if (!playingMenu){
            menuTheme.loop(-1);
            playingMenu = true;
        }
    }

    public static void stopMenu(){
        menuTheme.loop(0);
        menuTheme.stop();
        playingMenu = false;
    }

    public static void startGame(){
        if (!playingGameTheme){
            gameTheme.loop(-1);
            playingGameTheme = true;
        }
    }

    public static void stopGame(){
        gameTheme.loop(0);
        gameTheme.stop();
        playingGameTheme = false;
    }









    // Custom methods playing a particular sound
    // Please add your own methods below

    /*
    public static void asteroids() {
        play(bangMedium);
    }
    public static void extraShip() {
        play(extraShip);
    }*/

}