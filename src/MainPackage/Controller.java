package MainPackage;

import java.awt.*;

public interface Controller {
    Action action();

    void noAction();

    void revive();

    void noClick();

    void setEnemyShip(EnemyShip e);

    boolean theAnyButton();

    boolean isClicked();

    Point clickLocation();

    boolean shot();

}