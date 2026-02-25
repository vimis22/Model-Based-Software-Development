class TableBuilder {
    String name
    List<Column> columns = []

    TableBuilder(String name) {
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

def table(String name, @DelegatesTo(TableBuilder) Closure closure) {
    def builder = new TableBuilder(name)
    closure.delegate = builder
    closure.resolveStrategy = Closure.DELEGATE_FIRST
    closure()
    return builder
}

// Usage:
def myTable = table("users", {
    column "id", "INTEGER"
    column "username", "VARCHAR"
    column name: "email", type: "VARCHAR"
})

println "Table: ${myTable.name}"
myTable.columns.each { col ->
    println "  Column: ${col.name} (${col.type})"
}