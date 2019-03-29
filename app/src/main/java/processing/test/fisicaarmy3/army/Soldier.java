package processing.test.fisicaarmy3.army;

import fisica.FCircle;
import fisica.FContact;
import processing.core.PApplet;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;
import processing.test.fisicaarmy3.terrain.NiveauShape;
import processing.test.fisicaarmy3.utils.GameConstants;

public class Soldier extends FCircle implements SoldierType {

  PVector relPosition;
  private SoldiersMover army;
  boolean       isAlive     = true;
  private float  speed       = GameConstants.soldierSpeedStart;

  public Soldier(SoldiersMover army, PVector relPos) {
    super(GameConstants.soldierSizeStart);
    this.army         = army;
    this.relPosition  = relPos;
    setPosition(army.getAbsolutPosition().x + relPos.x, army.getAbsolutPosition().y + relPos.y);
    setGrabbable(false);
    setDamping(0.25f);
    setName(army.getName());
    FisicaArmy3.fiscaArmy3.world.add(this);
  }

  @Override
  public void updateSoldierSizeToZoom() {
    speed *= GameConstants.zoomFactor;
    setSize(getSize()*GameConstants.zoomFactor);
  }

  @Override
  public void updateSoldierWhenInFormationPositionToZoom() {
    relPosition.mult(GameConstants.zoomFactor);
    //setPosition(army.getAbsolutPosition().x + relPosition.x, army.getAbsolutPosition().y + relPosition.y);
    setPosition(getX()*GameConstants.zoomFactor,getY()*GameConstants.zoomFactor);
  }


  @Override
  public void updatePosition() {
    if (isMarching()) {
      PVector p  = new PVector(-getX()+(army.getAbsolutPosition().x + relPosition.x), -getY()+(army.getAbsolutPosition().y + relPosition.y));
      p.normalize();
      p.mult(speed*getTerrainFactor());
      setVelocity(p.x, p.y);
    } else {
      setVelocity(0, 0);
    }
  }

  public float getTerrainFactor(){
    float level = 1;

    for(Object b : getTouching()){
      if(b instanceof NiveauShape) level = level + 0.25f;
    }
    return (1/level);

  }


  @Override
  public PVector getRelPos() {
    return relPosition;
  }

  @Override
  public boolean isMarching() {
    float e = PApplet.dist(getX(), getY(), army.getAbsolutPosition().x+relPosition.x, army.getAbsolutPosition().y+relPosition.y);
    if (e > 8) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void attack(Soldier opponent) {
    int x = (int) FisicaArmy3.fiscaArmy3.random(1, 100);
    if (x<2) {
      opponent.killSoldier();
     // opponent.isAlive = false;
      //FisicaArmy3.fiscaArmy3.world.remove(opponent);
     // opponent.army.soldiers.remove(opponent);
    }
  }

  @Override
  public void contactTellSuperior(FContact c){
    this.army.contactTellSuperior(c);
  }

  public boolean isSoldierAlive(){
    return isAlive;
  }

  public void killSoldier(){
    this.isAlive = false;
    FisicaArmy3.fiscaArmy3.world.remove(this);
  }

}
