package game;

import utilities.HighScoreHandler;

import javax.swing.*;

import java.awt.*;

import static game.Constants.DELAY;

public class GameRunner {

    GameFrame frame;

    EscapeListener esc;

    Game game;
    TitleScreen title;
    View view;
    HighScoreHandler highScores;
    PlayerController ctrl;

    Timer repaintTimer;

    boolean paused;

    Dimension viewDimension;

    GameRunner(GameFrame f) {
        frame = f;
        esc = new EscapeListener(this);
        frame.addKeyListener(esc);
        frame.pack();

        highScores = new HighScoreHandler("SaveData/scores.txt",frame);//, false);
        ctrl = new PlayerController();
        frame.addKeyListener(ctrl);
        frame.addMouseListener(ctrl);



        view = new View();

        frame.addView(view);
        viewDimension = view.getPreferredSize();

        //game = new Game(ctrl);

        title = new TitleScreen(ctrl, highScores);

        view.showModel(title,false);

        game = new Game(ctrl,highScores);

        //title = new TitleScreen(ctrl, highScores);

        //view.showTitle(title);

        //view.showModel(title,false);

        repaintTimer = new Timer(DELAY,
                ev -> view.repaint());
        paused = false;
    }

    public void pauseGame(){
        if (!paused){
            paused = true;
            repaintTimer.stop();
        }
    }
    public void unPause(){
        if (paused){
            paused = false;
            repaintTimer.start();
        }
    }


    private void mainLoop() throws InterruptedException {

        /*
        JOptionPane.showMessageDialog(
                frame,
                "press ok to start the blideo bame"
        );
        */
        int score;
        while (true) {

            //title = new TitleScreen(ctrl);


            /* TITLE SCREEN WILL GO HERE
            JOptionPane.showMessageDialog(
                    frame,
                    "press ok to start the blideo bame"
            );
            /* */

            //view.showTitle(title);




            //runGame(title);

            //repaintTimer.start();

            /*

            title = new TitleScreen(ctrl);

            view.showTitle(title);

            frame.pack();

            repaintTimer.start();

            runGame(title);

            /* */



            //game = new Game(ctrl, highScores);

            //title = new TitleScreen(ctrl, highScores);
            title.revive();

            //view.showTitle(title);

            view.showModel(title,false);

            //view.setVisible(true);
            frame.pack();
            System.out.println("* setup done *");


            repaintTimer.start();

            System.out.println("* title started *");

            //runGame(game);

            runTitle(title);

            repaintTimer.stop();


            System.out.println("* title stopped *");

            //game = new Game(ctrl,highScores);

            game.revive();

            //view.showGame(game);

            view.showModel(game,true);

            frame.pack();

            repaintTimer.start();

            runGame(game);


            System.out.println("* game stopped *");


            repaintTimer.stop();
            //repaintTimer = null;
            //view.setVisible(false);
            view.hideGame();
            frame.pack();

            System.out.println("* Removed stuff *");
            //repaintTimer.restart();
            //repaint();
        }
    }

    /*
    public void gameStart() {
        try{
            runGame();
        } catch(Exception e){
            e.printStackTrace();
        }
    }*/


    private void runGame(Game g) throws InterruptedException {
        SoundManager.startGame();
        while (!g.endGame){
            runLoop(g);
        }
        SoundManager.stopThrust();
        SoundManager.stopGame();
    }

    private void runTitle(TitleScreen t) throws InterruptedException{
        SoundManager.startMenu();
        while (!t.endGame){
            /*
            if (ctrl.action.mousePressed){
                if (t.clicked(ctrl.action.mousePressLocation)){
                    t.showHighScores(highScores.longwindedLeaderboard());
                }
            }*/
            runLoop(t);
        }
        SoundManager.stopMenu();
    }

    private void runLoop(Model m) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        if (!paused) {
            m.update();
        }
        long endTime = System.currentTimeMillis();
        long timeout = DELAY - (endTime - startTime);
        if (timeout > 0){
            Thread.sleep(timeout);
        }/* else{
                missedFrames++;
            }
            System.out.println(missedFrames);
            /* */
    }

    public void quitPrompt(){
        pauseGame();
        if ((
                JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to quit?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION
                )
        ) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(frame, "aight bye", "closing", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(frame, "then why did you press escape? smh my head", "resuming", JOptionPane.PLAIN_MESSAGE);
        }
        unPause();
    }

    public void run() {
        try {
            mainLoop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
