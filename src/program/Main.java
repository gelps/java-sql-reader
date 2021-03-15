package program;

import program_util.Pair;
import table.Table;
import table.TableList;
import table.table_elements.TableSchema;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while (true) {
            TableList tableList = TableList.getTableList();
            Scanner myObj = new Scanner(System.in);
            System.out.println("Please enter SQL command");
            String userName = myObj.nextLine();
//            ArrayList<String> table = FileReader.loadTable(userName);
//            for (String s : table) System.out.println(s);
//            System.out.println("Command is: " + userName);
            try {
                Table table = tableList.getTable("hello");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            Pair<String, TableSchema.ColumnType> pair = new Pair<>("hello", TableSchema.ColumnType.INT);
            System.out.println(pair.second);

        }
    }
}
