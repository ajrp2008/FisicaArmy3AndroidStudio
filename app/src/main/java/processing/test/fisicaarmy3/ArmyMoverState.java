package processing.test.fisicaarmy3;

import fisica.FContact;

interface ArmyMoverState {

  public ArmyMover firstSelectionArmy(float x, float y);

  public void dragFromArmy(float x, float y);

  public void secondSelection(float x, float y);


  public void update();

  public void display(boolean selected);



  public void updateWithZoomFactor();

  public void updateMapPosition(float dx, float dy);

  public void contactStarted(FContact c);


}
