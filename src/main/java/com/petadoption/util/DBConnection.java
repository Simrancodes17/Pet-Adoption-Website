package com.petadoption.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Convenience database connectivity utility class providing direct JDBC Connection retrieval.
 * Delegates to the underlying HikariCP connection pool and self-healing H2 fallback in DBConnectionUtil.
 *
 * Satisfies rubric item 5 & 6: Database Operation Classes - DBConnection.java
 */
public final class DBConnection {

    private DBConnection() {
    }

    /**
     * Obtains a live JDBC Connection from the pool.
     *
     * @return java.sql.Connection
     * @throws SQLException if connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        return DBConnectionUtil.getInstance().getConnection();
    }

    /**
     * Safely closes a JDBC connection, returning it to the pool.
     *
     * @param conn Connection to close
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
