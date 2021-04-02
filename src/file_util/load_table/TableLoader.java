package file_util.load_table;

import file_util.load_table.FileException;
import program_util.DataConversionHelpers;
import program_util.Pair;
import table.Table;
import table.TableList;
import table.exceptions.InvalidSchemaException;
import table.exceptions.RowException;
import table.exceptions.TableNotFoundException;
import table.table_elements.TableSchema;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TableLoader {

    public static void loadTable(String filePath) {
        try {
            filePath = "src/tablefiles/" + filePath;
            File tableInput = new File(filePath);
            Scanner fileReader = new Scanner(tableInput);
            int lineNo = 1;
            String name = "";
            ArrayList<String> columns = new ArrayList<>();
            ArrayList<TableSchema.ColumnType> types = new ArrayList<>();
            Table table = null;
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                switch (lineNo) {
                    case 1:
                        name = data.substring(data.indexOf("\"") + 1);
                        name = name.substring(0, name.indexOf("\""));
                        break;
                    case 2:
                        columns = DataConversionHelpers.getStringBetweenQuotes(data);
                        break;
                    case 3:
                        ArrayList<String> typesString = DataConversionHelpers.getStringBetweenQuotes(data);
                        for (String typeString : typesString) {
                            if (typeString.equals("int")) {
                                types.add(TableSchema.ColumnType.INT);
                            } else if (typeString.equals("string")) {
                                types.add(TableSchema.ColumnType.STRING);
                            } else {
                                throw new FileException(FileException.FileError.COLUMN_TYPE_SPECIFIED_BY_FILE_INVALID, lineNo);
                            }
                        }
                        TableSchema schema = new TableSchema(DataConversionHelpers.convertListOfColumnTypesToPairs(columns, types));
                        Table.create_table(name, schema);
                        table = TableList.getTableList().getTable(name);
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        ArrayList<Pair<String, String>> row = DataConversionHelpers.convertListOfRowValuesToPairs(columns,
                                DataConversionHelpers.getStringBetweenQuotes(data));
                        table.insert_row(row);
                        break;
                }
                lineNo++;
            }
            fileReader.close();
        } catch (FileNotFoundException | RowException | FileException | InvalidSchemaException | TableNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
