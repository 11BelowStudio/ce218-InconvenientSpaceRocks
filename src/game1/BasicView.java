package game1;

import javax.swing.*;
import java.awt.*;

public class BasicView extends JComponent {
    // background colour
    public static final Color BG_COLOR = Color.black;

    private BasicGame game;

    public BasicView(BasicGame game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        // paint the background
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        //Now, in order to actually see the asteroids you'll need to add a for-each loop to the paintComponent method of BasicView that draws all the asteroids on the screen.
        for (BasicAsteroid ba: game.asteroids){
            ba.draw(g);
            //basically calls the draw method of each asteroid
        }
        game.ship.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}