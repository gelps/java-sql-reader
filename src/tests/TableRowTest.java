package tests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import program_util.DataConversionHelpers;
import program_util.Pair;
import table.exceptions.InvalidSchemaException;
import table.exceptions.RowException;
import table.table_elements.TableRow;
import table.table_elements.TableSchema;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TableRowTest {

    // **Test Series 1**
    // The following tests use the following schema:
    // Table: Students.
    // Column Names: name, studentNumber, program, yearLevel
    // Column Types: String, int, String, int

    TableSchema series1Schema;
    ArrayList<String> columnNames;

    @Before
    public void initSeries1Test() {
        // Setup Column Types
        Pair<String, TableSchema.ColumnType> name = new Pair<>("name", TableSchema.ColumnType.STRING);
        Pair<String, TableSchema.ColumnType> studentNumber = new Pair<>("studentNumber", TableSchema.ColumnType.INT);
        Pair<String, TableSchema.ColumnType> program = new Pair<>("program", TableSchema.ColumnType.STRING);
        Pair<String, TableSchema.ColumnType> yearLevel = new Pair<>("yearLevel", TableSchema.ColumnType.INT);

        ArrayList<Pair<String, TableSchema.ColumnType>> schema = new ArrayList<>();
        schema.add(name);
        schema.add(studentNumber);
        schema.add(program);
        schema.add(yearLevel);

        try {
            series1Schema = new TableSchema(schema);
            assertEquals(4, series1Schema.getSchema().size());
            assertEquals(TableSchema.ColumnType.STRING, series1Schema.getSchema().get("name"));
            assertEquals(TableSchema.ColumnType.INT, series1Schema.getSchema().get("studentNumber"));
            assertEquals(TableSchema.ColumnType.STRING, series1Schema.getSchema().get("program"));
            assertEquals(TableSchema.ColumnType.INT, series1Schema.getSchema().get("yearLevel"));
        } catch (InvalidSchemaException e) {
            fail("Series 1 TableSchema setup failed");
        }

        columnNames = new ArrayList<>(Arrays.asList("name", "studentNumber", "program", "yearLevel"));
        assertEquals(4, columnNames.size());
        assertEquals("name", columnNames.get(0));
        assertEquals("studentNumber", columnNames.get(1));
        assertEquals("program", columnNames.get(2));
        assertEquals("yearLevel", columnNames.get(3));

        System.out.println("Series 1 init passed.");
    }

    @Test
    public void insert_row_success() {
        ArrayList<String> student1 = new ArrayList<>(Arrays.asList("Kevin Rodger", "1022", "CPSC", "5"));
        ArrayList<String> student2 = new ArrayList<>(Arrays.asList("Anna Kelly", "20001", "EOSC", "3"));

        ArrayList<Pair<String, String>> rowStudent1 = DataConversionHelpers.convertListOfRowValuesToPairs(columnNames, student1);
        try {
            TableRow tableRow = TableRow.insert_row(rowStudent1, series1Schema);
            assertEquals(4, tableRow.getRowElements().size());
            assertTrue(tableRow.getRowElements().keySet().contains("name"));
            assertEquals("Kevin Rodger", tableRow.getRowElements().get("name"));
            assertTrue(tableRow.getRowElements().keySet().contains("studentNumber"));
            assertEquals("1022", tableRow.getRowElements().get("studentNumber"));
            assertTrue(tableRow.getRowElements().keySet().contains("program"));
            assertEquals("CPSC", tableRow.getRowElements().get("program"));
            assertTrue(tableRow.getRowElements().keySet().contains("yearLevel"));
            assertEquals("5", tableRow.getRowElements().get("yearLevel"));
            System.out.println("insert Student 1 passed");
        } catch (RowException | InvalidSchemaException e) {
            fail("Series 1 insert_row_success failed on Student 1");
        }

        ArrayList<Pair<String, String>> rowStudent2 = DataConversionHelpers.convertListOfRowValuesToPairs(columnNames, student2);
        try {
            TableRow tableRow = TableRow.insert_row(rowStudent2, series1Schema);
            assertEquals(4, tableRow.getRowElements().size());
            assertTrue(tableRow.getRowElements().keySet().contains("name"));
            assertEquals("Anna Kelly", tableRow.getRowElements().get("name"));
            assertTrue(tableRow.getRowElements().keySet().contains("studentNumber"));
            assertEquals("20001", tableRow.getRowElements().get("studentNumber"));
            assertTrue(tableRow.getRowElements().keySet().contains("program"));
            assertEquals("EOSC", tableRow.getRowElements().get("program"));
            assertTrue(tableRow.getRowElements().keySet().contains("yearLevel"));
            assertEquals("3", tableRow.getRowElements().get("yearLevel"));
            System.out.println("insert Student 2 passed");
        } catch (RowException | InvalidSchemaException e) {
            fail("Series 1 insert_row_success failed on Student 2");
        }

        System.out.println("insert_row success passed");
    }
}
