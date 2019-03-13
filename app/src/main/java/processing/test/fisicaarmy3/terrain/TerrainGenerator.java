package processing.test.fisicaarmy3.terrain;

import java.util.ArrayList;

import processing.core.PVector;
import processing.data.*;
import processing.test.fisicaarmy3.FisicaArmy3;

public class TerrainGenerator {

    Table table;

    public TerrainGenerator() {
        createTable();
    }
    //load existing table...............................
    public void loadShapesFromTable() {
        table = FisicaArmy3.fiscaArmy3.loadTable("new.csv", "header");
        ArrayList<PVector> shapeList = new ArrayList<PVector>();

        FisicaArmy3.fiscaArmy3.println(" GENERATE TERRAIN !!!");

        if(table != null){
            for (TableRow row : table.rows()) {
                float y = row.getFloat("x");
                float x = row.getFloat("y");

                if (x==10000&&y==10000) {
                    if (shapeList.size()>3) {
                        NiveauShape l = new NiveauShape();
                        for (PVector p : shapeList)l.vertex(p.x, p.y);
                        FisicaArmy3.fiscaArmy3.world.add(l);
                    }
                    shapeList.clear();
                } else {
                    shapeList.add(new PVector(x*1.4f,y*1.4f));
                }
            }}else{
            createTable();
        }
    }

    //create new table..................................
    void createTable() {
        table = new Table();
        table.addColumn("x");
        table.addColumn("y");
    }



}
