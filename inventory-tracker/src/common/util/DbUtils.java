package common.util;

import core.model.exception.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtils {
    public static void safeClose(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                ExceptionHandler.TO_LOG.reportException(e, "Unable to close connection");
            }
        }
    }

    public static void safeClose(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                ExceptionHandler.TO_LOG.reportException(e, "Unable to close statement");
            }
        }
    }

    public static void safeClose(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                ExceptionHandler.TO_LOG.reportException(e, "Unable to close result set");
            }
        }
    }
}
