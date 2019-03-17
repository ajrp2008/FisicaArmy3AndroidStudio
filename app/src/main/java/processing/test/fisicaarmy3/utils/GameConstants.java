package processing.test.fisicaarmy3.utils;

public class GameConstants {
//not changed...
public static float soldierSpeedStart;
  public static float soldierSizeStart;
  public static float armyGapSizeStart;
//changed...
public static float wayPointGap;
  public static float armySelectorSize;
//
public static float zoomFactor;
  public static float zoomFactorAccumulated;
  public static String debugText;
//

public static void initGameConstants(){
  //INITIAL CONDITIONS
  soldierSpeedStart       = 80;
  soldierSizeStart        = 4;
  armyGapSizeStart        = 6;

  armySelectorSize        = 50;
  wayPointGap             = 20;

  zoomFactor              = 1;
  zoomFactorAccumulated   = GameConstants.zoomFactor;

  debugText              = "DEBUG";
}

}
