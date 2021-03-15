package program_util;

import java.util.ArrayList;

public class DataConversionHelpers {

    // Convert array of columnNames and columnValues to pairs; columnValues[0] is the value for columnNames[0] and so on
    // This method does not do any sort of error handling, whoever calls it takes responsibility
    // REQUIRES: columnNames and columnValues are the same length
    public static ArrayList<Pair<String, String>> convertListOfRowValuesToPairs(
            ArrayList<String> columnNames, ArrayList<String> columnValues) {
        ArrayList<Pair<String, String>> row = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            row.add(new Pair<String, String>(columnNames.get(i), columnValues.get(i)));
        }
        return row;
    }
}
