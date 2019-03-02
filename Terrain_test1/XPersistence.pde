class Persistence {

  Table table;

  void saveToTable() {

    table = new Table();

    table.addColumn("x");
    table.addColumn("y");

    TableRow newRow = table.addRow();
    newRow.setInt("x", 1);
    newRow.setInt("y", 2);

    saveTable(table, "new.csv");
  }

  void loadFromTable() {
  }
  // Sketch saves the following to a file called "new.csv":
  // id,species,name
  // 0,Panthera leo,Lion
}
