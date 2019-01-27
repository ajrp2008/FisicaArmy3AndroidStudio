package processing.test.fisicaarmy3;

class GameConstants {
//not changed...
static float soldierSpeedStart;
static float soldierSizeStart;
static float armyGapSizeStart;
//changed...
static float wayPointGap;
static float armySelectorSize;
//
static float zoomFactor;
static float zoomFactorAccumulated;
static String debugText;
//

public static void initGameConstants(){
  //INITIAL CONDITIONS
  soldierSpeedStart       = 30;
  soldierSizeStart        = 4;
  armyGapSizeStart        = 6;

  armySelectorSize        = 50;
  wayPointGap             = 20;

  zoomFactor              = 1;
  zoomFactorAccumulated   = GameConstants.zoomFactor;

  debugText              = "DEBUG";
}

}
