class Soldier extends FCircle{
  Soldier(){
    super(20);
    this.setPosition(100,100);
    //world.add(this);
  }
  
  void move(char k){
   if(k=='u')setVelocity(0,-100);
   if(k=='j')setVelocity(0,100);
   if(k=='h')setVelocity(-100,0);
   if(k=='k')setVelocity(100,0);
    
  }
  
  
  
}
