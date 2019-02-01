package processing.test.fisicaarmy3.army;

import fisica.FContact;
import processing.core.PApplet;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;
import processing.test.fisicaarmy3.utils.GameConstants;

class ArmyMoverStateWar implements ArmyMoverState {

    private PVector retreatToLocation = null;

    private ArmyMover armyMover;

    ArmyMoverStateWar(ArmyMover armyMover) {
        this.armyMover = armyMover;
    }

    public ArmyMover firstSelectionArmy(float x, float y) {
        ArmyMover newSelectedArmy = null;
        //FIRST SELECTION: SELECT THIS ARMY
        if(armyMover.isInsideArmyArea(x,y)){
            newSelectedArmy = armyMover;
            retreatToLocation = null;
        }
        return newSelectedArmy;
    }

    public void dragFromArmy(float x, float y) {
        if (retreatToLocation == null) retreatToLocation = new PVector();
        retreatToLocation.set(x, y);
    }

    public void secondSelection(float x, float y) {
        if (retreatToLocation != null) {
            if (PApplet.dist(retreatToLocation.x, retreatToLocation.y, x, y) < GameConstants.armySelectorSize /2){// armyMover.armySelectorSize / 2) {
                this.retreatToLocation = null;
                this.armyMover.changeToRetreatState(x, y);
            }
        }
    }

    public void update() {
    }

    public void display(boolean selected) {
        FisicaArmy3.fiscaArmy3.noFill();
        if (retreatToLocation != null)
            FisicaArmy3.fiscaArmy3.ellipse(retreatToLocation.x, retreatToLocation.y, 30, 30);
    }

    public void updateWithZoomFactor() {
        if (retreatToLocation != null)
            retreatToLocation.mult(GameConstants.zoomFactor);

    }

    public void updateMapPosition(float dx, float dy) {
        if (retreatToLocation != null)
            retreatToLocation.add(dx, dy);
    }

    public void contactStarted(FContact c) {
       // this.armyMover.soldierMover.contactStarted(c);
    }
}
