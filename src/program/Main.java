package program;

import file_util.query.QueryException;
import file_util.query.QueryParser;
import file_util.load_table.TableLoader;
import program_util.conditions.InvalidConditionException;
import table.TableList;
import table.exceptions.TableNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws TableNotFoundException, InvalidConditionException {
        TableList tableList = TableList.getTableList();
        while (true) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("Please select an option");
            System.out.println("1. Load Table");
            System.out.println("2. Enter Query");
            String opt = myObj.nextLine();
            if (opt.equals("1")) {
                System.out.println("Please enter file name to load:");
                String fileName = myObj.nextLine();
                TableLoader.loadTable(fileName);
            }
            if (opt.equals("2")) {
                System.out.println("Please enter query:");
                String query = myObj.nextLine();
                try {
                    ArrayList<String> rows = QueryParser.parseQuery(query);
                    for (String row : rows) {
                        System.out.println(row);
                    }
                } catch (QueryException | TableNotFoundException | InvalidConditionException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
