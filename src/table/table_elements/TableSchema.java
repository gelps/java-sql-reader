package table.table_elements;

import program_util.Pair;
import table.exceptions.InvalidSchemaException;
import table.exceptions.TableExceptionErrorsEnums;

import java.util.ArrayList;
import java.util.HashMap;

public class TableSchema {

    public enum ColumnType {
        STRING,
        INT
    };

    private final HashMap<String, ColumnType> schema;
    private final ArrayList<String> defaultColumnOrder;

    // schema is a Pair such that schema.first = columnName and schema.second = columnType
    public TableSchema(ArrayList<Pair<String, ColumnType>> schema) throws InvalidSchemaException {
        this.schema = new HashMap<>();
        this.defaultColumnOrder = new ArrayList<>();
        for (Pair<String, TableSchema.ColumnType> column : schema) {
            if (this.schema.put(column.first, column.second) != null) throw new InvalidSchemaException(TableExceptionErrorsEnums.SchemaError.COLUMN_TYPE_SPECIFIED_MORE_THAN_ONCE);
            defaultColumnOrder.add(column.first);
        }
    }

    public HashMap<String, ColumnType> getSchema() {
        return this.schema;
    }

    public ArrayList<String> getDefaultColumnOrder() {
        return this.defaultColumnOrder;
    }

}
