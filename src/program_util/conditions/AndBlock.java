package program_util.conditions;

import table.table_elements.TableSchema;

import java.util.ArrayList;
import java.util.HashMap;

public class AndBlock implements ConditionBlock {

    ArrayList<ConditionBlock> andBlock;

    public AndBlock(ArrayList<ConditionBlock> andBlock) {
        this.andBlock = andBlock;
    }

    public boolean blockMeetsConditions(HashMap<String, String> rowElements, TableSchema tableSchema)
            throws InvalidConditionException {
        return andConditionsMet(rowElements, tableSchema);
    }

    private boolean andConditionsMet(HashMap<String, String> rowColumns, TableSchema tableSchema)
            throws InvalidConditionException {
        for (ConditionBlock conditionBlock : this.andBlock) {
            if (!conditionBlock.blockMeetsConditions(rowColumns, tableSchema)) return false;
        }
        return true;
    }
}
