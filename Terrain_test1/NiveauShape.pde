class NiveauShape extends FPoly{

  ArrayList<PVector> vertexes = new ArrayList<PVector>();
  boolean isTouched = false;
  float vertexSize=50;
  
  NiveauShape(){
    super();
    setFill(0);
    setSensor(true);
    setGrabbable(false);
    setFriction(1);
  }
  
  
  public void draw(processing.core.PGraphics graphics){

      graphics.fill(0,vertexSize,0);
    
    graphics.beginShape();
    
    graphics.curveVertex(vertexes.get(vertexes.size()-2).x,vertexes.get(vertexes.size()-2).y);
    graphics.curveVertex(vertexes.get(vertexes.size()-1).x,vertexes.get(vertexes.size()-1).y);
    for(PVector p : vertexes){
      graphics.curveVertex(p.x,p.y);
      //ellipse(p.x,p.y,5,5);
    }
    graphics.curveVertex(vertexes.get(vertexes.size()-1).x,vertexes.get(vertexes.size()-1).y);
   graphics.endShape();
  }
  
  public void vertex(float x, float y){
    super.vertex(x,y);
    vertexes.add(new PVector(x,y));  
    vertexSize = vertexSize +5;
  }
  
  void setTouched(boolean value){
    isTouched = value;
    
   // println("Touch"+frameCount);
  }


}
