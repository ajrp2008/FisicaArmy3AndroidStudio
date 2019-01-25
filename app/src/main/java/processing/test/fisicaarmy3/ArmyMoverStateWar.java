package processing.test.fisicaarmy3;

import fisica.FContact;
import processing.core.PApplet;
import processing.core.PVector;

class ArmyMoverStateWar implements ArmyMoverState {

  private FisicaArmy3 fisicaArmy3;
  PVector retreatToLocation = null;

  ArmyMover armyMover;

  ArmyMoverStateWar(FisicaArmy3 fisicaArmy3, ArmyMover armyMover) {
    this.fisicaArmy3 = fisicaArmy3;
    this.armyMover = armyMover;
  }

  public ArmyMover firstSelectionArmy(float x, float y){
    ArmyMover newSelectedArmy = null;
    PVector msp = armyMover.soldierMover.meanSoldierPosition();
    //FIRST SELECTION: SELECT THIS ARMY
    if (PApplet.dist(msp.x, msp.y, x, y)<armyMover.armySelectorSize/2) {
      newSelectedArmy = armyMover;
          retreatToLocation = null;

    }
    return newSelectedArmy;
  }

  public void dragFromArmy(float x, float y){
    if(retreatToLocation == null) retreatToLocation = new PVector();
    retreatToLocation.set(x,y);

  }

  public void secondSelection(float x, float y) {
    if(retreatToLocation != null){
    if (PApplet.dist(retreatToLocation.x,retreatToLocation.y, x, y)<armyMover.armySelectorSize/2) {
      this.retreatToLocation = null;
      armyMover.moverStateRetreat.retreatToLocation.set(x,y);
      armyMover.moverState   = armyMover.moverStateRetreat;
      armyMover.soldierMover.retreatTo(x,y);
    }
    }
  }

  public void update() {
    armyMover.soldierMover.updateArmy();
  }

  public void display(boolean selected) {
      if (selected) FisicaArmy3.fiscaArmy3.stroke(armyMover.soldierMover.r, armyMover.soldierMover.g, armyMover.soldierMover.b, 300);
      else FisicaArmy3.fiscaArmy3.stroke(armyMover.soldierMover.r,armyMover.soldierMover.g, armyMover.soldierMover.b, 100);
    FisicaArmy3.fiscaArmy3.noFill();

      if(retreatToLocation != null) FisicaArmy3.fiscaArmy3.ellipse(retreatToLocation.x,retreatToLocation.y,30,30);

  }


  public void updateWithZoomFactor() {
    armyMover.armySelectorSize*= GameConstants.zoomFactor;
    armyMover.soldierMover.updateArmyToZoom();
        if(retreatToLocation!=null)
        retreatToLocation.mult(GameConstants.zoomFactor);

  }

  public void updateMapPosition(float dx, float dy) {
    armyMover.soldierMover.updateMapPosition(dx, dy);
    if(retreatToLocation!=null)
            retreatToLocation.add(dx,dy);

  }

  public void contactStarted(FContact c) {
    this.armyMover.soldierMover.contactStarted(c);
  }
}
