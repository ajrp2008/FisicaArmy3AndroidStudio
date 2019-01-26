package processing.test.fisicaarmy3;

import fisica.FContact;

class ArmyMover {

    float armySelectorSize = GameConstants.armySelectorSizeStart;


    private ArmyMoverStateMarch moverStateFollowPath = new ArmyMoverStateMarch(this);
    private ArmyMoverStateWar moverStateWar = new ArmyMoverStateWar(this);
    private ArmyMoverStateRetreat moverStateRetreat = new ArmyMoverStateRetreat(this);

    private ArmyMoverState moverState = moverStateFollowPath;

    SoldiersMover soldierMover;

    ArmyMover(SoldiersMover army) {
        this.soldierMover = army;
        this.soldierMover.armyMover = this;
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
        moverState.updateWithZoomFactor();
    }

    void updateMapPosition(float dx, float dy) {
        moverState.updateMapPosition(dx, dy);
    }

    void dragFromArmy(float x, float y) {
        moverState.dragFromArmy(x, y);
    }

    void display(boolean selected) {
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
        moverStateFollowPath.wayPoints.clear();
        moverStateFollowPath.approveRoute = false;
        moverStateFollowPath.nextPoint = null;
        moverState = moverStateFollowPath;

    }

}
