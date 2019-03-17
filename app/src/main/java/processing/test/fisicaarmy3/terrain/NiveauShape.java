package processing.test.fisicaarmy3.terrain;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import fisica.FPoly;
import processing.core.*;


public class NiveauShape extends FPoly {

    ArrayList<PVector> vertexes = new ArrayList<PVector>();

    NiveauShape(){
        super();
        setFill(40,40);
        setSensor(true);
        setGrabbable(false);
        setFriction(1);
    }

/*
    public void draw(processing.core.PGraphics graphics){
        graphics.stroke(0,0,0);
        graphics.fill(0, vertexSize,0);

        graphics.beginShape();

        graphics.curveVertex(vertexes.get(vertexes.size()-2).x,vertexes.get(vertexes.size()-2).y);
        graphics.curveVertex(vertexes.get(vertexes.size()-1).x,vertexes.get(vertexes.size()-1).y);
        for(PVector p : vertexes){
            graphics.curveVertex(p.x,p.y);
        }
        graphics.curveVertex(vertexes.get(vertexes.size()-1).x,vertexes.get(vertexes.size()-1).y);
        graphics.endShape();
    }*/

    public void vertex(float x, float y){
        super.vertex(x,y);
        vertexes.add(new PVector(x,y));
    }

    public void setZoom(float factor){
        for(Object v: this.m_vertices)((Vec2)v).mulLocal(factor);
        this.recreateInWorld();
    }

}