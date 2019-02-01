package processing.test.fisicaarmy3.army;

import java.util.ArrayList;

import fisica.FContact;
import processing.core.PApplet;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;
import processing.test.fisicaarmy3.utils.GameConstants;

class ArmyMoverStateMarch implements ArmyMoverState {


  private ArmyMover armyMover;

  private boolean approveRoute = false;
  private ArrayList<PVector> wayPoints = new ArrayList<PVector>();
  private PVector nextPoint;


  ArmyMoverStateMarch(ArmyMover armyMover) {
    this.armyMover = armyMover;
  }

  void initState(){
    wayPoints.clear();
    approveRoute = false;
    nextPoint = null;
  }

  public ArmyMover firstSelectionArmy(float x, float y) {
    ArmyMover newSelectedArmy = null;
    //PVector msp = armyMover.soldierMover.meanSoldierPosition();

    //FIRST SELECTION: SELECT THIS ARMY
    //if (PApplet.dist(msp.x, msp.y, x, y) < GameConstants.armySelectorSize /2){
    if(armyMover.isInsideArmyArea(x,y)){
      newSelectedArmy = armyMover;
    }

    //FIRST SELECTION: SELECT LAST WAYPOINT
    if (!wayPoints.isEmpty()) {
      PVector wp = wayPoints.get(wayPoints.size() - 1);
      float distFromLastWayPoint = PApplet.dist(wp.x, wp.y, x, y);
      if (distFromLastWayPoint < GameConstants.armySelectorSize /2){
        newSelectedArmy = armyMover;
      }
    }
    return newSelectedArmy;
  }

  public void dragFromArmy(float x, float y) {
    //DRAG : ONLY WHEN NOT ARMY SELECTED AND NOT ROUTE APPROVED
    if(approveRoute || armyMover.isInsideArmyArea(x,y))return;
   // PVector msp = armyMover.soldierMover.meanSoldierPosition();
    //if (approveRoute || PApplet.dist(msp.x, msp.y, x, y) < GameConstants.armySelectorSize /2)return;//armyMover.armySelectorSize / 2) return;

    //DRAG : NEW WAYPOINT WHEN DISTANCE MORE THAN GAP!
    PVector lastPoint = wayPoints.isEmpty() ? armyMover.getArmyCenter() : wayPoints.get(wayPoints.size() - 1);
    float distance = PApplet.dist(lastPoint.x, lastPoint.y, x, y);
      if(distance > GameConstants.wayPointGap)
      wayPoints.add(new PVector(x, y));
  }

  public void secondSelection(float x, float y) {
    PApplet.println("Please move");
    //SECOND SELECTION: ONLY WHEN WAYPOINTS EXISTS
    if (wayPoints.isEmpty()) return;

    //SECOND SELECTION: Select last wayPoint - approve !!
    PVector wp = wayPoints.get(wayPoints.size() - 1);
    float distFromLastWayPoint = PApplet.dist(wp.x, wp.y, x, y);

    if (distFromLastWayPoint <GameConstants.armySelectorSize /2){// armyMover.armySelectorSize / 2) {
      approveRoute = true;
    }

    //SECOND SELECTION:  Select army again and route exists -> delete route!
    //PVector msp = armyMover.soldierMover.meanSoldierPosition();
   // if (PApplet.dist(msp.x, msp.y, x, y) < GameConstants.armySelectorSize /2 / 2 && !wayPoints.isEmpty()) {
      if (armyMover.isInsideArmyArea(x,y) && !wayPoints.isEmpty()) {
        wayPoints.clear();
      nextPoint = null;
      approveRoute = false;
    }
  }


  public void update() {
    if (approveRoute) {
      if (!wayPoints.isEmpty() && armyMover.isMarching() && nextPoint == null) {
        nextPoint = wayPoints.get(0);
        armyMover.commandArmyHeading(nextPoint.x, nextPoint.y);
      } else if (!wayPoints.isEmpty() && armyMover.isMarching() && nextPoint != null) {
        armyMover.commandArmyPosition(nextPoint.x, nextPoint.y);
        wayPoints.remove(nextPoint);
        nextPoint = null;
      } else if (wayPoints.isEmpty()) {
        approveRoute = false;
      }
    }
  }

  public void display(boolean selected) {
    if (!wayPoints.isEmpty()) {
      FisicaArmy3.fiscaArmy3.noFill();

      FisicaArmy3.fiscaArmy3.beginShape();
      PVector msp = armyMover.getArmyCenter();
      FisicaArmy3.fiscaArmy3.vertex(msp.x, msp.y);
      //FisicaArmy3.fiscaArmy3.vertex(armyMover.soldierMover.absolutPosition.x, armyMover.soldierMover.absolutPosition.y);
      for (PVector p : wayPoints) {
        if (armyMover.isNotStateWar()) {
          FisicaArmy3.fiscaArmy3.vertex(p.x, p.y);
        }
      }
      FisicaArmy3.fiscaArmy3.endShape();
      //FisicaArmy3.fiscaArmy3.ellipse(armyMover.soldierMover.absolutPosition.x, armyMover.soldierMover.absolutPosition.y, 3, 3);
      for (PVector p : wayPoints) {
        if (armyMover.isNotStateWar()) {
          FisicaArmy3.fiscaArmy3.ellipse(p.x, p.y, 3, 3);

          if (wayPoints.indexOf(p) >= (wayPoints.size() - 1)) {
            FisicaArmy3.fiscaArmy3.ellipse(p.x, p.y, GameConstants.armySelectorSize, GameConstants.armySelectorSize);
          }
        }
      }
    }
  }

  public void updateWithZoomFactor() {
    for (PVector wp : wayPoints) {
      wp.mult(GameConstants.zoomFactor);
    }
  }

  public void updateMapPosition(float dx, float dy) {
    for (PVector wp : wayPoints) {
      wp.add(dx, dy);
    }
  }

  public void contactStarted(FContact c) {
    this.armyMover.changeToWarState(c);
  }
}