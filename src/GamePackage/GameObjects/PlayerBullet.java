package GamePackage.GameObjects;

import java.awt.*;

public class PlayerBullet extends Bullet {

    public PlayerBullet(){ super(); objectType = PLAYER_OBJECT; } //bullet but it's a PLAYER_OBJECT instead

    @Override
    //colour is blue, fading to white as time to live expires
    protected void updateColour() { objectColour = new Color(255 - (5*timeToLive),255 - (3*timeToLive),255 - (timeToLive/2)); }

}
