package file_util.query;

public class QueryException extends Exception {

    public enum QueryError {
        MISSING_SELECT_STATEMENT,
        MISSING_FROM_STATEMENT,
        MISSING_WHERE_STATEMENT,
        ERROR_PARSING_WHERE
    }

    QueryError queryError;

    public QueryException(QueryError queryError) {
        this.queryError = queryError;
    }

    @Override
    public String getMessage() { return this.queryError.name(); }

}
