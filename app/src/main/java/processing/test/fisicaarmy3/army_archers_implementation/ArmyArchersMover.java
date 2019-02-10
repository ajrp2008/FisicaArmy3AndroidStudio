package processing.test.fisicaarmy3.army_archers_implementation;

import fisica.FCircle;
import processing.core.PVector;
import processing.test.fisicaarmy3.FisicaArmy3;
import processing.test.fisicaarmy3.army.ArmyMover;
import processing.test.fisicaarmy3.utils.GameConstants;

public class ArmyArchersMover extends ArmyMover {

    private SoldierArcherMover soldierArcherMover;
    private ArchersShootingArea shootingArea;

    public static ArmyMover createArmy(float x, float y, String name, float r, float g, float b){
        ArmyArchersMover       armyMover       = new ArmyArchersMover();
        SoldierArcherMover   soldiersMover   = new SoldierArcherMover(x,y,name,r,g,b,armyMover);
        armyMover.setSoldierMover(soldiersMover);
        armyMover.soldierArcherMover = soldiersMover;
        armyMover.shootingArea = new ArchersShootingArea(30,armyMover);
        armyMover.shootingArea.setSensor(true);
        FisicaArmy3.fiscaArmy3.world.add(armyMover.shootingArea);
        return armyMover;
    }

    @Override
    public void display(boolean selected) {
        super.display(selected);
      //  PVector p = getArmyCenter();
      //  FisicaArmy3.fiscaArmy3.noFill();
      //  FisicaArmy3.fiscaArmy3.ellipse(p.x,p.y, GameConstants.armySelectorSize*3,GameConstants.armySelectorSize*3);
    }

    @Override
    public void update() {
        super.update();
        PVector p = getArmyCenter();
        this.shootingArea.setPosition(p.x,p.y);
        this.shootingArea.setSize( GameConstants.armySelectorSize*3);
        if(FisicaArmy3.shootArrowsFlag){
            soldierArcherMover.shot();
            FisicaArmy3.shootArrowsFlag = false;
        }
    }
}
