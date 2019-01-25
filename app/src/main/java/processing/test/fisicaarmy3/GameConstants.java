package processing.test.fisicaarmy3;

class GameConstants {

static float soldierSpeedStart;
static float soldierSizeStart;
static float armyGapSizeStart;
static float wayPointGapStart;
static float armySelectorSizeStart;
static float zoomFactor;
static float zoomFactorAccumulated;
static String debugText;


public static void initGameConstants(){
  //INITIAL CONDITIONS
  soldierSpeedStart       = 30;

  soldierSizeStart        = 4;
  armyGapSizeStart        = 6;

  armySelectorSizeStart   = 50;

  wayPointGapStart        = 20;

  zoomFactor              = 1;
  zoomFactorAccumulated   = GameConstants.zoomFactor;

  debugText              = "DEBUG";
}

}
