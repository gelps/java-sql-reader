package table.exceptions;

public interface TableExceptionErrorsEnums {
    public enum RowErrors {
        COLUMN_TYPE_ERROR,
        ROW_DUPLICATE_KEYS_OR_KEY_NOT_IN_SCHEMA,
        ROW_MISSING_COLUMN
    }

    public enum SchemaError {
        COLUMN_NOT_FOUND_IN_SCHEMA,
        COLUMN_TYPE_SPECIFIED_MORE_THAN_ONCE
    }
}
