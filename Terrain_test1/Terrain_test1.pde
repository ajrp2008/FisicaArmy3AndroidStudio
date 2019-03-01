import fisica.*;
FWorld world;
ArrayList<PVector> vList = new ArrayList<PVector>();
boolean insertVertex = false;
boolean createShape  = false; 

boolean showBodies   = false;

Soldier soldier;

void setup() {
  size(800, 800);
  smooth();
  Fisica.init(this);
  world = new FWorld();
  world.setGravity(0, 0);
  soldier = new Soldier();
}

void draw() {
  background(100);
  
  world.step();
  world.draw();  
  for (PVector p : vList)ellipse(p.x, p.y, 5, 5);
  
  println("BODIES:"+world.getBodies().size());
  
}

void keyPressed() {
  
  if (key == 'c') {println("create shape!");createAShape();} 
  if (key == 'd') {println("clear points!");vList.clear();}
  if (key == 's') {println("create soldier");createSoldier();}
  if (key == 'b') {println("toggle bodies!");showBodies = !showBodies;}
 
  soldier.move(key);
}

void createSoldier(){
  world.remove(soldier);
  world.add(soldier);
}

void insertVertex() {
  vList.add(new PVector(mouseX, mouseY));
}

void createAShape() {  
  if (vList.size() >3) {
    NiveauShape l = new NiveauShape();
    for (PVector p : vList)l.vertex(p.x, p.y);
    world.add(l);
  }
  vList.clear();
}

public void contactStarted(FContact c) {
  contact(c,true);
}

public void contactPersisted(FContact c) {
    contact(c,true);

}

public void contactEnded(FContact c){ 
  contact(c,false);
} 

void contact(FContact c, boolean value){
    boolean case1 = c.getBody1() instanceof NiveauShape && c.getBody2() instanceof Soldier;
  boolean case2 = c.getBody2() instanceof NiveauShape && c.getBody1() instanceof Soldier;
  
  if(case1 || case2){
    NiveauShape n = case1 ? (NiveauShape)c.getBody1() :(NiveauShape)c.getBody2();
    n.setTouched(value); 
  }

}



void mousePressed() {
  insertVertex();
}
