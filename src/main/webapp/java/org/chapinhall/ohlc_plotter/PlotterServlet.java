package org.chapinhall.ohlc_plotter;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Dictionary;

public class PlotterServlet extends HttpServlet {
        public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
            Gson gson = new Gson();
        }

        public static Dictionary loadData() {
            return null;
        }

        public static PlotlyOHLCData processData(Dictionary data) {
            return null;
        }
}
