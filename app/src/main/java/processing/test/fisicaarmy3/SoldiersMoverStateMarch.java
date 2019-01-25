package processing.test.fisicaarmy3;

import fisica.FContact;
import processing.core.PVector;

class SoldiersMoverStateMarch implements SoldiersMoveState{

SoldiersMover army;

SoldiersMoverStateMarch(SoldiersMover army){
  this.army = army;
}

public void commandArmyPosition(float x, float y){
  army.absolutPosition.set(x,y);
}

public void commandArmyHeading(float x, float y){
  PVector target    = new PVector(x,y);
  PVector direction = target.sub(army.absolutPosition);
  for(Soldier s: army.soldiers){
    s.getRelPos().rotate(direction.heading()-army.heading);
  }
  army.heading      = direction.heading();
}

public void updateArmySoldiers(){
  army.isMarching = false;
  for(Soldier s: army.soldiers){
    s.updatePosition();
    if(s.isMarching() && s.isAlive){      army.isMarching=true;}
  }
}

public void updateState(){
}

public void updateArmyToZoom(){
  army.absolutPosition.mult(GameConstants.zoomFactor);
      for(Soldier s: army.soldiers){
          s.updateSoldierSizeToZoom();
          s.updateSoldierWhenInFormationPositionToZoom();
        }
}

public boolean isMarching(){
  return army.isMarching;
}

public void contactStarted(FContact c){
  army.armyWar.contactStarted(c);
  army.armyState      = army.armyWar;
//   collisionDetected=true;
}

public void retreatTo(float x, float y){
}

}
