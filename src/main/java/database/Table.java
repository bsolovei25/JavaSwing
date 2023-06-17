package database;

import java.util.*;

public class Table {
    public Table(List<String> columnNames) {
        this.table = new LinkedHashMap<>();
        this.columnNames = columnNames;
    }

    private Map<String, Map<String, String>> table; //String - id, String - column name, String - row value
    private List<String> columnNames;

    public void insertRow(List<String> data) {
        if (data.size() != columnNames.size()) {
            throw new IllegalArgumentException("Row values are imcompatiable with provided database");
        }

        Map<String, String> row = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            row.put(columnNames.get(i), data.get(i));
        }

        table.put(String.valueOf(table.size() + 1), row);
    }

    public Map<String, String> selectRowById(String id){
        return table.get(id);
    }

    public Map<String, Map<String, String>> selectAll(){
        return table;
    }
}
