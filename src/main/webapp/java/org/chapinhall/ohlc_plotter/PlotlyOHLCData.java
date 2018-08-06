package org.chapinhall.ohlc_plotter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

class PlotlyOHLCData {
    ArrayList<Integer> close;
    ArrayList<Integer> high;
    ArrayList<Integer> low;
    ArrayList<Integer> open;
    ArrayList<Date> x;
    String type = "candlestick";
    UUID uid = UUID.randomUUID();

    PlotlyOHLCData(List<DataRow> data) {
        close = getValueColumn(data, "close");
        open = getValueColumn(data, "open");
        high = getValueColumn(data, "max");
        low = getValueColumn(data, "min");
        x = getDateColumn(data);
    }

    static ArrayList<Date> getDateColumn(List<DataRow> data) {
        ArrayList<Date> dates = new ArrayList<Date>();
        for (DataRow row: data) {
            dates.add(row.first.getTime());
        }
        return dates;
    }

    static ArrayList<Integer> getValueColumn(List<DataRow> data, String col) {
        ArrayList<Integer> column = new ArrayList<Integer>();
        for (DataRow row: data) {
            column.add(row.second.get(col));
        }
        return column;
    }

}
