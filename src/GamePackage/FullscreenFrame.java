package GamePackage;

import java.awt.*;

//yes, this is pretty much the JEasyFrameFull class (Provided by Dr Dimitri Ognibene) but extending JEasyFrame

//was intended for some optional fullscreen functionality, but this didn't quite go to plan.
public class FullscreenFrame extends GameFrame {
    private final static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final static GraphicsDevice device = env.getScreenDevices()[0];
    private static final Rectangle RECTANGLE = device.getDefaultConfiguration().getBounds();
    private static final int WIDTH = RECTANGLE.width;
    private static final int HEIGHT = RECTANGLE.height;

    private static final Dimension FULLSCREEN_DIMENSION = new Dimension (WIDTH, HEIGHT);

    //same as GameFrame but it's undecorated
    public FullscreenFrame() {
        super();
        this.setUndecorated(true);
    }

    public void addView(View v){
        super.addView(v);
        v.setPreferredDimension(FULLSCREEN_DIMENSION);
        //this was intended to allow the dimension of the view to be changed
        // which in turn would be used to scale everything up/down in the game,
        // but I didn't quite manage to get this done so yeah this goes unimplemented
    }

    @Override
    //returning the dimension of the fullscreen stuff
    public Dimension getDimensions() {
        return FULLSCREEN_DIMENSION;
    }
}
