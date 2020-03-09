package game;


public class MainClass {


    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        GameRunner runner = new GameRunner(frame);
        frame.pack();
        runner.run();
    }

}
