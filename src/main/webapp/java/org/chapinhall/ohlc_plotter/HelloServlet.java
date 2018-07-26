package org.chapinhall.ohlc_plotter;

import javax.servlet.http.*;
import java.io.*;

public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        httpServletResponse.setContentType("text/html");
        PrintWriter w = httpServletResponse.getWriter();
        File f = new File("./src/main/webapp/java/org/chapinhall/ohlc_plotter/test_embed_2.html");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        while (line != null) {
            w.println(line);
            line = br.readLine();
        }
        br.close();
        w.flush();
        w.close();
    }
}
