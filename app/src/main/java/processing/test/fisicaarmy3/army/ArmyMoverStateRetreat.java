package processing.test.fisicaarmy3.army;

import fisica.FContact;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;
import processing.test.fisicaarmy3.utils.GameConstants;

class ArmyMoverStateRetreat implements ArmyMoverState {

    private PVector retreatToLocation = new PVector();

    private ArmyMover armyMover;

    ArmyMoverStateRetreat(ArmyMover armyMover) {
        this.armyMover = armyMover;
    }

    public ArmyMoverType firstSelectionArmy(float x, float y) {
        return null;
    }

    public void dragFromArmy(float x, float y) {
    }

    public void secondSelection(float x, float y) {
    }

    public void update() {
        if(armyMover.isInsideArmyArea(retreatToLocation.x, retreatToLocation.y)){
            this.armyMover.changeToMarchState();
        }
    }

    public void display(boolean selected) {
        if (retreatToLocation != null)
            FisicaArmy3.fiscaArmy3.ellipse(retreatToLocation.x, retreatToLocation.y, 30, 30);
    }

    public void updateWithZoomFactor() {
        retreatToLocation.mult(GameConstants.zoomFactor);
    }

    public void updateMapPosition(float dx, float dy) {
        retreatToLocation.add(dx, dy);
    }

    public void contactStarted(FContact c) {
    }

    void setRetreatLocation(float x, float y) {
        retreatToLocation.set(x, y);
    }

}
