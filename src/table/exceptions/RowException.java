package table.exceptions;

public class RowException extends Exception {

    TableExceptionErrorsEnums.RowErrors error;

    public RowException(TableExceptionErrorsEnums.RowErrors error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return "Row was not inserted:" + " " + "ERROR TYPE:" + error.name();
    }

}
