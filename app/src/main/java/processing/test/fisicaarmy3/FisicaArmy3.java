package processing.test.fisicaarmy3;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import fisica.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class FisicaArmy3 extends PApplet {



FWorld            world;
ArmySelector      armySelector; 
Button            zoomInButton,zoomOutButton; 
Button            resetButton;
float             buttonHeight = 100, buttonWidth = 300, buttonGap =50, buttonTopGap = 50, buttonLeftGap = 50;



public void setup(){
  
  //fullScreen();
  

  initGame();
  
  zoomInButton = new Button(width-(buttonWidth+buttonLeftGap), (buttonTopGap), buttonWidth, buttonHeight);
  zoomInButton.setText("Zoom In");
  
  zoomOutButton = new Button(width-(buttonWidth+buttonLeftGap), (buttonTopGap+buttonHeight+buttonGap), buttonWidth, buttonHeight);
  zoomOutButton.setText("Zoom Out");
  
    resetButton = new Button(width-(buttonWidth+buttonLeftGap), (buttonTopGap+3*buttonHeight+2*buttonGap), buttonWidth, buttonHeight);
  resetButton.setText("Reset");
}

public void initGame(){
  Fisica.init(this);
  world = new FWorld();
  world.setGravity(0,0);
  ellipseMode(CENTER);
  GameConstants.initGameConstants();
  armySelector    = new ArmySelector();
  armySelector.addArmy(new ArmyMover(new SoldiersMover(100,100,"A",0,255,0)));
  armySelector.addArmy(new ArmyMover(new SoldiersMover(200,150,"B",255,255,255)));
  armySelector.addArmy(new ArmyMover(new SoldiersMover(100,200,"C",200,0,0)));  
  armySelector.addArmy(new ArmyMover(new SoldiersMover(400,100,"D",0,255,0)));
  armySelector.addArmy(new ArmyMover(new SoldiersMover(600,150,"E",255,255,255)));
  armySelector.addArmy(new ArmyMover(new SoldiersMover(400,200,"F",200,0,0)));
 //Initial zoom based on screen size
  zoomMap(4);
  
}

public void draw(){
  background(50);
  //DEBUG TEXT 
  textSize(40); 
  fill(200,0,0);
   text("zoomFactorAccum: "+GameConstants.zoomFactorAccumulated,70,70);
  //  
  world.step();
  armySelector.update();
  armySelector.drawSelector();
  world.draw();
    armySelector.drawSelectedArmy();


  //
  zoomInButton.display();
  zoomOutButton.display();
  resetButton.display();
}

public void mousePressed(){
  if(zoomInButton.isPushed(mouseX,mouseY)){ zoomInButton_click();return;}
  if(zoomOutButton.isPushed(mouseX,mouseY)){ zoomOutButton_click(); return;}
  if(resetButton.isPushed(mouseX,mouseY)){ resetButton_click(); return;}

  boolean result = armySelector.selectArmy(mouseX,mouseY);
  
}

public void zoomInButton_click() {
  zoomMap(1.1f);
}

public void zoomOutButton_click() {
  zoomMap(0.9f);
}

public void resetButton_click(){
  initGame();
}

public void mouseDragged(){
  if(zoomInButton.isPressed || zoomOutButton.isPressed  || resetButton.isPressed){return;}
  
  boolean result = armySelector.dragFromArmy(mouseX,mouseY);
  if(!result){
    moveMap(mouseX-pmouseX,mouseY-pmouseY);
  }
}

public void mouseReleased(){
  zoomInButton.release();
  zoomOutButton.release();
  resetButton.release();
}

public void zoomMap(float zoom){
  GameConstants.zoomFactor = zoom;
  GameConstants.zoomFactorAccumulated *=GameConstants.zoomFactor;
  armySelector.updateWithZoomFactor();
}

public void moveMap(float dx, float dy){
  armySelector.updateMapPosition(dx,dy);
}


public void contactStarted(FContact c) {
    if(!c.getBody1().getName().equals(c.getBody2().getName())){
      Soldier s1 = (Soldier)c.getBody1();
      Soldier s2 = (Soldier)c.getBody2();
      s1.army.armyMover.contactStarted(c);
      s2.army.armyMover.contactStarted(c);
    }
}
class ArmySelector {
  float                 armySelectorSize  = GameConstants.armySelectorSizeStart;

  ArmyMover             selectedArmy      = null;
  ArrayList<ArmyMover>  armyList          = new ArrayList<ArmyMover>();

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
    for (ArmyMover ap : armySelector.armyList) {
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
      noStroke();
      if(a == selectedArmy) fill(255,255,0,100);else fill(30,0,0,100);
      ellipse(msp.x, msp.y, armySelectorSize, armySelectorSize);
      stroke(255,0,0);
      if(a == selectedArmy){textSize(30); continue; }else textSize(15); 
      text(a.soldierMover.name,msp.x+armySelectorSize/2, msp.y);
      text( ""+a.soldierMover.absolutPosition,msp.x+armySelectorSize/2, msp.y+30);
      text( "Start. "+ a.soldierMover.soldiers.size() + " Alive:"+a.soldierMover.armySizeAlive(),msp.x+armySelectorSize/2, msp.y+60);
      text( a.moverState.toString(),msp.x+armySelectorSize/2, msp.y+90);
      text( a.soldierMover.armyState.toString(),msp.x+armySelectorSize/2, msp.y+120);
      text( "Approveroute:"+a.moverStateFollowPath.approveRoute,msp.x+armySelectorSize/2, msp.y+150);
      text( "nextpoint:"+a.moverStateFollowPath.nextPoint,msp.x+armySelectorSize/2, msp.y+180);
            text( "marching:"+a.soldierMover.isMarching(),msp.x+armySelectorSize/2, msp.y+210);
      ///////////////////////////////////////////
    }

  }
  
  public void drawSelectedArmy(){
     ArmyMover a = selectedArmy;
      if(a==null)return;
      //CENTER OF ARMY///////////////////////////
     PVector msp = a.soldierMover.meanSoldierPosition();
      noStroke();
      stroke(255,0,0);
      rect(msp.x+armySelectorSize/2, msp.y-30-5,900,300);
            fill(255,255,0);
      textSize(30);
      text(a.soldierMover.name,msp.x+armySelectorSize/2, msp.y);
      text( ""+a.soldierMover.absolutPosition,msp.x+armySelectorSize/2, msp.y+30);
      text( "Start. "+ a.soldierMover.soldiers.size() + " Alive:"+a.soldierMover.armySizeAlive(),msp.x+armySelectorSize/2, msp.y+60);
      text( a.moverState.toString(),msp.x+armySelectorSize/2, msp.y+90);
      text( a.soldierMover.armyState.toString(),msp.x+armySelectorSize/2, msp.y+120);
      text( "Approveroute:"+a.moverStateFollowPath.approveRoute,msp.x+armySelectorSize/2, msp.y+150);
      text( "nextpoint:"+a.moverStateFollowPath.nextPoint,msp.x+armySelectorSize/2, msp.y+180);
            text( "marching:"+a.soldierMover.isMarching(),msp.x+armySelectorSize/2, msp.y+210);
      ///////////////////////////////////////////
    
  
  }
  
}
class ArmyMover {

