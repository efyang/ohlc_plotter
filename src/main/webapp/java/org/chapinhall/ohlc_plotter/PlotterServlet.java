package org.chapinhall.ohlc_plotter;

import com.google.gson.Gson;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class PlotterServlet extends HttpServlet {
        public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
            Gson gson = new Gson();
            PlotlyOHLCArgs ret = new PlotlyOHLCArgs(new PlotlyOHLCData(), "NAT PTC");
            String json = gson.toJson(ret);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().write(json);
        }

        private static final String JDBC_CONNECTION_URI = "java:comp/env/jdbc/fcda";
        private static final String SQL_QUERY_STRING = "";

        public static ArrayList<DataRow> loadData() throws SQLDataException, SQLException, NamingException {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup(JDBC_CONNECTION_URI);
            Connection con = ds.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL_QUERY_STRING);

            // iterate through results
            while (rs.next()) {
                Date date = rs.getDate("DATE");
                int entries = rs.getInt("ENTRIES");
                int exits = rs.getInt("EXITS");

                DataRow row = new DataRow(date);
                row.second.add(entries);
                row.second.add(exits);
            }
            rs.close();
            st.close();
            con.close();
            return null;
        }

        public static PlotlyOHLCData processData(ArrayList<DataRow> data) {
            return null;
        }
}
