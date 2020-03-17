package GamePackage;

import java.awt.*;

//basically a class that implements the abstract methods of Controller, by making some default methods

public class ControllerAdapter implements Controller {

    protected Action action;

    public ControllerAdapter(){ action = new Action(); }

    @Override
    public Action action() { return action; }

    @Override
    public void noAction() { action.noAction(); }

    @Override
    public void revive() {}

    @Override
    public void setEnemyShip(EnemyShip e) {}

    @Override
    public boolean theAnyButton() { return action.theAnyButton(); }

    @Override
    public boolean isClicked() { return action.clicked; }

    @Override
    public Point clickLocation() {
        if (isClicked()) {
            action.clicked = false;
            return action.clickLocation;
        } else{
            return null;
        }
    }

    @Override
    public boolean shot() { return action.shoot; }

    @Override
    public boolean p() { return action.p; }

}
