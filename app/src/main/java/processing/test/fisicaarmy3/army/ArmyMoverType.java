package processing.test.fisicaarmy3.army;

import fisica.FContact;
import processing.core.PVector;

public interface ArmyMoverType {

    ArmyMoverType firstSelectionArmy(float x, float y);

    void secondSelection(float x, float y);

    void update();

    void updateWithZoomFactor();

    void updateMapPosition(float dx, float dy);

    void dragFromArmy(float x, float y);

    void display(boolean selected);

    void contactStarted(FContact c);

    PVector getArmyCenter();

    void commandArmyHeading(float x, float y);

    void commandArmyPosition(float x, float y);
}
