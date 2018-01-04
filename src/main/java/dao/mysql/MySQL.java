package dao.mysql;

import constants.ConfigurationConstants;
import org.apache.log4j.Logger;

import java.sql.*;

public abstract class MySQL {
    protected static Connection connection = null;

    final static Logger logger = Logger.getLogger(MySQL.class);

    protected static Connection getConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            logger.error("MySQL Driver not Found!" + ex);
        }

        logger.info("MySQL Driver Registered");


        try {
            connection = DriverManager.getConnection(ConfigurationConstants.DB_URL, ConfigurationConstants.DB_USERNAME, ConfigurationConstants.DB_PASSWORD);
        } catch (SQLException ex) {
            logger.error("Connection failed!" + ex);
        }

        if (connection != null) {
            logger.info("Successfully connected to MySQL database");
        } else {
            logger.info("Connection failed!");
        }
        return connection;
    }

    /**
     * @param con
     * @param stmt
     * @param rs   To close statements, result sets and Connection
     */
    public static void close(Connection con, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     * @param con
     * @param stmt To close statements and Connection
     */
    public static void close(Connection con, Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     * @param con
     * @param pstmt
     * @param rs    To Close PreparedStatement and Connection
     */
    public static void close(Connection con, PreparedStatement pstmt,
                             ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     * @param con
     * @param pstmt
     * @param rs    To Close PreparedStatement and Connection
     */
    public static void close(Connection con, PreparedStatement pstmt, PreparedStatement pstmt2,
                             ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (pstmt2 != null) {
                pstmt2.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     * @param con To close connection
     */
    public static void closeConnection(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     * @param conn
     * @throws SQLException To Commit and Close the connection
     */
    public static void commitAndClose(Connection conn) throws SQLException {
        conn.commit();
        conn.close();
    }


}
