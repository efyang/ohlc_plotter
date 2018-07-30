package org.chapinhall.ohlc_plotter;

import java.util.UUID;

class PlotlyOHLCArgs {
    UUID id;
    PlotlyOHLCData data;
    String title;
    boolean showLink = false;

    PlotlyOHLCArgs(PlotlyOHLCData data, String title) {
        id = UUID.randomUUID();
        this.data = data;
        this.title = title;
    }
}
