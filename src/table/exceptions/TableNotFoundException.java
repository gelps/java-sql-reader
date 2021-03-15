package table.exceptions;

public class TableNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "The requested table could not be found.";
    }

}
