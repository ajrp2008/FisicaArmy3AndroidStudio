package processing.test.fisicaarmy3.army_archers_implementation;

import fisica.FCircle;
import processing.test.fisicaarmy3.FisicaArmy3;

public class Arrow extends FCircle {

    boolean isFlying = false;

    public Arrow(float v,float x,float y) {
        super(v);
        setGrabbable(false);
        setSensor(true);
        setDamping(0);
        setPosition(x,y);
        FisicaArmy3.fiscaArmy3.world.add(this);
    }

    public void shot(float x,float y){
        System.out.println("Shooting");
        setPosition(x,y);
        setVelocity(130,130);
    }

}
