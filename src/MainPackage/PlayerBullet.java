package MainPackage;

import java.awt.*;


public class PlayerBullet extends Bullet {

    PlayerBullet(){
        super();
        objectType = PLAYER_OBJECT;
    }

    @Override
    protected void updateColour() {
        objectColour = new Color(255 - (5*timeToLive),255 - (3*timeToLive),255 - (timeToLive/2));//, 128);
    }


    /*
    public void hit(){
        dead = true;
    }*/

    /*
    public void hitLogic(){
        //just here to not break the abstract method basically
    }*/



}
