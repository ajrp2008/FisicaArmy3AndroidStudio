package processing.test.fisicaarmy3;

import fisica.FContact;
import processing.core.PVector;

class SoldiersMoverStateRetreat implements SoldiersMoveState {

    private SoldiersMover army;
    private PVector retreatToLocation = new PVector();

    SoldiersMoverStateRetreat(SoldiersMover army) {
        this.army = army;
    }

    public void setRetreatLocation(float x, float y) {
        retreatToLocation.set(x, y);
    }

    public void commandArmyPosition(float x, float y) {
    }

    public void commandArmyHeading(float x, float y) {
    }

    public void updateArmySoldiers() {
    }

    public void updateState() {
        for (Soldier s : army.soldiers)
            s.updatePosition();
    }

    public boolean isMarching() {
        return false;
    }

    public void contactStarted(FContact c) {
    }

    public void updateArmyToZoom() {
        army.absolutPosition.mult(GameConstants.zoomFactor);
        for (Soldier s : army.soldiers) {
            s.updateSoldierSizeToZoom();
            s.relPosition.mult(GameConstants.zoomFactor);
            s.setPosition(s.getX() * GameConstants.zoomFactor, s.getY() * GameConstants.zoomFactor);
        }
    }

    @Override
    public void updateMapPosition(float dx, float dy) {

    }


}
