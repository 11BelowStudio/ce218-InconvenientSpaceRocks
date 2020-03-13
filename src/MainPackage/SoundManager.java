package MainPackage;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.util.Arrays;

// SoundManager for Asteroids

public class SoundManager {

    private static int nBullet = 0;
    private static boolean thrusting = false;
    private static boolean playingMenu = false;
    private static boolean playingGameTheme = false;


    // this may need modifying
    private final static String path = "sounds/";

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
    public final static Clip solidHit = getClip("solidHit");
    public final static Clip explosion = getClip("WeirdExplosion");
    private final static Clip yerDead = getClip("yerDead");
    public final static Clip nice = getClip("nice");

    /*
    public final static Clip[] clips = {bangLarge, bangMedium, bangSmall, beat1, beat2,
            extraShip, fire, saucerBig, saucerSmall, thrust};

     */

    public final static Clip[] clips = {andYouFailed,bweb,bwoab,nice,clap,crunch,intimidating,
    hum,longCrunch,menuTheme,mediumCrunch,ohno,solidHit,explosion,yerDead,gameTheme};

    static {
        Arrays.fill(bullets, clap);
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

    //playing a particular sound


    private static void playThis(Clip whatClip){ play(whatClip); }

    public static void playBweb(){ playThis(bweb); }//,playingBweb); }

    public static void playBwoab(){ playThis(bwoab); }//,playingBwoab);}

    public static void playOhNo(){ playThis(ohno); } //,playingOhNo); }

    public static void playAndYouFailed(){ playThis(andYouFailed); }//,playingYouFailed); }

    public static void playCrunch(){ playThis(crunch); }//,playingCrunch); }

    public static void playMedCrunch(){ playThis(mediumCrunch); } //,playingMediumCrunch); }

    public static void playIntimidating(){ playThis(intimidating); }

    public static void playExplosion(){ playThis(explosion); }




    //A depreciated method which would have been used to only play one instance of a single clip at a time.
    //turns out the method I was using to limit it to one instance of a clip wouldn't work so yeah it goes unused
    private static void PlayThis(Clip whatClip, boolean clipIsPlaying){
        if (!clipIsPlaying){
            clipIsPlaying = true;
            play(whatClip);
            clipIsPlaying = false;
        }
    }
    /*
    private static boolean playingBweb = false;
    private static boolean playingBwoab = false;
    private static boolean playingOhNo = false;
    private static boolean playingYouFailed = false;
    private static boolean playingCrunch = false;
    private static boolean playingMediumCrunch = false;
     */

}