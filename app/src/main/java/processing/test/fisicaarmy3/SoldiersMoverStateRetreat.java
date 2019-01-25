package processing.test.fisicaarmy3;

import fisica.FContact;
import processing.core.PVector;

class SoldiersMoverStateRetreat implements SoldiersMoveState {

      SoldiersMover army;
  PVector retreatToLocation = new PVector();

  SoldiersMoverStateRetreat(SoldiersMover army){
    this.army = army;
  }

    public void commandArmyPosition(float x, float y){}

    public void commandArmyHeading(float x, float y){}

    public void updateArmySoldiers(){}

    public void updateState(){
        army.isMarching = false;
    for(Soldier s: army.soldiers){
      s.updatePosition();
      if(s.isMarching()){army.isMarching=true;}
    }

    }

    public boolean isMarching(){return false;}

    public void contactStarted(FContact c){}

    public void retreatTo(float x, float y){}

    public void updateArmyToZoom(){
     army.absolutPosition.mult(GameConstants.zoomFactor);
        for(Soldier s: army.soldiers){
            s.updateSoldierSizeToZoom();
            s.relPosition.mult(GameConstants.zoomFactor);
           s.setPosition(s.getX()*GameConstants.zoomFactor,s.getY()*GameConstants.zoomFactor);
          }
  }




}
