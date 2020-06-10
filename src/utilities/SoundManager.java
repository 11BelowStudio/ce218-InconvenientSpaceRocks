package utilities;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
//import java.io.File;
import java.util.Arrays;

// SoundManager for Asteroids

//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene)

//edited slightly by me

public class SoundManager {

    private static int nBullet = 0;
    private static boolean thrusting = false;
    private static boolean playingMenu = false;
    private static boolean playingGameTheme = false;


    // this may need modifying
    private final static String path = "/sounds/";

    // note: having too many clips open may cause
    // "LineUnavailableException: No Free Voices"
    private final static Clip[] bullets = new Clip[15];


    private final static Clip andYouFailed = getClip("andYouFailed");
    private final static Clip gameTheme = getClip("AnSoundtrack");
    private final static Clip bweb = getClip("bweb");
    private final static Clip bwoab = getClip("bwoab");
    private final static Clip clap = getClip("clap");
    private final static Clip crunch = getClip("crunch");
    private final static Clip intimidating = getClip("duDOOOO");
    private final static Clip hum = getClip("hum");
    private final static Clip longCrunch = getClip("longCrunch");
    private final static Clip menuTheme = getClip("MenuTheme");
    private final static Clip mediumCrunch = getClip("notAsLongCrunch");
    private final static Clip ohno = getClip("ohno");
    private final static Clip solidHit = getClip("solidHit");
    private final static Clip explosion = getClip("WeirdExplosion");
    //private final static Clip yerDead = getClip("yerDead");
    private final static Clip nice = getClip("nice");

    static {
        Arrays.fill(bullets, clap);
    }


    // methods which do not modify any fields

    private static void play(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }


    private static Clip getClip(String filename) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            //AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path + filename + ".wav"));
            AudioInputStream sample = AudioSystem.getAudioInputStream(SoundManager.class.getResourceAsStream(path + filename + ".wav"));
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

    //playing a particular sound
    public static void playBweb(){ play(bweb); }
    public static void playBwoab(){ play(bwoab); }
    public static void playOhNo(){ play(ohno); }
    public static void playAndYouFailed(){ play(andYouFailed); }
    public static void playCrunch(){ play(crunch); }
    public static void playMedCrunch(){ play(mediumCrunch); }
    public static void playIntimidating(){ play(intimidating); }
    public static void playExplosion(){ play(explosion); }
    public static void playNice(){ play(nice); }
    public static void playSolidHit(){ play(solidHit); }
    public static void playLongCrunch(){ play(longCrunch);}
    //public static void playYerDead(){ play(yerDead); }




    //A depreciated method which would have been used to only play one instance of a single clip at a time.
    //turns out the method I was using to limit it to one instance of a clip wouldn't work so yeah it goes unused
    /*
    private static void play(Clip whatClip, boolean clipIsPlaying){
        if (!clipIsPlaying){
            clipIsPlaying = true;
            play(whatClip);
            clipIsPlaying = false;
        }
    }
    */

}