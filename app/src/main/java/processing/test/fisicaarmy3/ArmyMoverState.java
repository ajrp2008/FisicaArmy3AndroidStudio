package processing.test.fisicaarmy3;

import fisica.FContact;

interface ArmyMoverState {

  ArmyMover firstSelectionArmy(float x, float y);

  void dragFromArmy(float x, float y);

  void secondSelection(float x, float y);

  void update();

  void display(boolean selected);

  void updateWithZoomFactor();

  void updateMapPosition(float dx, float dy);

  void contactStarted(FContact c);

}
