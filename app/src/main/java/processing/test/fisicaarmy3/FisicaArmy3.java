package processing.test.fisicaarmy3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import processing.core.*;

import fisica.*;
import processing.test.fisicaarmy3.army.ArmyMover;
import processing.test.fisicaarmy3.army.Soldier;
import processing.test.fisicaarmy3.army_archers_implementation.ArchersShootingArea;
import processing.test.fisicaarmy3.army_archers_implementation.ArmyArchersMover;
import processing.test.fisicaarmy3.army_archers_implementation.Arrow;
import processing.test.fisicaarmy3.army_test_implementations.ArmyFootSoldiers;
import processing.test.fisicaarmy3.gui.Button;
import processing.test.fisicaarmy3.terrain.TerrainGenerator;
import processing.test.fisicaarmy3.utils.GameConstants;

public class FisicaArmy3 extends PAppletFisicaArmy {

    public static MainActivity mainActivity;
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

        TerrainGenerator t = new TerrainGenerator();
        t.loadShapesFromTable();
      //  t.saveToTable();

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
        background(20,60,10,60);
        //DEBUG TEXT
        textSize(40);
        fill(200, 0, 0);
        text("zoomFactorAccum: " + GameConstants.zoomFactorAccumulated, 70, 70);
        //
        world.step();
        armySelector.update();
        world.draw();

        armySelector.drawSelector();
        armySelector.drawSelectedArmy();


        //
        zoomInButton.display();
        zoomOutButton.display();
        resetButton.display();
        arrowsButton.display();
    }

    public void mousePressed() {
        if (zoomInButton.isPushed(mouseX, mouseY)) {
            background(20,60,10);

            zoomInButton_click();
            return;
        }
        if (zoomOutButton.isPushed(mouseX, mouseY)) {
            background(20,60,10);

            zoomOutButton_click();
            return;
        }
        if (resetButton.isPushed(mouseX, mouseY)) {
            background(20,60,10);

            resetButton_click();
            return;
        }
        if (arrowsButton.isPushed(mouseX, mouseY)) {
            background(20,60,10);

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

       // if(c.getBody1().getName().equals(c.getBody2().getName()))return; //DENNE LINJE KAN RYDDE OP I EN MASSE ....

        boolean soldierIsShot1 = c.getBody1() instanceof Arrow && c.getBody2() instanceof  Soldier;
        boolean soldierIsShot2 = c.getBody2() instanceof Arrow && c.getBody1() instanceof  Soldier;
        if(soldierIsShot1 || soldierIsShot2 ) {
            Arrow a = soldierIsShot1 ? ((Arrow)c.getBody1()) : ((Arrow)c.getBody2());
            Soldier s = soldierIsShot1 ?  ((Soldier)c.getBody2()) : ((Soldier)c.getBody1());
            if(!s.getName().equals(a.getName()))
                a.hitSoldier(s);
        }

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
        }
    }

    public void settings() {
       // size(1440, 2960);
        fullScreen();
        smooth();
    }
}
