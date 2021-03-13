package program;

import file_util.FileReader;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while (true) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("Please enter SQL command");
            String userName = myObj.nextLine();
            ArrayList<String> table = FileReader.loadTable(userName);
            for (String s : table) System.out.println(s);
            System.out.println("Command is: " + userName);
        }
    }
}
