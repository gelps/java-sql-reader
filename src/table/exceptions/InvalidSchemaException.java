package table.exceptions;

public class InvalidSchemaException extends Exception {

    TableExceptionErrorsEnums.SchemaError error;

    public InvalidSchemaException(TableExceptionErrorsEnums.SchemaError error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return error.name();
    }
}
