package processing.test.fisicaarmy3.army;

import fisica.FContact;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;
import processing.test.fisicaarmy3.utils.GameConstants;

public class ArmyMover implements ArmyMoverType {

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

    @Override
    public ArmyMoverType firstSelectionArmy(float x, float y) {
        return moverState.firstSelectionArmy(x, y);
    }

    @Override
    public void secondSelection(float x, float y) {
        moverState.secondSelection(x, y);
    }

    @Override
    public void update() {
        moverState.update();
        soldierMover.updateArmy();
    }

    @Override
    public void updateWithZoomFactor() {
        soldierMover.updateArmyToZoom();
        moverState.updateWithZoomFactor();
    }

    @Override
    public void updateMapPosition(float dx, float dy) {
        moverState.updateMapPosition(dx, dy);
        soldierMover.updateMapPosition(dx,dy);
    }

    @Override
    public void dragFromArmy(float x, float y) {
        moverState.dragFromArmy(x, y);
    }

    @Override
    public void display(boolean selected) {
        soldierMover.updateArmyColors(selected);
        moverState.display(selected);
        PVector p = getArmyCenter();
       // FisicaArmy3.fiscaArmy3.text(moverState.toString(),p.x+ GameConstants.armySelectorSize/2,p.y);
       // FisicaArmy3.fiscaArmy3.text(soldierMover.getStateName(),p.x+GameConstants.armySelectorSize/2,p.y+25);

    }

    @Override
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

    @Override
    public PVector getArmyCenter(){
        return soldierMover.meanSoldierPosition();
    }

    boolean isNotStateWar(){
        return (this.moverState != this.moverStateWar);
    }

    boolean isMarching(){
        return soldierMover.isMarching();
    }


    @Override
    public void commandArmyHeading(float x, float y) {
        soldierMover.commandArmyHeading(x,y);
    }

    @Override
    public void commandArmyPosition(float x, float y) {
        soldierMover.commandArmyPosition(x,y);
    }
}
