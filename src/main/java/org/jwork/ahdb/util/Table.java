package org.jwork.ahdb.util;


import java.util.List;
import java.util.Map;

import static org.jwork.ahdb.util.U.list;

public class Table {

    private Row<String> columnNames;
    private List<Row> rows = list();
    private List<Integer> columnWidths = list();

    public Table(List names) {
        columnNames = new Row<>(names);
    }

    public void putRow(Row row) {
        rows.add(row);
    }

    // currently for ES only
    public Table putAll(List<Map<String, Object>> rows) {
        rows.forEach(map -> {
            Row<Object> row = new Row<>();
            columnNames.forEach(k -> row.add(map.get(k)));
            putRow(row);
        });
        return this;
    }

    public void prettyPrint() {
        computeColumnWidths();
        StringBuffer sb = new StringBuffer();
        sb.append(lineSeparatorSb());
        sb.append(rowSb(columnNames));
        sb.append(lineSeparatorSb());
        rows.forEach(row -> sb.append(rowSb(row)));
        sb.append(lineSeparatorSb());
        System.out.println(sb);
    }

    private StringBuilder lineSeparatorSb() {
        StringBuilder bigSb = new StringBuilder();
        columnWidths.forEach(w -> {
            StringBuilder sb = new StringBuilder();
            sb.append("+");
            for (int i = 0; i < w; i++) {
                sb.append("-");
            }
            bigSb.append(sb);
        });
        bigSb.append("+\n");
        return bigSb;
    }

    private StringBuilder rowSb(Row row) {
        StringBuilder bigSb = new StringBuilder();
        for (int i = 0; i < row.size(); i++) {
            StringBuilder sb = new StringBuilder();
            int w = columnWidths.get(i);

            Object cell = row.get(i);
            if (cell == null) {
                cell = "null";
            }
            String cellStr = U.fixedLenStr(cell.toString(), w);

            sb.append("|").append(cellStr);
            bigSb.append(sb);
        }
        bigSb.append("|\n");
        return bigSb;
    }

    private void computeColumnWidths() {
        rows.add(0, columnNames);
        int width = rows.get(0).size();
        for (int w = 0; w < width; w++) {
            int max = 0;
            for (Row row : rows) {
                Object o = row.get(w);
                int ol = U.length(o);
                if (ol > max) {
                    max = ol;
                }
            }
            columnWidths.add(max);
        }
        rows.remove(0);
    }

    private void testPutRow() {
        U.sleep(5);

    }

}
