package processing.test.fisicaarmy3;

import fisica.FContact;
import processing.core.PVector;

class SoldiersMoverStateWar implements SoldiersMoveState {

    private SoldiersMover army;
    private FContact firstContact;
    private PVector positionContact = new PVector();

    private int frameCount;
    private int colisionFrame = 50;

    SoldiersMoverStateWar(SoldiersMover army) {
        this.army = army;
    }

    void initState() {
        this.firstContact = null;
    }


    public void commandArmyPosition(float x, float y) {
    }

    public void commandArmyHeading(float x, float y) {
    }

    public void updateArmySoldiers() {
        frameCount++;
        //if ((frameCount%(20*(int)random(1,3))==0 || frameCount%50==0) && frameCount>colisionFrame) { //After 50 frames - move towa
        if (frameCount == 1 || (frameCount % 50 == 0 && frameCount > colisionFrame)) {
            for (Soldier s : army.soldiers) {
                float dx = -s.getX() + positionContact.x; //s.army.absolutPosition.x;
                float dy = -s.getY() + positionContact.y;//s.army.absolutPosition.y;
                PVector p = new PVector(dx, dy);
                p.mult(0.25f * FisicaArmy3.fiscaArmy3.random(1, 3));
                s.setVelocity(p.x, p.y);
            }
        }
    }

    public void updateState() {
    }


    public void updateArmyToZoom() {
        positionContact.mult(GameConstants.zoomFactor);
        army.absolutPosition.mult(GameConstants.zoomFactor);
        for (Soldier s : army.soldiers) {
            s.updateSoldierSizeToZoom();
            s.relPosition.mult(GameConstants.zoomFactor);
            s.setPosition(s.getX() * GameConstants.zoomFactor, s.getY() * GameConstants.zoomFactor);
        }
    }

    @Override
    public void updateMapPosition(float dx, float dy) {
        this.positionContact.add(dx, dy);
    }

    public boolean isMarching() {
        return true;
    }

    public void contactStarted(FContact c) {
        if (this.firstContact == null) {
            this.firstContact = c;
            this.positionContact = new PVector(c.getX(), c.getY());
        }

        if (frameCount > colisionFrame) {
            Soldier s1 = (Soldier) c.getBody1();
            Soldier s2 = (Soldier) c.getBody2();
            s1.attack(s2);
            s2.attack(s1);
        }
    }


}