    float armySelectorSize   = GameConstants.armySelectorSizeStart;

  
  ArmyMoverStateFollowPath   moverStateFollowPath = new ArmyMoverStateFollowPath(this);
  ArmyMoverStateWar          moverStateWar        = new ArmyMoverStateWar(this);
  ArmyMoverStateRetreat      moverStateRetreat    = new ArmyMoverStateRetreat(this);

  ArmyMoverState             moverState           = moverStateFollowPath;

  SoldiersMover              soldierMover;

  ArmyMover(SoldiersMover army) {
    this.soldierMover = army;
    this.soldierMover.armyMover = this;
  }
  
  public ArmyMover firstSelectionArmy(float x,float y){
     return moverState.firstSelectionArmy(x,y);
  }
  
  public void secondSelection(float x, float y){
     moverState.secondSelection(x,y);
  }
  
  public void update() {
    moverState.update();
  }

  public void updateWithZoomFactor() {
    moverState.updateWithZoomFactor();
  }

  public void updateMapPosition(float dx, float dy) {
    moverState.updateMapPosition(dx,dy);
  }

  public void dragFromArmy(float x, float y) {
    moverState.dragFromArmy(x, y);
  }

  public void display(boolean selected) {
    moverState.display(selected);
  }
  
  public void contactStarted(FContact c){
    moverState.contactStarted(c);
  }

}
interface ArmyMoverState {

