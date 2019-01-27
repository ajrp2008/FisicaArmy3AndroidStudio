package processing.test.fisicaarmy3;

import fisica.FContact;

class ArmyMover {

    private ArmyMoverStateMarch moverStateFollowPath = new ArmyMoverStateMarch(this);
    private ArmyMoverStateWar moverStateWar = new ArmyMoverStateWar(this);
    private ArmyMoverStateRetreat moverStateRetreat = new ArmyMoverStateRetreat(this);

    private ArmyMoverState moverState = moverStateFollowPath;

    SoldiersMover soldierMover;

    private ArmyMover(){}

    static ArmyMover createArmy(float x, float y, String name, float r, float g, float b){
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
    }

    void updateWithZoomFactor() {
        soldierMover.updateArmyToZoom();
        moverState.updateWithZoomFactor();
    }

    void updateMapPosition(float dx, float dy) {
        moverState.updateMapPosition(dx, dy);
    }

    void dragFromArmy(float x, float y) {
        moverState.dragFromArmy(x, y);
    }

    void display(boolean selected) {
        soldierMover.updateArmyColors();
        moverState.display(selected);
    }

    public void contactStarted(FContact c) {
        moverState.contactStarted(c);
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

}
