package processing.test.fisicaarmy3;

import java.util.ArrayList;

import fisica.FContact;
import processing.core.PVector;

class SoldiersMover {

    private String name;

    private ArmyMover armyMover;

    private SoldiersMoveState armyState;
    private SoldiersMoverStateMarch armyMarch = new SoldiersMoverStateMarch(this);
    private SoldiersMoverStateWar armyWar = new SoldiersMoverStateWar(this);
    private SoldiersMoverStateRetreat armyRetreat = new SoldiersMoverStateRetreat(this);

    private ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
    private PVector absolutPosition = new PVector();
    private int armySize = 25;
    private float r, g, b;

    SoldiersMover(float x, float y, String name, float r, float g, float b, ArmyMover armyMover) {
        this.armyMover = armyMover;
        armyState = armyMarch;
        getAbsolutPosition().set(x, y);
        this.r = r;
        this.g = g;
        this.b = b;
        this.name = name;
        createSoldiers();
        initSquareFormation();
    }

    public void createSoldiers() {
        for (int i = 0; i < armySize; i++) {
            Soldier s = new Soldier(this, new PVector());
            s.setFill(r, g, b);
            soldiers.add(s);
        }
    }

    public void initSquareFormation() {
        for (int i = 0; i < soldiers.size(); i++) {
            float length = (float) Math.sqrt(soldiers.size());
            int column = i % (int) length;
            int row = i / (int) length;
            Soldier s = soldiers.get(i);
            s.relPosition.set((column - (length - 1.0f) / 2.0f) * GameConstants.armyGapSizeStart * GameConstants.zoomFactor, (row - (length - 1) / 2) * GameConstants.armyGapSizeStart * GameConstants.zoomFactor);
        }
    }

    PVector meanSoldierPosition() {
        PVector meanPos = new PVector();
        int sizeAlive = 0;
        for (Soldier s : soldiers) {
            if (s.isAlive) {
                meanPos.add(s.getX(), s.getY());
                sizeAlive++;
            }
        }
        armySize = sizeAlive;
        meanPos.div(sizeAlive);

        return meanPos;
    }

    float armySizeAlive() {
        return armySize;
    }

    void updateArmyToZoom() {
        armyState.updateArmyToZoom();
    }

    void updateMapPosition(float dx, float dy) {
        getAbsolutPosition().add(dx, dy);
        armyState.updateMapPosition(dx,dy);
        for (Soldier s : soldiers) {
            s.setPosition(s.getX() + dx, s.getY() + dy);
        }
    }

    void commandArmyPosition(float x, float y) {
        armyState.commandArmyPosition(x, y);
    }

    void commandArmyHeading(float x, float y) {
        armyState.commandArmyHeading(x, y);
    }

    void updateArmy() {
        armyState.updateArmySoldiers();
        armyState.updateState();
    }

    void updateArmyColors(boolean selected){
        if(selected)
            FisicaArmy3.fiscaArmy3.stroke(r,g,b,255);
        else
            FisicaArmy3.fiscaArmy3.stroke(r,g,b,100);

    }

    boolean isMarching() {
        return !armyState.isMarching();
    }

    public void contactStarted(FContact c) {
        armyState.contactStarted(c);
    }

    boolean isNotStateWar(){
        return (this.armyState != this.armyWar);
    }

    void changeToRetreatState(float x, float y) {
        this.armyWar.initState();
        this.armyRetreat.setRetreatLocation(x,y);
        this.getAbsolutPosition().set(x, y);
        this.armyState = this.armyRetreat;
    }

    void changeToMarchState() {
        this.armyState = this.armyMarch;
    }

    void changeToWarState(FContact c) {
        this.armyWar.contactStarted(c);
        this.armyState      = this.armyWar;
    }

    void contactTellSuperior(FContact c) {
        this.armyMover.contactStarted(c);
    }

    String getName(){
        return name;
    }

    String getStateName(){
        return armyState.toString();
    }

    public PVector getAbsolutPosition() {
        return absolutPosition;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

}
