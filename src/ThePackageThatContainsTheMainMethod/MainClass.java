package ThePackageThatContainsTheMainMethod;

import GamePackage.GameRunner;

public class MainClass {

    public static void main(String[] args) {
        new GameRunner().run(); //creates a GameRunner, and runs it.
        //GameRunner runner = new GameRunner();
        //runner.run();
    }

}
