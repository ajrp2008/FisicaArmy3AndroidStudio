class NiveauShape extends FPoly{

  ArrayList<PVector> vertexes = new ArrayList<PVector>();
  
  public void draw(processing.core.PGraphics graphics){
  //  super.draw(graphics);
    noFill();
    stroke(255,0,0);
    
    beginShape();
    curveVertex(30, 20);
    vertex(vertexes.get(0).x,vertexes.get(0).y);
    for(PVector p : vertexes){
      curveVertex(p.x,p.y);
    
    }
    //curveVertex(vertexes.get(vertexes.size()-1).x,vertexes.get(vertexes.size()-1).y);
    //curveVertex(vertexes.get(2).x,vertexes.get(2).y);
   // bezierVertex(80, 0, 80, 75, 30, 75);
    endShape();
    
    
  }
  
  public void vertex(float x, float y){
    super.vertex(x,y);
    vertexes.add(new PVector(x,y));
  
  }


}
