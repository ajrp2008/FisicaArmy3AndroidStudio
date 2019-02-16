package processing.test.fisicaarmy3.army_archers_implementation;

import processing.core.PVector;
import processing.test.fisicaarmy3.army.ArmyMover;
import processing.test.fisicaarmy3.army.Soldier;
import processing.test.fisicaarmy3.army.SoldiersMover;

public class SoldierArcherMover extends SoldiersMover {


    SoldierArcherMover(float x, float y, String name, float r, float g, float b, ArmyMover armyMover) {
        super(x, y, name, r, g, b, armyMover);
    }

    @Override
    public void createSoldiers() {
        for (int i = 0; i < armySize; i++) {
            SoldierArcher s = new SoldierArcher(this, new PVector());
            s.setFill(r, g, b);
            getSoldiers().add(s);
        }
    }

    void shot() {
        for (Soldier s : getSoldiers()) {
            //s.setSize(s.getSize());
            //s.setPosition(s.getX(),s.getY());
            ((SoldierArcher) s).shoot(getArmyHeading());
        }
    }

    @Override
    public void updateMapPosition(float dx, float dy) {
        super.updateMapPosition(dx,dy);
        for (Soldier s : getSoldiers()) {
            Arrow a = ((SoldierArcher) s).arrow;
            if(a == null) continue;
            a.updateMapPosArrow(dx, dy);
        }
    }

    @Override
    public void updateArmyToZoom() {
        super.updateArmyToZoom();
        for (Soldier s : getSoldiers()) {
            Arrow a = ((SoldierArcher) s).arrow;
            if(a == null) continue;
            a.updateToZoomArrow();
        }
    }

}
