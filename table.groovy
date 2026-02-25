class TableBuilder {
    String name
    List<Column> columns = []
    
    void name(String name) {
        this.name = name
    }
    
    void column(String name, String type) {
        columns << new Column(name: name, type: type)
    }
    
    void column(Map args) {
        columns << new Column(name: args.name, type: args.type)
    }
}

class Column {
    String name
    String type
}

def table(@DelegatesTo(TableBuilder) Closure closure) {
    def builder = new TableBuilder()
    closure.delegate = builder
    closure.resolveStrategy = Closure.DELEGATE_FIRST
    closure()
    return builder
}

// Usage:
def myTable = table {
    name "users"
    column "id", "INTEGER"
    column "username", "VARCHAR"
    column name: "email", type: "VARCHAR"
}

println "Table: ${myTable.name}"
myTable.columns.each { col ->
    println "  Column: ${col.name} (${col.type})"
}