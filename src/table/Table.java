package table;

import table.table_elements.TableRow;
import table.table_elements.TableSchema;

import java.util.HashMap;

public class Table {

    // This should not be changed; a new table should be made
    private final HashMap<String, TableSchema.ColumnType> tableSchema;

    private Table(HashMap<String, TableSchema.ColumnType> tableSchema) {
        this.tableSchema = tableSchema;
    }

    // Creates a new Table; should only be used via the table load function
    public static Table create_table(HashMap<String, TableSchema.ColumnType> tableSchema) {
        return new Table(tableSchema);
    }

    // Inserts a row into the table
//    public boolean insert_table(String columnName, String columnValue) {
//    }


}
