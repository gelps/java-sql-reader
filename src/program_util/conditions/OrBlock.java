package program_util.conditions;

import table.table_elements.TableSchema;

import java.util.ArrayList;
import java.util.HashMap;

public class OrBlock implements ConditionBlock {

    ArrayList<ConditionBlock> orBlock;

    public OrBlock(ArrayList<ConditionBlock> orBlock) {
        this.orBlock = orBlock;
    }

    public boolean blockMeetsConditions(HashMap<String, String> rowElements, TableSchema tableSchema)
            throws InvalidConditionException {
        return orConditionsMet(rowElements, tableSchema);
    }

    private boolean orConditionsMet(HashMap<String, String> rowColumns, TableSchema tableSchema)
            throws InvalidConditionException {
        for (ConditionBlock conditionBlock : this.orBlock) {
            if (conditionBlock.blockMeetsConditions(rowColumns, tableSchema)) return true;
        }
        return false;
    }
}
