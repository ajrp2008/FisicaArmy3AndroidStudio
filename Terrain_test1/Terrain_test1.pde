import fisica.*;

FWorld world;

int circleCount = 20;
float hole = 50;
float topMargin = 50;
float bottomMargin = 300;
float sideMargin = 100;
float xPos = 0;

void setup(){
    size(800, 800);
  smooth();

  Fisica.init(this);

  world = new FWorld();
  world.setGravity(0, 0);
  
  NiveauShape l = new NiveauShape();
  l.vertex(100,100);
  l.vertex(130, 200);
  l.vertex(90, 300);
  l.vertex(300, 350);
  l.vertex(250, 200);
  l.vertex(280, 110);
  l.setStatic(true);
  l.setFill(0);
  l.setFriction(1);
  world.add(l);
  
}

void draw(){
  world.step();
  world.draw();
}
