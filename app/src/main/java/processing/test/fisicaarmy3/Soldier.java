package processing.test.fisicaarmy3;

import fisica.FCircle;
import processing.core.PApplet;
import processing.core.PVector;

class Soldier extends FCircle {

  private FisicaArmy3 fisicaArmy3;
  PVector relPosition    =  new PVector();
  SoldiersMover army;

  boolean  isAlive        = true;

  float    speed          = GameConstants.soldierSpeedStart;

  Soldier(FisicaArmy3 fisicaArmy3, SoldiersMover army, PVector relPos) {
    super(GameConstants.soldierSizeStart);
    this.fisicaArmy3 = fisicaArmy3;
    this.army         = army;
    this.relPosition  = relPos;
    setPosition(army.absolutPosition.x + relPos.x, army.absolutPosition.y + relPos.y);
    setGrabbable(false);
    setDamping(0.25f);
    setName(army.name);
    fisicaArmy3.world.add(this);
  }

  public void updateSoldierSizeToZoom() {
    speed *= GameConstants.zoomFactor;
    setSize(getSize()*GameConstants.zoomFactor);
  }

  public void updateSoldierWhenInFormationPositionToZoom() {
    relPosition.mult(GameConstants.zoomFactor);
    setPosition(army.absolutPosition.x + relPosition.x, army.absolutPosition.y + relPosition.y);
  }


  public void updatePosition() {
    if (isMarching()) {
      PVector p  = new PVector(-getX()+(army.absolutPosition.x + relPosition.x), -getY()+(army.absolutPosition.y + relPosition.y));
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
    float e = PApplet.dist(getX(), getY(), army.absolutPosition.x+relPosition.x, army.absolutPosition.y+relPosition.y);
    if (e > 3) {
      PApplet.println(fisicaArmy3.frameCount+"I MARCH"+this.getVelocityX());
      return true;
    } else {
      return false;
    }
  }

  public void attack(Soldier opponent) {
    int x = (int) fisicaArmy3.random(1, 100);
    //println("hit x:"+x);
    if (x<2) {
      //println("There is at hit :"+ x + "SOLDIER DEAD !!!");
      opponent.isAlive = false;
      fisicaArmy3.world.remove(opponent);
     // opponent.army.soldiers.remove(opponent);
    }
  }
}