  public ArmyMover firstSelectionArmy(float x, float y);

  public void dragFromArmy(float x, float y);

  public void secondSelection(float x, float y);


  public void update();

  public void display(boolean selected);



  public void updateWithZoomFactor();

  public void updateMapPosition(float dx, float dy);

  public void contactStarted(FContact c);


}
class ArmyMoverStateFollowPath implements ArmyMoverState {

  boolean approveRoute = false;

  ArmyMover armyMover;


  float wayPointsGap = GameConstants.wayPointGapStart;

  ArrayList<PVector> wayPoints = new ArrayList<PVector>();
  PVector nextPoint;


  ArmyMoverStateFollowPath(ArmyMover armyMover) {
    this.armyMover = armyMover;
  }

  public ArmyMover firstSelectionArmy(float x, float y) {
    ArmyMover newSelectedArmy = null;

    PVector msp = armyMover.soldierMover.meanSoldierPosition();


    //FIRST SELECTION: SELECT THIS ARMY
    if (dist(msp.x, msp.y, x, y)<armyMover.armySelectorSize/2) {
      newSelectedArmy = armyMover; 
    }

    //FIRST SELECTION: SELECT LAST WAYPOINT
    if (!wayPoints.isEmpty()) {
      PVector wp =  wayPoints.get(wayPoints.size()-1);
      float distFromLastWayPoint = dist(wp.x, wp.y, x, y);

      if (distFromLastWayPoint < armyMover.armySelectorSize/2) {
        newSelectedArmy = armyMover;
      }
    }

    return newSelectedArmy;
  }

  public void dragFromArmy(float x, float y) {


    //DRAG : ONLY WHEN NOT ARMY SELECTED AND NOT ROUTE APPROVED
    PVector msp = armyMover.soldierMover.meanSoldierPosition();  
    if (approveRoute || dist(msp.x, msp.y, x, y)<armyMover.armySelectorSize/2)return;

    //DRAG : NEW WAYPOINT WHEN DISTANCE MORE THAN GAP!
    PVector lastPoint  = wayPoints.isEmpty() ? armyMover.soldierMover.absolutPosition : wayPoints.get(wayPoints.size()-1);
    float distance     = dist(lastPoint.x, lastPoint.y, x, y);      
    if (distance > wayPointsGap)
      wayPoints.add(new PVector(x, y));
  }

  public void secondSelection(float x, float y) {
    println("Please move");
    //SECOND SELECTION: ONLY WHEN WAYPOINTS EXISTS
    if (wayPoints.isEmpty())return;


    //SECOND SELECTION: Select last wayPoint - approve !!
    PVector wp =  wayPoints.get(wayPoints.size()-1);
    float distFromLastWayPoint = dist(wp.x, wp.y, x, y);

    if (distFromLastWayPoint < armyMover.armySelectorSize/2) {
      approveRoute = true;
    }

    //SECOND SELECTION:  Select army again and route exists -> delete route!
    PVector msp = armyMover.soldierMover.meanSoldierPosition();

    if (dist(msp.x, msp.y, x, y)<armyMover.armySelectorSize/2 && !wayPoints.isEmpty()) {
      wayPoints.clear();
      nextPoint = null;
      approveRoute = false;
    }
  }


