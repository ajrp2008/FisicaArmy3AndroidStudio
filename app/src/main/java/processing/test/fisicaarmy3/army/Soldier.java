package processing.test.fisicaarmy3.army;

import fisica.FCircle;
import fisica.FContact;
import processing.core.PApplet;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;
import processing.test.fisicaarmy3.terrain.NiveauShape;
import processing.test.fisicaarmy3.utils.GameConstants;

public class Soldier extends FCircle implements SoldierType {

  PVector relPosition1 = new PVector(),relPosition2 = new PVector(),relPosition3 = new PVector(),relPosition4 = new PVector();;
  float s = 15;

  PVector relPosition;
  private SoldiersMover army;
  boolean       isAlive     = true;
  private float  speed       = GameConstants.soldierSpeedStart;
  private PVector velocityVector = new PVector();



  public Soldier(SoldiersMover army, PVector relPos) {
    super(GameConstants.soldierSizeStart);
    this.army         = army;
    this.relPosition  = relPos;
   // this.relPosition1.set(relPos.x,relPos.y);
   // this.relPosition2.set(relPos.x,relPos.y);
  //  this.relPosition3.set(relPos.x,relPos.y);
    setPosition(army.getAbsolutPosition().x + relPos.x, army.getAbsolutPosition().y + relPos.y);
    setGrabbable(false);
    setDamping(0.25f);
    setName(army.getName());
    FisicaArmy3.fiscaArmy3.world.add(this);
  }

  public void draw(processing.core.PGraphics graphics) {

    //if(this.army.)
    FisicaArmy3.fiscaArmy3.noStroke();
    FisicaArmy3.fiscaArmy3.fill(120,relPosition1.z);
    FisicaArmy3.fiscaArmy3.ellipse(relPosition1.x, relPosition1.y,s,s);
    FisicaArmy3.fiscaArmy3.fill(120,relPosition2.z);
    FisicaArmy3.fiscaArmy3.ellipse(relPosition2.x, relPosition2.y,s*0.9f,s*0.9f);
    FisicaArmy3.fiscaArmy3.fill(120,relPosition3.z);
    FisicaArmy3.fiscaArmy3.ellipse(relPosition3.x, relPosition3.y,s*1.4f,s*1.4f);
    FisicaArmy3.fiscaArmy3.fill(120,relPosition4.z);
    FisicaArmy3.fiscaArmy3.ellipse(relPosition4.x, relPosition4.y,s*1.85f,s*1.85f);


    super.draw(graphics);


  }



    @Override
  public void updateSoldierSizeToZoom() {
    speed *= GameConstants.zoomFactor;
    setSize(getSize()*GameConstants.zoomFactor);
    s *=GameConstants.zoomFactor;
  }

  @Override
  public void updateSoldierWhenInFormationPositionToZoom() {
    relPosition.mult(GameConstants.zoomFactor);
    relPosition1.mult(GameConstants.zoomFactor);
    relPosition2.mult(GameConstants.zoomFactor);
    relPosition3.mult(GameConstants.zoomFactor);
    relPosition4.mult(GameConstants.zoomFactor);


    //setPosition(army.getAbsolutPosition().x + relPosition.x, army.getAbsolutPosition().y + relPosition.y);
    setPosition(getX()*GameConstants.zoomFactor,getY()*GameConstants.zoomFactor);
  }


  @Override
  public void updatePosition() {
    if (isMarching()) {
      velocityVector.set(-getX()+(army.getAbsolutPosition().x + relPosition.x), -getY()+(army.getAbsolutPosition().y + relPosition.y));
      //PVector p  = new PVector(-getX()+(army.getAbsolutPosition().x + relPosition.x), -getY()+(army.getAbsolutPosition().y + relPosition.y));
      velocityVector.normalize();
      velocityVector.mult(speed*getTerrainFactor());
      setVelocity(velocityVector.x, velocityVector.y);

      if(relPosition1.z>80) {
        relPosition4.set(relPosition3.x, relPosition3.y, relPosition3.z);
        //if (relPosition3.z < 0 && relPosition2.z > 0)
          relPosition3.set(relPosition2.x, relPosition2.y, relPosition2.z);
        //if (relPosition2.z < 0 && relPosition1.z > 0)
          relPosition2.set(relPosition1.x, relPosition1.y, relPosition1.z);
       // if (relPosition1.z < 0)
          relPosition1.set(getX(), getY(), 0);
      }
      relPosition1.set(relPosition1.x,relPosition1.y,relPosition1.z+3f);
      relPosition2.set(relPosition2.x,relPosition2.y,relPosition2.z-1f);
      relPosition3.set(relPosition3.x,relPosition3.y,relPosition3.z-1f);
      relPosition4.set(relPosition4.x,relPosition4.y,relPosition4.z-1f);


    } else {

      if(relPosition1.z>0)
      relPosition1.set(relPosition1.x,relPosition1.y,relPosition1.z-0.5f);
      if(relPosition2.z>0)
      relPosition2.set(relPosition2.x,relPosition2.y,relPosition2.z-0.5f);
      if(relPosition3.z>0)
      relPosition3.set(relPosition3.x,relPosition3.y,relPosition3.z-0.5f);
      if(relPosition4.z>0)
      relPosition4.set(relPosition4.x,relPosition4.y,relPosition4.z-0.5f);




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
