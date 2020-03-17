package GamePackage.GameObjects;

import java.awt.*;

public class EnemyBullet extends Bullet {

    //same as a normal bullet, but it's an enemy
    public EnemyBullet(){ super();objectType = ENEMY_OBJECT; }


    @Override
    //bullets are orange, fading to white as time to live expires
    protected void updateColour() { objectColour = new Color(255 - (timeToLive/2),255 - (3*timeToLive),255 - (5*timeToLive)); }
}
