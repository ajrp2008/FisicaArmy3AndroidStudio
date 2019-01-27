package processing.test.fisicaarmy3;

import fisica.FContact;

interface SoldiersMoveState {

    void commandArmyPosition(float x, float y);

    void commandArmyHeading(float x, float y);

    void updateArmySoldiers();

    void updateState();

    boolean isMarching();

    void contactStarted(FContact c);

    void updateArmyToZoom();

    void updateMapPosition(float dx, float dy);

}
