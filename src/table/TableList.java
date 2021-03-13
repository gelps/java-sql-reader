package table;

import java.util.HashMap;

public class TableList {

    private HashMap<String, Table> tablesMap;
    private static TableList tableList;

    private TableList() {
        tablesMap = new HashMap<>();
    }

    public static TableList getTableList() {
        if (tableList == null) {
            tableList = new TableList();
        }
        return tableList;
    }
}
