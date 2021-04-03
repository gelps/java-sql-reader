package tests;

import file_util.load_table.TableLoader;
import file_util.query.QueryException;
import file_util.query.QueryParser;
import org.junit.Test;
import program_util.conditions.InvalidConditionException;
import table.exceptions.TableNotFoundException;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QueryParserTest {

    @Test
    public void queryParserNmsTest() {
        TableLoader.loadTable("nms.txt");
        try {
            ArrayList<String> result = QueryParser.parseQuery("SELECT * FROM Northwest Manufacturing School WHERE (AND (OR (GEQ name Emily A) (EQ studentid 500)) (EQ program Car Fixer))");
            assertEquals(1, result.size());
            assertEquals("studentid = \"500\" name = \"John G\" program = \"Car Fixer\" year = \"1\"", result.get(0));
        } catch (QueryException | TableNotFoundException | InvalidConditionException e) {
            fail("fail parse Nms - caught error unexpected");
        }
    }

    @Test
    public void queryParserCctTest() {
        TableLoader.loadTable("cct.txt");
        try {
            ArrayList<String> result = QueryParser.parseQuery("SELECT city FROM Canadian Cities WHERE (OR (AND (EQ province BC) (GT population-2000 3000000)) (AND (EQ province ON) (GEQ population-2016 1000000)))");
            assertEquals(2, result.size());
            assertEquals("city = \"Toronto\"", result.get(0));
            assertEquals("city = \"Ottawa\"", result.get(1));
            ArrayList<String> result2 = QueryParser.parseQuery("SELECT * FROM Canadian Cities WHERE (IN city Vancouver,Toronto,Calgary)");
            assertEquals(3, result2.size());
        } catch (QueryException | TableNotFoundException | InvalidConditionException e) {
            fail("fail parse Cct - caught error unexpected");
        }
    }
}
