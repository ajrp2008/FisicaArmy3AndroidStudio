package processing.test.fisicaarmy3.terrain;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import fisica.FPoly;
import processing.core.*;
import processing.test.fisicaarmy3.FisicaArmy3;


class NiveauShape extends FPoly {

    ArrayList<PVector> vertexes = new ArrayList<PVector>();
    boolean isTouched = false;
    float vertexSize = 10;
    static float numberNiveau = 10;

    NiveauShape(){
        super();
        numberNiveau+=3;
        //setFill(numberNiveau);
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
        vertexSize = vertexSize +10;
    }

    public void setZoom(float factor){
        for(Object v: this.m_vertices){
            ((Vec2)v).mulLocal(factor);
            System.out.println("vertex er ændret!!" + m_vertices.indexOf(v));
        }
    }

}