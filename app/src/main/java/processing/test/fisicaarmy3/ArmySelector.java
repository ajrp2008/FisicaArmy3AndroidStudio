package processing.test.fisicaarmy3;

import java.util.ArrayList;

import processing.core.PVector;

class ArmySelector {
  private FisicaArmy3 fisicaArmy3;
  float                 armySelectorSize  = GameConstants.armySelectorSizeStart;

  ArmyMover             selectedArmy      = null;
  ArrayList<ArmyMover> armyList          = new ArrayList<ArmyMover>();

  public ArmySelector(FisicaArmy3 fisicaArmy3) {
    this.fisicaArmy3 = fisicaArmy3;
  }

  public void addArmy(ArmyMover a) {
    armyList.add(a);
  }

  public boolean selectArmy(float x, float y) {
    ArmyMover newSelectedArmy = null;

    for (ArmyMover a : armyList) {
      ArmyMover selected =a.firstSelectionArmy(x,y);
      if(selected!=null)  newSelectedArmy = selected;
    }

    if(selectedArmy != null) selectedArmy.secondSelection(x,y);


    selectedArmy = newSelectedArmy;


    return selectedArmy != null;
  }

  public boolean dragFromArmy(float x, float y) {
    if (selectedArmy!=null) {
      selectedArmy.dragFromArmy(x, y);
    }
    return    selectedArmy != null;
  }

  public void updateWithZoomFactor() {
    armySelectorSize *= GameConstants.zoomFactor;
    for (ArmyMover ap : this.armyList) {
      ap.updateWithZoomFactor();
    }
  }

  public void updateMapPosition(float dx, float dy) {
    for (ArmyMover ap : fisicaArmy3.armySelector.armyList) {
      ap.updateMapPosition(dx,dy);
    }
  }

  public void update() {
    for (ArmyMover a : armyList) {
      a.update();
    }
  }

  public void drawSelector() {
    for (ArmyMover a : armyList) {

     // if(a==selectedArmy)continue;
      a.display(a == selectedArmy);
      //CENTER OF ARMY///////////////////////////
      PVector msp = a.soldierMover.meanSoldierPosition();
      fisicaArmy3.noStroke();
      if(a == selectedArmy) fisicaArmy3.fill(255,255,0,100);else fisicaArmy3.fill(30,0,0,100);
      fisicaArmy3.ellipse(msp.x, msp.y, armySelectorSize, armySelectorSize);
      fisicaArmy3.stroke(255,0,0);
      if(a == selectedArmy){
          fisicaArmy3.textSize(30); continue; }else fisicaArmy3.textSize(15);
      fisicaArmy3.text(a.soldierMover.name,msp.x+armySelectorSize/2, msp.y);
      fisicaArmy3.text( ""+a.soldierMover.absolutPosition,msp.x+armySelectorSize/2, msp.y+30);
      fisicaArmy3.text( "Start. "+ a.soldierMover.soldiers.size() + " Alive:"+a.soldierMover.armySizeAlive(),msp.x+armySelectorSize/2, msp.y+60);
      fisicaArmy3.text( a.moverState.toString(),msp.x+armySelectorSize/2, msp.y+90);
      fisicaArmy3.text( a.soldierMover.armyState.toString(),msp.x+armySelectorSize/2, msp.y+120);
      fisicaArmy3.text( "Approveroute:"+a.moverStateFollowPath.approveRoute,msp.x+armySelectorSize/2, msp.y+150);
      fisicaArmy3.text( "nextpoint:"+a.moverStateFollowPath.nextPoint,msp.x+armySelectorSize/2, msp.y+180);
            fisicaArmy3.text( "marching:"+a.soldierMover.isMarching(),msp.x+armySelectorSize/2, msp.y+210);
      ///////////////////////////////////////////
    }

  }

  public void drawSelectedArmy(){
     ArmyMover a = selectedArmy;
      if(a==null)return;
      //CENTER OF ARMY///////////////////////////
     PVector msp = a.soldierMover.meanSoldierPosition();
      fisicaArmy3.noStroke();
      fisicaArmy3.stroke(255,0,0);
      fisicaArmy3.rect(msp.x+armySelectorSize/2, msp.y-30-5,900,300);
            fisicaArmy3.fill(255,255,0);
      fisicaArmy3.textSize(30);
      fisicaArmy3.text(a.soldierMover.name,msp.x+armySelectorSize/2, msp.y);
      fisicaArmy3.text( ""+a.soldierMover.absolutPosition,msp.x+armySelectorSize/2, msp.y+30);
      fisicaArmy3.text( "Start. "+ a.soldierMover.soldiers.size() + " Alive:"+a.soldierMover.armySizeAlive(),msp.x+armySelectorSize/2, msp.y+60);
      fisicaArmy3.text( a.moverState.toString(),msp.x+armySelectorSize/2, msp.y+90);
      fisicaArmy3.text( a.soldierMover.armyState.toString(),msp.x+armySelectorSize/2, msp.y+120);
      fisicaArmy3.text( "Approveroute:"+a.moverStateFollowPath.approveRoute,msp.x+armySelectorSize/2, msp.y+150);
      fisicaArmy3.text( "nextpoint:"+a.moverStateFollowPath.nextPoint,msp.x+armySelectorSize/2, msp.y+180);
            fisicaArmy3.text( "marching:"+a.soldierMover.isMarching(),msp.x+armySelectorSize/2, msp.y+210);
      ///////////////////////////////////////////


  }

}
