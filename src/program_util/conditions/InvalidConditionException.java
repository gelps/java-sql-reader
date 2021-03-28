package program_util.conditions;

import table.exceptions.InvalidSchemaException;

public class InvalidConditionException extends Exception {

    public enum ConditionError {
        GIVEN_CONDITION_DOES_NOT_MEET_SPECIFICATION,
        COLUMN_INDICATED_BY_CONDITION_NOT_FOUND_IN_ROW,
        CONDITION_CONSTRUCTOR_WHERE_TYPE_ERROR,
        UNKNOWN_ERROR_WHILE_CHECKING_ROW_WITH_SINGLE_CONDITION
    }

    ConditionError error;

    public InvalidConditionException(ConditionError error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return error.name();
    }
}
