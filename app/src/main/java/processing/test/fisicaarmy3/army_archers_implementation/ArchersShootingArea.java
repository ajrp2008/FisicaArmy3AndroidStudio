package processing.test.fisicaarmy3.army_archers_implementation;

import fisica.FCircle;
import processing.core.PGraphics;
import processing.test.fisicaarmy3.FisicaArmy3;


public class ArchersShootingArea extends FCircle {

    ArmyArchersMover armyMover;

    public ArchersShootingArea(float v, ArmyArchersMover armyMover) {
        super(v);
        this.armyMover = armyMover;
    }

    @Override
    public void draw(PGraphics pGraphics) {
        //super.draw(pGraphics);
        //HEADING ??
        FisicaArmy3.fiscaArmy3.noFill();
        FisicaArmy3.fiscaArmy3.ellipse(getX(),getY(), getSize(),getSize());
    }
}
