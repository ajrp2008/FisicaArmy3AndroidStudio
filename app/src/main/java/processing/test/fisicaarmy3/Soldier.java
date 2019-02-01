package processing.test.fisicaarmy3;

import fisica.FCircle;
import fisica.FContact;
import processing.core.PApplet;
import processing.core.PVector;

class Soldier extends FCircle {

  PVector relPosition    =  new PVector();
  private SoldiersMover army;

  boolean  isAlive        = true;

  private float    speed          = GameConstants.soldierSpeedStart;

  Soldier(SoldiersMover army, PVector relPos) {
    super(GameConstants.soldierSizeStart);
    this.army         = army;
    this.relPosition  = relPos;
    setPosition(army.getAbsolutPosition().x + relPos.x, army.getAbsolutPosition().y + relPos.y);
    setGrabbable(false);
    setDamping(0.25f);
    setName(army.getName());
    FisicaArmy3.fiscaArmy3.world.add(this);
  }

  public void updateSoldierSizeToZoom() {
    speed *= GameConstants.zoomFactor;
    setSize(getSize()*GameConstants.zoomFactor);
  }

  public void updateSoldierWhenInFormationPositionToZoom() {
    relPosition.mult(GameConstants.zoomFactor);
    setPosition(army.getAbsolutPosition().x + relPosition.x, army.getAbsolutPosition().y + relPosition.y);
  }


  public void updatePosition() {
    if (isMarching()) {
      PVector p  = new PVector(-getX()+(army.getAbsolutPosition().x + relPosition.x), -getY()+(army.getAbsolutPosition().y + relPosition.y));
      p.normalize();
      p.mult(speed);
      setVelocity(p.x, p.y);
    } else {
      setVelocity(0, 0);
    }
  }

  public PVector getRelPos() {
    return relPosition;
  }

  public boolean isMarching() {
    float e = PApplet.dist(getX(), getY(), army.getAbsolutPosition().x+relPosition.x, army.getAbsolutPosition().y+relPosition.y);
    if (e > 8) {
      PApplet.println(FisicaArmy3.fiscaArmy3.frameCount+"I MARCH"+this.getVelocityX());
      return true;
    } else {
      return false;
    }
  }

  public void attack(Soldier opponent) {
    int x = (int) FisicaArmy3.fiscaArmy3.random(1, 100);
    //println("hit x:"+x);
    if (x<2) {
      //println("There is at hit :"+ x + "SOLDIER DEAD !!!");
      opponent.isAlive = false;
      FisicaArmy3.fiscaArmy3.world.remove(opponent);
     // opponent.army.soldiers.remove(opponent);
    }
  }

  void contactTellSuperior(FContact c){
    this.army.contactTellSuperior(c);
  }

}
