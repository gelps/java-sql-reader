package program_util.conditions;

import table.table_elements.TableSchema;

import java.util.HashMap;

public interface ConditionBlock {

    public boolean blockMeetsConditions(HashMap<String, String> rowElements, TableSchema tableSchema)
            throws InvalidConditionException;

}
