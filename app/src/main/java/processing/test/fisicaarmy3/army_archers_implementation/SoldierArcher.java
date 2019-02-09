package processing.test.fisicaarmy3.army_archers_implementation;

import processing.core.PVector;
import processing.test.fisicaarmy3.army.Soldier;
import processing.test.fisicaarmy3.army.SoldiersMover;

public class SoldierArcher extends Soldier {

    Arrow arrow;

    public SoldierArcher(SoldiersMover army, PVector relPos) {
        super(army, relPos);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    public void shoot(){
        if(arrow==null){
            arrow = new Arrow(10,getX(),getY());
        }
        arrow.setSize(arrow.getSize());//bug fix fisica : to reset arrows...
        arrow.setPosition(arrow.getX(),arrow.getY());//bug fix fisica : to reset arrows...

        arrow.shot(this.getX(),this.getY());
    }
}