  public void update() {

    armyMover.soldierMover.updateArmy();

    if (approveRoute) {
      if (!wayPoints.isEmpty() && !armyMover.soldierMover.isMarching() && nextPoint == null) {
        nextPoint = wayPoints.get(0);
        armyMover.soldierMover.commandArmyHeading(nextPoint.x, nextPoint.y);
      } else if (!wayPoints.isEmpty() && !armyMover.soldierMover.isMarching() && nextPoint != null) {
        armyMover.soldierMover.commandArmyPosition(nextPoint.x, nextPoint.y);
        wayPoints.remove(nextPoint);
        nextPoint = null;
      } else if (wayPoints.isEmpty()) {
        approveRoute = false;
      }
    }
  }

  public void display(boolean selected) {
    if (!wayPoints.isEmpty()) {
      if (selected) stroke(armyMover.soldierMover.r, armyMover.soldierMover.g, armyMover.soldierMover.b, 300);
      else stroke(armyMover.soldierMover.r, armyMover.soldierMover.g, armyMover.soldierMover.b, 100);
      noFill();

      beginShape();
      PVector msp = armyMover.soldierMover.meanSoldierPosition();
      vertex(msp.x, msp.y);
      vertex(armyMover.soldierMover.absolutPosition.x, armyMover.soldierMover.absolutPosition.y);
      for (PVector p : wayPoints) {
        if (armyMover.soldierMover.armyState != armyMover.soldierMover.armyWar) {
          vertex(p.x, p.y);
        }
      }
      endShape();
      ellipse(armyMover.soldierMover.absolutPosition.x, armyMover.soldierMover.absolutPosition.y, 3, 3);
      for (PVector p : wayPoints) {
        if (armyMover.soldierMover.armyState != armyMover.soldierMover.armyWar) {
          ellipse(p.x, p.y, 3, 3);

          if (wayPoints.indexOf(p) >=( wayPoints.size()-1)) {
            ellipse(p.x, p.y, armyMover.armySelectorSize, armyMover.armySelectorSize);
          }
        }
      }
    }
  }

  public void updateWithZoomFactor() {
    armyMover.armySelectorSize*= GameConstants.zoomFactor;
    wayPointsGap *= GameConstants.zoomFactor;
    armyMover.soldierMover.updateArmyToZoom();
    for (PVector wp : wayPoints) {
      wp.mult(GameConstants.zoomFactor);
    }
  }

  public void updateMapPosition(float dx, float dy) {
    for (PVector wp : wayPoints) {
      wp.add(dx, dy);
    }
    armyMover.soldierMover.updateMapPosition(dx, dy);
  }

  public void contactStarted(FContact c) {
    this.armyMover.soldierMover.contactStarted(c);
    this.armyMover.moverState = this.armyMover.moverStateWar;
  }
}

class ArmyMoverStateRetreat implements ArmyMoverState{ 

    PVector retreatToLocation = new PVector();
  
  ArmyMover armyMover;
  
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

