package table;

import program_util.Pair;
import program_util.conditions.ConditionBlock;
import program_util.conditions.InvalidConditionException;
import table.exceptions.InvalidSchemaException;
import table.exceptions.RowException;
import table.table_elements.TableRow;
import table.table_elements.TableSchema;

import java.util.ArrayList;

public class Table {

    // This should not be changed; a new table should be made
    private final TableSchema tableSchema;
    private final String tableName;
    private final ArrayList<TableRow> tableRows;

    private Table(String tableName, TableSchema tableSchema) {
        tableRows = new ArrayList<>();
        this.tableName = tableName;
        this.tableSchema = tableSchema;
    }

    // Creates a new Table
    public static void create_table(String tableName, TableSchema tableSchema) {
        Table table = new Table(tableName, tableSchema);
        TableList.getTableList().addTable(table);
    }

    public void insert_row (ArrayList<Pair<String, String>> row) throws
            RowException, InvalidSchemaException {
        this.tableRows.add(TableRow.insert_row(row, this.tableSchema));
    }

    public ArrayList<ArrayList<String>> getTableRows(ArrayList<String> requestedColumns,
                                                     ConditionBlock conditionBlock) throws InvalidConditionException {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        for (TableRow tableRow : this.tableRows) {
            ArrayList<String> row;
            row = tableRow.getColumns(requestedColumns, conditionBlock);
            if (row != null) rows.add(row);
        }
        return rows;
    }

    public String getTableName() {
        return this.tableName;
    }

}
