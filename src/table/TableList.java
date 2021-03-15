package table;

import table.exceptions.TableNotFoundException;

import java.util.HashMap;

public class TableList {

    private HashMap<String, Table> tablesMap;
    private static TableList tableList;

    private TableList() {
        tablesMap = new HashMap<>();
    }

    public Table getTable(String tableName) throws TableNotFoundException {
        Table table = tablesMap.get(tableName);
        if (table != null) return table;
        else throw new TableNotFoundException();
    }

    public static TableList getTableList() {
        if (tableList == null) {
            tableList = new TableList();
        }
        return tableList;
    }
}
