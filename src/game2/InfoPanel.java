package game2;

import utilities.AttributeLabel;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    AttributeLabel<Integer> scoreLabel;
    AttributeLabel<Integer> levelLabel;
    AttributeLabel<Integer> livesLabel;


    //Game game;

    /*
    InfoPanel(Game game){

        this.game = game;


    }*/

    InfoPanel(){

        this.setLayout(new GridLayout(1,3));
        scoreLabel = new AttributeLabel<>("Score: ",0);
        levelLabel = new AttributeLabel<>("Level: ",0);
        livesLabel = new AttributeLabel<>("Lives: ",0);

        this.add(scoreLabel);
        this.add(levelLabel);
        this.add(livesLabel);
    }

    void updateScore(int newScore){
        scoreLabel.showValue(newScore);
    }

    void updateLevel(int newLevel){
        levelLabel.showValue(newLevel);
    }

    void updateLives(int newLives){
        livesLabel.showValue(newLives);
    }

    void updateAll(int newScore, int newLevel, int newLives){
        updateScore(newScore);
        updateLevel(newLevel);
        updateLives(newLives);
    }
}
