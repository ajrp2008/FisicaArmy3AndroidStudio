class NiveauShape extends FPoly{

  ArrayList<PVector> vertexes = new ArrayList<PVector>();
  
  NiveauShape(){
    super();
    setFill(0);
    setSensor(true);
    setFriction(1);
  }
  
  
  public void draw(processing.core.PGraphics graphics){
   // noFill();
   // super.draw(graphics);
    stroke(0);
       ellipse(vertexes.get(0).x,vertexes.get(0).y,15,15);

    
    //noFill();
    stroke(255,0,0);
    beginShape();
    
    curveVertex(vertexes.get(vertexes.size()-2).x,vertexes.get(vertexes.size()-2).y);
    curveVertex(vertexes.get(vertexes.size()-1).x,vertexes.get(vertexes.size()-1).y);
    for(PVector p : vertexes){
      curveVertex(p.x,p.y);
      ellipse(p.x,p.y,5,5);
    }
    curveVertex(vertexes.get(vertexes.size()-1).x,vertexes.get(vertexes.size()-1).y);
    endShape();
    
    
  }
  
  public void vertex(float x, float y){
    super.vertex(x,y);
    vertexes.add(new PVector(x,y));
  
  }


}
