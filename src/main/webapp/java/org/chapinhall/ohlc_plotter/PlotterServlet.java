package org.chapinhall.ohlc_plotter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class PlotterServlet extends HttpServlet {
        public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM").create();
            //try {
                //List<DataRow> data = loadData();
                List<DataRow> data = generateRandomData();

                PlotlyOHLCData processedData = processData(data);
                PlotlyOHLCArgs ret = new PlotlyOHLCArgs(processedData, "NAT PTC");
                String json = gson.toJson(ret);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(json);
            //}
            /*
            catch (SQLException e) {
                log("SQL Error: ", e);
            } catch (NamingException e) {
                log("Naming Error: ", e);
            }*/
        }

        private static final String JDBC_CONNECTION_URI = "java:comp/env/jdbc/fcda";
        private static final String SQL_QUERY_STRING = "";

        private static List<DataRow> loadData() throws SQLException, NamingException {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup(JDBC_CONNECTION_URI);
            Connection con = ds.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL_QUERY_STRING);

            List<DataRow> res = new ArrayList<DataRow>();
            // iterate through results
            while (rs.next()) {
                Date date = rs.getDate("DATE");
                int entries = rs.getInt("ENTRIES");
                int exits = rs.getInt("EXITS");

                DataRow row = new DataRow(date);
                row.second.put("entries", entries);
                row.second.put("exits", exits);
                res.add(row);
            }
            rs.close();
            st.close();
            con.close();

            return res;
        }

        private static Random r = new Random();
        private static int generateRandom(int min, int max) {
            return r.nextInt(max - min + 1) + min;
        }
        private static List<DataRow> generateRandomData() {
            List<DataRow> res = new ArrayList<DataRow>();
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            // generate 1000 weeks of data
            for (int i = 0; i < 1000; i++) {
                DataRow row = new DataRow(c.getTime());
                row.second.put("entries", generateRandom(0, 100));
                row.second.put("exits", generateRandom(0, 100));
                res.add(row);
                c.add(Calendar.DATE, 7);
            }
            return res;
        }

        private static void processWeekly(List<DataRow> data) {
            // add net and cumsum data to weekly data
            int weekCumSum = 0;
            for (DataRow row : data) {
                row.second.put("net", row.second.get("entries") - row.second.get("exits"));
                weekCumSum += row.second.get("net");
                row.second.put("cumsum", weekCumSum);
            }
            // [ entries exits net cumsum]
        }

        private static PlotlyOHLCData processData(List<DataRow> data) {
            // [ entries exits net cumsum]
            processWeekly(data);
            // TBD?
            int initialValue = 0;
            // we assume that the entries are already in date-sorted order
            List<DataRow> monthly = new ArrayList<DataRow>();
            int prevMonth = -1;
            int prevMax = Integer.MIN_VALUE;
            int prevMin = Integer.MAX_VALUE;
            int cumSum = 0;
            for (DataRow row : data) {
                int year = row.first.get(Calendar.YEAR);
                int month = row.first.get(Calendar.MONTH);
                int weeklyNet = row.second.get("net");
                int weeklyCumSum = row.second.get("cumsum");
                if (month != prevMonth) {
                    // create a new row at the start of the month
                    DataRow newRow = new DataRow(new GregorianCalendar(year, month, 1).getTime());
                    newRow.second.put("net", weeklyNet);
                    monthly.add(newRow);

                    // legit new month, add the min and max to the previous row
                    if (prevMonth != -1) {
                        // if not the first row ever
                        DataRow prevRow = monthly.get(monthly.size() - 2);
                        prevRow.second.put("min", initialValue + prevMin);
                        prevRow.second.put("max", initialValue + prevMax);
                        prevRow.second.put("cumsum", initialValue + cumSum);
                        prevRow.second.put("close", initialValue + cumSum);
                        monthly.get(monthly.size() - 1).second.put("open", initialValue + cumSum);
                    } else {
                        // if is the first row ever
                        monthly.get(monthly.size() - 1).second.put("open", initialValue);
                    }
                    prevMax = weeklyCumSum;
                    prevMin = weeklyCumSum;

                    // set the previous month as the current block start
                    prevMonth = month;
                } else {
                    // if this month is still the same as the previous month
                    if (weeklyCumSum > prevMax) {
                        prevMax = weeklyCumSum;
                    }

                    if (weeklyCumSum < prevMin) {
                        prevMin = weeklyCumSum;
                    }

                    // add to the existing row for the current month
                    DataRow currentSumRow = monthly.get(monthly.size() - 1);
                    Integer net = currentSumRow.second.get("net");
                    currentSumRow.second.put("net", net + row.second.get("net"));
                }
                cumSum += weeklyNet;
            }
            // add in the values for the last row
            DataRow lastRow = monthly.get(monthly.size() - 1);
            lastRow.second.put("min", initialValue + prevMin);
            lastRow.second.put("max", initialValue + prevMax);
            lastRow.second.put("cumsum", initialValue + cumSum);
            lastRow.second.put("close", initialValue + cumSum);

            // pass 2: include endpoints in min and max calculations
            for (DataRow row: monthly) {
                int min = row.second.get("min");
                int max = row.second.get("max");
                int close = row.second.get("close");
                int open = row.second.get("open");
                row.second.put("min", Math.min(min, Math.min(open, close)));
                row.second.put("max", Math.max(max, Math.max(open, close)));
            }

            return new PlotlyOHLCData(monthly);
        }
}
