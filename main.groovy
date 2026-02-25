import groovy.transform.ToString

class Column {
    String name;

}

@ToString
class Table {
    String name;
    Map<int, Column> columns;

    Table(String name) {
        this.name = name;
    }

    void addColumn(int position, String columnName) {
        this.columns[]
    }
}

def myTable = new Table("MyTable")
println myTable
