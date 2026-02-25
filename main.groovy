import groovy.transform.ToString

class Column {
    String name;

    Column(String name) {
        this.name = name;
    }
}

@ToString
class Table {
    String name;
    Map<Integer, Column> columns = [:];

    Table(String name) {
        this.name = name;
    }

    void addColumn(int position, String columnName) {
        columns.put(position, new Column(columnName));
    }
}

def myTable = new Table("MyTable")
println myTable
