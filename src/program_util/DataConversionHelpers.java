package program_util;

import table.table_elements.TableSchema;

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

    // Convert array of columnNames and columnTypes to pairs
    public static ArrayList<Pair<String, TableSchema.ColumnType>> convertListOfColumnTypesToPairs(
            ArrayList<String> columnNames, ArrayList<TableSchema.ColumnType> columnTypes) {
        ArrayList<Pair<String, TableSchema.ColumnType>> types = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            types.add(new Pair<String, TableSchema.ColumnType>(columnNames.get(i), columnTypes.get(i)));
        }
        return types;
    }

    public static ArrayList<String> getStringBetweenQuotes(String initialString) {
        ArrayList<String> stringArray = new ArrayList<>();
        while (initialString.length() > 0) {
            String thisString;
            if (initialString.contains("\"")) {
                thisString = initialString.substring(initialString.indexOf("\"") + 1);
                initialString = initialString.substring(initialString.indexOf("\"") + 1);
            } else {
                return stringArray;
            }
            if (initialString.contains("\"")) {
                thisString = thisString.substring(0, thisString.indexOf("\""));
                stringArray.add(thisString);
                if (initialString.length() > (initialString.indexOf("\"") + 1)) {
                    initialString = initialString.substring(initialString.indexOf("\"") + 1);
                } else {
                    return stringArray;
                }
            }
        }
        return stringArray;
    }

}
