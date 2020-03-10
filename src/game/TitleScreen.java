package game;

import utilities.Vector2D;

import java.util.ArrayList;

import static game.Constants.FRAME_HEIGHT;
import static game.Constants.HALF_WIDTH;
import static game.GameObject.UP_RADIANS;

public class TitleScreen extends Model {

    static String[] openingCrawlThing = new String[]{
            "Like last thursday or something,",
            "in that one corner of the universe with the rocks,",
            "you know, near that hyperspace bypass,",
            "the one that smells of MS paint, that one.",
            "",
            "",
            "",
            "",
            "",
            "TITLE SCREEN OPENING CRAWL THING",
            "Episode 2562361.632*e+352",
            "THE LACK OF IDEAS",
            "",
            "",
            "pretend this says something deep",
            "like idk some sort of epic story",
            "about some intergalactic war in space",
            "(tbh they really should make a film",
            "about something like that at some point.",
            "actually, that's a rather good idea tbh",
            "imma take a note of it and then",
            "do it when no-one is looking",
            "and then get really really rich from it",
            "I'm thinking of calling it",
            "'Luminous spheroid of plasma Conflict'",
            "let me know if you think that sounds like a nice film name)",
            "Anywho, back to the point at hand",
            "uhh...",
            "",
            "",
            "",
            "oh yeah!",
            "",
            "Inconvenient Space Rocks!",
            "yeah there's a bunch of rocks in space",
            "and they're being very inconvenient.",
            "pls shoot them for us k thx bye",
            "and also dont die",
            "",
            "",
            "",
            "",
            "",
            "you know you can just press any key to skip this, right?",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "bruh",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "According to all known laws of aviation, there is no way that a rock should be able to fly",
            "unless it weighs 90kg.",
            "in which case you can get a trebuchet to make it fly 300 metres forward",
            "but that's besides the point because how the hecc did these rocks get up here",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "why are you still here? just to suffer?",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "still here?",
            "ok so press p when starting a level",
            "its fun ;)",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "uwu",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "upvotes to the left",
            "edit: we did it, Reddit!"
    };


    ArrayList<GameObject> aliveHUD;

    TitleScreen(PlayerController ctrl){
        super(ctrl);

        aliveHUD = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            bigAsteroidStack.push(new BigAsteroid());
        }
        for (int i = 0; i < 25; i++) {
            mediumAsteroidStack.push(new MediumAsteroid());
        }
        for (int i = 0; i < 150; i++){
            asteroidStack.push(new Asteroid());
        }

        int distFromBottom = 0;
        for (String s: openingCrawlThing) {
            hudObjects.add(new StringObject(new Vector2D(HALF_WIDTH,FRAME_HEIGHT+distFromBottom),Vector2D.polar(UP_RADIANS,100),s));
            distFromBottom += 20;
        }
    }

    @Override
    public void update() {

        alive.clear();
        dead.clear();

        for (GameObject g: gameObjects) {
            g.update();
            if (g.dead){
                dead.add(g);
                if (g instanceof BigAsteroid){
                    bigAsteroidStack.push((BigAsteroid) g);
                } else if(g instanceof MediumAsteroid){
                    mediumAsteroidStack.push((MediumAsteroid) g);
                } else if (g instanceof Asteroid){
                    asteroidStack.push((Asteroid) g);
                }
            } else{
                if (g instanceof Asteroid && Math.random() < 0.05){
                    asteroidStack.push((Asteroid) g);
                } else {
                    alive.add(g);
                }
            }
        }

        //ArrayList<GameObject> aliveHUD = new ArrayList<>();

        for (GameObject h: hudObjects){
            h.update();
            if (!h.dead){
                aliveHUD.add(h);
            }
        }

        synchronized (Game.class) {
            gameObjects.clear();
            gameObjects.addAll(alive);

            hudObjects.clear();
            hudObjects.addAll(aliveHUD);
        }

    }
}
