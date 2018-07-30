package org.chapinhall.ohlc_plotter;

import java.util.Date;
import java.util.UUID;

class PlotlyOHLCData {
    long[] close;
    long[] high;
    long[] low;
    long[] open;
    Date[] x;
    String type = "candlestick";
    UUID uid;

    PlotlyOHLCData() {
        uid = UUID.randomUUID();
    }
}
