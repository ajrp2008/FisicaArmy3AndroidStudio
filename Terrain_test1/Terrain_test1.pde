import fisica.*;
FWorld world;
ArrayList<PVector> vList = new ArrayList<PVector>();
boolean insertVertex     = false;
boolean createShape      = false; 

boolean showBodies       = false;

Soldier     soldier;
Persistence persistence  = new Persistence();

void setup() {
  size(1600, 800);
  smooth();
  Fisica.init(this);
  world = new FWorld();
  world.setGravity(0, 0);
  soldier = new Soldier();
  
  persistence.loadShapesFromTable();
}

void draw() {
        background(70,110,60,180);
  textSize(40);
  text("soldier level:"+soldier.TerrainLevel, 50, 50);

  world.step();
  world.draw();  
  for (PVector p : vList)ellipse(p.x, p.y, 5, 5);

  //println("BODIES:"+world.getBodies().size());
  if(soldier!=null)soldier.update();
  
}

void keyPressed() {

  if (key == 'c') {
    println("create shape!");
    persistence.addNewShape(vList);
    createAShape();
  } 
  if (key == 'd') {
    println("clear points!");
    vList.clear();
  }
  if (key == 's') {
    println("create soldier");
    createSoldier();
  }
  if (key == 'b') {
    println("toggle bodies!");
    showBodies = !showBodies;
  }
  if (key == 'f') {
    println("save to file!");
    persistence.saveToTable();
  }

  soldier.move(key);
}

void createSoldier() {
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
  contactTerrain(c, true, 1);
}

public void contactPersisted(FContact c) {
  contactTerrain(c, true, 0);
}

public void contactEnded(FContact c) { 
  contactTerrain(c, false, -1);
} 

void contactTerrain(FContact c, boolean value, float addLevel) {
  boolean case1 = c.getBody1() instanceof NiveauShape && c.getBody2() instanceof Soldier;
  boolean case2 = c.getBody2() instanceof NiveauShape && c.getBody1() instanceof Soldier;

  if (case1 || case2) {
    NiveauShape n = case1 ? (NiveauShape)c.getBody1() :(NiveauShape)c.getBody2();
    Soldier s = case1 ? (Soldier)c.getBody2() :(Soldier)c.getBody1();
    s.TerrainLevel = s.TerrainLevel + addLevel;
    n.setTouched(value);
  }
}



void mousePressed() {
  insertVertex();
  if(soldier!=null){
    soldier.setTargetPosition(mouseX,mouseY);
  }
  
}
