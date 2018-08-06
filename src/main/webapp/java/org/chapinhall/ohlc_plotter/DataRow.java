package org.chapinhall.ohlc_plotter;

import java.util.*;

public class DataRow {
    Calendar first;
    Map<String, Integer> second;

    DataRow(Date first) {
        this.first = Calendar.getInstance();
        this.first.setTime(first);
        this.second = new HashMap<String, Integer>();
    }

    DataRow(Date first, Map<String, Integer> second) {
        this.first = Calendar.getInstance();
        this.first.setTime(first);
        this.second = second;
    }
}
