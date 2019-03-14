package processing.test.fisicaarmy3.army_archers_implementation;

import fisica.FCircle;
import processing.core.PGraphics;
import processing.test.fisicaarmy3.FisicaArmy3;


public class ArchersShootingArea extends FCircle {

    ArmyArchersMover armyMover;
    boolean armyInsideZone = false;
    String name;


    public ArchersShootingArea(float v, ArmyArchersMover armyMover, String name) {
        super(v);
        this.armyMover = armyMover;
        this.name = name;
    }

    @Override
    public void draw(PGraphics pGraphics) {
        //super.draw(pGraphics);
        //HEADING ??
       // FisicaArmy3.fiscaArmy3.noFill();
       // FisicaArmy3.fiscaArmy3.ellipse(getX(),getY(), getSize(),getSize());
    }

    public String getName(){
        return this.name;
    }
}
