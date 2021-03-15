package table.table_elements;
import program_util.Pair;
import table.exceptions.InvalidSchemaException;
import table.exceptions.RowException;
import table.exceptions.TableExceptionErrorsEnums;

import java.util.*;

public class TableRow {

    private HashMap<String, String> rowElements;
    private TableSchema tableSchema;

    private TableRow(HashMap<String, String> rowElements, TableSchema tableSchema) {
        this.rowElements = rowElements;
        this.tableSchema = tableSchema;
    }

    /*
        Some notes on Java HashMaps and Sets
        ** HashMap.put(key, value) returns existing value if key already in map, otherwise null and puts key in map
        ** HashMap.get(key) returns the value if key is in map, otherwise null
        ** HashMap.keySet() -- REMOVING from keySet removes from the HashMap as well!
        ** Set.remove() returns true if the item was removed, false otherwise
     */

    // For the pair row, row.first = columnName, row,second = columnValue
    // No column can have a null value
    public static TableRow insert_row(ArrayList<Pair<String, String>> row, TableSchema tableSchema)
            throws RowException, InvalidSchemaException {
        HashMap<String, String> rowElements = new HashMap<>();
        Set<String> requiredColumns = copyKeySet(tableSchema.getSchema().keySet());
        for (Pair<String, String> column : row) {
            // If column type is specified as int, check if String is a valid int
            if (!verifyColumnType(column, tableSchema)) throw new RowException(TableExceptionErrorsEnums.RowErrors.COLUMN_TYPE_ERROR);
            if (!requiredColumns.remove(column.first)) throw new RowException(TableExceptionErrorsEnums.RowErrors.ROW_DUPLICATE_KEYS_OR_KEY_NOT_IN_SCHEMA);
            rowElements.put(column.first, column.second);
        }
        // If not all columns in schema used
        if (requiredColumns.size() != 0) throw new RowException(TableExceptionErrorsEnums.RowErrors.ROW_MISSING_COLUMN);
        return new TableRow(rowElements, tableSchema);
    }

    // TableRow promises rows are inserted with the right schema, but it's the tables job to check
    // ensure all columns specified in columnsRequired exist in the schema!
    public HashMap<String, String> getColumns(ArrayList<String> columnsRequested) {
        HashMap<String, String> requestedColumns = new HashMap<>();
        for (String columnRequested : columnsRequested) {
            requestedColumns.put(columnRequested, rowElements.get(columnRequested));
        }
        return requestedColumns;
    }

    // Column is a Pair with pair.first = columnName and pair.second = value;
    private static boolean verifyColumnType(Pair<String, String> column, TableSchema schema) throws InvalidSchemaException {
        TableSchema.ColumnType columnType = schema.getSchema().get(column.first);
        if (columnType == null) throw new InvalidSchemaException(TableExceptionErrorsEnums.SchemaError.COLUMN_NOT_FOUND_IN_SCHEMA);
        if (columnType == TableSchema.ColumnType.INT) {
            try {
                Integer.parseInt(column.second);
                return true;
            }
            catch(NumberFormatException e) {
                return false;
            }
        } else {
            return true;
        }
    }

    private static HashSet<String> copyKeySet(Set<String> keySet) {
        HashSet<String> copiedKeySet = new HashSet();
        for (String key : keySet) {
            copiedKeySet.add(key);
        }
        return copiedKeySet;
    }

    public HashMap<String, String> getRowElements() {
        return rowElements;
    }
}
