package org.dmp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danielpm.dev
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionSQLite {

    // Oracle connection
    private static Connection conn;
    private final static String DRIVER = "org.sqlite.JDBC";
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static Connection getConexion() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } else {
                System.out.println("There is already an active DB connection...");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionOra.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Database Driver Class Not Found", ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionOra.class.getName()).log(Level.SEVERE, null, ex);
            System.out.printf("AN EXCEPTION HAS OCCURRED:%n");
            System.out.printf("Message: %s %n", ex.getMessage());
            System.out.printf("SQL State: %s %n", ex.getSQLState());
            System.out.printf("Error Code: %s %n", ex.getErrorCode());
            throw new RuntimeException("Database Connection Error", ex);
        }
        return conn;
    }

    public static void closeConexion() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConexionOra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

