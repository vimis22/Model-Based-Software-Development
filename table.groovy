class TableBuilder {
    String name
    List<Column> columns = []

    TableBuilder(String name) {
        this.name = name
    }
    
    void column(String name) {
        columns << new Column(name: name)
    }
    
    void column(Map args) {
        columns << new Column(name: args.name)
    }
}

class Column {
    String name
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
    column "id"
    column "username"
    column name: "email"
})

println "Table: ${myTable.name}"
myTable.columns.each { col ->
    println "  Column: ${col.name}"
}