package processing.test.fisicaarmy3.army_archers_implementation;

import fisica.FCircle;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;
import processing.test.fisicaarmy3.utils.GameConstants;

public class Arrow extends FCircle {

    private boolean outOfRange = false;
    private PVector velocity  = new PVector(130, 0);
    private PVector start     = new PVector(0,0);
    private float maxRange    = 500;

    Arrow(float v, float x, float y) {
        super(v);
        setGrabbable(false);
        setSensor(true);
        setDamping(0);
        setPosition(x, y);
    }

    void shot(float x, float y, float angle) {
        System.out.println("Shooting");

        outOfRange = false;

        setPosition(x, y);
        start.set(x,y);

        velocity.set(130, 0);
        velocity.rotate(angle);
        setVelocity(velocity.x, velocity.y);

        FisicaArmy3.fiscaArmy3.world.add(this);
    }


    @Override
    public void draw(PGraphics pGraphics) {
        if(!outOfRange)super.draw(pGraphics);

        float range = FisicaArmy3.dist(start.x,start.y,getX(),getY());
        if(range > maxRange) {
            outOfRange = true;
            FisicaArmy3.fiscaArmy3.world.remove(this);
        }
    }

    void updateMapPosArrow(float dx, float dy) {
       setPosition(getX() + dx, getY() + dy);
       start.add(dx,dy);
   }

    void updateToZoomArrow() {
       setSize(getSize()* GameConstants.zoomFactor);
       setPosition(getX()* GameConstants.zoomFactor, getY()* GameConstants.zoomFactor);
       start.mult(GameConstants.zoomFactor);
   }
}