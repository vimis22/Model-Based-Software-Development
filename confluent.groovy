class ExprString {
    String value
    ExprString(String value) { this.value = value }
    String toString() { value }
}

class ColumnBuilder {
    int index
    List<Cell> cellValues = []
    
    ColumnBuilder(int index) {
        this.index = index
    }
    
    ColumnBuilder addCell(Map args, ExprString content) {
        cellValues << new Cell(row: args.row, content: content)
        return this  // Returns 'this' for method chaining
    }
}

class Cell {
    int row
    ExprString content
}

class TableBuilder {
    String name
    Map<Integer, ColumnBuilder> columns = [:]
    
    TableBuilder(String name) {
        this.name = name
    }
    
    /**
     * Creates or retrieves a column builder. Used both in the DSL closure
     * and when adding cells later.  Always returns the column builder for
     * the given index, creating it if necessary.
     */
    ColumnBuilder column(int index) {
        if (!columns.containsKey(index)) {
            columns[index] = new ColumnBuilder(index)
        }
        return columns[index]
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
def myTable = table("users") {
    column 1
    column 2
    column 3
}

myTable
    .column(1)
    .addCell(row: 2, new ExprString("cell content"))
    .addCell(row: 5, new ExprString("cell content"))

myTable.column(2)
    .addCell(row: 1, new ExprString("cell content"))
    .addCell(row: 2, new ExprString("cell content"))

println "Table: ${myTable.name}"
myTable.columns.each { col ->
    println "  Column: ${col.key}, Cells: ${col.value.cellValues.size()}"
    col.value.cellValues.each { cell ->
        println "    Row ${cell.row}: ${cell.content}"
    }
}