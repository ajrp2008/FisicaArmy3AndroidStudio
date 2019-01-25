package processing.test.fisicaarmy3;

import fisica.FContact;

class ArmyMover {

  private FisicaArmy3 fisicaArmy3;
  float armySelectorSize   = GameConstants.armySelectorSizeStart;


  ArmyMoverStateFollowPath   moverStateFollowPath = new ArmyMoverStateFollowPath(fisicaArmy3, this);
  ArmyMoverStateWar          moverStateWar        = new ArmyMoverStateWar(fisicaArmy3, this);
  ArmyMoverStateRetreat      moverStateRetreat    = new ArmyMoverStateRetreat(fisicaArmy3, this);

  ArmyMoverState             moverState           = moverStateFollowPath;

  SoldiersMover              soldierMover;

  ArmyMover(FisicaArmy3 fisicaArmy3, SoldiersMover army) {
    this.fisicaArmy3 = fisicaArmy3;
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
