package processing.test.fisicaarmy3;

import fisica.FContact;
import processing.core.PApplet;
import processing.core.PVector;

class ArmyMoverStateRetreat implements ArmyMoverState{

  PVector retreatToLocation = new PVector();

  private ArmyMover armyMover;

  ArmyMoverStateRetreat(ArmyMover armyMover){
    this.armyMover = armyMover;
  }

  public ArmyMover firstSelectionArmy(float x, float y){
    return null;
  }

  public void dragFromArmy(float x, float y){}

  public void secondSelection(float x, float y){}

  public void update(){
        armyMover.soldierMover.updateArmy();
        PVector msp = armyMover.soldierMover.meanSoldierPosition();
        if(PApplet.dist(retreatToLocation.x,retreatToLocation.y, msp.x,msp.y)<armyMover.armySelectorSize/2){
          this.armyMover.changeToMarchState();
        }
  }

  public void display(boolean selected){
        if(retreatToLocation != null) FisicaArmy3.fiscaArmy3.ellipse(retreatToLocation.x,retreatToLocation.y,30,30);
  }

  public void updateWithZoomFactor(){
      armyMover.armySelectorSize*= GameConstants.zoomFactor;
    armyMover.soldierMover.updateArmyToZoom();
    retreatToLocation.mult(GameConstants.zoomFactor);
}

  public void updateMapPosition(float dx, float dy){
        armyMover.soldierMover.updateMapPosition(dx, dy);
        retreatToLocation.add(dx,dy);
  }

  public void contactStarted(FContact c){}

}
