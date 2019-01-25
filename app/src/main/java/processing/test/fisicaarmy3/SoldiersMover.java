package processing.test.fisicaarmy3;

import java.util.ArrayList;

import fisica.FContact;
import processing.core.PVector;

class SoldiersMover {

    private FisicaArmy3 fisicaArmy3;
    ArmyMover armyMover;

String              name;

SoldiersMoveState           armyState;
SoldiersMoveState           armyMarch            = new SoldiersMoverStateMarch(this);
SoldiersMoveState           armyWar              = new SoldiersMoverStateWar(fisicaArmy3, this);
SoldiersMoveState           armyRetreat          = new SoldiersMoverStateRetreat(this);

ArrayList<Soldier> soldiers             = new ArrayList<Soldier>();
PVector absolutPosition      = new PVector();
boolean             isMarching           = false;
float               heading              = 0;
int                 armySize             = 25;
float               r,g,b;

SoldiersMover(FisicaArmy3 fisicaArmy3, float x, float y, String name, float r, float g, float b){
    this.fisicaArmy3 = fisicaArmy3;
    armyState = armyMarch;
  absolutPosition.set(x,y);
  this.r = r; this.g = g; this.b = b;
  this.name = name;
  fisicaArmy3.createSoldiers(this);
  fisicaArmy3.initSquareFormation(this);
}

public PVector meanSoldierPosition(){
  PVector meanPos  = new PVector();
  int   sizeAlive = 0;
  for(Soldier s: soldiers){
    if(s.isAlive){
      meanPos.add(s.getX(),s.getY());
      sizeAlive++;
    }
  }
  armySize = sizeAlive;
  meanPos.div(sizeAlive);

  return meanPos;
}

public float armySizeAlive(){
  return armySize;
}

public void updateArmyToZoom(){
  armyState.updateArmyToZoom();
}

public void updateMapPosition(float dx, float dy){
  absolutPosition.add(dx, dy);
    ((SoldiersMoverStateWar)(armyWar)).positionContact.add(dx, dy);
    for (Soldier s :soldiers) {
      s.setPosition(s.getX()+dx, s.getY()+dy);
    }
}

public void commandArmyPosition(float x, float y){
  armyState.commandArmyPosition(x,y);
}

public void commandArmyHeading(float x, float y){
  armyState.commandArmyHeading(x,y);
}

public void updateArmy(){
  armyState.updateArmySoldiers();
  armyState.updateState();
}

public boolean isMarching(){
  return armyState.isMarching();
}


public void contactStarted(FContact c) {
  armyState.contactStarted(c);
}

public void retreatTo(float x, float y){
  armyState.retreatTo(x,y);
}

}
