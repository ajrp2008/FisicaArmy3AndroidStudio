package processing.test.fisicaarmy3;

import fisica.FContact;
import processing.core.PVector;

class SoldiersMoverStateMarch implements SoldiersMoveState {

    private SoldiersMover army;
    private float heading = 0;
    private boolean isMarching = false;

    SoldiersMoverStateMarch(SoldiersMover army) {
        this.army = army;
    }

    public void commandArmyPosition(float x, float y) {
        army.absolutPosition.set(x, y);
    }

    public void commandArmyHeading(float x, float y) {
        PVector target = new PVector(x, y);
        PVector direction = target.sub(army.absolutPosition);
        for (Soldier s : army.soldiers) {
            s.getRelPos().rotate(direction.heading() - heading);
        }
        heading = direction.heading();
    }

    public void updateArmySoldiers() {
        isMarching = false;
        for (Soldier s : army.soldiers) {
            s.updatePosition();
            if (s.isMarching() && s.isAlive) {
                isMarching = true;
            }
        }
    }

    public void updateState() {
    }

    public void updateArmyToZoom() {
        army.absolutPosition.mult(GameConstants.zoomFactor);
        for (Soldier s : army.soldiers) {
            s.updateSoldierSizeToZoom();
            s.updateSoldierWhenInFormationPositionToZoom();
        }
    }

    @Override
    public void updateMapPosition(float dx, float dy) {

    }

    public boolean isMarching() {
        return isMarching;
    }

    public void contactStarted(FContact c) {
        this.army.changeToWarState(c);
    }


}
