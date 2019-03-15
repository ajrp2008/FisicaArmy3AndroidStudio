class Persistence {

  Table table;

  Persistence() {
    createTable();
  }
  //load existing table...............................
  void loadShapesFromTable() {
    table = loadTable("new.csv", "header");
    ArrayList<PVector> shapeList = new ArrayList<PVector>();
    
    if(table != null){
    for (TableRow row : table.rows()) {
      float x = row.getFloat("x");
      float y = row.getFloat("y");

      if (x==10000&&y==10000) {
        if (shapeList.size()>3) {
          NiveauShape l = new NiveauShape();
          for (PVector p : shapeList)l.vertex(p.x, p.y);
          world.add(l);
        }
        shapeList.clear();
      } else {
        shapeList.add(new PVector(x,y));
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

  void addNewShape(ArrayList<PVector> plist) {
    TableRow newRow = table.addRow();
    newRow.setFloat("x", 10000);
    newRow.setFloat("y", 10000);
    for (PVector p : plist) {
      TableRow newRow2 = table.addRow();
      newRow2.setFloat("x", p.x);
      newRow2.setFloat("y", p.y);
    }
    
    TableRow newRow3 = table.addRow();
    newRow3.setFloat("x", 10000);
    newRow3.setFloat("y", 10000);
  }

  void saveToTable() {
    saveTable(table, "new.csv");
  }
}
