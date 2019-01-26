package processing.test.fisicaarmy3;

import java.util.ArrayList;

import fisica.FContact;
import processing.core.PApplet;
import processing.core.PVector;

class ArmyMoverStateMarch implements ArmyMoverState {

  boolean approveRoute = false;

  ArmyMover armyMover;


  float wayPointsGap = GameConstants.wayPointGapStart;

  ArrayList<PVector> wayPoints = new ArrayList<PVector>();
  PVector nextPoint;


  ArmyMoverStateMarch(ArmyMover armyMover) {
    this.armyMover = armyMover;
  }

  public ArmyMover firstSelectionArmy(float x, float y) {
    ArmyMover newSelectedArmy = null;

    PVector msp = armyMover.soldierMover.meanSoldierPosition();


    //FIRST SELECTION: SELECT THIS ARMY
    if (PApplet.dist(msp.x, msp.y, x, y) < armyMover.armySelectorSize / 2) {
      newSelectedArmy = armyMover;
    }

    //FIRST SELECTION: SELECT LAST WAYPOINT
    if (!wayPoints.isEmpty()) {
      PVector wp = wayPoints.get(wayPoints.size() - 1);
      float distFromLastWayPoint = PApplet.dist(wp.x, wp.y, x, y);

      if (distFromLastWayPoint < armyMover.armySelectorSize / 2) {
        newSelectedArmy = armyMover;
      }
    }

    return newSelectedArmy;
  }

  public void dragFromArmy(float x, float y) {


    //DRAG : ONLY WHEN NOT ARMY SELECTED AND NOT ROUTE APPROVED
    PVector msp = armyMover.soldierMover.meanSoldierPosition();
    if (approveRoute || PApplet.dist(msp.x, msp.y, x, y) < armyMover.armySelectorSize / 2) return;

    //DRAG : NEW WAYPOINT WHEN DISTANCE MORE THAN GAP!
    PVector lastPoint = wayPoints.isEmpty() ? armyMover.soldierMover.absolutPosition : wayPoints.get(wayPoints.size() - 1);
    float distance = PApplet.dist(lastPoint.x, lastPoint.y, x, y);
    if (distance > wayPointsGap)
      wayPoints.add(new PVector(x, y));
  }

  public void secondSelection(float x, float y) {
    PApplet.println("Please move");
    //SECOND SELECTION: ONLY WHEN WAYPOINTS EXISTS
    if (wayPoints.isEmpty()) return;


    //SECOND SELECTION: Select last wayPoint - approve !!
    PVector wp = wayPoints.get(wayPoints.size() - 1);
    float distFromLastWayPoint = PApplet.dist(wp.x, wp.y, x, y);

    if (distFromLastWayPoint < armyMover.armySelectorSize / 2) {
      approveRoute = true;
    }

    //SECOND SELECTION:  Select army again and route exists -> delete route!
    PVector msp = armyMover.soldierMover.meanSoldierPosition();

    if (PApplet.dist(msp.x, msp.y, x, y) < armyMover.armySelectorSize / 2 && !wayPoints.isEmpty()) {
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
      if (selected)
        FisicaArmy3.fiscaArmy3.stroke(armyMover.soldierMover.r, armyMover.soldierMover.g, armyMover.soldierMover.b, 300);
      else
        FisicaArmy3.fiscaArmy3.stroke(armyMover.soldierMover.r, armyMover.soldierMover.g, armyMover.soldierMover.b, 100);
      FisicaArmy3.fiscaArmy3.noFill();

      FisicaArmy3.fiscaArmy3.beginShape();
      PVector msp = armyMover.soldierMover.meanSoldierPosition();
      FisicaArmy3.fiscaArmy3.vertex(msp.x, msp.y);
      FisicaArmy3.fiscaArmy3.vertex(armyMover.soldierMover.absolutPosition.x, armyMover.soldierMover.absolutPosition.y);
      for (PVector p : wayPoints) {
        if (!armyMover.soldierMover.isStateWar()) {
          FisicaArmy3.fiscaArmy3.vertex(p.x, p.y);
        }
      }
      FisicaArmy3.fiscaArmy3.endShape();
      FisicaArmy3.fiscaArmy3.ellipse(armyMover.soldierMover.absolutPosition.x, armyMover.soldierMover.absolutPosition.y, 3, 3);
      for (PVector p : wayPoints) {
        if (!armyMover.soldierMover.isStateWar()) {
          FisicaArmy3.fiscaArmy3.ellipse(p.x, p.y, 3, 3);

          if (wayPoints.indexOf(p) >= (wayPoints.size() - 1)) {
            FisicaArmy3.fiscaArmy3.ellipse(p.x, p.y, armyMover.armySelectorSize, armyMover.armySelectorSize);
          }
        }
      }
    }
  }

  public void updateWithZoomFactor() {
    armyMover.armySelectorSize *= GameConstants.zoomFactor;
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
    this.armyMover.changeToWarState(c);
  }
}
