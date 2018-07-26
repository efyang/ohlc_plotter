package org.chapinhall.ohlc_plotter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PlotterServlet extends HttpServlet {
        public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

        }

        public PlotlyOHLCData loadData() {
            return new PlotlyOHLCData();
        }


}
