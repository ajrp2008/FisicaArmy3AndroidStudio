package processing.test.fisicaarmy3;

import fisica.FContact;

interface SoldiersMoveState {

    public void commandArmyPosition(float x, float y);

    public void commandArmyHeading(float x, float y);

    public void updateArmySoldiers();

    public void updateState();

    public boolean isMarching();

    public void contactStarted(FContact c);

    public void updateArmyToZoom();

}
