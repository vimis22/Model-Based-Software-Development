class Table {
    public String name
    public Map<Integer, Column> columns = new HashMap()
}

class TableBuilder {
    private Table table = new Table();
    private Integer lastColumn

    TableBuilder name(String name) {
        this.table.name = name;
        return this
    }
    
    TableBuilder column(int indx) {
        this.table.columns[indx] = new Column(index: indx)
        this.lastColumn = indx
        return this
    }

    TableBuilder addCell(Integer row, Expression expression) {
        this.table.columns[lastColumn].cells[row] = new Cell(row, expression)
        return this
    }

    Table getTable() {
        return this.table
    }
}

class Column {
    int index
    Map<Integer, Cell> cells = new HashMap()
}

class Cell {
    int index
    Expression expr

    public Cell(int rowIndx, Expression expr){
        this.index = rowIndx
        this.expr = expr
    }
}

abstract class Expression {
    public abstract String evaluate() //evaluate the expression, the result is always a string
}

class ExprString extends Expression {
    String stringValue

    public ExprString(String stringValue){
        this.stringValue = stringValue
    }

    public String evaluate(){
        return this.stringValue
    }
}

def table(String name, @DelegatesTo(TableBuilder) Closure closure) {
    def builder = new TableBuilder(name)
    closure.delegate = builder
    closure.resolveStrategy = Closure.DELEGATE_FIRST
    closure()
    return builder
}

// Usage:
// def myTable = table("users", {
//     column 1 //width: "2cm"
//     column 2 //width: "5cm"
//     column 3 //width: "5cm"
// })
def myTable = new TableBuilder()
    .name("users")
    .column(1)
    .addCell(2, new ExprString("cell content"))
    .addCell(5, new ExprString("yaay"))
    .column(2)
    .addCell(1, new ExprString("another one"))
    .addCell(2, new ExprString("deltarune tomorrow"))
    .getTable()

println "Table: ${myTable.name}"
myTable.columns.each { col ->
    println "  Column: ${col.key}"
    col.value.cells.each { cellidx, cell ->
    println "        Cell: ${cellidx}"
    println "           Cell data: ${cell.expr.evaluate()}"
    }
}