        if(dist(retreatToLocation.x,retreatToLocation.y, msp.x,msp.y)<armyMover.armySelectorSize/2){
          
          this.armyMover.soldierMover.armyState = this.armyMover.soldierMover.armyMarch;
          
          ((ArmyMoverStateFollowPath)this.armyMover.moverStateFollowPath).wayPoints.clear();
          ((ArmyMoverStateFollowPath)this.armyMover.moverStateFollowPath).approveRoute = false;
          ((ArmyMoverStateFollowPath)this.armyMover.moverStateFollowPath).nextPoint = null;
          this.armyMover.moverState = this.armyMover.moverStateFollowPath;

        }        
  }

  public void display(boolean selected){
        if(retreatToLocation != null) ellipse(retreatToLocation.x,retreatToLocation.y,30,30);
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
class ArmyMoverStateWar implements ArmyMoverState {

  PVector retreatToLocation = null;
  
  ArmyMover armyMover;

  ArmyMoverStateWar(ArmyMover armyMover) {
    this.armyMover = armyMover;
  }  

  public ArmyMover firstSelectionArmy(float x, float y){
    ArmyMover newSelectedArmy = null;
    PVector msp = armyMover.soldierMover.meanSoldierPosition();
    //FIRST SELECTION: SELECT THIS ARMY
    if (dist(msp.x, msp.y, x, y)<armyMover.armySelectorSize/2) {
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
    if (dist(retreatToLocation.x,retreatToLocation.y, x, y)<armyMover.armySelectorSize/2) {
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
      if (selected) stroke(armyMover.soldierMover.r, armyMover.soldierMover.g, armyMover.soldierMover.b, 300);
      else stroke(armyMover.soldierMover.r,armyMover.soldierMover.g, armyMover.soldierMover.b, 100);
      noFill();
      
      if(retreatToLocation != null) ellipse(retreatToLocation.x,retreatToLocation.y,30,30);
      
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
public void createSoldiers(SoldiersMover army) {
  for (int i=0; i<army.armySize; i++) {
    Soldier s = new Soldier(army, new PVector());
    s.setFill(army.r, army.g, army.b);
    army.soldiers.add(s);
  }
}


public void initSquareFormation(SoldiersMover army) {
  for (int i=0; i<army.soldiers.size(); i++) {
    float length       = sqrt(army.soldiers.size());
    int column         = i%(int)length; 
    int row            = i/(int)length; 
    Soldier s = army.soldiers.get(i);
    s.relPosition.set((column-(length-1.0f)/2.0f)*GameConstants.armyGapSizeStart*GameConstants.zoomFactor, (row-(length-1)/2)*GameConstants.armyGapSizeStart*GameConstants.zoomFactor);
  }
}
class Button{
 
  boolean isPressed = false;
  
  float x,y,w,h;
  float r=100,g=0,b=0;
  String text;
  
  Button(float x, float y, float w, float h){
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  
  public void display(){
    fill(r,g,b);
    rect(x,y,w,h,20);
  }
  
  public void setText(String text){
    this.text = text;
  }
  
  public boolean isPushed(float x, float y){
    boolean isClicked = ((this.x < x && (this.x+w)>x))&(this.y < y && (this.y + h) > y);
    if(isClicked){g=200;isPressed = true;}
    return isClicked;
  }

  public void release(){
    isPressed = false;
    g=0;
  }  
}
  
static class GameConstants{
  
  static float soldierSpeedStart;
  static float soldierSizeStart;
  static float armyGapSizeStart;
  static float wayPointGapStart;
  static float armySelectorSizeStart;
  static float zoomFactor;
  static float zoomFactorAccumulated;
  static String debugText;
  
  
  public static void initGameConstants(){
    //INITIAL CONDITIONS
    soldierSpeedStart       = 30;
  
    soldierSizeStart        = 4;
    armyGapSizeStart        = 6;
  
    armySelectorSizeStart   = 50;
  
    wayPointGapStart        = 20;
  
    zoomFactor              = 1;
    zoomFactorAccumulated   = GameConstants.zoomFactor;
  
    debugText              = "DEBUG";
  }

}
class SoldiersMover{
  
  ArmyMover           armyMover;
  
  String              name;
  
  SoldiersMoveState           armyState;
  SoldiersMoveState           armyMarch            = new SoldiersMoverStateMarch(this);
  SoldiersMoveState           armyWar              = new SoldiersMoverStateWar(this);  
  SoldiersMoveState           armyRetreat          = new SoldiersMoverStateRetreat(this);
  
  ArrayList<Soldier>  soldiers             = new ArrayList<Soldier>();
  PVector             absolutPosition      = new PVector();  
  boolean             isMarching           = false;
  float               heading              = 0;
  int                 armySize             = 25;
  float               r,g,b;  
      
  SoldiersMover(float x, float y, String name,float r,float g,float b){
    armyState = armyMarch;
    absolutPosition.set(x,y);
    this.r = r; this.g = g; this.b = b;
    this.name = name;
    createSoldiers(this);
    initSquareFormation(this);
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
interface SoldiersMoveState{
  
    public void commandArmyPosition(float x, float y);

    public void commandArmyHeading(float x, float y);
    
    public void updateArmySoldiers();
    
    public void updateState();
    
    public boolean isMarching();
    
    public void contactStarted(FContact c);
    
    public void retreatTo(float x, float y);
    
    public void updateArmyToZoom();
    
}
class SoldiersMoverStateMarch implements SoldiersMoveState{

  SoldiersMover    army;

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
class SoldiersMoverStateRetreat implements SoldiersMoveState {

      SoldiersMover    army;
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
class SoldiersMoverStateWar implements SoldiersMoveState {

  SoldiersMover      army;
  FContact  firstContact;
  PVector   positionContact = new PVector();

  int       frameCount;
  int       colisionFrame  = 50;
  

  SoldiersMoverStateWar(SoldiersMover army) {
    this.army = army;
  }

  public void commandArmyPosition(float x, float y) {
  }

  public void commandArmyHeading(float x, float y) {
  }

  public void updateArmySoldiers() {
    frameCount++;
    //if ((frameCount%(20*(int)random(1,3))==0 || frameCount%50==0) && frameCount>colisionFrame) { //After 50 frames - move towa
      if(frameCount == 1 || (frameCount%50==0 && frameCount>colisionFrame)){
      for (Soldier s : army.soldiers) {
        float dx = -s.getX() + positionContact.x; //s.army.absolutPosition.x;
        float dy = -s.getY() + positionContact.y;//s.army.absolutPosition.y;
        PVector p = new PVector(dx,dy);
        p.mult(0.25f*random(1,3));
        s.setVelocity(p.x, p.y);
      }
    }
    
    
    
  }

  public void updateState() {
  }
  
    
  public void updateArmyToZoom(){
    positionContact.mult(GameConstants.zoomFactor);
     army.absolutPosition.mult(GameConstants.zoomFactor);
        for(Soldier s: army.soldiers){
            s.updateSoldierSizeToZoom();
            s.relPosition.mult(GameConstants.zoomFactor);
            s.setPosition(s.getX()*GameConstants.zoomFactor,s.getY()*GameConstants.zoomFactor);
          }
  }

  public boolean isMarching() {
    return true;
  }

  public void contactStarted(FContact c) {
    if(this.firstContact==null){
      this.firstContact = c;
      this.positionContact = new PVector(c.getX(),c.getY());
    }
    
    if(frameCount > colisionFrame){
      Soldier s1 = (Soldier)c.getBody1();
      Soldier s2 = (Soldier)c.getBody2();
      s1.attack(s2);
      s2.attack(s1);
      
    }
  }
  
  public void retreatTo(float x, float y){
    this.firstContact = null;
    ((SoldiersMoverStateRetreat)this.army.armyRetreat).retreatToLocation.set(x,y);
    this.army.absolutPosition.set(x,y);
    this.army.armyState = this.army.armyRetreat;
  }
  
}
class Soldier extends FCircle {

  PVector  relPosition    =  new PVector();
  SoldiersMover     army;

  boolean  isAlive        = true;

  float    speed          = GameConstants.soldierSpeedStart;

  Soldier(SoldiersMover army, PVector relPos) {
    super(GameConstants.soldierSizeStart);
    this.army         = army;
    this.relPosition  = relPos;
    setPosition(army.absolutPosition.x + relPos.x, army.absolutPosition.y + relPos.y);
    setGrabbable(false);
    setDamping(0.25f);
    setName(army.name);
    world.add(this);
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
    float e = dist(getX(), getY(), army.absolutPosition.x+relPosition.x, army.absolutPosition.y+relPosition.y);
    if (e > 3) {
      println(frameCount+"I MARCH"+this.getVelocityX());
      return true;
    } else { 
      return false;
    }
  }

  public void attack(Soldier opponent) {
    int x = (int)random(1, 100);
    //println("hit x:"+x);
    if (x<2) {
      //println("There is at hit :"+ x + "SOLDIER DEAD !!!");
      opponent.isAlive = false;
      world.remove(opponent);
     // opponent.army.soldiers.remove(opponent);
    }
  }
}
  public void settings() {  size(1440,2960);  smooth(); }
}
