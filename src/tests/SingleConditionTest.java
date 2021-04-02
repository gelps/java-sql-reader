package tests;

import file_util.load_table.TableLoader;
import org.junit.Before;
import org.junit.Test;
import program_util.conditions.InvalidConditionException;
import program_util.conditions.SingleCondition;
import table.TableList;
import table.exceptions.TableNotFoundException;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SingleConditionTest {

    @Before
    public void loadTableNms() {
        TableLoader.loadTable("nms.txt");
    }

    @Test
    public void testGEQ() {
        SingleCondition condition;
        try {
            condition = new SingleCondition("name", SingleCondition.Where.GEQ, "Emily");
            HashMap<String, String> emilyA = new HashMap<>();
            emilyA.put("studentid", "102");
            emilyA.put("name", "Emily A");
            emilyA.put("program", "Helicopter Repair Shop Sales Specialist");
            emilyA.put("year", "1");
            try {
                boolean result = condition.blockMeetsConditions(emilyA, TableList.getTableList().getTable("Northwest Manufacturing School").getTableSchema());
                assertTrue(result);
            } catch (TableNotFoundException e) {
                fail("failed at check 1");
            }
        } catch (InvalidConditionException e) {
            fail("failed at check 2");
        }
    }
}
