package program;

import file_util.FileException;
import file_util.TableLoader;
import program_util.Pair;
import program_util.conditions.AndBlock;
import program_util.conditions.InvalidConditionException;
import table.Table;
import table.TableList;
import table.exceptions.InvalidSchemaException;
import table.exceptions.TableNotFoundException;
import table.table_elements.TableSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws TableNotFoundException, InvalidConditionException {
        TableList tableList = TableList.getTableList();
        while (true) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("Please select an option");
            System.out.println("1. Load Table");
            String opt = myObj.nextLine();
            if (opt.equals("1")) {
                System.out.println("Please enter file name to load:");
                String fileName = myObj.nextLine();
                TableLoader.loadTable(fileName);
            }
            if (opt.equals("2")) {
                ArrayList<ArrayList<String>> results = TableList.getTableList().getTable("Northwest Manufacturing School").getTableRows(new
                        ArrayList<>(Arrays.asList("studentid", "program", "year")), new AndBlock(new ArrayList<>()));
                for (ArrayList<String> arr : results) {
                    String s = "";
                    for (String g : arr) {
                        s = s + " " + g;
                    }
                    System.out.println(s);
                }
            }
        }
    }
}
