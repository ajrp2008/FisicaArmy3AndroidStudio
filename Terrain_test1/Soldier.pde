class Soldier extends FCircle{
  
  float TerrainLevel = 1;
  PVector targetPosition = new PVector();
  PVector speed = new PVector();
  
  Soldier(){
    super(10);
    this.setPosition(100,100);
    //world.add(this);
  }
  
  void move(char k){
   if(k=='u')setVelocity(0,-100);
   if(k=='j')setVelocity(0,100);
   if(k=='h')setVelocity(-100,0);
   if(k=='k')setVelocity(100,0);
  }
  
  void update(){
    if(dist(targetPosition.x,targetPosition.y,getX(),getY())>10){
      speed.set(getX(),getY());
      speed.sub(targetPosition);
      speed.normalize();
      speed.mult(-100/(TerrainLevel*0.5));
      setVelocity(speed.x,speed.y);
    }
  }
  
  void setTargetPosition(float x, float y){
    targetPosition.set(x,y);
  }
  
  
  
}
