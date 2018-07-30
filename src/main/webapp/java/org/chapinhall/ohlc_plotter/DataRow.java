package org.chapinhall.ohlc_plotter;

import java.util.ArrayList;
import java.util.Date;

public class DataRow {
    Date first;
    ArrayList<Integer> second;

    DataRow(Date first) {
        new DataRow(first, new ArrayList<Integer>());
    }

    DataRow(Date first, ArrayList<Integer> second) {
        this.first = first;
        this.second = second;
    }
}
