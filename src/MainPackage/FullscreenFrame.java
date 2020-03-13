package MainPackage;

import java.awt.*;

//yes, this is pretty much the JEasyFrameFull class but essentially if it extended JEasyFrame
public class FullscreenFrame extends GameFrame {
    public final static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final static GraphicsDevice device = env.getScreenDevices()[0];
    public static final Rectangle RECTANGLE = device.getDefaultConfiguration().getBounds();
    public static final int WIDTH = RECTANGLE.width;
    public static final int HEIGHT = RECTANGLE.height;

    public FullscreenFrame() {
        super();
        this.setUndecorated(true);
    }

    public void addView(View v){
        super.addView(v);
        v.setPreferredDimension(new Dimension (WIDTH, HEIGHT));
        //this was intended to allow the dimension of the view to be changed
        // which in turn would be used to scale everything up/down in the game,
        // but I didn't quite manage to get this done so yeah this goes unimplemented
    }
}
