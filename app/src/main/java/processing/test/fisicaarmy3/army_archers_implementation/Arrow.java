package processing.test.fisicaarmy3.army_archers_implementation;

import fisica.FCircle;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;

public class Arrow extends FCircle {

    boolean isFlying = false;
    PVector p = new PVector(130,0);

    public Arrow(float v,float x,float y) {
        super(v);
        setGrabbable(false);
        setSensor(true);
        setDamping(0);
        setPosition(x,y);
        FisicaArmy3.fiscaArmy3.world.add(this);
    }

    public void shot(float x,float y, float angle){
        System.out.println("Shooting");
        setPosition(x,y);
        p.set(130,0);
        p.rotate(angle);
        setVelocity(p.x,p.y);
    }

}
