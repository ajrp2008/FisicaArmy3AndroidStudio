class Persistence {

  Table table;

  Persistence(){
    createTable();
  }

  void createTable(){
    table = new Table();
    table.addColumn("x");
    table.addColumn("y");    
  }

  void addNewShape(ArrayList<PVector> plist){
      TableRow newRow = table.addRow();
      newRow.setFloat("x", 10000);
      newRow.setFloat("y", 10000);
    for(PVector p: plist){
      newRow = table.addRow();
      newRow.setFloat("x", p.x);
      newRow.setFloat("y", p.y);
    }  
  }

  void saveToTable() {
    saveTable(table, "new.csv");
  }

  void loadFromTable() {
  }
  // Sketch saves the following to a file called "new.csv":
  // id,species,name
  // 0,Panthera leo,Lion
}
