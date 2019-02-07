package processing.test.fisicaarmy3.army_implementations;

import fisica.FContact;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;
import processing.test.fisicaarmy3.army.ArmyMover;
import processing.test.fisicaarmy3.army.ArmyMoverType;
import processing.test.fisicaarmy3.utils.GameConstants;

public class ArmyFootSoldiers implements ArmyMoverType {

    private ArmyMover mover;

    public static ArmyFootSoldiers createArmy(float x, float y, String name, float r, float g, float b){
        ArmyFootSoldiers army = new ArmyFootSoldiers();
        army.mover      = ArmyMover.createArmy(x,y,name,r,g,b);
        return army;
    }

    private ArmyFootSoldiers(){
    }

    public ArmyMoverType firstSelectionArmy(float x, float y) {
        ArmyMoverType result = null;
        if(mover.firstSelectionArmy(x,y)!=null){
            result = this;
        }
        return result;
    }

    @Override
    public void secondSelection(float x, float y) {
        mover.secondSelection(x,y);
    }

    @Override
    public void update() {
        mover.update();
        shootArrowsUpdate(FisicaArmy3.shootArrowsFlag);
    }

    @Override
    public void updateWithZoomFactor() {
        mover.updateWithZoomFactor();
    }

    @Override
    public void updateMapPosition(float dx, float dy) {
        mover.updateMapPosition(dx,dy);
    }

    @Override
    public void dragFromArmy(float x, float y) {
        mover.dragFromArmy(x,y);
    }

    @Override
    public void display(boolean selected) {
        mover.display(selected);
        PVector p = mover.getArmyCenter();
        FisicaArmy3.fiscaArmy3.noFill();
        FisicaArmy3.fiscaArmy3.ellipse(p.x,p.y, GameConstants.armySelectorSize*3,GameConstants.armySelectorSize*3);
    }

    @Override
    public void contactStarted(FContact c) {
        mover.contactStarted(c);
    }

    @Override
    public PVector getArmyCenter() {
        return mover.getArmyCenter();
    }

    @Override
    public void commandArmyHeading(float x, float y) {
        mover.commandArmyHeading(x,y);
    }

    @Override
    public void commandArmyPosition(float x, float y) {
        mover.commandArmyPosition(x,y);
    }


    public void shootArrowsUpdate(boolean shoot){
        

    }

}
