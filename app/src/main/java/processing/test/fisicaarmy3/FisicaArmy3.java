package processing.test.fisicaarmy3;

import processing.core.*;

import fisica.*;
import processing.test.fisicaarmy3.army.ArmyMover;
import processing.test.fisicaarmy3.army.Soldier;
import processing.test.fisicaarmy3.army_archers_implementation.ArchersShootingArea;
import processing.test.fisicaarmy3.army_archers_implementation.ArmyArchersMover;
import processing.test.fisicaarmy3.army_test_implementations.ArmyFootSoldiers;
import processing.test.fisicaarmy3.gui.Button;
import processing.test.fisicaarmy3.utils.GameConstants;

public class FisicaArmy3 extends PApplet {

    //comment
    public static FisicaArmy3 fiscaArmy3;
    public FWorld world;

    private ArmySelector armySelector;
    private Button zoomInButton;
    private Button zoomOutButton;
    private Button resetButton;
    private float buttonHeight = 100, buttonWidth = 300, buttonGap = 50, buttonTopGap = 50, buttonLeftGap = 50;

    private Button arrowsButton;

    //shoot arrows - debug test
    public static boolean shootArrowsFlag = false;



    public void setup() {

        initGame();

        zoomInButton = new Button(this, width - (buttonWidth + buttonLeftGap), (buttonTopGap), buttonWidth, buttonHeight);
        zoomInButton.setText("Zoom In");

        zoomOutButton = new Button(this, width - (buttonWidth + buttonLeftGap), (buttonTopGap + buttonHeight + buttonGap), buttonWidth, buttonHeight);
        zoomOutButton.setText("Zoom Out");

        resetButton = new Button(this, width - (buttonWidth + buttonLeftGap), (buttonTopGap + 3 * buttonHeight + 2 * buttonGap), buttonWidth, buttonHeight);
        resetButton.setText("Reset");

        arrowsButton = new Button(this, width - (buttonWidth + buttonLeftGap), (buttonTopGap + 4 * buttonHeight + 4 * buttonGap), buttonWidth, buttonHeight);
        arrowsButton.setText("arrows");
    }

    private void initGame() {
        fiscaArmy3 = this;
        Fisica.init(this);
        world = new FWorld();
        world.setGravity(0, 0);
        ellipseMode(CENTER);
        GameConstants.initGameConstants();
        armySelector = new ArmySelector();
        armySelector.addArmy(ArmyArchersMover.createArmy(100, 100, "A", 0, 255, 0));
        armySelector.addArmy(ArmyMover.createArmy(200, 150, "B", 255, 255, 255));
        armySelector.addArmy(ArmyMover.createArmy(100, 200, "C", 200, 0, 0));
        armySelector.addArmy(ArmyArchersMover.createArmy(400, 100, "A", 0, 255, 0));
        armySelector.addArmy(ArmyMover.createArmy(600, 150, "B", 255, 255, 255));
        armySelector.addArmy(ArmyMover.createArmy(400, 200, "C", 200, 0, 0));
        //Initial zoom based on screen size
        zoomMap(4);

    }

    public void draw() {
        background(0);
        //DEBUG TEXT
        textSize(40);
        fill(200, 0, 0);
        text("zoomFactorAccum: " + GameConstants.zoomFactorAccumulated, 70, 70);
        //
        world.step();
        armySelector.update();
        armySelector.drawSelector();
        world.draw();
        armySelector.drawSelectedArmy();


        //
        zoomInButton.display();
        zoomOutButton.display();
        resetButton.display();
        arrowsButton.display();
    }

    public void mousePressed() {
        if (zoomInButton.isPushed(mouseX, mouseY)) {
            zoomInButton_click();
            return;
        }
        if (zoomOutButton.isPushed(mouseX, mouseY)) {
            zoomOutButton_click();
            return;
        }
        if (resetButton.isPushed(mouseX, mouseY)) {
            resetButton_click();
            return;
        }
        if (arrowsButton.isPushed(mouseX, mouseY)) {
            arrowsButton_click();
            return;
        }

        boolean result = armySelector.selectArmy(mouseX, mouseY);

    }

    public void zoomInButton_click() {
        zoomMap(1.1f);
    }

    public void zoomOutButton_click() {
        zoomMap(0.9f);
    }

    public void resetButton_click() {
        initGame();
    }

    public void arrowsButton_click() {
        shootArrowsFlag = true;
    }

    public void mouseDragged() {
        if (zoomInButton.isPressed() || zoomOutButton.isPressed() || resetButton.isPressed()) {
            return;
        }

        boolean result = armySelector.dragFromArmy(mouseX, mouseY);
        if (!result) {
            moveMap(mouseX - pmouseX, mouseY - pmouseY);
        }
    }

    public void mouseReleased() {
        zoomInButton.release();
        zoomOutButton.release();
        resetButton.release();
        arrowsButton.release();
    }

    public void zoomMap(float zoom) {
        GameConstants.zoomFactor = zoom;
        GameConstants.zoomFactorAccumulated *= GameConstants.zoomFactor;
        armySelector.updateWithZoomFactor();
    }

    public void moveMap(float dx, float dy) {
        armySelector.updateMapPosition(dx, dy);
    }

    public void contactStarted(FContact c) {
         boolean enteringArchersZone1 = c.getBody1() instanceof ArchersShootingArea && c.getBody2() instanceof  Soldier;
        if(enteringArchersZone1){
           ArchersShootingArea shootZone = ((ArchersShootingArea)c.getBody1());
            Soldier s2 = ((Soldier)c.getBody2());
            if(!shootZone.getName().equals(s2.getName())){
                // shoot
            }
        }

        if(!(c.getBody1() instanceof Soldier) || !(c.getBody2() instanceof Soldier) ) return;
        if (!c.getBody1().getName().equals(c.getBody2().getName())) {
            Soldier s1 = (Soldier) c.getBody1();
            Soldier s2 = (Soldier) c.getBody2();
            s1.contactTellSuperior(c);
            s2.contactTellSuperior(c);
            //s1.army.armyMover.contactStarted(c);
            //s2.army.armyMover.contactStarted(c);
        }
    }
/*
    public void createSoldiers(SoldiersMover army) {
        for (int i = 0; i < army.armySize; i++) {
            Soldier s = new Soldier(army, new PVector());
            s.setFill(army.r, army.g, army.b);
            army.soldiers.add(s);
        }
    }
*/
   /* public void initSquareFormation(SoldiersMover army) {
        for (int i = 0; i < army.soldiers.size(); i++) {
            float length = sqrt(army.soldiers.size());
            int column = i % (int) length;
            int row = i / (int) length;
            Soldier s = army.soldiers.get(i);
            s.relPosition.set((column - (length - 1.0f) / 2.0f) * GameConstants.armyGapSizeStart * GameConstants.zoomFactor, (row - (length - 1) / 2) * GameConstants.armyGapSizeStart * GameConstants.zoomFactor);
        }
    }*/

    public void settings() {
       // size(1440, 2960);
        fullScreen();
        smooth();
    }
}
