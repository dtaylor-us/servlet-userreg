package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;

public class MyListener implements ServletContextListener {
    ServletContext ctx;
    Connection con;
    Statement s;
    PreparedStatement ps;
    ResultSet rs;
    int count;

    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:2424/docker_env",
                    "root",
                    "password");

            s = con.createStatement();


            //fetching pageviews value from table counter
            rs = s.executeQuery("select pageview from counter");
            while (rs.next()) {
                count = rs.getInt(1);
            }

            ctx = sce.getServletContext();
            ctx.setAttribute("pcount", count);


        } catch (Exception e) {

        }


    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ctx = sce.getServletContext();
            count = (Integer) ctx.getAttribute("pcount");
            ps = con.prepareStatement("update counter set pcount='" + count + "'");
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

