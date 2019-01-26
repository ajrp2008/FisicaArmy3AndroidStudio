package processing.test.fisicaarmy3;

import fisica.FContact;

class ArmyMover {

  float armySelectorSize   = GameConstants.armySelectorSizeStart;


  ArmyMoverStateFollowPath   moverStateFollowPath = new ArmyMoverStateFollowPath(this);
  ArmyMoverStateWar          moverStateWar        = new ArmyMoverStateWar(this);
  ArmyMoverStateRetreat      moverStateRetreat    = new ArmyMoverStateRetreat(this);

  ArmyMoverState             moverState           = moverStateFollowPath;

  SoldiersMover              soldierMover;

  ArmyMover(SoldiersMover army) {
    this.soldierMover = army;
    this.soldierMover.armyMover = this;
  }

  public ArmyMover firstSelectionArmy(float x, float y){
     return moverState.firstSelectionArmy(x,y);
  }

  public void secondSelection(float x, float y){
     moverState.secondSelection(x,y);
  }

  public void update() {
    moverState.update();
  }

  public void updateWithZoomFactor() {
    moverState.updateWithZoomFactor();
  }

  public void updateMapPosition(float dx, float dy) {
    moverState.updateMapPosition(dx,dy);
  }

  public void dragFromArmy(float x, float y) {
    moverState.dragFromArmy(x, y);
  }

  public void display(boolean selected) {
    moverState.display(selected);
  }

  public void contactStarted(FContact c){
    moverState.contactStarted(c);
  }

}
