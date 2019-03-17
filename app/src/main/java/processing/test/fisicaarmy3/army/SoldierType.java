package processing.test.fisicaarmy3.army;

import fisica.FContact;
import processing.core.PVector;

interface SoldierType {
    void updateSoldierSizeToZoom();

    void updateSoldierWhenInFormationPositionToZoom();

    void updatePosition();

    PVector getRelPos();

    boolean isMarching();

    void attack(Soldier opponent);

    void contactTellSuperior(FContact c);

}
