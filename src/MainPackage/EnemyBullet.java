package MainPackage;

import java.awt.*;

public class EnemyBullet extends Bullet {


    EnemyBullet(){
        super();
        objectType = ENEMY_OBJECT;
    }


    @Override
    protected void updateColour() {
        objectColour = new Color(255 - (timeToLive/2),255 - (3*timeToLive),255 - (5*timeToLive));//, 128);
    }
}
