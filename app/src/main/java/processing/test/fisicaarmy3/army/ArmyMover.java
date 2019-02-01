package processing.test.fisicaarmy3.army;

import fisica.FContact;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;
import processing.test.fisicaarmy3.utils.GameConstants;

public class ArmyMover {

    private ArmyMoverStateMarch moverStateFollowPath = new ArmyMoverStateMarch(this);
    private ArmyMoverStateWar moverStateWar = new ArmyMoverStateWar(this);
    private ArmyMoverStateRetreat moverStateRetreat = new ArmyMoverStateRetreat(this);

    private ArmyMoverState moverState = moverStateFollowPath;

    private SoldiersMover soldierMover;

    private ArmyMover(){}

    public static ArmyMover createArmy(float x, float y, String name, float r, float g, float b){
        ArmyMover       armyMover       = new ArmyMover();
        SoldiersMover   soldiersMover   = new SoldiersMover(x,y,name,r,g,b,armyMover);
        armyMover.setSoldierMover(soldiersMover);

       return armyMover;
    }

    private void setSoldierMover(SoldiersMover soldierMover){
        this.soldierMover = soldierMover;
    }

    ArmyMover firstSelectionArmy(float x, float y) {
        return moverState.firstSelectionArmy(x, y);
    }

    void secondSelection(float x, float y) {
        moverState.secondSelection(x, y);
    }

    void update() {
        moverState.update();
        soldierMover.updateArmy();
    }

    void updateWithZoomFactor() {
        soldierMover.updateArmyToZoom();
        moverState.updateWithZoomFactor();
    }

    void updateMapPosition(float dx, float dy) {
        moverState.updateMapPosition(dx, dy);
        soldierMover.updateMapPosition(dx,dy);
    }

    void dragFromArmy(float x, float y) {
        moverState.dragFromArmy(x, y);
    }

    void display(boolean selected) {
        soldierMover.updateArmyColors(selected);
        moverState.display(selected);
        PVector p = getArmyCenter();
        FisicaArmy3.fiscaArmy3.text(moverState.toString(),p.x+ GameConstants.armySelectorSize/2,p.y);
        FisicaArmy3.fiscaArmy3.text(soldierMover.getStateName(),p.x+GameConstants.armySelectorSize/2,p.y+25);

    }

    public void contactStarted(FContact c) {
        moverState.contactStarted(c);
        soldierMover.contactStarted(c);
    }

    void changeToWarState(FContact c){
        soldierMover.contactStarted(c);
        moverState = moverStateWar;
    }

    void changeToRetreatState(float x, float y){
        moverStateRetreat.setRetreatLocation(x,y);
        moverState   = moverStateRetreat;
        soldierMover.changeToRetreatState(x,y);
    }

    void changeToMarchState(){
        soldierMover.changeToMarchState();
        moverStateFollowPath.initState();
        moverState = moverStateFollowPath;
    }

    boolean isInsideArmyArea(float x, float y){
        boolean result = false;
        PVector msp = getArmyCenter();
        if (FisicaArmy3.fiscaArmy3.dist(x, y, msp.x, msp.y) < GameConstants.armySelectorSize /2) {
            result = true;
        }
        return result;
    }

    PVector getArmyCenter(){
        return soldierMover.meanSoldierPosition();
    }

    boolean isNotStateWar(){
        return (this.moverState != this.moverStateWar);
    }

    boolean isMarching(){
        return soldierMover.isMarching();
    }


    public void commandArmyHeading(float x, float y) {
        soldierMover.commandArmyHeading(x,y);
    }

    public void commandArmyPosition(float x, float y) {
        soldierMover.commandArmyPosition(x,y);
    }
}
