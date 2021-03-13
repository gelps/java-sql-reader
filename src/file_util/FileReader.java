package file_util;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files



public class FileReader {

    public static ArrayList<String> loadTable(String filePath) {
        ArrayList<String> tableRepresentation = new ArrayList<>();
        try {
            // e.g. "table.txt"
            File tableInput = new File(filePath);
            Scanner fileReader = new Scanner(tableInput);
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                tableRepresentation.add(data);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            // e.printStackTrace();
        }
        return tableRepresentation;
    }
}

