package program_util.conditions;

import table.table_elements.TableSchema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SingleCondition implements ConditionBlock {

    private String columnName;
    private Where condition;
    private String conditionValueString;
    private Set<String> inSet;

    public enum Where {
        GT, EQ, LT, GEQ, LEQ, NEQ, IN
    }

    public SingleCondition(String columnName, Where condition, String
            conditionValueString) throws InvalidConditionException {
        if (condition == Where.IN)
            throw new InvalidConditionException(InvalidConditionException.ConditionError.CONDITION_CONSTRUCTOR_WHERE_TYPE_ERROR);
        this.columnName = columnName;
        this.condition = condition;
        this.conditionValueString = conditionValueString;
    };

    public SingleCondition(String columnName, Where condition, Set<String>
            conditionValueSet) throws InvalidConditionException {
        if (condition != Where.IN)
            throw new InvalidConditionException(InvalidConditionException.ConditionError.CONDITION_CONSTRUCTOR_WHERE_TYPE_ERROR);
        this.columnName = columnName;
        this.condition = condition;
        this.inSet = conditionValueSet;
    };

    @Override
    public boolean blockMeetsConditions(HashMap<String, String> rowElements, TableSchema tableSchema) throws
            InvalidConditionException {
        return singleConditionMet(rowElements, tableSchema);
    }

    private boolean singleConditionMet(HashMap<String, String> rowElements, TableSchema tableSchema) throws
            InvalidConditionException {
        if (rowElements.get(this.columnName) == null)
            throw new InvalidConditionException(InvalidConditionException.ConditionError.COLUMN_INDICATED_BY_CONDITION_NOT_FOUND_IN_ROW);
        if (condition == Where.IN) {
            return inSet.contains(rowElements.get(this.columnName));
        }
        int conditionValueInt;
        int columnValueInt;
        if (tableSchema.getSchema().get(this.columnName) == TableSchema.ColumnType.INT) {
            conditionValueInt = Integer.parseInt(this.conditionValueString);
            columnValueInt = Integer.parseInt(rowElements.get(this.columnName));
        } else {
            conditionValueInt = 0;
            columnValueInt = rowElements.get(this.columnName).compareTo(this.conditionValueString);
        }
        if (this.condition == Where.GT) return (columnValueInt > conditionValueInt);
        if (this.condition == Where.EQ) return (columnValueInt == conditionValueInt);
        if (this.condition == Where.LT) return (columnValueInt < conditionValueInt);
        if (this.condition == Where.GEQ) return (columnValueInt >= conditionValueInt);
        if (this.condition == Where.LEQ) return (columnValueInt <= conditionValueInt);
        if (this.condition == Where.NEQ) return (columnValueInt != conditionValueInt);
        else throw new InvalidConditionException(InvalidConditionException.ConditionError.UNKNOWN_ERROR_WHILE_CHECKING_ROW_WITH_SINGLE_CONDITION);
    }
}
