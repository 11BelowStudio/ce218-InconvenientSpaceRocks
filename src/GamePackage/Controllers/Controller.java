package GamePackage.Controllers;

import GamePackage.GameObjects.EnemyShip;

import java.awt.*;
//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene) (enhanced by me)
public interface Controller {
    Action action();

    void noAction();

    void revive();

    void setEnemyShip(EnemyShip e);

    boolean theAnyButton();

    boolean isClicked();

    Point clickLocation();

    boolean shot();

    boolean p();

}