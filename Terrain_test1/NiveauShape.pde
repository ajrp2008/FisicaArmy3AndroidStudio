class NiveauShape extends FPoly{

  public void draw(processing.core.PGraphics graphics){
    super.draw(graphics);
    noFill();
    stroke(255,0,0);
    ellipse(100,100,400,300);
  }


}
